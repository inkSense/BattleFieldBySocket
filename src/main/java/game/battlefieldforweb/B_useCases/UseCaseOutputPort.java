package game.battlefieldforweb.B_useCases;

import game.battlefieldforweb.A_entities.objectsAndDataStructures.Game;

import java.awt.*;

public interface UseCaseOutputPort {

    Point askPlayerWhereToShoot(int playerNumber);

    void presentFieldLabel(Point point, String label);

    void presentGame(Game game);

    void setFieldToHit(Point point);


}
