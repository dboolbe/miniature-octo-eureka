package net.thesyndicate.io;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by dboolbe on 9/12/21.
 */
public class MulticastTransmitter {
    private String hostName;
    private int portNumber;
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;
    
    public MulticastTransmitter(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public void multicast(String multicastMessage) {
        try {
            socket = new DatagramSocket();
            group = InetAddress.getByName(hostName);
            buf = multicastMessage.getBytes();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, portNumber);
            socket.send(packet);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        
        MulticastTransmitter multicastTransmitter = new MulticastTransmitter("230.0.0.0", 4446);
        multicastTransmitter.multicast("Hello, World!");
    }
}
