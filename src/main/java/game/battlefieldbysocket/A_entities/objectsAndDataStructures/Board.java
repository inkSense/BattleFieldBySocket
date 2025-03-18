package game.battlefieldbysocket.A_entities.objectsAndDataStructures;

import java.awt.*;
import java.util.List;

public class Board {
    private final int sideLength;

    private List<Cell> cells;

    public Board(int sideLength){

        this.sideLength = sideLength;

        buildCells();

    }

    public int getSideLength() {
        return sideLength;
    }

    public List<Cell> getCells() {
        return cells;
    }

    void buildCells(){
        for(int x = 0; x < sideLength; x++) {
            for (int y = 0; y < sideLength; y++) {
                cells.add(new Cell(new Point(x, y)));
            }
        }
    }

    public boolean isAlreadyHit(Point point) {
        for(Cell cell : cells){
            if(cell.position == point){
                return cell.hit;
            }
        }
        return false; //Nur für Punkte, die es gar nicht auf dem Board gibt.
    }

    public boolean hasPoint(Point point){
        for(Cell cell : cells){
            if(cell.position == point){
                return true;
            }
        }
        return false;
    }

    public void setHit(Point point){
        for(Cell cell : cells){
            if(cell.position == point){
                cell.hit = true;
            }
        }
    }



}
