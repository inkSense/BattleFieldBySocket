package game.battlefieldforweb;

import game.battlefieldforweb.B_useCases.UseCaseInteractor;
import game.battlefieldforweb.C_adapters.ServerController;
import game.battlefieldforweb.C_adapters.Presenter;
//import game.battlefieldbysocket.D_frameworksAndDrivers.ui.JavaFxView;
//import javafx.scene.Scene;
import javafx.application.Application;
import javafx.stage.Stage;
import game.battlefieldforweb.C_adapters.AdapterOutputPort;
import game.battlefieldforweb.D_frameworksAndDrivers.ui.SwingView;




import javax.swing.*;

public class Main extends Application {

    private static final boolean USE_SWING = true;

    @Override
    public void start(Stage primaryStage) {
//        JavaFxView javaFxView = new JavaFxView();
//        setUp(javaFxView);
//        Scene scene = javaFxView.createScene();
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Battle Field");
//        primaryStage.show();
    }

    private static void startSwing(){
        SwingUtilities.invokeLater(() -> {
            SwingView swingView = new SwingView();
            Main main = new Main();
            main.setUp(swingView);
            swingView.createAndShowGUI();
        });
    }

    private void setUp(AdapterOutputPort view) {
        Presenter presenter = new Presenter(view);
        UseCaseInteractor useCaseInteractor = new UseCaseInteractor(presenter);
        ServerController serverController = new ServerController(useCaseInteractor);
        view.setController(serverController);
    }

    public static void main(String[] args) {
        if (USE_SWING) {
            startSwing();
        } else {
            launch();
        }
    }

}
