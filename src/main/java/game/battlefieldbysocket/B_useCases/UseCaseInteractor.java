package game.battlefieldbysocket.B_useCases;

import game.battlefieldbysocket.A_entities.objectsAndDataStructures.*;

import java.awt.*;
import java.util.logging.Logger;

public class UseCaseInteractor {
    UseCaseOutputPort useCaseOutputPort;
    Game game;

    private static final Logger log = Logger.getLogger(UseCaseInteractor.class.getName());

    public UseCaseInteractor() {
    }

    public UseCaseInteractor(UseCaseOutputPort port) {
        this.useCaseOutputPort = port;
        this.game = new SetGameUseCase().makeGame();
    }


    public void sendShot(Point point) {

    }


    public void process(Point point){
        String pointString = point.toString();
        useCaseOutputPort.presentFieldLabel(point, pointString);
    }



    public void processShot(Point point){

        var playGame = new PlayGameUseCase(game);
        var report = new ReportStatusUseCase(game);


        if(playGame.isValidOnBoardOfPlayer(point, 0)) {
            playGame.putShotToBoardOfPlayer(point, 0);
            playGame.updateShipsOfPlayer(point, 0);
        } else {
            log.info("No Valid Shot. Choose again.");
        }

//        report.reportStatusOfBoards();
//        report.reportStatusOfShips();


        if(playGame.isOver()){
            log.info("Isch over.");
        }

        useCaseOutputPort.presentGame(game);
    }










    //public increaseNumberOfHitSegmentsIfHit

    public int getSideLengthFromGameConf(){
        return GameConf.fieldSideLength;
    }

}
