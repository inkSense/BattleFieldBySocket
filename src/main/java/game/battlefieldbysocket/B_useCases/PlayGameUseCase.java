package game.battlefieldbysocket.B_useCases;

import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Cell;
import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Game;
import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Player;
import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Ship;

import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

public class PlayGameUseCase {
    Game game;

    UseCaseOutputPort outputPort;

    private static final Logger log = Logger.getLogger(PlayGameUseCase.class.getName());

    public void setOutputPort(UseCaseOutputPort outputPort) {
        this.outputPort = outputPort;
    }

    PlayGameUseCase(Game game){
        this.game = game;
    }

    void play(){
        while(!isOver()){
            letPlayerMakeTurn(game.getCurrentPlayerNumber());
            game.switchCurrentPlayer();
        }
    }

    void letPlayerMakeTurn(int playerNumber){
        Point shot = getShotOfPlayer(playerNumber);
        Player oppositePlayer = game.getOppositePlayer();
        oppositePlayer.getBoard().setHit(shot);
        oppositePlayer.update();
    }

    Point getShotOfPlayer(int playerNumber){
        Point point;
        do {
            point = outputPort.askPlayerWhereToShoot(playerNumber);
        } while (!isValidOnBoardOfPlayer(point, playerNumber));
        return point;
    }

    boolean isValidOnBoardOfPlayer(Point point, int playerNumber){
        Player player = game.getPlayer(playerNumber);
        boolean hasPoint = player.getBoard().hasPoint(point);
        boolean pointFree = !player.getBoard().isAlreadyHit(point);
        return hasPoint && pointFree;
    }

    public void putShotToBoardOfOppositePlayer(Point shot) {
        for(Cell cell: game.getOppositePlayer().getBoard().getCells()) {
            if (cell.position.equals(shot)) {
                cell.hit = true;
                break;
            }
        }
    };

    public void putShotToBoardOfPlayer(Point shot, int playerNumber) {
        for(Cell cell: game.getPlayer(playerNumber).getBoard().getCells()) {
            if (cell.position.equals(shot)) {
                cell.hit = true;
                break;
            }
        }
    };

    public void updateShipsOfPlayer(Point newShot, int playerNumber) {
        List<Ship> ships = game.getPlayer(playerNumber).getShips();
        for(Ship ship: ships){
            for(Point segment : ship.getSegments()){
                if(segment.equals(newShot)){
                    ship.incrementNumberOfHitSegments();
                }
            }
        }
    }



    public boolean isOver(){
        for(Player player: game.getPlayers()){
            if(game.allShipsOfPlayerSunk(player)){
                log.info("Player is down: " + player.getTeamName());
                return true;
            }
        }
        return false;
    }



}
