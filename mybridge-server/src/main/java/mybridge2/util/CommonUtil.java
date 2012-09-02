package mybridge2.util;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 公共处理方法，放在这里，给其他模块调用
 * 
 * @author t00108272
 */
public class CommonUtil {
	private static final Logger logger = Logger.getLogger(CommonUtil.class);

	/**
	 * 将byte数组中的某些字段转化成int
	 * 
	 * @param data
	 * @param offset
	 * @param len
	 * @return
	 */
	public static int toInt(byte[] data, int offset, int len) {
		int b = data[offset] & 0xff;
		for (int i = 1; i < len; i++) {
			b = b << 8 | data[offset + i] & 0xff;
		}
		return b;
	}

	/**
	 * 将int转化成3为的byte数组,并且该int是字节倒序的
	 * 
	 * @return
	 */
	public static byte[] to3Byte(int length) {
		byte[] value = new byte[3];
		value[0] = (byte) (length & 0xff);
		value[1] = (byte) (length >> 8 & 0xff);
		value[2] = (byte) (length >> 16 & 0xff);

		return value;
	}

	/**
	 * 将一个string转化成为一个占32个位字节长度的byte数组。
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] to32Byte(String str) {
		byte[] value = new byte[32];

		byte[] valueTmp = str.getBytes();

		for (int i = 0; i < valueTmp.length; i++) {
			value[i] = valueTmp[i];
		}

		for (int j = valueTmp.length; j < value.length; j++) {
			value[j] = 0x00;
		}
		return value;
	}

	/**
	 * 合法的IP地址包含了xxx.xxx.xxx.xxx
	 * 
	 * @param rule
	 * @return
	 */
	public static boolean isIpAddress(String ipAddress) {
		if (null == ipAddress) {
			return false;
		}

		// 判断IP中是否都是合法的数字
		StringTokenizer stTokens = new StringTokenizer(ipAddress, ".");
		if (stTokens.countTokens() != 4) {
			return false;
		}

		int ipVal = 0;
		try {
			for (int i = 0; i < 4; i++) {
				ipVal = Integer.parseInt(stTokens.nextToken());

				if (ipVal < 0 || ipVal > 255) {
					return false;
				}
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * 检查字符串的首字符为字母
	 * 
	 * @param s
	 * @return
	 */
	public static boolean checkFirstChar(String s) {
		char c = s.charAt(0);
		if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断字符串是否是字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChar(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 将byte的第index位开始的长度为8为的byte数组转化成为一个long型数据
	 * 
	 * @param bb
	 * @param index
	 * @return
	 */
	public static long getLong(byte[] bb, int index) {
		return ((long) bb[index + 0] & 0xff) << 56
				| ((long) bb[index + 1] & 0xff) << 48
				| ((long) bb[index + 2] & 0xff) << 40
				| ((long) bb[index + 3] & 0xff) << 32
				| ((long) bb[index + 4] & 0xff) << 24
				| ((long) bb[index + 5] & 0xff) << 16
				| ((long) bb[index + 6] & 0xff) << 8
				| ((long) bb[index + 7] & 0xff) << 0;
	}

	/**
	 * 将一个long型的数据转化到byte数组，从byte的第index位开始
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putLong(byte[] bb, long x, int index) {
		bb[index + 0] = (byte) (x >> 56);
		bb[index + 1] = (byte) (x >> 48);
		bb[index + 2] = (byte) (x >> 40);
		bb[index + 3] = (byte) (x >> 32);
		bb[index + 4] = (byte) (x >> 24);
		bb[index + 5] = (byte) (x >> 16);
		bb[index + 6] = (byte) (x >> 8);
		bb[index + 7] = (byte) (x >> 0);
	}

	/**
	 * 将short转化成byte
	 * 
	 * @param b
	 * @param s
	 * @param index
	 */
	public static void putShort(byte b[], short s, int index) {
		b[index] = (byte) (s >> 8);
		b[index + 1] = (byte) (s >> 0);
	}

	/**
	 * 将byte转化成为short
	 * 
	 * @param b
	 * @param index
	 * @return
	 */
	public static short getShort(byte[] b, int index) {
		return (short) (b[index] << 8 | b[index + 1] & 0xff);
	}

	/**
	 * 将int转化成byte
	 * 
	 * @param bb
	 * @param x
	 * @param index
	 */
	public static void putInt(byte[] bb, int x, int index) {
		bb[index + 0] = (byte) (x >> 24);
		bb[index + 1] = (byte) (x >> 16);
		bb[index + 2] = (byte) (x >> 8);
		bb[index + 3] = (byte) (x >> 0);
	}

	/**
	 * 将byte转化成为short
	 * 
	 * @param bb
	 * @param index
	 * @return
	 */
	public static int getInt(byte[] bb, int index) {
		return ((bb[index + 0] & 0xff) << 24 | (bb[index + 1] & 0xff) << 16
				| (bb[index + 2] & 0xff) << 8 | (bb[index + 3] & 0xff) << 0);
	}

	/**
	 * 将指定byte数组以16进制的形式打印到日志中
	 */
	public static void printHexString(String header, byte[] b) {
		StringBuilder sb = new StringBuilder();
		sb.append(header + "[");
		sb.append(System.getProperty("line.separator"));

		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase() + " ");

			if ((i + 1) % 16 == 0) {
				sb.append(System.getProperty("line.separator"));
			}
		}
		sb.append("]");

		logger.debug(sb.toString());
	}
}
