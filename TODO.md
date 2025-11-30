Stuff To Do With This Project


1) Better performance, the goal is to get to 1000 packets per second
   * Can look into using a ring buffer to speed up how many we can send/receive
   * Look into other ways to speed up packet send and receive

2) implement a reliable replay mechanism 
   * Find out ASAP when there is a packet missing (receiver side) <- What I am on currently
   * Determine how long we wait before requesting that packet over tcp(receiver side)
   * Determine how long we keep packets that have already been sent in memory before discarding them (sender side)
   * Define an api to that allows you to persist packet information for a list of received packets that are no longer needed exactly but .
     we still want to keep in case we want to replay them later. (Receiver-side) (The condition here is that there will no packets)
     missing when we persist this information)

3) Make the api of the library nice and easy to use
   * Make sure the library is well documented
   * Add examples of how to use the library for different use cases

