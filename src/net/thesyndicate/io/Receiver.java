package net.thesyndicate.io;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dboolbe on 9/12/21.
 */
public class Receiver extends Thread {
    private List<String> listQueue;
    private boolean running;
    
    public Receiver() {
        listQueue = Collections.synchronizedList(new LinkedList<>());
    }
    
    public void addMessage(String message) {
        listQueue.add(message);
    }
    
    public String removeMessage() {
        return listQueue.remove(0);
    }
    
    public int queueSize() {
        return listQueue.size();
    }
    
    private void processMessage(String message) {
        switch(message) {
            case "end":
                running = false;
                break;
            default:
                System.out.println("> " + message);
        }
    }

    public void run() {
        while (running) {
            if (queueSize() > 0) {
                processMessage(removeMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        
        Object expected, actual;
        
        Receiver receiver = new Receiver();
        
        expected = 0;
        actual = receiver.queueSize();
        assert actual == expected : "Queue size expected: " + expected + " actual: " + actual;
        
        receiver.addMessage("Bob");
        
        expected = 1;
        actual = receiver.queueSize();
        assert actual == expected : "Queue size expected: " + expected + " actual: " + actual;
        
        receiver.addMessage("Rob");
        receiver.addMessage("Jim");
        receiver.addMessage("end");
        
        expected = 4;
        actual = receiver.queueSize();
        assert actual == expected : "Queue size expected: " + expected + " actual: " + actual;
        
        receiver.start();
    }
}
