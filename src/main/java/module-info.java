module game.battlefieldforweb {
    requires javafx.controls;
    requires java.desktop;
    requires java.logging;

    opens game.battlefieldforweb.D_frameworksAndDrivers.ui to javafx.graphics;
    exports game.battlefieldforweb;
}