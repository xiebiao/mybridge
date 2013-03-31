package com.github.mybridge.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

import com.github.mybridge.core.MySQLProtocol;

public class ServerHandler extends SimpleChannelHandler {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(ServerHandler.class);
	private MySQLProtocol mysql = new MySQLProtocol();

	public ServerHandler() {
		logger.debug(this.getClass().getName() + " init");
		System.out.println(this.getClass().getName() + " init");
	}

	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelOpen(ctx, e);
		logger.debug("channelOpen");
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.debug("channelConnected");
		mysql.onConnected(e.getChannel());
	}

	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e)
			throws Exception {
		mysql.writeComplete();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getChannel().close();
		logger.debug("exceptionCaught");
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		logger.debug("messageReceived");
		if (e.getMessage() instanceof ChannelBuffer) {
			ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
			byte[] bytes = new byte[buffer.readableBytes()];
			buffer.readBytes(bytes);
			mysql.onRequestReceived(e.getChannel(), bytes);
		}
	}
}
