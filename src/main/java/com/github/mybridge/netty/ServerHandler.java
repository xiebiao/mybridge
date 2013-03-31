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
	}

	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelOpen(ctx, e);
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		mysql.onConnected(e.getChannel());
	}

	@Override
	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e)
			throws Exception {
		mysql.writeComplete();
		logger.debug(e.toString() + " " + ctx.toString());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getChannel().close();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		if (e.getMessage() instanceof ChannelBuffer) {
			ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
			byte[] bytes = new byte[buffer.readableBytes()];
			buffer.readBytes(bytes);
			mysql.onRequestReceived(e.getChannel(), bytes);
		}
	}
}
