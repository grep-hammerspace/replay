package org.example.sender;


import lombok.Data;
import lombok.Getter;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

/*
A builder class that is used to create instances of DatagramPacket with a custom header sent in the byte[]
 */
@Getter
public class UdpPacketBuilder implements PacketBuilder{

    private Long sequenceNumber;
    private byte[] payload;
    private InetAddress address;
    private int port;

    private final int BYTES_FOR_SEQUENCE_NUMBER = 8;

    private UdpPacketBuilder(){}

    public static  UdpPacketBuilder builder()
    {
    return new UdpPacketBuilder();
    }

    @Override
    public UdpPacketBuilder withSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    @Override
    public UdpPacketBuilder withPayload(byte[] payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public PacketBuilder to(InetAddress address, int port) {
        this.port = port;
        this.address = address;
        return this;
    }

    @Override
    public DatagramPacket build() throws IllegalArgumentException {
        if (payload == null){
            throw new IllegalArgumentException("Payload must not be null.");
        }

        ByteBuffer buffer = ByteBuffer.allocate(BYTES_FOR_SEQUENCE_NUMBER + payload.length);
        buffer.putLong(sequenceNumber);
        buffer.put(payload);

        byte[] finalPacketBytes = buffer.array();

        return new DatagramPacket(finalPacketBytes, finalPacketBytes.length,address,port);
    }
}
