package game.battlefieldbysocket.B_useCases;

import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Game;

import java.awt.*;

public interface UseCaseOutputPort {

    void presentGamePlayer1();
    void presentGamePlayer2();

    Point askPlayerWhereToShoot(int playerNumber);

    void presentFieldLabel(Point point, String label);

    void presentGame(Game game);

    void setFieldToHit(Point point);

    void setFieldToShipLabel(Point point);

}
