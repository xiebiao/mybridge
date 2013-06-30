package com.github.mybridge.mysql.packet;

public interface Packet {

    public abstract byte[] getBytes();

    public abstract void putBytes(byte[] bytes);
}
