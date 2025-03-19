package game.battlefieldbysocket.C_adapters;

import java.awt.*;

public interface Controller {
    int getSideLengthFromGameConf();
    void handleFieldClick(Point point);
}
