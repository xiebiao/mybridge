package com.github.mybridge.mysql.packet;

/**
 * <pre>
 * https://dev.mysql.com/doc/internals/en/connection-phase.html#section-handshake-phase
 * </pre>
 * 
 * @author xiebiao
 * 
 */
public enum HandshakeState {
	WRITE_INIT/** 初始化 */
	, READ_AUTH/** 验证客户端用户名密码 */
	, WRITE_RESULT/** 结束 */
	, READ_COMMOND/** 处理client执行操作的命令 */
	, CLOSE /** 关闭 */
	;

}
