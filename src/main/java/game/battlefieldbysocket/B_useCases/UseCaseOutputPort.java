package game.battlefieldbysocket.B_useCases;

import java.awt.*;

public interface UseCaseOutputPort {

    void presentGamePlayer1();
    void presentGamePlayer2();

    Point askPlayerWhereToShoot(String teamName);

    void presentFieldLabel(Point point, String label);

}
