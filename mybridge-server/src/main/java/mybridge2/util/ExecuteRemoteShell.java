package mybridge2.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author zKF36895
 * @version [版本号, 2011-5-17]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
//public final class ExecuteRemoteShell {
//	/**
//	 * hostname
//	 */
//	String hostname;
//
//	/**
//	 * username
//	 */
//	String username;
//
//	/**
//	 * hostpwd
//	 */
//	String hostpwd;
//
//	/**
//	 * execmdline
//	 */
//	String execmdline;
//
//	/**
//	 * 标准输出
//	 */
//	InputStream stdout;
//
//	/**
//	 * 执行命令退出的exitcode
//	 */
//	int exitCode;
//
//	/**
//	 * @param hostname
//	 *            the hostname to set
//	 */
//	public void setHostname(String hostname) {
//		this.hostname = hostname;
//	}
//
//	/**
//	 * @param username
//	 *            the username to set
//	 */
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	/**
//	 * @param hostpwd
//	 *            the hostpwd to set
//	 */
//	public void setHostpwd(String hostpwd) {
//		this.hostpwd = hostpwd;
//	}
//
//	/**
//	 * @param execmdline
//	 *            the execmdline to set
//	 */
//	public void setExecmdline(String execmdline) {
//		this.execmdline = execmdline;
//	}
//
//	/**
//	 * <一句话功能简述> <功能详细描述>
//	 * 
//	 * @return [参数说明]
//	 * 
//	 * @return InputStream [返回类型说明]
//	 * @exception throws [违例类型] [违例说明]
//	 * @see [类、类#方法、类#成员]
//	 */
//	public InputStream getStdout() {
//		return stdout;
//	}
//
//	/**
//	 * <一句话功能简述> <功能详细描述>
//	 * 
//	 * @param stdout
//	 *            [参数说明]
//	 * 
//	 * @return void [返回类型说明]
//	 * @exception throws [违例类型] [违例说明]
//	 * @see [类、类#方法、类#成员]
//	 */
//	public void setStdout(InputStream stdout) {
//		this.stdout = stdout;
//	}
//
//	/**
//	 * <一句话功能简述> <功能详细描述>
//	 * 
//	 * @return [参数说明]
//	 * 
//	 * @return int [返回类型说明]
//	 * @exception throws [违例类型] [违例说明]
//	 * @see [类、类#方法、类#成员]
//	 */
//	public int getExitCode() {
//		return exitCode;
//	}
//
//	/**
//	 * <一句话功能简述> <功能详细描述>
//	 * 
//	 * @param exitCode
//	 *            [参数说明]
//	 * 
//	 * @return void [返回类型说明]
//	 * @exception throws [违例类型] [违例说明]
//	 * @see [类、类#方法、类#成员]
//	 */
//	public void setExitCode(int exitCode) {
//		this.exitCode = exitCode;
//	}
//
//	/**
//	 * @param hostname
//	 * @param username
//	 * @param hostpwd
//	 * @param execmdline
//	 */
//	public ExecuteRemoteShell(String hostname, String username, String hostpwd,
//			String execmdline) {
//		this.hostname = hostname;
//		this.username = username;
//		this.hostpwd = hostpwd;
//		this.execmdline = execmdline;
//	}
//
//	public void exec() {
//		ConnectionThread connectionThread = new ConnectionThread();
//		connectionThread.run();
//	}
//
//	/**
//	 * The SSH-2 connection is established in this thread. If we would not use a
//	 * separate thread (e.g., put this code in the event handler of the "Login"
//	 * button) then the GUI would not be responsive (missing window repaints if
//	 * you move the window etc.)
//	 */
//	class ConnectionThread {
//		public void run() {
//			Connection conn = new Connection(hostname);
//			try {
//				/*
//				 * 
//				 * CONNECT AND VERIFY SERVER HOST KEY (with callback)
//				 */
//
//				String[] hostkeyAlgos = database
//						.getPreferredServerHostkeyAlgorithmOrder(hostname);
//
//				if (hostkeyAlgos != null)
//					conn.setServerHostKeyAlgorithms(hostkeyAlgos);
//
//				conn.connect(new AdvancedVerifier());
//
//				/*
//				 * 
//				 * AUTHENTICATION PHASE
//				 */
//
//				boolean enableKeyboardInteractive = true;
//				boolean enableDSA = true;
//				boolean enableRSA = true;
//
//				String lastError = null;
//
//				while (true) {
//					if ((enableDSA || enableRSA)
//							&& conn.isAuthMethodAvailable(username, "publickey")) {
//						// 如果启用了DSA
//						if (enableDSA) {
//							File key = new File(idDSAPath);
//
//							if (key.exists()) {
//								/*
//								 * EnterSomethingDialog esd = new
//								 * EnterSomethingDialog(loginFrame,
//								 * "DSA Authentication", new String[] {
//								 * lastError, "Enter DSA private key password:"
//								 * }, true); esd.setVisible(true);
//								 */
//
//								boolean res = conn.authenticateWithPublicKey(
//										username, key, hostpwd);
//
//								if (res == true)
//									break;
//
//								lastError = "DSA authentication failed.";
//							}
//							enableDSA = false; // do not try again
//						}
//
//						// 如果启用了RSA
//						if (enableRSA) {
//							File key = new File(idRSAPath);
//
//							if (key.exists()) {
//								boolean res = conn.authenticateWithPublicKey(
//										username, key, hostpwd);
//								if (res == true)
//									break;
//
//								lastError = "RSA authentication failed.";
//							}
//							enableRSA = false; // do not try again
//						}
//
//						continue;
//					}
//
//					if (enableKeyboardInteractive
//							&& conn.isAuthMethodAvailable(username,
//									"keyboard-interactive")) {
//						InteractiveLogic il = new InteractiveLogic(lastError,
//								hostpwd);
//
//						boolean res = conn.authenticateWithKeyboardInteractive(
//								username, il);
//
//						if (res == true)
//							break;
//
//						if (il.getPromptCount() == 0) {
//							// aha. the server announced that it supports
//							// "keyboard-interactive", but when
//							// we asked for it, it just denied the request
//							// without sending us any prompt.
//							// That happens with some server
//							// versions/configurations.
//							// We just disable the "keyboard-interactive" method
//							// and notify the user.
//
//							lastError = "Keyboard-interactive does not work.";
//
//							enableKeyboardInteractive = false; // do not try
//																// this again
//						} else {
//							lastError = "Keyboard-interactive auth failed."; // try
//																				// again,
//																				// if
//																				// possible
//						}
//
//						continue;
//					}
//
//					if (conn.isAuthMethodAvailable(username, "password")) {
//						if (hostpwd == null)
//							throw new IOException("Login aborted by user");
//
//						boolean res = conn.authenticateWithPassword(username,
//								hostpwd);
//
//						if (res == true)
//							break;
//
//						lastError = "Password authentication failed."; // try
//																		// again,
//																		// if
//																		// possible
//
//						continue;
//					}
//
//					throw new IOException(
//							"No supported authentication methods available.");
//				}
//
//				/*
//				 * 
//				 * AUTHENTICATION OK. DO SOMETHING.
//				 */
//
//				Session sess = conn.openSession();
//				sess.startShell();
//				sess.execCommand(execmdline);
//
//				stdout = new StreamGobbler(sess.getStdout());
//				BufferedReader br = new BufferedReader(new InputStreamReader(
//						stdout));
//				while (true) {
//					String line = br.readLine();
//					if (line == null)
//						break;
//
//				}
//
//				/* Show exit status, if available (otherwise "null") */
//
//				exitCode = sess.getExitStatus();
//
//				/* Close this session */
//
//				sess.close();
//
//			} catch (IOException e) {
//
//			}
//
//			/*
//			 * 
//			 * CLOSE THE CONNECTION.
//			 */
//			conn.close();
//		}
//	}
//
//	/*
//	 * NOTE: to get this feature to work, replace the "tilde" with your home
//	 * directory, at least my JVM does not understand it. Need to check the
//	 * specs.
//	 */
//
//	static final String knownHostPath = "~/.ssh/known_hosts";
//
//	static final String idDSAPath = "~/.ssh/id_dsa";
//
//	static final String idRSAPath = "~/.ssh/id_rsa";
//
//	KnownHosts database = new KnownHosts();
//
//	/**
//	 * 实现交互式登录逻辑，用于可远程执行shell脚本 The logic that one has to implement if
//	 * "keyboard-interactive" autentication shall be supported.
//	 * 
//	 * @version 1.0 2011-4-28
//	 * @author Name ID
//	 */
//	class InteractiveLogic implements InteractiveCallback {
//		int promptCount = 0;
//
//		String lastError;
//
//		String interactiveInputPwd;
//
//		private InteractiveLogic(String lastError, String interactiveInputPwd) {
//			this.lastError = lastError;
//			this.interactiveInputPwd = interactiveInputPwd;
//		}
//
//		/*
//		 * the callback may be invoked several times, depending on how many
//		 * questions-sets the server sends
//		 */
//
//		public String[] replyToChallenge(String name, String instruction,
//				int numPrompts, String[] prompt, boolean[] echo)
//				throws IOException {
//			String[] result = new String[numPrompts];
//
//			for (int i = 0; i < numPrompts; i++) {
//				/*
//				 * Often, servers just send empty strings for "name" and
//				 * "instruction"
//				 */
//
//				// String[] content = new String[] { lastError, name,
//				// instruction,
//				// prompt[i] };
//				if (lastError != null) {
//					/* show lastError only once */
//					lastError = null;
//				}
//
//				if (interactiveInputPwd == null) {
//					throw new IOException("Login aborted by user");
//				}
//				result[i] = interactiveInputPwd;
//				promptCount++;
//			}
//
//			return result;
//		}
//
//		/*
//		 * We maintain a prompt counter - this enables the detection of
//		 * situations where the ssh server is signaling "authentication failed"
//		 * even though it did not send a single prompt.
//		 */
//
//		public int getPromptCount() {
//			return promptCount;
//		}
//	}
//
//	/**
//	 * 把远程主机添加到konw host，并且确保鉴权通过 This ServerHostKeyVerifier asks the user on
//	 * how to proceed if a key cannot be found in the in-memory database.
//	 * 
//	 */
//	class AdvancedVerifier implements ServerHostKeyVerifier {
//		// 该方法会返回true 连接远程主机确保鉴权通过
//		public boolean verifyServerHostKey(String hostname, int port,
//				String serverHostKeyAlgorithm, byte[] serverHostKey)
//				throws Exception {
//			// final String host = hostname;
//			// final String algo = serverHostKeyAlgorithm;
//
//			// String message;
//
//			/* Check database */
//
//			int result = database.verifyHostkey(hostname,
//					serverHostKeyAlgorithm, serverHostKey);
//
//			switch (result) {
//			case KnownHosts.HOSTKEY_IS_OK:
//				return true;
//
//			case KnownHosts.HOSTKEY_IS_NEW:
//				/* Be really paranoid. We use a hashed hostname entry */
//
//				String hashedHostname = KnownHosts
//						.createHashedHostname(hostname);
//
//				/* Add the hostkey to the in-memory database */
//
//				database.addHostkey(new String[] { hashedHostname },
//						serverHostKeyAlgorithm, serverHostKey);
//
//				/* Also try to add the key to a known_host file */
//
//				try {
//					KnownHosts.addHostkeyToFile(new File(knownHostPath),
//							new String[] { hashedHostname },
//							serverHostKeyAlgorithm, serverHostKey);
//				} catch (IOException ignore) {
//				}
//				return true;
//			case KnownHosts.HOSTKEY_HAS_CHANGED:
//				/*
//				 * message = "WARNING! Hostkey for " + host +
//				 * " has changed!\nAccept anyway?\n";
//				 */
//				// =============清空本地的konw_host,调用该方法===============：
//				// ====如果出现hostkey已经改变的清空，那么执行清空本机konw_host文件夹的内容=============================================
//				String emptyLoclKnowHost[] = new String[] { "echo '' > /root/.ssh/known_hosts" };
//				ExecuteCmdlineUtil cmdlineUtil = new ExecuteCmdlineUtil();
//				cmdlineUtil.exec(emptyLoclKnowHost);
//				// 如果出现该清空，继续调用一次上一个case:KnownHosts.HOSTKEY_IS_NEW中的代码
//				verifyServerHostKey(hostname, port, serverHostKeyAlgorithm,
//						serverHostKey);
//				return true;
//			default:
//				throw new IllegalStateException();
//			}
//		}
//	}
//
//}
