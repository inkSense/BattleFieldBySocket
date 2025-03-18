package game.battlefieldbysocket.B_useCases;

import game.battlefieldbysocket.A_entities.objectsAndDataStructures.GameConf;

import java.awt.*;

public class UseCaseInteractor {
    UseCaseOutputPort useCaseOutputPort;

    public UseCaseInteractor(UseCaseOutputPort port) {
        this.useCaseOutputPort = port;
    }


    public void sendShot(Point point) {

    }

    public void presentGame(){
        useCaseOutputPort.presentGamePlayer1();
        useCaseOutputPort.presentGamePlayer2();
    }

    public void process(Point point){
        String pointString = point.toString();
        useCaseOutputPort.presentFieldLabel(point, pointString);
    }

    public int getSideLengthFromGameConf(){
        return GameConf.fieldSideLength;
    }

}
