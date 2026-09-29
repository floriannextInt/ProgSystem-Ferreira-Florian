public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {

		memory[offset] = (byte) (value >> 24);
		memory[offset + 1] = (byte) (value >> 16);
		memory[offset + 2] = (byte) (value >> 8);
		memory[offset + 3] = (byte) (value);

		return 4;
    }
	

    public static int readInt(byte[] memory, int offset) {

		int b0 = (memory[offset] & 0xFF) << 24;
		int b1 = (memory[offset + 1] & 0xFF) << 16;
		int b2 = (memory[offset + 2] & 0xFF) << 8;
		int b3 = (memory[offset + 3] & 0xFF);

		return b0 | b1 | b2 | b3;
	}
	

    public static int writeShort(byte[] memory, int offset, short value) {
		memory[offset] = (byte) (value >> 8);
		memory[offset + 1] = (byte) (value);
        return 2;
    }
	

    public static short readShort(byte[] memory, int offset) {
		int b0 = (memory[offset] & 0xFF) << 8;
		int b1 = (memory[offset + 1] & 0xFF);

		return (short) (b0 | b1);
    }
	
	
	public static int writeLong(byte[] memory, int offset, long value) {
		memory[offset] = (byte) (value >> 56);
		memory[offset + 1] = (byte) (value >> 48);
		memory[offset + 2] = (byte) (value >> 40);
		memory[offset + 3] = (byte) (value >> 32);
		memory[offset + 4] = (byte) (value >> 24);
		memory[offset + 5] = (byte) (value >> 16);
		memory[offset + 6] = (byte) (value >> 8);
		memory[offset + 7] = (byte) (value);

		return 8;
    }
	

    public static long readLong(byte[] memory, int offset) {
		
		long b0 = (memory[offset] & 0xFFL) << 56;
		long b1 = ((long) memory[offset + 1] & 0xFFL) << 48;
		long b2 = ((long) memory[offset + 2] & 0xFFL) << 40;
		long b3 = ((long) memory[offset + 3] & 0xFFL) << 32;
		long b4 = ((long) memory[offset + 4] & 0xFFL) << 24;
		long b5 = ((long) memory[offset + 5] & 0xFFL) << 16;
		long b6 = ((long) memory[offset + 6] & 0xFFL) << 8;
		long b7 = ((long) memory[offset + 7] & 0xFFL);

		return b0 | b1 | b2 | b3 | b4 | b5 | b6 | b7;
	}
	
	
	public static int writeString(byte[] memory, int offset, String str, int maxLength) {
		
		if (maxLength < 0 || maxLength > 16) {
			throw new IllegalArgumentException("maxLength doit etre entre 0 et 16");
		}

		byte[] text = str.getBytes(StandardCharsets.UTF_8);
		
		if (maxLength > 0) memory[offset] = text.length > 0 ? text[0] : 0;
		if (maxLength > 1) memory[offset + 1] = text.length > 1 ? text[1] : 0;
		if (maxLength > 2) memory[offset + 2] = text.length > 2 ? text[2] : 0;
		if (maxLength > 3) memory[offset + 3] = text.length > 3 ? text[3] : 0;
		if (maxLength > 4) memory[offset + 4] = text.length > 4 ? text[4] : 0;
		if (maxLength > 5) memory[offset + 5] = text.length > 5 ? text[5] : 0;
		if (maxLength > 6) memory[offset + 6] = text.length > 6 ? text[6] : 0;
		if (maxLength > 7) memory[offset + 7] = text.length > 7 ? text[7] : 0;
		if (maxLength > 8) memory[offset + 8] = text.length > 8 ? text[8] : 0;
		if (maxLength > 9) memory[offset + 9] = text.length > 9 ? text[9] : 0;
		if (maxLength > 10) memory[offset + 10] = text.length > 10 ? text[10] : 0;
		if (maxLength > 11) memory[offset + 11] = text.length > 11 ? text[11] : 0;
		if (maxLength > 12) memory[offset + 12] = text.length > 12 ? text[12] : 0;
		if (maxLength > 13) memory[offset + 13] = text.length > 13 ? text[13] : 0;
		if (maxLength > 14) memory[offset + 14] = text.length > 14 ? text[14] : 0;
		if (maxLength > 15) memory[offset + 15] = text.length > 15 ? text[15] : 0;

		return maxLength;
	}

	public static String readString(byte[] memory, int offset, int maxLength) {
		
		if (maxLength < 0 || maxLength > 16) {
			throw new IllegalArgumentException("maxLength doit être entre 0 et 16");
		}
		
		int position = 0;

		byte p0 = maxLength > 0 ? memory[offset] : 0;
		if (p0 != 0) position = 1;

		byte p1 = maxLength > 1 && position == 1 ? memory[offset + 1] : 0;
		if (p1 != 0) position = 2;

		byte p2 = maxLength > 2 && position == 2 ? memory[offset + 2] : 0;
		if (p2 != 0) position = 3;

		byte p3 = maxLength > 3 && position == 3 ? memory[offset + 3] : 0;
		if (p3 != 0) position = 4;

		byte p4 = maxLength > 4 && position == 4 ? memory[offset + 4] : 0;
		if (p4 != 0) position = 5;

		byte p5 = maxLength > 5 && position == 5 ? memory[offset + 5] : 0;
		if (p5 != 0) position = 6;

		byte p6 = maxLength > 6 && position == 6 ? memory[offset + 6] : 0;
		if (p6 != 0) position = 7;

		byte p7 = maxLength > 7 && position == 7 ? memory[offset + 7] : 0;
		if (p7 != 0) position = 8;

		byte p8 = maxLength > 8 && position == 8 ? memory[offset + 8] : 0;
		if (p8 != 0) position = 9;

		byte p9 = maxLength > 9 && position == 9 ? memory[offset + 9] : 0;
		if (p9 != 0) position = 10;

		byte p10 = maxLength > 10 && position == 10 ? memory[offset + 10] : 0;
		if (p10 != 0) position = 11;

		byte p11 = maxLength > 11 && position == 11 ? memory[offset + 11] : 0;
		if (p11 != 0) position = 12;

		byte p12 = maxLength > 12 && position == 12 ? memory[offset + 12] : 0;
		if (p12 != 0) position = 13;

		byte p13 = maxLength > 13 && position == 13 ? memory[offset + 13] : 0;
		if (p13 != 0) position = 14;

		byte p14 = maxLength > 14 && position == 14 ? memory[offset + 14] : 0;
		if (p14 != 0) position = 15;

		byte p15 = maxLength > 15 && position == 15 ? memory[offset + 15] : 0;
		if (p15 != 0) position = 16;

		byte[] text = {p0, p1, p2, p3, p4, p5, p6, p7,
				p8, p9, p10, p11, p12, p13, p14, p15};

		return new String(text, 0, position, StandardCharsets.UTF_8);
	}
}