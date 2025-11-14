package org.example.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class DefaultUdpPacketListener{

    private static final Logger logger = LoggerFactory.getLogger(DefaultUdpPacketListener.class);
    private DatagramSocket socket;
    private int port;
    private InetAddress address;
    private PacketDecoder decoder;
    private final int MAX_SIZE_PACKET_PAYLOAD = 1500;


    public DefaultUdpPacketListener(PacketDecoder decoder, int port, InetAddress address) {
        this.decoder = decoder;
        this.port = port;
        this.address = address;
    }

    public void start() throws SocketException {
        logger.info("Starting listener at {}:{}", this.address,this.port);
        socket = new DatagramSocket(this.port, this.address);
        int i =0;
        Long s = System.nanoTime();

        while (i < 1000000){
            listenLoop();
            i++;
        }
        Long e = System.nanoTime();
        logger.info("ns: {}",e-s);
        logger.info("Processsed {} messages/s",1000000/((e-s)/1000000000));

    }

    private void listenLoop() {
        byte[] buffer = new byte[MAX_SIZE_PACKET_PAYLOAD];
        DatagramPacket rawPacket = new DatagramPacket(buffer, buffer.length);
        try{
        socket.receive(rawPacket);
//        logger.info("Recieved raw packet {}", rawPacket.getData());
        SequencedPacket packet = decoder.decode(rawPacket);
//        logger.info("Successfully recieved packet with seqno{} and body {}", packet.sequenceNumber(), packet.payload());
        } catch (Exception e) {
            logger.info("Failed while attempting to decode {}", rawPacket);
            logger.error("UDP listener error", e);
        }
    }

}
