package org.example.sender;


import java.net.DatagramPacket;
import java.util.logging.Logger;

public class DefaultPacketGenerator implements PacketGenerator {
    Logger logger = Logger.getLogger(DefaultPacketGenerator.class.getName());
    private final SequenceNumberGenerator sequenceNumberGenerator;

    public DefaultPacketGenerator(SequenceNumberGenerator sequenceNumberGenerator) {
        this.sequenceNumberGenerator = sequenceNumberGenerator;
    }

    @Override
    public PacketBuilder nextPacketBody() {
        Long nextSeqNo =   sequenceNumberGenerator.next();
        // For testing purposes, we are going to, on purpose, skip every 3rd packet.
        if (nextSeqNo % 3 != 0){
            return UdpPacketBuilder.builder()
                    .withSequenceNumber(nextSeqNo)
                    .withPayload("Dummy payload".getBytes());
        }

        return null;

    }

}
