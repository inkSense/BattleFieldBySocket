package game.battlefieldbysocket.C_adapters;

import game.battlefieldbysocket.B_useCases.UseCaseOutputPort;

import java.awt.*;

public class ClientPresenter implements UseCaseOutputPort {
    private final SocketClientConnection clientConnection;

    @Override
    public void presentFieldLabel(Point p, String label) {
        // -> Übersetze in Nachricht, z.B. "MARK_FIELD x y label"
        // -> Schicke über clientConnection.
    }

    // usw.
}

