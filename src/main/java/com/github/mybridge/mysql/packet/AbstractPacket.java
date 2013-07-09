package com.github.mybridge.mysql.packet;

public abstract class AbstractPacket implements Packet {

    public abstract byte[] getBytes();

    public abstract void putBytes(byte[] bytes);

    private static byte packetNumber = 0;

    public synchronized void packetNumberInc() {
        packetNumber++;
    }

    public synchronized void setPacketNumber(byte id) {
        packetNumber = id;
    }

    public byte getPacketNumber() {
        return packetNumber;
    }
}
