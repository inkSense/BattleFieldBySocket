package game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket;

import
import game.battlefieldbysocket.C_adapters.ClientController;
import game.battlefieldbysocket.D_frameworksAndDrivers.ui.SwingView;

public class ClientMain {
    public static void main(String[] args) {
        // Erstelle die UI
        SwingView view = new SwingView();

        // Erstelle die Netzwerkverbindung (ClientSocketImplementation sollte sendMessage() implementieren)
        ClientSocketImplementation network = new ClientSocketImplementation("192.168.x.y", 12345);
        network.start();  // Startet den Netzwerk-Thread, der Nachrichten empfängt

        // Erstelle den ClientController und setze ihn in die SwingView
        ClientController clientController = new ClientController(network, view);
        view.setController(clientController);

        // Starte die UI
        view.createAndShowGUI();

        // Jetzt kann der ClientController sowohl Benutzeraktionen (Klicks) verarbeiten
        // als auch eingehende Nachrichten vom Server verarbeiten.
    }
}

