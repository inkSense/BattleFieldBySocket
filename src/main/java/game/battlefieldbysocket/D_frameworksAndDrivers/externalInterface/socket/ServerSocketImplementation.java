package game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketImplementation {

    private final int port;
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private int nextPlayerId = 1;

    public ServerSocketImplementation(int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server läuft auf Port " + port);

            // Endlosschleife: Warte auf neue Clients
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Neuer Client verbunden: " + clientSocket.getInetAddress());

                // Erzeuge einen neuen ClientHandler und übergebe 'this'
                ClientHandler handler = new ClientHandler(clientSocket, nextPlayerId++, this);
                clients.add(handler);
                new Thread(handler).start();

                // Sende Begrüßung an den Client
                handler.sendMessage("WELCOME " + (nextPlayerId - 1));

                // Sobald zwei Spieler verbunden sind, starte das Spiel
                if (clients.size() == 2) {
                    broadcast("START");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sendet eine Nachricht an alle verbundenen Clients.
     */
    public void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    /**
     * Sendet eine Nachricht an alle Clients außer dem, dessen playerId senderId entspricht.
     */
    public void broadcastExcept(int senderId, String message) {
        for (ClientHandler client : clients) {
            if (client.getPlayerId() != senderId) {
                client.sendMessage(message);
            }
        }
    }
}
