public class MemoryManager {

	public static final int BLOCK_SIZE = 512;
	public static final int TOTAL_MEMORY = 1024 * 1024;
	public static final int NUM_BLOCKS = TOTAL_MEMORY / BLOCK_SIZE;

	public static final int SUPERBLOCK_OFFSET = 0;
	public static final int BITMAP_OFFSET = BLOCK_SIZE;
	public static final int INODE_TABLE_OFFSET = 2 * BLOCK_SIZE;
	public static final int DATA_OFFSET = 129 * BLOCK_SIZE;

	public static final int INODE_SIZE = 128;
	public static final int INODE_TABLE_SIZE = DATA_OFFSET - INODE_TABLE_OFFSET;
	public static final int MAX_INODES = INODE_TABLE_SIZE / INODE_SIZE;
	public static final int FIRST_DATA_BLOCK = DATA_OFFSET / BLOCK_SIZE;

	private byte[] memory;

	public MemoryManager() {
		this.memory = new byte[TOTAL_MEMORY];
		initializeFilesystem();
	}

	private void initializeFilesystem() {

		writeSuperblock();

		memory[BITMAP_OFFSET] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 1] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 2] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 3] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 4] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 5] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 6] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 7] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 8] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 9] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 10] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 11] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 12] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 13] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 14] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 15] = (byte) 0xFF;
		memory[BITMAP_OFFSET + 16] = (byte) 0x01;
	}

	private void writeSuperblock() {

		Utils.writeString(
                memory,
                SUPERBLOCK_OFFSET,
                "MYFS1.0",
                16);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 16,
                BLOCK_SIZE);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 20,
                TOTAL_MEMORY);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 24,
                NUM_BLOCKS);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 28,
                MAX_INODES);
    }
	}

	public byte[] getFilesystemMemory() {
		return memory;
	}
	
	public boolean setBlockUsed(
        int blockNumber,
        boolean used) {

    if (blockNumber < 0 ||
        blockNumber >= NUM_BLOCKS) {
        return false;
    }

    int byteIndex = blockNumber / 8;
    int bitPosition = blockNumber % 8;
    int offset = BITMAP_OFFSET + byteIndex;
	int masque = 1;

	if (used) {
		current = (byte) (current | (1 << bitPosition));
	} else if (!used) {
		current = (byte) (current & ~(1 << bitPosition));
	}

    return true;
	}

	public int isBlockUsed(int blockNumber) {

		if (blockNumber < 0 ||
			blockNumber >= NUM_BLOCKS) {
			return -1;
		}

		int byteIndex = blockNumber / 8;
		int bitPosition = blockNumber % 8;
		int offset = BITMAP_OFFSET + byteIndex;
		
		return ((memory[offset] >> bitPosition) & 1) == 1;
	}

	public int allocateBlock() {
		
	for (int i = 129; i < NUM_BLOCKS; i++) {
		if (!isBlockUsed(i)) {
			setBlockUsed(i, true);
				return i;
		}
	}
		return -1;
	}
}


//return (data >> byte.position) & 0x1;

//dans la classe Inode : 

// TODO
// memoryManager.INODE_TABLE_SIZE + (inodeNumber * INODE_SIZE);

// TODO
// byte[] mem = memoryManager.getFilesysteme();
// return Utils.readInt(mem, getInodeOffset) + 4;

// TODO
// byte[] mem = memoryManager.getFilesysteme();
// return Utils.readInt(mem, getInodeOffset) + 8;

// TODO
// byte[] mem = memoryManager.getFilesysteme();
// 