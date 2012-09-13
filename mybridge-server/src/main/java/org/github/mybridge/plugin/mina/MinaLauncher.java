package org.github.mybridge.plugin.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.github.mybridge.Launcher;
import org.github.mybridge.AbstractLauncher;
import org.github.mybridge.exception.ConfigurationException;
import org.github.mybridge.utils.Configuration;

public class MinaLauncher extends AbstractLauncher implements Launcher {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	private Configuration config;

	public MinaLauncher(Configuration config) {
	}

	public void start() {
		IoAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime()
				.availableProcessors() + 1);
		if (config.isDebug()) {
			LOG.debug("Setting io logging");
			DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
			LoggingFilter loggingFilter = new LoggingFilter();
			chain.addLast("logging", loggingFilter);
		}
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new MySQLProtocalCodecFactory()));
		acceptor.getFilterChain().addLast("threadPool",
				new ExecutorFilter(Executors.newCachedThreadPool()));
		acceptor.setHandler(new MysqlServerHandler());
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		InetSocketAddress address = new InetSocketAddress(config.getIp(),
				config.getPort());
		try {
			acceptor.bind(address);			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void init() throws ConfigurationException {
		// TODO Auto-generated method stub
		
	}

}
