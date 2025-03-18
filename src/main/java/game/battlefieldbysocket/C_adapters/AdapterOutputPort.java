package game.battlefieldbysocket.C_adapters;

import java.awt.*;

public interface AdapterOutputPort {

    void setController(Controller controller);
    void presentQuestionWhereToShoot();

    Point setOpenForInputAndGetIt();

    void setTextFieldLabel(Point point, String label);

    void markFieldWithLabel(Point point, String label);


}
