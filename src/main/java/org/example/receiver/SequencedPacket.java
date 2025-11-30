package org.example.receiver;

public interface SequencedPacket {
    Long sequenceNumber();
    byte[] payload();

}
