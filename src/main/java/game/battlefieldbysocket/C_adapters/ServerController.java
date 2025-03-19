package game.battlefieldbysocket.C_adapters;

import game.battlefieldbysocket.B_useCases.UseCaseInteractor;

import java.awt.*;

public class ServerController implements Controller {

    UseCaseInteractor useCaseInteractor;

    public ServerController(UseCaseInteractor useCaseInteractor) {
        this.useCaseInteractor = useCaseInteractor;
    }

    public void handleFieldClick(Point point) {

        useCaseInteractor.processShot(point);


    }

    public int getSideLengthFromGameConf(){
        return useCaseInteractor.getSideLengthFromGameConf();

    }



}
