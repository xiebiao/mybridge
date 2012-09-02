package mybridge2.util;

/**
 * 定义消息错误码
 * 
 * @author t00108272
 */
public interface ErrorCode {
	/** ********************在返回失败的情况下，具体的错误码***************** */
	/**
	 * 无错误
	 */
	int ERRORCODE_SUCCESS = 490040000;

	/**
	 * 参数有误
	 */
	int ERRORCODE_PARAM_ERROR = 490040001;

	/**
	 * 重复注册
	 */
	int ERRORCODE_ALREADY_REGISTER = 490040002;

	/**
	 * 重复创建schema
	 */
	int ERRORCODE_ALREADY_CREATESHEMA = 490040003;

	/**
	 * 创建schema失败
	 */
	int ERRORCODE_CREATESHEMA_FAILED = 490040004;

	/**
	 * 连接zookeeper失败
	 */
	int ERRORCODE_ZOOKEEPER_FAIL = 490040005;

	/**
	 * 服务端错误
	 */
	int ERRORCODE_SERVICE_ERROR = 490040006;

	/**
	 * 节点未注册成功 返回该错误码表示DBGrid不能正常处理任务
	 */
	int ERRORCODE_DBGRID_UNREGISTED = 490040007;

	/**
	 * 创建环形结构失败.
	 */
	int ERROR_CREATE_CRICLE = 490040008;

	/**
	 * 在zookeeper上添加schema失败
	 */
	int ERRORCODE_ZOOKEEPER_ADDSCHEMAFAIL = 490040009;

	/**
	 * 编码错误
	 */
	int ERRORCODE_ENCODE_EERROR = 490040010;

	/**
	 * 解码错误
	 */
	int ERRORCODE_DECODE_EERROR = 490040011;

	/**
	 * 没有集群
	 */
	int ERRORCODE_NO_CLUSTER = 490040012;

	/**
	 * node节点超时
	 */
	int ERRORCODE_NODE_TIMEOUT = 490040013;

	/**
	 * 创建logic数据库名或密码失败
	 */
	int ERRORCODE_LOGICDB_NAMEPASSWORD_ERROR = 490040014;

	/**
	 * 申请租房时 没找到集群
	 */
	int ERRORCODE_CLUSTER_NOTEXISTS_ERROR = 490040015;

	/**
	 * 创建logic数据库时 用户名字已经存在
	 */
	int ERRORCODE_LOGICDB_NOTEXISTS_ERROR = 490040016;

	/**
	 * 集群资源不足
	 */
	int ERRORCODE_CLUSTER_NOTRESOURSE = 490040017;

	/**
	 * 用户逻辑数据个数超过上限
	 */
	int ERRORCODE_LOGICDB_OVERLIMIT = 490040018;

	/**
	 * 对象为空
	 */
	int ERRORCODE_OBJECT_ISNULL = 490040019;
}
