package game.battlefieldforweb.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @GetMapping("/api/game/status")
    public String getGameStatus() {
        // For now, returns a simple string.
        // Later, this will be connected to the game logic.
        return "Game server is running!";
    }
}
