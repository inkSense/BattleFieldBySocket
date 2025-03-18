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

            // Beispiel: Sende den JOIN-Befehl an den Server
            out.println("JOIN MeinName");
            String response = in.readLine();
            System.out.println("Antwort vom Server: " + response);

            // Hier kannst du weitere Nachrichten austauschen:
            // out.println("Weitere Nachricht");
            // System.out.println("Serverantwort: " + in.readLine());

            // Nach dem Austausch kannst du die Verbindung schließen
            close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("Verbindung geschlossen.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Main-Methode zum Starten des Clients
    public static void main(String[] args) {
        String host = "localhost"; // oder die IP-Adresse des Servers
        int port = 12345;
        ClientSocketImplementation client = new ClientSocketImplementation(host, port);
        client.start();
    }
}
