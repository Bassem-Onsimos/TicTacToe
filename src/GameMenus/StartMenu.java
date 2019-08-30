
package GameMenus;

import Game.Game;
import Game.GameDifficulty;
import Game.GameMode;
import GameEngine.GameState.State;
import GameMenu.AbstractMenu;
import GameMenu.MenuItem;
import GameMenu.SubMenuInitializer;

public class StartMenu extends AbstractMenu {

    private Game game;
    
    public StartMenu(Game game) {
        super(game);
        this.game = game;
    }

    @Override
    public void initiate() {
        
        setTitle("Tic Tac Toe");
        
        
        
        addItem(new SubMenuInitializer("Single player") {
            @Override
            public void initiate() {
                
                addSubMenuItem(new MenuItem("Easy") {
                    @Override
                    public void function() {
                        game.getController().initializeGame(GameMode.singlePlayer, GameDifficulty.easy);
                        game.setState(State.inGame);
                    }
                });
                
                addSubMenuItem(new MenuItem("Medium") {
                    @Override
                    public void function() {
                        game.getController().initializeGame(GameMode.singlePlayer, GameDifficulty.medium);
                        game.setState(State.inGame);
                    }
                });
                
                addSubMenuItem(new MenuItem("Hard") {
                    @Override
                    public void function() {
                        game.getController().initializeGame(GameMode.singlePlayer, GameDifficulty.hard);
                        game.setState(State.inGame);
                    }
                });
                
                addSubMenuItem(new MenuItem("Main Menu") {
                    @Override
                    public void function() {
                    }
                });
                
            }
        });
        
        addItem(new MenuItem("Multiplayer") {
            @Override
            public void function() {
                game.getController().initializeGame(GameMode.multiPlayer, GameDifficulty.easy);
                game.setState(State.inGame);
            }
        });
        
        addItem(new MenuItem("Exit") {
            @Override
            public void function() {
                System.exit(0);
            }
        });
        
    }

}
    
    
