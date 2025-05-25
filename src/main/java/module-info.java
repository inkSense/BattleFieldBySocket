module game.battlefieldforweb {
    requires javafx.controls;
    requires java.desktop;
    requires java.logging;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;

    opens game.battlefieldforweb.D_frameworksAndDrivers.ui to javafx.graphics;
    opens game.battlefieldforweb;
    opens game.battlefieldforweb.presentation;
    opens game.battlefieldforweb.A_entities;
    exports game.battlefieldforweb;
}