package game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private int playerId;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, int playerId) {
        this.socket = socket;
        this.playerId = playerId;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            String message;
            // Solange Nachrichten empfangen, verarbeiten wir sie
            while ((message = in.readLine()) != null) {
                System.out.println("Nachricht von Spieler " + playerId + ": " + message);
                // Hier könntest du die Nachrichten an die Spiellogik weiterleiten
                // oder spezifisch parsen (z.B. JOIN, SHOOT, etc.)
            }
        } catch (IOException e) {
            System.out.println("Verbindung zu Spieler " + playerId + " verloren.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // Ignorieren
            }
        }
    }
}

