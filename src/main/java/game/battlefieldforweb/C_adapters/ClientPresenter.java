package game.battlefieldforweb.C_adapters;

import game.battlefieldforweb.A_entities.objectsAndDataStructures.Game;
import game.battlefieldforweb.B_useCases.UseCaseOutputPort;
import game.battlefieldforweb.D_frameworksAndDrivers.externalInterface.socket.ClientSocketImplementation;

import java.awt.*;

public class ClientPresenter implements UseCaseOutputPort {
    // Wir gehen davon aus, dass ClientSocketImplementation bereits eine sendMessage(String) Methode hat.
    private final ClientSocketImplementation clientConnection;

    public ClientPresenter(ClientSocketImplementation clientConnection) {
        this.clientConnection = clientConnection;
    }

    /**
     * Falls der Use Case den Schützen nach einer Eingabe fragt.
     * Hier implementieren wir z. B. keine Eingabe und geben null zurück.
     */
    @Override
    public Point askPlayerWhereToShoot(int playerNumber) {
        // Diese Methode wird im reinen Client-Server-Szenario möglicherweise nicht benötigt,
        // da die UI (SwingView) lokal die Eingabe übernimmt.
        return null;
    }

    /**
     * Übersetzt ein Update für ein bestimmtes Feld in eine Netzwerk-Nachricht.
     * Beispiel: "MARK_FIELD x y label"
     */
    @Override
    public void presentFieldLabel(Point p, String label) {
        String msg = "MARK_FIELD " + p.x + " " + p.y + " " + label;
        clientConnection.sendMessage(msg);
    }

    /**
     * Präsentiert den gesamten Spielzustand.
     * Hier könnte man den kompletten Zustand serialisieren und senden.
     * Für dieses Beispiel senden wir einfach eine textuelle Repräsentation.
     */
    @Override
    public void presentGame(Game game) {
        // Dies ist ein sehr vereinfachter Ansatz – in einer echten Anwendung
        // würdest Du ein geeignetes Format (z. B. JSON) verwenden.
        String msg = "GAME_STATE " + game.toString();
        clientConnection.sendMessage(msg);
    }

    /**
     * Speziell, um ein Feld als "getroffen" (Hit) zu markieren.
     * Übersetzt in eine Nachricht, z. B. "SET_FIELD_TO_HIT x y".
     */
    @Override
    public void setFieldToHit(Point point) {
        String msg = "SET_FIELD_TO_HIT " + point.x + " " + point.y;
        clientConnection.sendMessage(msg);
    }
}
