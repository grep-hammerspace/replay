package org.example.receiver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DefaultSequencedPacket implements SequencedPacket{

    private Long sequenceNumber;
    private byte[] payload;

    public DefaultSequencedPacket(Long sequenceNumber, byte[] payload){
        this.sequenceNumber = sequenceNumber;
        this.payload= payload;
    }

    @Override
    public Long sequenceNumber() {
        return this.sequenceNumber;
    }

    @Override
    public byte[] payload() {
        return new byte[0];
    }
}
