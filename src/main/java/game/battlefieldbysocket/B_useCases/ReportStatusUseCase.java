package game.battlefieldbysocket.B_useCases;

import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Cell;
import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Game;
import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Player;
import game.battlefieldbysocket.A_entities.objectsAndDataStructures.Ship;

import java.util.logging.Logger;

public class ReportStatusUseCase {

    Game game;

    UseCaseOutputPort outputPort;

    private static final Logger log = Logger.getLogger(PlayGameUseCase.class.getName());

    public ReportStatusUseCase(Game game) {
        this.game = game;
    }

    public void setOutputPort(UseCaseOutputPort outputPort) {
        this.outputPort = outputPort;
    }

    public void reportStatusOfBoards(){
        for(Player player : game.getPlayers()){
            log.info("Cells of Team " + player.getTeamName() + ": ");
            for(Cell cell : player.getBoard().getCells()){
                if(cell.hit || cell.occupied){
                    log.info(cell.toString());
                }
            }
        }
        System.out.println();
    }

    public void reportStatusOfShips(){
        for(Player player : game.getPlayers()){
            log.info("Ships of Team " + player.getTeamName() + ": ");
            for(Ship ship : player.getShips()){
                log.info(ship.toString());
            }
        }
        System.out.println();
    }



}
