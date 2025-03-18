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
     * Parst den eingehenden Befehl und leitet ihn weiter an die Spiellogik.
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
                    // Hier könntest Du Deine Logik für das Spielbeitreten aufrufen:
                    System.out.println("Spieler " + playerId + " tritt bei: " + playerName);
                    // z.B.: gameLogic.joinGame(playerId, playerName);
                } else {
                    System.out.println("Ungültiges JOIN-Kommando: " + message);
                }
                break;
            case "SHOOT":
                if (tokens.length >= 3) {
                    try {
                        int x = Integer.parseInt(tokens[1]);
                        int y = Integer.parseInt(tokens[2]);
                        // Hier rufst Du Deine Schuss-Logik auf, z.B.:
                        System.out.println("Spieler " + playerId + " schießt auf (" + x + ", " + y + ")");
                        // z.B.: gameLogic.shoot(playerId, new Point(x, y));
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
