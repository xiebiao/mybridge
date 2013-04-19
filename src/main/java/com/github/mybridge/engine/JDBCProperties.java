package com.github.mybridge.engine;

import java.sql.Driver;
import java.util.Properties;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public final class JDBCProperties {
	public static final String CHARACTER_ENCODING = "utf8";
	@XStreamAsAttribute
	String schema;
	String url;
	String user;
	String password;
	String driverName;
	int maxActive; // 最大激活连接数
	int maxIdle;// 最大空闲数
	int minIdle;// 最小空闲数
	int maxWait;// 在等待到maxWait毫秒后 会根据是否要创建连接 还是要继续等待

	public static String getPropertiesString() {
		return "characterEncoding=" + CHARACTER_ENCODING;
	}

	@XStreamOmitField
	Driver driver;
	@XStreamOmitField
	private Properties properties;

	public JDBCProperties() {
		driverName = "com.mysql.jdbc.Driver";
	}

	public String getSchema() {
		return schema;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public Driver getDriver() {
		try {
			driver = (Driver) Class.forName(this.driverName).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return driver;
	}

	public Properties getProperties() {
		properties = new Properties();
		properties.put("user", user);
		properties.put("password", password);
		return properties;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public String getDriverName() {
		return driverName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
