package game.battlefieldbysocket.A_entities.objectsAndDataStructures;

import java.util.List;
import java.awt.*;

public class Ship {
    private final String teamName;
    private final int numberOfSegments;
    private int numberOfHitSegments;
    private List<Point> segments;

    Ship(String teamName, int numberOfSegments){
        this.teamName = teamName;
        this.numberOfSegments = numberOfSegments;
        this.numberOfHitSegments = 0;
    }

    public String getTeamName() {
        return teamName;
    }


    public int getNumberOfSegments() {
        return numberOfSegments;
    }


    public int getNumberOfHitSegments() {
        return numberOfHitSegments;
    }

    public List<Point> getSegments() {
        return segments;
    }

    public void setSegments(List<Point> segments) {
        this.segments = segments;
    }

    public void incrementNumberOfHitSegments() {
        this.numberOfHitSegments += 1;
    }

    public boolean isSunk(){
        return numberOfSegments == numberOfHitSegments;
    }






}
