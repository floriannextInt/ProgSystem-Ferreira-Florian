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
		return Utils.readInt(mem, getInodeOffset()) + 4;
    }

    public int getFileSize() {
        byte[] mem = memoryManager.getFilesystemMemory();
		return Utils.readInt(mem, getInodeOffset()) + 8;
    }

    public int[] getDirectPointers() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] pointers =
                new int[DIRECT_POINTERS];

		byte[] mem = memoryManager.getFilesystemMemory();
		for (int i = 0; i < DIRECT_POINTERS; i++) {
			pointers[i] = Utils.readInt(memory, getInodeOffset() + 12 + (i * 4));
		}
		
        return pointers;
    }
}