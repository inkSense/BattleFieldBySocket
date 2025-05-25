package game.battlefieldforweb.A_entities.objectsAndDataStructures;

import java.util.Map;

public class GameConf {
    public static final int fieldSideLength = 5;
    public static final Map<Class<? extends Ship>, Integer> fleet = Map.of(Destroyer.class, 1, Cruiser.class, 1 );
}
