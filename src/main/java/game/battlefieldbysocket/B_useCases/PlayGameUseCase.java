package game.battlefieldbysocket.B_useCases;

import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Game;
import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Player;

import java.awt.*;

public class PlayGameUseCase {
    Game game;

    UseCaseOutputPort outputPort;

    public void setOutputPort(UseCaseOutputPort outputPort) {
        this.outputPort = outputPort;
    }

    PlayGameUseCase(Game game){
        this.game = game;
    }

    void play(){
        while(!game.isOver()){
            letPlayerMakeTurn(game.getCurrentPlayer());
            game.switchCurrentPlayer();
        }
    }

    void letPlayerMakeTurn(Player player){
        Point shot = getShotOfPlayer(player);
        Player oppositePlayer = game.getOppositePlayer(player);
        oppositePlayer.getBoard().setHit(shot);
        oppositePlayer.update();
    }

    Point getShotOfPlayer(Player player){
        Point point;
        do {
            point = outputPort.askPlayerWhereToShoot(player.getTeamName());
        } while (!isValidOnBoardOfPlayer(point));
        return point;
    }

    boolean isValidOnBoardOfPlayer(Point point){
        Player player = game.getCurrentPlayer();
        boolean hasPoint = player.getBoard().hasPoint(point);
        boolean pointFree = !player.getBoard().isAlreadyHit(point);
        return hasPoint && pointFree;
    }

    boolean isValidOnBoardOfPlayer2(Point point){
        boolean hasPoint = game.getPlayer2().getBoard().hasPoint(point);
        boolean pointFree = !game.getPlayer2().getBoard().isAlreadyHit(point);
        return hasPoint && pointFree;
    }

    boolean gameIsOver(){
        return false;
    }



}
