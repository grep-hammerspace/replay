package org.example.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public interface UdpServer {

    void start() throws SocketException;
    void stop();
    void send(DatagramPacket packet) throws Exception;
}
