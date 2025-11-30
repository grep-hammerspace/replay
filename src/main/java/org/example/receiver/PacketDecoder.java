package org.example.receiver;

import java.net.DatagramPacket;

public interface PacketDecoder {
    DefaultSequencedPacket decode(DatagramPacket rawPacket);
}
