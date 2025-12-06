package org.example.receiver;

import java.net.InetAddress;

public class Main {

    public static void main(String[] args) throws Exception {
        InetAddress localhost = InetAddress.getByName("localhost");
        PacketDecoder decoder = new DefaultUdpDecoder();

        DefaultUdpPacketReceiver receiver = new DefaultUdpPacketReceiver(decoder, 44444,55555, localhost);
        receiver.start();

    }
}
