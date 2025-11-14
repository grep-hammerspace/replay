package org.example.client;

import java.net.InetAddress;

public class Main {

    public static void main(String[] args) throws Exception {
        InetAddress localhost = InetAddress.getByName("localhost");
        PacketDecoder decoder = new DefaultUdpDecoder();

        DefaultUdpPacketListener listener = new DefaultUdpPacketListener(decoder, 44444, localhost);
        listener.start();

    }
}
