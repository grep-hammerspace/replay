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
        DefaultReplaySender sender = new DefaultReplaySender("test-sender",45369);
        sender.start();
        SequenceNumberGenerator sequenceNumberGenerator = new SequenceNumberGenerator();
        InetAddress localhost = InetAddress.getByName("localhost");
        int i = 0;
        Long start = System.nanoTime();
        while (true){
            Long seqno = sequenceNumberGenerator.next();
            logger.info("Sequence number: {}", seqno);
            if (seqno % 3== 0) {
                logger.info("Sequence number mod 3: {}", seqno %3);
                String body = "Packet Body";
                DatagramPacket packet = UdpPacketBuilder.builder()
                        .withPayload(body.getBytes())
                        .withSequenceNumber(seqno)
                        .to(localhost, 44444).build();
//                logger.info("Sendin packet number {}", i);
                sender.send(packet);
                i++;
                Thread.sleep(3000);
            }
        }

    }
}