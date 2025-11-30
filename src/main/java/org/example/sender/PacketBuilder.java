package org.example.sender;

import java.net.DatagramPacket;
import java.net.InetAddress;

public interface PacketBuilder {

    // Set the sequence number for the udp packet.
    PacketBuilder withSequenceNumber(Long sequenceNumber);

    // Set the payload for udp packet.
    PacketBuilder withPayload(byte[] payload);

    PacketBuilder to(InetAddress address, int port);

    // Returns a DatagramPacket with a custom header passed into the payload byte array
    DatagramPacket build();
}
