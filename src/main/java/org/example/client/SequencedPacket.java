package org.example.client;

public interface SequencedPacket {
    Long sequenceNumber();
    byte[] payload();

}
