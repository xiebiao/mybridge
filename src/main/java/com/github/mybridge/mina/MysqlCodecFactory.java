package com.github.mybridge.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;


public class MysqlCodecFactory implements ProtocolCodecFactory {
	private final Encoder encoder;
	private final Decoder decoder;

	public MysqlCodecFactory() {
		encoder = new Encoder();
		decoder = new Decoder();
	}

	public ProtocolEncoder getEncoder(IoSession session) {
		return encoder;
	}

	public ProtocolDecoder getDecoder(IoSession session) {
		return decoder;
	}

}
