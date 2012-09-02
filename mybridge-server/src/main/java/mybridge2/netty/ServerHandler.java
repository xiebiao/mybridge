package mybridge2.netty;

import mybridge2.packet.HandshakePacket;
import mybridge2.packet.Packet;
import mybridge2.packet2.PacketInit;

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

	public ServerHandler() {
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		NetFSM fsm = new NetFSM();
		ctx.setAttachment(fsm);
		fsm.onConnect(e.getChannel());
		for (Packet request : fsm.getRequests()) {
			if (request instanceof HandshakePacket) {
				LOG.debug("request is ...HandshakePacket");

				PacketInit pack = new PacketInit();
				e.getChannel().write("xxx");
			} else {
				ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
				PacketBuffer packetBuf = new PacketBuffer(buffer);
				request.write2Buffer(packetBuf);
				e.getChannel().write(packetBuf);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getChannel().close();
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
