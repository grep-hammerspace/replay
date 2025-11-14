package org.example.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class DefaultUdpDecoder implements PacketDecoder{

    private final int BYTES_FOR_SEQUENCE_NUMBER = 8;
    private static final Logger logger = LoggerFactory.getLogger(DefaultUdpDecoder.class);

    @Override
    public DefaultSequencedPacket decode(DatagramPacket rawPacket) {
        int length = rawPacket.getLength();
        if (length < 0){
            throw new IllegalArgumentException("Recieve packet was too short to contain a sequence number");
        }

        //Drop unused space from the buffer
        ByteBuffer buffer = ByteBuffer.wrap(rawPacket.getData(),0,length);

        //Read first 8 bytes for the sequence number.
        Long sequenceNumber = buffer.getLong();

        //get actual payload by reading to end of buffer
        byte[] payload = new byte[length - BYTES_FOR_SEQUENCE_NUMBER];
        buffer.get(payload);
//        String s = new String(payload, StandardCharsets.UTF_8);
//        logger.info("Decoded string of payload is {}", s);

        return new DefaultSequencedPacket(sequenceNumber, payload);

    }
}
