package game.battlefieldbysocket.A_entities.objectsAndDataStructures;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class Game {

    private List<Player> players;
    private int currentPlayer;

    private Map conf;

    private static final Logger log = Logger.getLogger(Game.class.getName());

    public Game(Player player1, Player player2){
        this.players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        Random random = new Random();
        currentPlayer = random.nextInt(2);
    }

    public List<Player> getPlayers(){
        return players;
    }

    public Player getPlayer(int playerNumber){
        return players.get(playerNumber);
    }

    public int getCurrentPlayerNumber(){
        return currentPlayer;
    }

    public Board getBoardOfCurrentPlayer(){
        return players.get(currentPlayer).getBoard();
    }
    public Board getBoardOfOppositePlayer(){
        return getOppositePlayer().getBoard();
    }


    public Map getConf() {
        return conf;
    }

    public void setConf(Map conf) {
        this.conf = conf;
    }

    public Player getOppositePlayer(){
        if(currentPlayer == 0){
            return players.get(1);
        } else {
            return players.get(0);
        }
    }

    public void addShipForPlayer1(Ship ship){
        players.get(0).addShip(ship);
    }

    public void addShipForPlayer2(Ship ship){
        players.get(1).addShip(ship);;

    }



    public void setHitToPlayer1Board(Point point){
        players.get(0).getBoard().setHit(point);
    }

    public boolean isAlreadyHitOnPlayer1Board(Point point){
        return players.get(0).getBoard().isAlreadyHit(point);
    }

    public boolean isAlreadyHitOnPlayer2Board(Point point){
        return players.get(1).getBoard().isAlreadyHit(point);
    }

    public boolean allShipsOfPlayerSunk(Player player){
        for(Ship ship : player.getShips()){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    public void switchCurrentPlayer(){
        currentPlayer = (currentPlayer + 1) % 2;
    }




}
