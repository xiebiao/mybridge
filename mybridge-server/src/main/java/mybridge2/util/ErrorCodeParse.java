package mybridge2.util;

import java.io.IOException;

import mybridge2.netty.Config;

public class ErrorCodeParse {
	public ErrorCodeParse() throws IOException {
		String path = System.getenv("RDS_HOME");
		String filePath = path + "/cfg/master.conf";

		Config.init(filePath);

	}

	public static String parse(int errorCode) {
		return Config.getConfig(String.valueOf(errorCode));

	}
}
