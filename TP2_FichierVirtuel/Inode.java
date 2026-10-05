public class Inode {

    private MemoryManager memoryManager;
    private int inodeNumber;

    public static final int INODE_SIZE = 128;
    public static final int DIRECT_POINTERS = 10;

    public Inode(
            MemoryManager memoryManager,
            int inodeNumber) {

        this.memoryManager = memoryManager;
        this.inodeNumber = inodeNumber;
    }

    public int getInodeOffset() {
        int offset = MemoryManager.INODE_TABLE_OFFSET + (inodeNumber * MemoryManager.INODE_SIZE);
        return offset;
    }

    public int getFileType() {
        byte[] mem = memoryManager.getFilesystemMemory();
		return Utils.readInt(mem, getInodeOffset() + 4);
    }

    public int getFileSize() {
        byte[] mem = memoryManager.getFilesystemMemory();
		return Utils.readInt(mem, getInodeOffset() + 8);
    }

    public int[] getDirectPointers() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] pointers =
                new int[DIRECT_POINTERS];

		byte[] mem = memoryManager.getFilesystemMemory();
		for (int i = 0; i < DIRECT_POINTERS; i++) {
			pointers[i] = Utils.readInt(memory, getInodeOffset() + 28 + (i * 4));
		}
		
        return pointers;
    }
	
	public void writeToMemory(
        int fileType,
        int fileSize,
        long creationTime,
        long modificationTime,
        int[] directPointers,
        int indirectPointer,
        short permissions,
        int linkCount) {

		byte[] memory =
				memoryManager.getFilesystemMemory();

		int offset = getInodeOffset();

		offset += Utils.writeInt(memory, offset, inodeNumber);
		offset += Utils.writeInt(memory, offset, fileType);
		offset += Utils.writeInt(memory, offset, fileSize);
		offset += Utils.writeLong(memory, offset, creationTime);
		offset += Utils.writeLong(memory, offset, modificationTime);
		
		for (int i = 0; i < DIRECT_POINTERS; i++) {
			int value = 0;
			if (directPointers != null) {
				if (i < directPointers.length) {
					value = directPointers[i];
				}
			}
			offset += Utils.writeInt(memory, offset, value);
		}
		
		offset += Utils.writeInt(memory, offset, indirectPointer);
		offset += Utils.writeShort(memory, offset, permissions);
		offset += Utils.writeInt(memory, offset, linkCount);
		
	}
}