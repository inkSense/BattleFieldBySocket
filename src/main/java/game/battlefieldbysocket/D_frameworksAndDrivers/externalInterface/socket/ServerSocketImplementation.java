package game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Point;

/**
 * Minimaler Socket-Server, der zwei Spieler akzeptiert und
 * ein einfaches textbasiertes Protokoll abwickelt:
 *
 * 1) Beide Spieler schicken "JOIN <Name>"
 * 2) Server antwortet mit "WELCOME <PlayerID>"
 * 3) Sobald 2 Spieler da sind: "START"
 * 4) Dann im Wechsel:
 *    - "YOUR_TURN"
 *    - Client: "SHOOT x y"
 *    - Server: "RESULT MISS" (oder "HIT/SUNK")
 *    - Server: "OPPONENT_MOVE x y MISS"
 *    - Nächster Zug an den anderen Spieler usw.
 *
 * Diese Klasse ist zur Demonstration komplett in sich
 * – in der Praxis solltest Du die Logik in UseCases / Adaptern auslagern.
 */
public class ServerSocketImplementation {

    private final int port;
    private ServerSocket serverSocket;

    private Socket playerOneSocket;
    private PrintWriter out1;
    private BufferedReader in1;

    private Socket playerTwoSocket;
    private PrintWriter out2;
    private BufferedReader in2;

    private String playerOneName;
    private String playerTwoName;

    public ServerSocketImplementation(int port) {
        this.port = port;
    }

    /**
     * Startet den Server und nimmt genau 2 Spieler an.
     * Danach wird eine simple Zug-Schleife gestartet.
     */
    public void startServer() {
        try {
            // ServerSocket öffnen
            serverSocket = new ServerSocket(port);
            System.out.println("Server läuft auf Port " + port);
            System.out.println("Warte auf Spieler 1...");

            // --- Spieler 1 verbinden ---
            playerOneSocket = serverSocket.accept();
            System.out.println("Spieler 1 verbunden: " + playerOneSocket.getInetAddress());

            // Streams vorbereiten
            in1 = new BufferedReader(new InputStreamReader(playerOneSocket.getInputStream()));
            out1 = new PrintWriter(playerOneSocket.getOutputStream(), true);

            // JOIN-Nachricht lesen
            String joinMessage1 = in1.readLine();
            if (joinMessage1 != null && joinMessage1.startsWith("JOIN ")) {
                playerOneName = joinMessage1.substring(5).trim();
                out1.println("WELCOME 1");
                // Spieler 1 muss warten, bis Spieler 2 da ist
                out1.println("WAIT");
                System.out.println("Spieler 1 heißt: " + playerOneName);
            } else {
                out1.println("ERROR Unexpected command");
                playerOneSocket.close();
                return;
            }

            // --- Spieler 2 verbinden ---
            System.out.println("Warte auf Spieler 2...");
            playerTwoSocket = serverSocket.accept();
            System.out.println("Spieler 2 verbunden: " + playerTwoSocket.getInetAddress());

            // Streams vorbereiten
            in2 = new BufferedReader(new InputStreamReader(playerTwoSocket.getInputStream()));
            out2 = new PrintWriter(playerTwoSocket.getOutputStream(), true);

            // JOIN-Nachricht lesen
            String joinMessage2 = in2.readLine();
            if (joinMessage2 != null && joinMessage2.startsWith("JOIN ")) {
                playerTwoName = joinMessage2.substring(5).trim();
                out2.println("WELCOME 2");
                System.out.println("Spieler 2 heißt: " + playerTwoName);
            } else {
                out2.println("ERROR Unexpected command");
                playerTwoSocket.close();
                return;
            }

            // Beide Spieler da: Spiel starten
            out1.println("START");
            out2.println("START");

            // Einfacher Rundentakt: abwechselnd Spieler 1 und Spieler 2
            // (solange kein GameOver)
            boolean gameOver = false;
            int currentPlayer = 1; // beginnt bei Spieler 1

            while (!gameOver) {
                if (currentPlayer == 1) {
                    out1.println("YOUR_TURN");
                    String commandP1 = in1.readLine();
                    if (commandP1 == null) {
                        // Spieler 1 hat Verbindung getrennt
                        out2.println("GAMEOVER WIN Opponent disconnected");
                        break;
                    }
                    handleCommand(commandP1, 1);
                    currentPlayer = 2; // Nächster Zug Spieler 2
                } else {
                    out2.println("YOUR_TURN");
                    String commandP2 = in2.readLine();
                    if (commandP2 == null) {
                        // Spieler 2 hat Verbindung getrennt
                        out1.println("GAMEOVER WIN Opponent disconnected");
                        break;
                    }
                    handleCommand(commandP2, 2);
                    currentPlayer = 1; // Nächster Zug Spieler 1
                }
            }

            // Ressourcen schließen
            closeConnections();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Einfache Befehlsverarbeitung (Parsing).
     * Hier nur "SHOOT x y" als Beispiel.
     *
     * @param command   Eingehender Text
     * @param playerNum 1 oder 2
     */
    private void handleCommand(String command, int playerNum) {
        if (command.startsWith("SHOOT ")) {
            // Beispiel: "SHOOT 3 5"
            String coords = command.substring(6).trim();
            String[] parts = coords.split("\\s+");
            if (parts.length == 2) {
                try {
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);

                    // Hier könntest du die UseCase-Logik anstoßen (z.B. shootUseCase.shoot(playerNum, x, y))
                    // Wir machen hier nur ein Dummy-Ergebnis:
                    boolean hit = false; // z.B. Logik: habe ich was getroffen?

                    sendResult(playerNum, x, y, hit);
                } catch (NumberFormatException e) {
                    sendError(playerNum, "Invalid coordinates");
                }
            } else {
                sendError(playerNum, "Invalid SHOOT command format");
            }
        } else {
            // Unbekannter Befehl
            sendError(playerNum, "Unknown command");
        }
    }

    /**
     * Sendet das Ergebnis an den Schießenden und eine OPPONENT_MOVE-Meldung an den anderen.
     *
     * @param shooter  1 oder 2
     * @param x        Koordinate
     * @param y        Koordinate
     * @param hit      Treffer oder nicht
     */
    private void sendResult(int shooter, int x, int y, boolean hit) {
        PrintWriter outShooter = (shooter == 1) ? out1 : out2;
        PrintWriter outOther   = (shooter == 1) ? out2 : out1;

        if (hit) {
            outShooter.println("RESULT HIT");
            outOther.println("OPPONENT_MOVE " + x + " " + y + " HIT");
        } else {
            outShooter.println("RESULT MISS");
            outOther.println("OPPONENT_MOVE " + x + " " + y + " MISS");
        }
        // Hier könntest du erkennen, ob ein Schiff versenkt oder das Spiel vorbei ist.
        // Ggf. "GAMEOVER ..." schicken usw.
    }

    private void sendError(int playerNum, String errorMsg) {
        PrintWriter out = (playerNum == 1) ? out1 : out2;
        out.println("ERROR " + errorMsg);
    }

    /**
     * Verbindungen und ServerSocket schließen.
     */
    private void closeConnections() {
        try {
            if (playerOneSocket != null && !playerOneSocket.isClosed()) {
                playerOneSocket.close();
            }
            if (playerTwoSocket != null && !playerTwoSocket.isClosed()) {
                playerTwoSocket.close();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
