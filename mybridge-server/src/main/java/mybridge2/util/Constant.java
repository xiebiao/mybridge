package mybridge2.util;

/**
 * 常量类
 * 
 * @author hKF36894
 */
public class Constant {
	/**
	 * 包头占用字节数
	 */
	public static final byte PACKET_HEADER_LEN = 4;

	/**
	 * 逻辑包头的长度，即包含原始4字节包头+4校验位+1命令字+4SN号
	 */
	public static final byte LOGIC_HEADER_LEN = 8;

	// public static final byte LOGIC_HEADER_LEN = 13;

	/**
	 * 包头里表示本数据包长度的字段占用字节数
	 */
	public static final byte PACKETLEN_FIELD_LEN = 3;

	/**
	 * 命令字占用字节数
	 */
	public static final byte MESSAGE_CMD_LEN = 1;

	/**
	 * 应答消息占用字节数，不包含数据包头
	 */
	public static final short ACK_MSG_LEN = 3;

	/**
	 * 码流中string的固定长度，用于编解码
	 */
	public static final int STRING_LEN = 32;

	/**
	 * REGISTER_MSG_PORT_LEN 注册消息占用的字节数
	 */
	public static final short REGISTER_MSG_PORT_LEN = 4;

	/**
	 * REGISTER_MSG_LEN
	 */
	public static final short REGISTER_MSG_LEN = STRING_LEN * 2 + 4
			+ REGISTER_MSG_PORT_LEN + 4;

	/**
	 * DataGrid集群节点的个数，固定为3个
	 */
	public static final byte DATANODE_NUM = 3;

	/**
	 * 接收单个数据包的最大长度，暂定为1k,目前没有超过只长度的消息，
	 */
	public static final int MAX_DATA_LEN = 1024;

	/**
	 * 该参数主要用于解码时，判断是否码流异常
	 */
	public static final int CODER_VALID_FLAG = 0x12A145C9;

	// 码流中的校验位，校验码不对的一律是乱码

	/**
	 * 消息头中的packet_no,请求时固定为0,应答时固定为1
	 */
	public static final byte REQUEST_PACKET_NO = 0x00;

	/**
	 * 消息头中的packet_no,请求时固定为0,应答时固定为1
	 */
	public static final byte RESPONSE_PACKET_NO = 0x01;

	/**
	 * 心跳消息ID常量
	 */
	public static final byte HEART_BEAT_ID = 0x033;

	/**
	 * 包长度
	 */
	public static final byte PACKET_LEN = 3;

	/**
	 * 消息序列号
	 */
	public static final byte MSG_SN = 4;

	/**
	 * 配置项相关的定义
	 */
	public static final String CONFIG_DIR_FILENAME = "/cfg/config.properties";

	/**
	 * 配置文件路径
	 */
	public static final String LOGCFG_DIR_FILENAME = "/cfg/log4j.properties";

	/**
	 * 配置文件路径
	 */
	public static final String CONFIG_ITEM_HOSTIP = "dbgrid.dbmaster.ip";

	/**
	 * 端口
	 */
	public static final String CONFIG_ITEM_HOSTPORT = "dbgrid.dbmaster.port";

	/**
	 * 主机
	 */
	public static final String CONFIG_ITEM_DBHOST = "dbgrid.database.host";

	/**
	 * schema
	 */
	public static final String CONFIG_ITEM_DBSCHEMA = "dbgrid.database.schema";

	/**
	 * 用户名
	 */
	public static final String CONFIG_ITEM_DBUSER = "dbgrid.database.user";

	/**
	 * 密码
	 */
	public static final String CONFIG_ITEM_DBPWD = "dbgrid.database.password";

	// innobackup文件路径

	/**
	 * innobackup备份路径
	 */
	public static final String INNOBACKUP_BACKUP_FILE_PATH = "backup.file.path";

	/**
	 * innobackup恢复路径
	 */
	public static final String INNOBACKUP_REGAIN_FILE_PATH = "regain.file.path";

	/**
	 * innobackup用户名
	 */
	public static final String INNOBACKUP_USERNAME = "backup.user";

	/**
	 * innobackup密码
	 */
	public static final String INNOBACKUP_PASSWORD = "backup.password";

	/**
	 * innobackup Mysql 配置文件名称
	 */
	public static final String INNOBACKUP_MYSQL_CONFIGURATION_FILE = "backup.mysql.configuration.file";

	/**
	 * innobackup 备份时生成的配置文件名称
	 */
	public static final String INNOBACKUP_CONFIGURATION_FILE = "backup.configuration.file";

	/**
	 * innobackup 远程恢复的环境用户名
	 */
	public static final String INNOBACKUP_SCP_USERNAME = "scp.userName";

	/**
	 * innobackup 远程恢复的环境密码
	 */
	public static final String INNOBACKUP_SCP_PASSOWRD = "scp.password";

	/**
	 * innobackup 备份数据写入文件路径
	 */
	public static final String BACKUP_WRITE_FILE_PATH = "backup.write.file.path";

	/**
	 * DBGRID_SERVER_PORT
	 */
	public static final String DBGRID_SERVER_PORT = "dbgrid.server.port";

	/**
	 * DBGRID_SERVER_HEARTBEAT_PORT 心跳端口
	 */
	public static final String DBGRID_SERVER_HEARTBEAT_PORT = "dbgrid.server.heartbeat.port";

	/**
	 * DBGRID_HEARTBEAT_RATE 心跳频率
	 */

	public static final String DBGRID_HEARTBEAT_RATE = "dbgrid.heartbeat.rate";

	/**
	 * 本机IP
	 */
	public static final String LOCAL_HOST_IP = "dbgrid.localHostIP";

	/**
	 * 分隔符
	 */
	public static final String SPLIT = ";";

	/**
	 * DATANODE_CAPABILITY_INFINITY
	 */
	public static final long DATANODE_CAPABILITY_INFINITY = 30 * 1024 * 1024;

	// 数据节点的容量，0表示无限制
	// 暂时这么写，实际上容量是要获取系统信息的，而目前规则尙未制定

	/**
	 * RECONNECT_INTERVIEW
	 */
	public static final long RECONNECT_INTERVIEW = 30000; // 与DB

	// Master断连后，尝试重连的时间间隔，微秒

	/**
	 * 命令字定义 目前确定是在请求及响应消息均使用同一命令字
	 */
	public static final byte BASE_CMD = 0x30;

	/**
	 * 向master注册的命令
	 */
	public static final byte REGISTER_CMD = BASE_CMD;

	/**
	 * 接受master Create schema的请求
	 */
	public static final byte CREATESCHEMA_CMD = BASE_CMD + 1;

	/**
	 * 消息处理成功
	 */
	public static final byte OPERATE_SUCCESS = 0;

	/**
	 * 消息处理失败
	 */
	public static final byte OPERATE_FAILED = 1;

	/**
	 * local_msg_sn
	 */
	public static int local_msg_sn = 0;

	/**
	 * nextMsg_sn
	 * 
	 * @return int
	 */
	public static int nextMsg_sn() {
		// 暂时使用一个byte，循环使用
		return local_msg_sn++;
	}
}
