package org.example.sender;


import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


public class DefaultReplaySender implements ReplaySender {

    private DatagramSocket udpSocket;
    private volatile Boolean started = false;
    private Socket tcpSocket;
    private String serverName;
    private int udpPort;
    private InetAddress address;
    private static final Logger logger = LoggerFactory.getLogger(DefaultReplaySender.class);
    private PacketGenerator packetGenerator;
    private final Long2ObjectOpenHashMap<DatagramPacket> packets = new Long2ObjectOpenHashMap<DatagramPacket>();


    public DefaultReplaySender(InetAddress address, String serverName, int udpPort, PacketGenerator packetGenerator) throws SocketException {
        this.address = address;
        this.serverName= serverName;
        this.udpPort = udpPort;
        this.packetGenerator = packetGenerator;
    }

    @Override
    public void start() throws IOException, InterruptedException {
        if (!started) {
            logger.info("Starting udp client for {} on port {}", this.serverName, this.udpPort);
            this.udpSocket = new DatagramSocket(0);
            while (true){
                PacketBuilder packetStub = packetGenerator.nextPacketBody();
                if (packetStub != null) {
                    send(packetStub);
                    Thread.sleep(500);
                }
            }
        }
        throw new IllegalStateException("Server is already started");

    }

    @Override
    public void stop() throws IOException {
        if (started) {
            logger.info("Closing server {}", this.serverName);
            this.udpSocket.close();
        }
        throw new IllegalStateException("Server is already stopped");
    }


    private void send(PacketBuilder packetStub) throws IOException {
        DatagramPacket packet = packetStub.to(address,udpPort).build();
        //Long2ObjectOpenHashMap is not thread-safe, so we are going to use synchronization.
        synchronized (packets){
            packets.put(packetStub.getSequenceNumber(),packet);
        }
        udpSocket.send(packet);
    }

    @Override
    public DatagramPacket[] replay(Long startingSequenceNumber, Long endingSequenceNumber){
        List<DatagramPacket> result = new ArrayList<>();
        synchronized (packets){
            for(Long i = startingSequenceNumber; i <= endingSequenceNumber; i++){
                result.add(packets.get(i));
            }
        }
        return result.toArray(new DatagramPacket[result.size()]);

    }

    @Override
    public DatagramPacket replay(Long sequenceNumber){
        synchronized (packets){
            return packets.get(sequenceNumber);
        }

    }
}
