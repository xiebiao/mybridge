package org.github.mybridge.plugin.netty;

import org.github.mybridge.core.MySQLProtocol;
import org.github.mybridge.utils.StringUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

public class ServerHandler extends SimpleChannelHandler {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	private MySQLProtocol mysql = new MySQLProtocol();

	public ServerHandler() {
	}

	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelOpen(ctx, e);
		LOG.debug("channelOpen...");
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		LOG.debug("channelConnected...");
		mysql.onConnected(e.getChannel());
	}

	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e)
			throws Exception {
		mysql.writeComplete();
		LOG.debug("writeComplete...");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getChannel().close();
	}

	public void channelBound(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		LOG.debug("channelBound");
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		if (e.getMessage() instanceof ChannelBuffer) {
			ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
			byte[] bytes = new byte[buffer.capacity() - 4];
			buffer.getBytes(4, bytes, 0, bytes.length);
			mysql.onRequestReceived(e.getChannel(), bytes);
		}
	}
}
