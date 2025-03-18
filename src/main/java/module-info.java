module game.battlefieldbysocket {
    requires javafx.controls;
    requires java.desktop;
    requires java.logging;

    opens game.battlefieldbysocket.D_frameworksAndDrivers.ui to javafx.graphics;
    exports game.battlefieldbysocket;
}