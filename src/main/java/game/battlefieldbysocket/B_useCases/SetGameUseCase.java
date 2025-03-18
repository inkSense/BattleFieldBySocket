package game.battlefieldbysocket.B_useCases;

import game.battlefieldbysocket.A_entities.objectsAndDataStructures.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Random;
import java.util.logging.Logger;

public class SetGameUseCase {
    private static final Logger log = Logger.getLogger(SetGameUseCase.class.getName());
    Game game;

    public Game makeGame(){
        Player player1 = new Player(new Board(GameConf.fieldSideLength), "Player 1");
        Player player2 = new Player(new Board(GameConf.fieldSideLength), "Player 2");

        this.game = new Game(player1, player2);

        createShipsBasedOnConf(player1.getTeamName(), player2.getTeamName());
        distributeShipsRandomly();

        return game;
    }

    private static Ship createShipInstance(Class<? extends Ship> shipClass, String teamName) {
        try {
            return shipClass.getConstructor(String.class).newInstance(teamName);
        } catch (Exception e) {
            throw new RuntimeException("Konnte kein Schiff erstellen", e);
        }
    }

    private void createShipsBasedOnConf(String player1Name, String player2Name){
        Set<Class<? extends Ship>> shipTypes = GameConf.fleet.keySet();
        for(Class<? extends Ship> shipType : shipTypes){
            int numberOfShips = GameConf.fleet.get(shipType);
            for(int i = 0; i < numberOfShips; i++ ){
                game.addShipForPlayer1(createShipInstance(shipType, player1Name));
                game.addShipForPlayer2(createShipInstance(shipType, player2Name));
            }
        }
    }

    private void distributeShipsRandomly(){
        for(Player player : game.getPlayers()){
            for(Ship ship : player.getShips()){
                setRandomPosition(ship, player.getBoard());
            }
        }
    }

    private void setRandomPosition(Ship ship, Board board){
        List<Point> points = Collections.emptyList();
        outerLoop:
        for(int i = 0; i < 100; i++){
            // Suche Position

            points = buildRandomAdjacentPoints(ship.getNumberOfSegments());
            for(Point point : points){
                for(Cell cell : board.getCells()) {
                    if(point == cell.position && cell.occupied){
                        if(i==99){
                            log.info("Couldnt find any position for a ship. Giving up.");
                        }
                        continue outerLoop;
                    }
                }
            }
            // Alle Punkte sind frei. Die Suche ist vorbei.
            break outerLoop;
        }
        ship.setSegments(points);
        board.setOccupied(points);

    }

    private List<Point> buildRandomAdjacentPoints(int numberOfPoints) {
        // Falls du in deinem Code noch keinen Random-Generator hast:
        Random random = new Random();

        int sideLength = GameConf.fieldSideLength;

        // Entscheide zufällig, ob horizontal oder vertikal
        boolean horizontal = random.nextBoolean();

        // Liste, die wir zurückgeben
        List<Point> adjacentPoints = new ArrayList<>();

        if (horizontal) {
            // Stelle sicher, dass genug Platz auf der X-Achse bleibt,
            // damit numberOfPoints Felder nebeneinander passen.
            int xStart = random.nextInt(sideLength - numberOfPoints + 1);
            int yFixed = random.nextInt(sideLength);

            for (int i = 0; i < numberOfPoints; i++) {
                adjacentPoints.add(new Point(xStart + i, yFixed));
            }
        } else {
            // Vertikale Variante
            int xFixed = random.nextInt(sideLength);
            int yStart = random.nextInt(sideLength - numberOfPoints + 1);

            for (int i = 0; i < numberOfPoints; i++) {
                adjacentPoints.add(new Point(xFixed, yStart + i));
            }
        }

        return adjacentPoints;
    }

}
