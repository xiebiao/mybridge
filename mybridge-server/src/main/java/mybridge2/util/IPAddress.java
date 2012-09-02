package mybridge2.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 
 * <一句话功能简述> 本机的IP地址管理 <功能详细描述>
 * 
 * @author d00125339
 * @version [版本号, 2011-4-14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class IPAddress {
	/**
	 * <一句话功能简述> 返回本机IP地址 <功能详细描述> 暂时返回第一块eth网卡的第一个IP地址
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	static public String getLocalIP() {
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface nif = netInterfaces.nextElement();

				if (!nif.getName().startsWith("eth")) {
					continue;
				}
				Enumeration<InetAddress> iparray = nif.getInetAddresses();
				while (iparray.hasMoreElements()) {
					String ip = iparray.nextElement().getHostAddress();
					if (null != ip && ip.contains(".")) {

						return ip;
					}
				}
			}
		} catch (Exception e) {

		}
		return null;
	}
}
