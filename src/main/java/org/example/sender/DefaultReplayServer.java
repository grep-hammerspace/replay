package org.example.sender;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class DefaultReplayServer implements ReplayServer {

    private DatagramSocket socket;
    private String serverName;
    private int port;
    private static final Logger logger = LoggerFactory.getLogger(DefaultReplayServer.class);


    public DefaultReplayServer(String serverName, int port) throws SocketException {
        this.serverName= serverName;
        this.port = port;
    }

    @Override
    public void start() throws SocketException {
        logger.info("Starting server {} on port {}", this.serverName, this.port);
        this.socket = new DatagramSocket(this.port);

    }

    @Override
    public void stop() {
        logger.info("Closing server {}", this.serverName);
        this.socket.close();
    }

    @Override
    public void send(DatagramPacket packet) throws IOException {
        socket.send(packet);
    }

    @Override
    public void replay(Long startingSequenceNumber, Long endingSequenceNumber) throws Exception {

    }

    @Override
    public void replay(Long SequenceNumber) throws Exception {

    }
}
