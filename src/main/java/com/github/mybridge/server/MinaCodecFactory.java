package com.github.mybridge.server;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;


public class MinaCodecFactory implements ProtocolCodecFactory {
	private final MinaEncoder encoder;
	private final MinaDecoder decoder;

	public MinaCodecFactory() {
		encoder = new MinaEncoder();
		decoder = new MinaDecoder();
	}

	public ProtocolEncoder getEncoder(IoSession session) {
		return encoder;
	}

	public ProtocolDecoder getDecoder(IoSession session) {
		return decoder;
	}

}
