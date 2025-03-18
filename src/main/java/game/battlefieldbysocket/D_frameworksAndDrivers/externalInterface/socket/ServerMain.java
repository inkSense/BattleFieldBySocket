package game.battlefieldbysocket.D_frameworksAndDrivers.externalInterface.socket;

public class ServerMain {
    public static void main(String[] args) {
        int port = 12345; // Definiere den Port, auf dem der Server laufen soll
        ServerSocketImplementation server = new ServerSocketImplementation(port);
        server.startServer();
    }
}

