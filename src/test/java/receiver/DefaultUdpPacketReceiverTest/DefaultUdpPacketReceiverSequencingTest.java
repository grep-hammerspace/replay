package receiver.DefaultUdpPacketReceiverTest;

import org.example.receiver.*;
import org.junit.Before;
import org.junit.Test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DefaultUdpPacketReceiverSequencingTest {

    private DefaultUdpPacketReceiver receiver;
    private DatagramSocket mockSocket;
    private PacketDecoder mockDecoder;

    @Before
    public void setUp() throws Exception {

        mockSocket = mock(DatagramSocket.class);
        mockDecoder = mock(PacketDecoder.class);

        receiver = new DefaultUdpPacketReceiver(mockDecoder, 9999, InetAddress.getLoopbackAddress());

        receiver.setSocket(mockSocket);
        receiver.setDecoder(mockDecoder);

        // Mock socket.receive() so that listenLoop() thinks a datagram arrived
        doAnswer(invocation -> {
            DatagramPacket p = invocation.getArgument(0);
            p.setData(new byte[] {1});   // simulate raw data
            return null;
        }).when(mockSocket).receive(any(DatagramPacket.class));
    }

    @Test
    public void oneMissedPacketAddedToMissedPacketList() {
        // simulate decode results 0, 1, 3
        when(mockDecoder.decode(any(DatagramPacket.class)))
                .thenReturn(new DefaultSequencedPacket(0L, new byte[]{}))
                .thenReturn(new DefaultSequencedPacket(1L, new byte[]{}))
                .thenReturn(new DefaultSequencedPacket(3L, new byte[]{}));

        receiver.handlePacket();
        receiver.handlePacket();
        receiver.handlePacket();

        assertEquals(List.of(2L), receiver.getMissedSequenceNumbers());
    }

    @Test
    public void multipleMissedPacketsAddedToMissedPacketList() {
        // simulate decode results 0, 1, 5
        when(mockDecoder.decode(any(DatagramPacket.class)))
                .thenReturn(new DefaultSequencedPacket(0L, new byte[]{}))
                .thenReturn(new DefaultSequencedPacket(1L, new byte[]{}))
                .thenReturn(new DefaultSequencedPacket(5L, new byte[]{}));

        receiver.handlePacket();
        receiver.handlePacket();
        receiver.handlePacket();

        assertEquals(List.of(2L, 3L, 4L), receiver.getMissedSequenceNumbers());
    }

    @Test
    public void noMissedPacketsMeansMissedPacketListIsEmpty() {
        when(mockDecoder.decode(any(DatagramPacket.class)))
                .thenReturn(new DefaultSequencedPacket(0L, new byte[]{}))
                .thenReturn(new DefaultSequencedPacket(1L, new byte[]{}))
                .thenReturn(new DefaultSequencedPacket(2L, new byte[]{}));

        receiver.handlePacket();
        receiver.handlePacket();
        receiver.handlePacket();

        assertEquals(List.of(), receiver.getMissedSequenceNumbers());
    }
}
