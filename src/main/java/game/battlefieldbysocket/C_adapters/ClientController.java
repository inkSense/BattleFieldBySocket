package game.battlefieldbysocket.C_adapters;

import game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket.ClientSocketImplementation;
import game.battlefieldbysocket.D_frameworksAndDrivers.ui.SwingView;
import java.awt.*;

public class ClientController {

    private final ClientSocketImplementation network;
    private final SwingView swingView;

    public ClientController(ClientSocketImplementation network, SwingView swingView) {
        this.network = network;
        this.swingView = swingView;
    }

    /**
     * Wird von der SwingView aufgerufen, wenn ein Feld angeklickt wird.
     * Anstatt die lokale Spiellogik aufzurufen, senden wir jetzt einen
     * Netzwerkbefehl an den Server.
     */
    public void handleFieldClick(Point point) {
        // Sende den Befehl an den Server, z. B. "SHOOT x y"
        String command = "SHOOT " + point.x + " " + point.y;
        network.sendMessage(command);
        System.out.println("ClientController: Gesendet -> " + command);
    }

    /**
     * Beispiel: Diese Methode wird aufgerufen, wenn der Server eine Nachricht sendet,
     * die einen Feld-Update beinhaltet, z. B. "OPPONENT_MOVE x y HIT".
     */
    public void processServerMessage(String message) {
        // Einfaches Parsing; in der Praxis wäre hier ein robustes Parsing sinnvoll.
        String[] tokens = message.split("\\s+");
        if (tokens.length >= 4 && tokens[0].equals("OPPONENT_MOVE")) {
            try {
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                String label = tokens[3];
                // Aktualisiere die UI lokal
                swingView.markFieldWithLabel(new Point(x, y), label);
                System.out.println("ClientController: Feld (" + x + "," + y + ") mit " + label + " markiert.");
            } catch (NumberFormatException e) {
                System.out.println("ClientController: Fehler beim Parsen der Koordinaten aus: " + message);
            }
        }
        // Weitere Nachrichten-Typen können hier verarbeitet werden.
    }

    /**
     * Beispiel: Liefert die Spielfeldgröße. Diese Information könnte auch
     * direkt vom Server gesendet werden, hier als Platzhalter.
     */
    public int getSideLengthFromGameConf(){
        return 10;  // Beispielwert; in der Praxis von der Server-Konfiguration abhängig
    }
}
