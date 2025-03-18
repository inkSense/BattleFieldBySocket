package game.battlefieldbysocket.C_adapters;

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




}
