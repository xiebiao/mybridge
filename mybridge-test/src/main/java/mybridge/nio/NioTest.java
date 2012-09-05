package mybridge.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
			try {
				ByteBuffer bb = ByteBuffer.allocate((int)fc.size());
				fc.read(bb);
				bb.flip();
				byte[] bytes = new byte[bb.remaining()];
				bb.get(bytes);
				System.out.println("bufferRead:	" + new String(bytes));
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
