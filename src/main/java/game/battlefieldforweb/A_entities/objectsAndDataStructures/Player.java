package game.battlefieldforweb.A_entities.objectsAndDataStructures;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private Board board;
    private List<Ship> ships ;
    private String teamName;

    public Player(Board board, String teamName) {
        this.board = board;
        this.teamName = teamName;
        this.ships = new ArrayList<>();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship) {
        this.ships.add(ship);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void update(){
        for(Cell cell : board.getCells()){
            for(Ship ship: ships) {
                for(Point segment : ship.getSegments()){
                    if(segment == cell.position){
                        cell.hit = true;
                        ship.incrementNumberOfHitSegments();
                    }
                }
            }
        }
    }

}
