package mybridge2.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author zKF36895
 * @version [版本号, 2011-5-18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class ExecuteCmdlineUtil {
	Logger logger = Logger.getLogger(this.getClass());

	InputStream errorStream = null;

	InputStream InputStream = null;

	OutputStream outputStream = null;

	public InputStream getErrorStream() {
		return errorStream;
	}

	public void setErrorStream(InputStream errorStream) {
		this.errorStream = errorStream;
	}

	public InputStream getInputStream() {
		return InputStream;
	}

	public void setInputStream(InputStream inputStream) {
		InputStream = inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * 执行shell脚本入口
	 * 
	 * @param cmdarray
	 *            cmdarray
	 * @return true or false;
	 */
	public boolean exec(String[] cmdarray) {
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		boolean executeResult = false;
		// 后续锁定该执行的文件
		try {
			synchronized (runtime) {
				process = runtime.exec(cmdarray);
				process.waitFor();
			}
			errorStream = process.getErrorStream();
			InputStream = process.getInputStream();
			outputStream = process.getOutputStream();
			// 如果脚本执行成功
			if (process.exitValue() == 0) {
				executeResult = true;
			} else {
				// 后续操作correctStream,同时记录error日志
				executeResult = false;
				logger.error("commnad execute fail,the execute command is \n"
						+ cmdarray);
			}
		} catch (IOException e) {
			e.printStackTrace();
			// return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return executeResult;
	}
}
