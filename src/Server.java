import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private int portNumber;
    private List<ClientConnectionRunnable> clientRunnables;
    
    public Server(int portNumber) {
        this.portNumber = portNumber;
        this.clientRunnables = new ArrayList<>();
    }
    
    public void runServer() {
        try(ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println(serverSocket);
            runClientAcceptanceLoop(serverSocket);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private class ClientConnectionRunnable implements Runnable {
        Socket socket;
        
        public ClientConnectionRunnable(Socket socket) {
            this.socket = socket;
        }
        
        public Socket getSocket() {
            return socket;
        }
        
        public void run() {
            System.out.println("Client connection established");
            try(
                Socket clientSocket = socket;
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                String inputLine;
                
                while((inputLine = in.readLine()) != null) {
                    sendOutMessage("Server: " + inputLine);
                    if(inputLine.equals("Bye.")) {
                        break;
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void sendOutMessage(String message) {
        for(ClientConnectionRunnable clientRunnable : clientRunnables) {
            PrintWriter out = null;
            try {
                out = new PrintWriter(clientRunnable.getSocket().getOutputStream(), true);
                out.println(message);
            } catch(IOException e) {
                e.printStackTrace();
            } finally {
                if(out != null) {
                    out.close();
                }
            }
        }
    }
    
    public void runClientAcceptanceLoop(ServerSocket serverSocket) {
        while(true) {
            try {
                ClientConnectionRunnable clientRunnable = new ClientConnectionRunnable(serverSocket.accept());
                clientRunnables.add(clientRunnable);
                Thread t = new Thread(clientRunnable);
                t.start();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        int portNumber = Integer.parseInt(args[0]);
        
        Server server = new Server(portNumber);
        server.runServer();
    }
}
