package game.battlefieldbysocket.D_frameworksAndDrivers.ui;

import game.battlefieldbysocket.C_adapters.AdapterOutputPort;
import game.battlefieldbysocket.C_adapters.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import java.awt.Point;
import javafx.scene.control.TextArea;

public class JavaFxView implements AdapterOutputPort {

    Controller controller;

    private final int fieldSideLength = 100;
    private final BorderPane root = new BorderPane();
    private final GridPane grid = new GridPane();
    private final TextArea logArea = new TextArea(); // Anzeigefeld für Logs

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Scene createScene() {

        int n = controller.getSideLengthFromGameConf();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                Region field = createClickableField(col, row);
                grid.add(field, col, row);
            }
        }

        logArea.setEditable(false); // Nur anzeigen, nicht bearbeiten
        logArea.setPrefHeight(100); // Höhe für Log-Bereich

        root.setCenter(grid);
        root.setBottom(logArea); // Log-Feld unten platzieren


        return new Scene(root, n * fieldSideLength, n * fieldSideLength);
    }

    private Region createClickableField(int x, int y) {
        Region region = new Region();
        region.setPrefSize(fieldSideLength, fieldSideLength);
        region.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");

        // Klick-Event
        region.setOnMouseClicked(e -> {
            Point point = new Point(x, y);
            controller.handleFieldClick(point);
        });

        return region;
    }


    @Override
    public void setFieldLabel(Point point, String label) {
        // logArea.clear();
        logArea.setText(label);
    }

    @Override
    public void presentQuestionWhereToShoot() {

    }

    public Point setOpenForInputAndGetIt(){

        return null;
    }


}
