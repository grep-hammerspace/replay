package org.example.receiver;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.ArrayList;


@Getter
@Setter
public class DefaultUdpPacketReceiver{

    private static final Logger logger = LoggerFactory.getLogger(DefaultUdpPacketReceiver.class);
    private DatagramSocket socket;
    private int udpPort;
    private int tcpPort;
    private InetAddress address;
    private PacketDecoder decoder;
    private long lastSequenceNumber = 0L;
    private ArrayList<Long> missedSequenceNumbers = new ArrayList<Long>();
    private final int MAX_SIZE_PACKET_PAYLOAD = 1500;


    public DefaultUdpPacketReceiver(PacketDecoder decoder, int udpPort, int tcpPort, InetAddress address) {
        this.decoder = decoder;
        this.udpPort = udpPort;
        this.address = address;
        this.tcpPort = tcpPort;
    }

    public void start() throws SocketException {
        logger.info("Starting udp listener at {}:{}", this.address,this.udpPort);
        socket = new DatagramSocket(this.udpPort, this.address);
        logger.info("Creating TCP connection to receive missed packets at {}:{}", this.address, this.tcpPort);
        logger.info("Created TCP connection");
        int i =0;
        Long s = System.nanoTime();
        while (i < 50){
            listenLoop();
            i++;
        }
        System.out.println(missedSequenceNumbers);
//        Long e = System.nanoTime();
//        logger.info("ns: {}",e-s);
//        logger.info("Processsed {} messages/s",1000000/((e-s)/1000000000));

    }

    private void listenLoop() {
        byte[] buffer = new byte[MAX_SIZE_PACKET_PAYLOAD];
        DatagramPacket rawPacket = new DatagramPacket(buffer, buffer.length);
        try{
            socket.receive(rawPacket);
            SequencedPacket packet = decoder.decode(rawPacket);
            long sequenceNumberFromPacket = packet.sequenceNumber();
            logger.info("Received packet with sequence number {}", sequenceNumberFromPacket);
            if (sequenceNumberFromPacket > lastSequenceNumber + 1l){
                //We missed at least one packet
                logger.info("Expected seqno {} but received seqno {}", lastSequenceNumber + 1L, packet.sequenceNumber());
                logMissedPackets(this.lastSequenceNumber, packet.sequenceNumber());
            }
            lastSequenceNumber = sequenceNumberFromPacket;
        } catch (Exception e) {
            logger.info("Failed while attempting to decode {}", rawPacket);
            logger.error("UDP listener error", e);
        }
    }

    private void logMissedPackets(Long from, Long to){
        for (Long i = from + 1L; i < to; i++){
            logger.info("Missed packet with sequence number {}", i);
            missedSequenceNumbers.add(i);
        }
    }

    public void stop() {
        logger.info("Stopping UDP listener at {}:{}", this.address,this.udpPort);
        socket.close();
    }

    // WARN: Only for testing purposes
    public void handlePacket(){
        listenLoop();
    }

}
