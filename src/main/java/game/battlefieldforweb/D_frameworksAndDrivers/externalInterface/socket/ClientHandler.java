package game.battlefieldforweb.D_frameworksAndDrivers.externalInterface.socket;

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
    private ServerSocketImplementation server; // Referenz auf den Server

    public ClientHandler(Socket socket, int playerId, ServerSocketImplementation server) {
        this.socket = socket;
        this.playerId = playerId;
        this.server = server;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Getter für playerId
    public int getPlayerId() {
        return playerId;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Nachricht von Spieler " + playerId + ": " + message);
                processCommand(message);
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

    /**
     * Parst den eingehenden Befehl und leitet ihn weiter an die Spiellogik bzw. an den anderen Client.
     */
    private void processCommand(String message) {
        String[] tokens = message.split("\\s+");
        if (tokens.length == 0) {
            return;
        }
        String command = tokens[0];
        switch (command) {
            case "JOIN":
                if (tokens.length >= 2) {
                    String playerName = tokens[1];
                    System.out.println("Spieler " + playerId + " tritt bei: " + playerName);
                    // Hier könntest Du z.B. gameLogic.joinGame(playerId, playerName) aufrufen
                } else {
                    System.out.println("Ungültiges JOIN-Kommando: " + message);
                }
                break;
            case "SHOOT":
                if (tokens.length >= 3) {
                    try {
                        int x = Integer.parseInt(tokens[1]);
                        int y = Integer.parseInt(tokens[2]);
                        System.out.println("Spieler " + playerId + " schießt auf (" + x + ", " + y + ")");
                        // Anstatt lokal zu verarbeiten, senden wir eine Nachricht an den anderen Client.
                        // Hier schicken wir z. B. "OPPONENT_MOVE x y X"
                        String opponentMessage = "OPPONENT_MOVE " + x + " " + y + " X";
                        server.broadcastExcept(playerId, opponentMessage);
                    } catch (NumberFormatException e) {
                        System.out.println("Ungültige Koordinaten im SHOOT-Kommando: " + message);
                    }
                } else {
                    System.out.println("Ungültiges SHOOT-Kommando: " + message);
                }
                break;
            default:
                System.out.println("Unbekannter Befehl: " + message);
                break;
        }
    }
}
