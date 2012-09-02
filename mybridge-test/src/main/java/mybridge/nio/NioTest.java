package mybridge.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

import mybridge2.util.CharsetUtils;

public class NioTest {
	public static void fileRead() {
		File file = new File("/home/xiaog/Desktop/11");
		try {
			FileInputStream in = new FileInputStream(file);
			byte[] b = new byte[1024];
			in.read(b);
		System.out.println(new String(b));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void bufferRead() {
		File file = new File("/home/xiaog/Desktop/11");
		try {
			FileInputStream in = new FileInputStream(file);
			FileChannel fc = in.getChannel();
			ByteBuffer bb = ByteBuffer.allocate(1024);
			try {
				fc.read(bb);
				bb.flip();
				CharBuffer c = bb.asCharBuffer();
				// System.out.print(System.getProperty("file.encoding"));
				StringBuffer db = new StringBuffer();
				while (c.hasRemaining()) {
					db.append(c.get());
				}
				System.out.println(new String(db.toString().getBytes("utf-8"),
						"gbk"));
				System.out.println(CharsetUtils.getCharset(db.toString()));
				fc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		fileRead();
		bufferRead();
	}
}
