Stuff To Do With This Project


1) Better performance, the goal is to get to 1000 packets per second
   * Can look into using a ring buffer to speed up how many we can send/receive
   * Look into using Datagram channels instead of DatagramSocket, and using a Bytebuffer alongside
      apparently using DatagramPacket makes copies of stuff, so its slow and uses memory.

2) implement a reliable replay mechanism 
   * ~~Find out ASAP when there is a packet missing (receiver side)~~ Never mind apparently theres a better way to do this with a window buffer.
   * Determine how long we wait before requesting that packet over tcp(receiver side) <- What Im Doing Now, Current plan is to do that immediately
   * Find a clean way to have both tcp and udp sockets open on the same host.
   * Determine how long we keep packets that have already been sent in memory before discarding them (sender side) + how we store them without taking up hella memory.
   * Define an api to that allows you to persist packet information for a list of received packets that are no longer needed exactly but
     we still want to keep in case we want to replay them later. (Receiver-side) (The condition here is that there will no packets)
     missing when we persist this information)

3) Make the api of the library nice and easy to use
   * Make sure the library is well documented
   * Add examples of how to use the library for different use cases


Notes for things i am working on
Long2ObjectOpenHashMap - Stores millions of packets without boxing overhead.

BitSet Sliding Window - Efficient way to track missing packets over the last WINDOW_SIZE sequence numbers.

You only need a few hundred/thousand entries for retransmission detection.

For more security, to prevent multiple connections to the Replay Server
HttpServer uses a ServerSocket under the hood. You could:
Accept one Socket manually.
Handle HTTP protocol parsing yourself (or use a minimal HTTP parser).


