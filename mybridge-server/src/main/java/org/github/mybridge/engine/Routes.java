package org.github.mybridge.engine;

import java.util.List;

public class Routes {
	private List<Group> group;

	public List<Group> getGroup() {
		return group;
	}

	public void setGroup(List<Group> group) {
		this.group = group;
	}

	class Group {
		private Master master;
		private Slave slave;
		private String database = "";

		public Master getMaster() {
			return master;
		}

		public String getDatabase() {
			return database;
		}

		public void setDatabase(String database) {
			this.database = database;
		}

		public void setMaster(Master master) {
			this.master = master;
		}

		public Slave getSlave() {
			return slave;
		}

		public void setSlave(Slave slave) {
			this.slave = slave;
		}
	}

	class Server {
		private String ip;
		private int port;
		private String user;
		private String password;

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}
	}

	class BaseInfo {
		private String password;
		private String user;

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}
	}

	class Master extends BaseInfo {
		private List<Server> servers;

		public List<Server> getServers() {
			return servers;
		}

		public void setServers(List<Server> servers) {
			this.servers = servers;
		}
	}

	class Slave extends BaseInfo {
		public List<Server> getServers() {
			return servers;
		}

		public void setServers(List<Server> servers) {
			this.servers = servers;
		}

		private List<Server> servers;
	}
}
