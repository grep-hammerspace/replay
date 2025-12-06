package org.example.sender;

import lombok.Getter;

import java.net.DatagramPacket;
import java.net.InetAddress;


public interface PacketBuilder {

    // Get seqno to put in the map. Here bc Lombok needs it
    Long getSequenceNumber();

    // Set the sequence number for the udp packet.
    PacketBuilder withSequenceNumber(Long sequenceNumber);

    // Set the payload for udp packet.
    PacketBuilder withPayload(byte[] payload);

    PacketBuilder to(InetAddress address, int port);

    // Returns a DatagramPacket with a custom header passed into the payload byte array
    DatagramPacket build();
}
