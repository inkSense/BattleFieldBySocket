package game.battlefieldbysocket.A_entities.objectsAndDataStructures;

import java.awt.*;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class Game {

    private final Player player1;
    private final Player player2;
    private int currentPlayer;

    private Map conf;

    private static final Logger log = Logger.getLogger(Game.class.getName());

    public Game(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        Random random = new Random();
        currentPlayer = random.nextInt(2) + 1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
    public Board getBoardOfOppositePlayer(Player player){
        if(player == player1){
            return player2.getBoard();
        } else {
            return player1.getBoard();
        }
    }

    public Map getConf() {
        return conf;
    }

    public void setConf(Map conf) {
        this.conf = conf;
    }

    public Player getOppositePlayer(Player player){
        if(player == player1){
            return player2;
        } else {
            return player1;
        }
    }

    public void addShipForPlayer1(Ship ship){
        player1.addShip(ship);
    }

    public void addShipForPlayer2(Ship ship){
        player2.addShip(ship);

    }

    public boolean isOver(){
        if(allShipsOfPlayer1Sunk()){
            return true;
        }
        if(allShipsOfPlayer2Sunk()){
            return true;
        }
        return false;
    }

    public void setHitToPlayer1Board(Point point){
        player1.getBoard().setHit(point);
    }

    public boolean isAlreadyHitOnPlayer1Board(Point point){
        return player1.getBoard().isAlreadyHit(point);
    }

    public boolean isAlreadyHitOnPlayer2Board(Point point){
        return player2.getBoard().isAlreadyHit(point);
    }

    private boolean allShipsOfPlayer1Sunk(){
        for(Ship ship : player1.getShips()){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
    }

    private boolean allShipsOfPlayer2Sunk(){
        for(Ship ship : player2.getShips()){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
    }

    public Player getCurrentPlayer(){
        if( 1 == currentPlayer){
            return player1;
        } else {
            return player2;
        }
    }

    public void switchCurrentPlayer(){
        switch(currentPlayer){
            case 1:
                currentPlayer = 2; break;
            case 2:
                currentPlayer = 1; break;
        }
    }
}
