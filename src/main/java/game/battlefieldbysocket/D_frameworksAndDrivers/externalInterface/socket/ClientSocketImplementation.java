package game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketImplementation {

    private final String host;
    private final int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    // Listener für eingehende Nachrichten
    private ClientMessageListener messageListener;

    public ClientSocketImplementation(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setMessageListener(ClientMessageListener listener) {
        this.messageListener = listener;
    }

    public void start() {
        try {
            // Verbindung zum Server herstellen
            socket = new Socket(host, port);
            System.out.println("Verbunden mit Server: " + host + ":" + port);

            // Streams initialisieren
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Sende initialen JOIN-Befehl
            out.println("JOIN MeinName");

            // Starte einen separaten Thread zum kontinuierlichen Lesen vom Server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        // Statt die Nachricht direkt auszugeben, an den Listener weiterreichen
                        if (messageListener != null) {
                            messageListener.onMessageReceived(message);
                        } else {
                            System.out.println("Server: " + message);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Fehler beim Lesen vom Server: " + e.getMessage());
                }
            }).start();

            // Optional: Falls weitere Interaktionen notwendig sind, kann hier eine Eingabeschleife stehen.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    // Methode zum Schließen der Verbindung (optional)
    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Verbindung geschlossen.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
