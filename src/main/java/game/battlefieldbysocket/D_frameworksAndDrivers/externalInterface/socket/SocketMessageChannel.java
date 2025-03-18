package game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket;

public interface SocketMessageChannel {
    void send(String message);
    String receive();
}
