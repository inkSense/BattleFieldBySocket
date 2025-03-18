package game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket;

import java.util.LinkedList;
import java.util.Queue;

public class LocalMessageChannel implements SocketMessageChannel {
    private Queue<String> messages = new LinkedList<>();

    @Override
    public void send(String message) {
        // Einfach in die Queue packen
        messages.offer(message);
    }

    @Override
    public String receive() {
        // Falls leer, kommt null zurück oder wir blockieren in echt
        return messages.poll();
    }
}

