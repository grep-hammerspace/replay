package org.example.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws IOException, InterruptedException {
        PacketGenerator packetGenerator = new DefaultPacketGenerator(new SequenceNumberGenerator());
//        DefaultReplaySender sender = new DefaultReplaySender(InetAddress.getByName("localhost"),"test-sender",44444,45370, packetGenerator);
//        sender.start();
    }
}