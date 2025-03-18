package game.battlefieldbysocket.B_useCases;

import game.battlefieldbysocket.A_entities.objectsAndDataStructures.*;

import java.awt.*;

public class UseCaseInteractor {
    UseCaseOutputPort useCaseOutputPort;
    Game game;

    public UseCaseInteractor(UseCaseOutputPort port) {
        this.useCaseOutputPort = port;
        this.game = new SetGameUseCase().makeGame();
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

    public void processShot(Point point){

        PlayGameUseCase playGame = new PlayGameUseCase(game);


        Player player2 = game.getPlayer2();
        for(Cell cell: player2.getBoard().getCells()){
            if(cell.position.equals(point)){
                cell.hit = true;
                if(cell.occupied){
                    for(Ship ship: player2.getShips()){
                        for(Point segment : ship.getSegments()){
                            if(segment.equals(point)){
                                ship.incrementNumberOfHitSegments();
                            }
                        }
                    }

                }
            }
        }



        useCaseOutputPort.presentGame(game);
    }

    public int getSideLengthFromGameConf(){
        return GameConf.fieldSideLength;
    }

}
