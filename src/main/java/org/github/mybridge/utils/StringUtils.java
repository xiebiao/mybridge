package org.github.mybridge.utils;

public class StringUtils {
	private final static String blank = "    ";

	public static String printHex(byte[] bytes) {
		return printHex(bytes, bytes.length);
	}

	/**
	 * 
	 * <pre>
	 * 74 68 69 73 20 69 73 20     t h i s . i s . 
	 * 61 20 74 65 73 74 20 74     a . t e s t . t 
	 * 65 78 74                  				    e x t
	 * </pre>
	 * 
	 * @param bytes
	 * @param length
	 *            默认为 @param bytes 的长度
	 * @return
	 */
	public static String printHex(byte[] bytes, int length) {

		if (bytes == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (length > bytes.length) {
			length = bytes.length;
		}
		StringBuffer sb = new StringBuffer();
		int fieldLength = 8;
		int rows = length / fieldLength;
		int index = 0;
		for (int i = 0; (i < rows) && (index < length); i++) {
			int pointer = index;
			// print hexadecimal
			for (int filed = 0; filed < fieldLength; filed++) {
				appendIntegerHex(sb, bytes[pointer]);
				pointer++;
			}
			sb.append(blank);
			// print text
			for (int filed = 0; filed < fieldLength; filed++) {
				appendChar(sb, bytes[index]);
				index++;
			}
			sb.append("\n");
		}
		// print last
		for (int filed = index; filed < length; filed++) {
			appendIntegerHex(sb, bytes[filed]);
		}
		sb.append(blank);
		for (int filed = index; filed < length; filed++) {
			appendChar(sb, bytes[filed]);
		}
		return sb.toString();
	}

	private static void appendIntegerHex(StringBuffer sb, byte b) {
		String hex = Integer.toHexString(b & 0xff);
		if (hex.length() == 1) {
			hex = "0" + hex;
		}
		sb.append(hex + " ");
	}

	private static void appendChar(StringBuffer sb, byte b) {
		if ((b > 32) && (b < 127)) {
			sb.append((char) b + " ");
		} else {
			sb.append(". ");
		}
	}

	public final static void main(String[] args) {
		String str = "this is a test text";
		byte[] bytes = str.getBytes();
		System.out.println(StringUtils.printHex(bytes, bytes.length));
	}
}
