package game.battlefieldbysocket.A_entities.objectsAndDataStructures;

import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Cruiser;
import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Destroyer;
import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Ship;

import java.util.Map;

public class GameConf {
    public static final int fieldSideLength = 6;
    public static final Map<Class<? extends Ship>, Integer> fleet = Map.of(Destroyer.class, 1, Cruiser.class, 1 );
}
