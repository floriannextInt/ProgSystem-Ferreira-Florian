import java.util.*;

public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {
        this.memoryManager =
                new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory =
                memoryManager.getFilesystemMemory();
				
        for (int i = 0; i < MemoryManager.MAX_INODES; i++) {
            int offset = MemoryManager.INODE_TABLE_OFFSET + (i * Inode.INODE_SIZE);
            int resultat = Utils.readInt(memory, offset);
            if (resultat != i) {
                return i;
            }
        }
        return -1;
    }

    public boolean createFile(
        String directory,
        String filename) {

		int inodeNum = allocateInode();

		if (inodeNum == -1) {
			return false;
		}

		Inode inode =
				new Inode(memoryManager, inodeNum);

		int fileType = 1;
		int fileSize = 0;

		long creationTime =
				System.currentTimeMillis();

		long modificationTime =
				creationTime;

		int[] directPointers =
				new int[Inode.DIRECT_POINTERS];

		int indirectPointer = 0;

		short permissions = 0;

		int linkCount = 1;

		inode.writeToMemory(
				fileType,
				fileSize,
				creationTime,
				modificationTime,
				directPointers,
				indirectPointer,
				permissions,
				linkCount);

		return true;
	}


    public MemoryManager getMemoryManager() {
        return memoryManager;
    }
}