package org.example.sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

public interface ReplaySender {

    void start() throws IOException, IOException, InterruptedException;
    void stop() throws IOException;

   //Replay a missed sequence of packets from startingSequenceNumber to endingSequenceNumber (inclusive)
    DatagramPacket[] replay(Long startingSequenceNumber, Long endingSequenceNumber) throws Exception;

    // Replay a single missed packet with the given sequence number
    DatagramPacket replay(Long SequenceNumber) throws Exception;
}
