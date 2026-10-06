import java.util.*;
import java.io.FileReader;
import java.io.IOException;

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
	
	public boolean writeFile(
        int inodeNum,
        byte[] data) {

		int blocksNeeded =
				(data.length
				+ MemoryManager.BLOCK_SIZE - 1)
				/ MemoryManager.BLOCK_SIZE;

		if (blocksNeeded > Inode.DIRECT_POINTERS) {
			return false;
		}

		int[] blockPointers =
				new int[Inode.DIRECT_POINTERS];

		for (int i = 0; i < blocksNeeded; i++) {
			int blockNumber = memoryManager.allocateBlock();
			if (blockNumber == -1) {
				return false;
			}
			blockPointers[i] = blockNumber;
		}

		byte[] memory =
				memoryManager.getFilesystemMemory();

		int bytesRemaining =
				data.length;

		int dataSrcOffset = 0;

		for (int i = 0; i < blocksNeeded; i++) {
			int bytesToCopy = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
			int blockNumber = blockPointers[i];
			int blockOffset = blockNumber * MemoryManager.BLOCK_SIZE;
			for (int j = 0; j < bytesToCopy; j++) {
				memory[blockOffset + j] =
						data[dataSrcOffset + j];
			}

			bytesRemaining -= bytesToCopy;
			dataSrcOffset += bytesToCopy;
		}

		Inode inode = new Inode(memoryManager, inodeNum);
		inode.writeToMemory(1, data.length, System.currentTimeMillis(), System.currentTimeMillis(), blockPointers, 0, (short) 0, 1);

		return true;
	}
	
	public byte[] readFile(int inodeNum) {

		Inode inode =
				new Inode(memoryManager, inodeNum);

		int fileSize =
				inode.getFileSize();

		if (fileSize == 0) {
			return new byte[0];
		}

		byte[] fileData =
				new byte[fileSize];

		byte[] memory =
				memoryManager.getFilesystemMemory();

		int[] blockPointers =
				inode.getDirectPointers();

		int bytesrestant = fileSize;

		int dataDestOffset = 0;

		int blocksNeeded = (fileSize + MemoryManager.BLOCK_SIZE - 1) / MemoryManager.BLOCK_SIZE;

		for (int i = 0; i < blocksNeeded; i++) {

			int bytesACopier = Math.min(bytesrestant, MemoryManager.BLOCK_SIZE);

			int blockNumber = blockPointers[i];

			int blockOffset = blockNumber * MemoryManager.BLOCK_SIZE;

			for (int j = 0; j < bytesACopier; j++) {
				fileData[dataDestOffset + j] =
						memory[blockOffset + j];
			}
			bytesrestant -= bytesACopier;
			dataDestOffset += bytesACopier;
		}
		return fileData;
	}
	
	public boolean deleteFile(int inodeNum) {

		Inode inode = new Inode(memoryManager, inodeNum);

		int fileSize = inode.getFileSize();

		int blocksNeeded =
				(fileSize
				+ MemoryManager.BLOCK_SIZE - 1)
				/ MemoryManager.BLOCK_SIZE;

		int[] blockPointers =
				inode.getDirectPointers();

		for (int i = 0; i < blocksNeeded; i++) {
			int blockNumber = blockPointers[i];
			memoryManager.setBlockUsed(blockNumber, false);
		}

		int[] emptyPointers = new int[Inode.DIRECT_POINTERS];
		inode.writeToMemory(-1, 0, 0L, 0L, emptyPointers, 0, (short) 0, 0);

		return true;
	}
	
	public boolean writeExternalFile(String filename) {

		StringBuilder builder = new StringBuilder();
		try (FileReader reader = new FileReader(filename)) {
			char[] buffer = new char[1024];
			int count;
			while ((count = reader.read(buffer)) != -1) {
				builder.append( buffer, 0, count);
			}
		} catch (IOException e) {
			return false;
		}
		byte[] data = builder.toString().getBytes();
		if (!createFile("/", "external.txt")) {
			return false;
		}
		return writeFile(0, data);
	}
}