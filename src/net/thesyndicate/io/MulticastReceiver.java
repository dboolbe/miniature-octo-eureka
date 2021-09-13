package net.thesyndicate.io;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by dboolbe on 9/12/21.
 */
public class MulticastReceiver extends Thread {
    private String hostName;
    private int portNumber;
    
    private MulticastSocket socket = null;
    private byte[] buf = new byte[256];
    private InetAddress group = null;
    private boolean running = false;
    
    public MulticastReceiver(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }
    
    private void establishConnection() {
        try {
            socket = new MulticastSocket(portNumber);
            group = InetAddress.getByName(hostName);
            socket.joinGroup(group);
            running = true;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private void terminateConnection() {
        try {
            socket.leaveGroup(group);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
    
    private String receiveMessage() {
        String message = null;
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            message = new String(packet.getData(), 0, packet.getLength());
        } catch(IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void run() {
        establishConnection();
        while (running) {
            String received = receiveMessage();
            System.out.println("> " + received);
            if ("end".equals(received)) {
                running = false;
            }
        }
        terminateConnection();
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        
        MulticastReceiver multicastReceiver = new MulticastReceiver("230.0.0.0", 4446);
        multicastReceiver.start();
    }
}
