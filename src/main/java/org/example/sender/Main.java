package org.example.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws IOException, InterruptedException {
        DefaultReplayServer server = new DefaultReplayServer("test-server",45369);
        server.start();
        SequenceNumberGenerator sequenceNumberGenerator = new SequenceNumberGenerator();
        InetAddress localhost = InetAddress.getByName("localhost");
        int i = 0;
        Long start = System.nanoTime();
        while (true){
            String body = "udp packet body number " + i;
            DatagramPacket packet = UdpPacketBuilder.builder()
                    .withPayload(body.getBytes())
                    .withSequenceNumber(sequenceNumberGenerator.next())
                    .to(localhost,44444).build();
//            logger.info("Sendin packet number {}", i);
            server.send(packet);
            i++;
        }

    }
}