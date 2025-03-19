package game.battlefieldbysocket;

import game.battlefieldbysocket.B_useCases.UseCaseInteractor;
import game.battlefieldbysocket.C_adapters.ClientController;
import game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket.ClientMessageListener;
import game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket.ClientSocketImplementation;
import game.battlefieldbysocket.D_frameworksAndDrivers.ui.SwingView;

public class ClientMain {
    public static void main(String[] args) {
        // Erstelle die UI
        SwingView view = new SwingView();

        // Erstelle die Netzwerkverbindung
        ClientSocketImplementation network = new ClientSocketImplementation("192.168.x.y", 12345);

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
