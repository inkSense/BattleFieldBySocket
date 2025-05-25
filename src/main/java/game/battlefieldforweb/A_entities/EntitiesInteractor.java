package game.battlefieldforweb.A_entities;

import game.battlefieldforweb.A_entities.objectsAndDataStructures.Cell;

import java.awt.Point;
import java.util.*;
import java.util.List;

public class EntitiesInteractor {

    boolean isValid(List<Cell> cells){
        boolean neighbours = cellsContainOnlyNeighboursInARow(cells);
        boolean different = doesntHaveDoublets(cells);
        return  neighbours && different;
    }

    List<Cell> buildAllCells(Point bow, Point stern){
        List<Cell> cells = new ArrayList<>();

        if (bow.x == stern.x) { // Vertikale Linie
            int minY = Math.min(bow.y, stern.y);
            int maxY = Math.max(bow.y, stern.y);
            for (int y = minY; y <= maxY; y++) {
                cells.add(new Cell(new Point(bow.x, y)));
            }
        } else if (bow.y == stern.y) { // Horizontale Linie
            int minX = Math.min(bow.x, stern.x);
            int maxX = Math.max(bow.x, stern.x);
            for (int x = minX; x <= maxX; x++) {
                cells.add(new Cell(new Point(x, bow.y)));
            }
        }
        return cells;
    }


    private boolean cellsContainOnlyNeighboursInARow(List<Cell> cells){
        List<Integer> xValues = cells.stream().map( c -> c.position.x).toList();
        List<Integer> yValues = cells.stream().map( c -> c.position.y).toList();
        boolean xValuesAreConsecutive = valuesAreConsecutive(xValues);
        boolean yValuesAreConsecutive = valuesAreConsecutive(yValues);

        boolean cellsHaveTheSameXValue = valuesAreAllTheSame(xValues);
        boolean cellsHaveTheSameYValue = valuesAreAllTheSame(yValues);

        if(xValuesAreConsecutive && cellsHaveTheSameYValue){
            return true;
        }
        if(yValuesAreConsecutive && cellsHaveTheSameXValue){
            return true;
        }
        return false;
    };

    private boolean doesntHaveDoublets(List<Cell> cells) {
        Set<Point> seenPoints = new HashSet<>();
        for (Cell cell : cells) {
            if (!seenPoints.add(cell.position)) {
                return false;
            }
        }
        return true;
    }

    private boolean valuesAreAllTheSame(List<Integer> integersList){
        Set<Integer> integerSet = new HashSet<>(integersList);
        return integerSet.size() == 1;
    }

    private boolean valuesAreConsecutive(List<Integer> integers){
        integers.sort(Comparator.naturalOrder());
        for(int i = 1; i < integers.size(); i++ ){
            int last = integers.get(i-1);
            int current = integers.get(i);
            if(current - last != 1){
                return false;
            }
        }
        return true;
    }


}
