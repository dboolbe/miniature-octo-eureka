package net.thesyndicate.io;

/**
 * Created by dboolbe on 9/12/21.
 */
public class Multicast {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        
        MulticastReceiver multicastReceiver = new MulticastReceiver("230.0.0.0", 4446);
        multicastReceiver.start();
        
        MulticastTransmitter multicastTransmitter = new MulticastTransmitter("230.0.0.0", 4446);
        multicastTransmitter.multicast("Hello, World!");
        multicastTransmitter.multicast("Chao, World!");
        multicastTransmitter.multicast("Bye, World!");
        multicastTransmitter.multicast("Bye");
        multicastTransmitter.multicast("end");
    }
}
