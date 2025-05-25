package game.battlefieldforweb;

import game.battlefieldforweb.C_adapters.ClientController;
import game.battlefieldforweb.D_frameworksAndDrivers.externalInterface.socket.ClientSocketImplementation;
import game.battlefieldforweb.D_frameworksAndDrivers.ui.SwingView;

public class ClientMain {
    public static void main(String[] args) {
        // Erstelle die UI
        SwingView view = new SwingView();

        // Erstelle die Netzwerkverbindung
        ClientSocketImplementation network = new ClientSocketImplementation("192.168.178.132", 12345);

        // Erstelle den ClientController und verknüpfe ihn mit der UI und dem Netzwerkadapter
        ClientController clientController = new ClientController(network, view);
        view.setController(clientController);

        // Setze den MessageListener so, dass eingehende Nachrichten an den ClientController weitergegeben werden
        network.setMessageListener(message -> clientController.processServerMessage(message));

        // Starte den Netzwerkadapter
        network.start();

        // Starte die UI
        view.createAndShowGUI();
    }
}
