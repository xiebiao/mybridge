package org.github.mybridge.core.packet;

public enum HandShakeState {
	WRITE_INIT, // 初始化
	READ_AUTH, // 验证客户端用户名密码
	WRITE_RESULT, // 结束
	READ_COMMOND, // 处理client执行操作的命令
	CLOSE;// 关闭
}
