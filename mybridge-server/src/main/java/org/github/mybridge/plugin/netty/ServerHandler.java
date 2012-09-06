package org.github.mybridge.plugin.netty;

import mybridge2.netty.NetFSM;
import mybridge2.packet.Packet;

import org.github.mybridge.core.handler.Handler;
import org.github.mybridge.core.handler.MysqlCommondHandler;
import org.github.mybridge.core.packet.HandshakeState;
import org.github.mybridge.core.packet.InitialHandshakePacket;
import org.github.mybridge.core.packet.PacketNum;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ServerHandler extends SimpleChannelHandler {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	private HandshakeState state;
	private Handler handler;

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
		PacketNum.num = 0;
		state = HandshakeState.WRITE_INIT;
		handler = new MysqlCommondHandler();
		InitialHandshakePacket initPacket = new InitialHandshakePacket();
		byte[] temp = initPacket.getBytes();
		e.getChannel().write(temp);
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
		LOG.debug("messageReceived");
		try {
			NetFSM fsm = (NetFSM) ctx.getAttachment();
			fsm.getRequests().clear();
			PacketBuffer mBuffer = (PacketBuffer) e.getMessage();
			fsm.onMessage(e.getChannel(), mBuffer);
			for (Packet request : fsm.getRequests()) {
				ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
				PacketBuffer packetBuf = new PacketBuffer(buffer);
				request.write2Buffer(packetBuf);
				e.getChannel().write(packetBuf);
			}
		} catch (Exception e1) {
			System.err.print(">>>>>>>>>>>>>>>>>>");
			e1.printStackTrace();
		}
	}
}
