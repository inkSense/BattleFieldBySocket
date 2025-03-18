package game.battlefieldbysocket.C_adapters;

import java.awt.*;

public interface AdapterOutputPort {

    void setController(Controller controller);
    void presentQuestionWhereToShoot();

    Point setOpenForInputAndGetIt();

    void setFieldLabel(Point point, String label);


}
