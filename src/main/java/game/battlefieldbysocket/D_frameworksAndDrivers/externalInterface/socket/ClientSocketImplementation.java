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

    public ClientSocketImplementation(String host, int port) {
        this.host = host;
        this.port = port;
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
                        System.out.println("Server: " + message);
                        // Hier könntest Du das Parsing der Protokoll-Kommandos einbauen
                    }
                } catch (IOException e) {
                    System.out.println("Fehler beim Lesen vom Server: " + e.getMessage());
                }
            }).start();

            // Optional: Eingabe vom Benutzer lesen, um weitere Nachrichten zu senden
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
            }

            close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Verbindung geschlossen.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "192.168.178.132"; // IP-Adresse des Servers
        int port = 12345;
        ClientSocketImplementation client = new ClientSocketImplementation(host, port);
        client.start();
    }
}
