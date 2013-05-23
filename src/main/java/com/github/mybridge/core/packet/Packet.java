package com.github.mybridge.core.packet;

public interface Packet {

    public abstract byte[] getBytes();

    public abstract void putBytes(byte[] bytes);
}
