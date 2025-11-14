package org.example.client;

import java.net.DatagramPacket;

public interface PacketDecoder {
    DefaultSequencedPacket decode(DatagramPacket rawPacket);
}
