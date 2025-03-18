package game.battlefieldbysocket.C_adapters;

import game.battlefieldbysocket.A_entities.objectsAndDataStructures.*;
import game.battlefieldbysocket.B_useCases.UseCaseOutputPort;
import java.awt.*;

public class Presenter implements UseCaseOutputPort {

    AdapterOutputPort adapterOutputPort;

    public Presenter(AdapterOutputPort adapterOutputPort) {
        this.adapterOutputPort = adapterOutputPort;
    }

    @Override
    public void presentGamePlayer1() {
        // adapterInputPort
    }

    @Override
    public void presentGamePlayer2() {
        // adapterInputPort
    }

    @Override
    public Point askPlayerWhereToShoot(String teamName){
        Point shot = adapterOutputPort.setOpenForInputAndGetIt();
        return shot;
    }

    @Override
    public void presentFieldLabel(Point point, String label) {
        adapterOutputPort.setTextFieldLabel(point, label);
    }

    public void setFieldToHit(Point point){
        adapterOutputPort.markFieldWithLabel(point, "X");
    }

    public void setFieldToStruck(Point point){
        adapterOutputPort.markFieldWithLabel(point, "HIT");
    }

    public void setFieldToShipLabel(Point point){
        adapterOutputPort.markFieldWithLabel(point, "Boat");
    }


    public void presentGame(Game game){
        Board board = game.getBoardOfCurrentPlayer();
        for(Cell cell : board.getCells()){
            if(cell.occupied){
                // Do nothing
                //setFieldToShipLabel(cell.position);
            }
            if(cell.hit){
                setFieldToHit(cell.position);
            }
        }

    };


}
