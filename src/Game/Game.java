
package Game;

import GameEngine.AbstractGame;
import GameMenus.PauseMenu;
import GameMenus.PostGameMenu;
import GameMenus.StartMenu;
import java.awt.Graphics2D;

public class Game extends AbstractGame {

    private Controller controller;

    public Game(int width, int height, float scale, String windowTitle) {
        super(width, height, scale, windowTitle);
    }
    
    @Override
    public void initiate() {
        setDebugInfoDisplayed(false);
        setResizable(true);
        
        setStartMenu(new StartMenu(this));
        setPauseMenu(new PauseMenu(this));
        setPostGameMenu(new PostGameMenu(this));
        
        controller = new Controller(this);
    }

    @Override
    public void update() {
        controller.update();
    }

    @Override
    public void render(Graphics2D g) {
        controller.render(g);
    }

    public Controller getController() {
        return controller;
    }
    
}
