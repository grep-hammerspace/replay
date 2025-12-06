package org.example.sender;

import java.net.DatagramPacket;

public interface PacketGenerator {

    PacketBuilder nextPacketBody();
}
