package com.github.mybridge.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

public class NettyServerHandler extends SimpleChannelHandler {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(NettyServerHandler.class);
	private NettyMySQLProtocolImpl mysql;

	public NettyServerHandler() {
		mysql = new NettyMySQLProtocolImpl();
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
		mysql.writeCompleted();
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
