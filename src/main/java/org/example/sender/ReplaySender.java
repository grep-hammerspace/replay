package org.example.sender;

import java.net.DatagramPacket;
import java.net.SocketException;

public interface ReplaySender {

    void start() throws SocketException;
    void stop();
    void send(DatagramPacket packet) throws Exception;

   //Replay a missed sequence of packets from startingSequenceNumber to endingSequenceNumber (inclusive)
    void replay(Long startingSequenceNumber, Long endingSequenceNumber) throws Exception;

    // Replay a single missed packet with the given sequence number
    void replay(Long SequenceNumber) throws Exception;
}
