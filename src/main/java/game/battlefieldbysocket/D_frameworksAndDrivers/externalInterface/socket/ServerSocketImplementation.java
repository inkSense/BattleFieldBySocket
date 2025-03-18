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

            // Endlosschleife: Wartet auf neue Clients
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Neuer Client verbunden: " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket, nextPlayerId++);
                clients.add(handler);
                new Thread(handler).start();

                // Sende Begrüßung an den Client
                handler.sendMessage("WELCOME " + (nextPlayerId - 1));

                // Wenn zwei Spieler verbunden sind, können wir z.B. das Spiel starten:
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
    private void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
}
