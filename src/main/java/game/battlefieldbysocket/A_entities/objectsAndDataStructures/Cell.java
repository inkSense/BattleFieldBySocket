package game.battlefieldbysocket.A_entities.objectsAndDataStructures;

import java.awt.*;

public class Cell {

    public Cell(Point position) {
        this.position = position;
        this.hit = false;
        this.occupied = false;
    }
    public Point position;
    public boolean hit;
    public boolean occupied;


}
