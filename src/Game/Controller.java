
package Game;

import GameEngine.GameState.State;
import Model.Cell;
import Model.CellContent;
import Model.Grid;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class Controller {
    private Game game;
    //
    private Grid grid;
    //
    private int player;
    //
    private GameMode gameMode;
    private GameDifficulty gameDifficulty;
    //
    private boolean gameEnded;
    
    public Controller(Game game) {
        this.game = game;
    }
    
    public void update() {
        grid.update();
        
        if(game.getInput().isButtonUp(MouseEvent.BUTTON1)) {
            float x = game.getInput().getMouseX();
            float y = game.getInput().getMouseY();
            
            for(Cell cell : grid.getCells()) {
                if(cell.getBounds().contains(x, y)) {
                    if(cell.getContent() == CellContent.empty){
                        player = grid.fillCell(cell, player);
                        
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                        }
                        
                        if(gameMode == GameMode.singlePlayer && !gameEnded){
                            if(gameDifficulty == GameDifficulty.easy) player = grid.randomAiPlay();
                            else player = grid.minimaxAiPlay();
                        }
                        break;
                    }
                }                
            }
        }
       
    }
    
    public void render(Graphics2D g) {
        grid.render(g);
    }
    
    public void initializeGame(GameMode gameMode, GameDifficulty gameDifficulty) {
        this.gameMode = gameMode;
        this.gameDifficulty = gameDifficulty;
        restartGame();
    }
    
    public void restartGame() {
        grid = new Grid(game);
        
        if(gameMode == GameMode.singlePlayer && gameDifficulty == GameDifficulty.hard) player = grid.minimaxAiPlay();
        else player = 1;
        
        gameEnded = false;
    }
    
    public void endGame(boolean gameWon) {
        if(gameWon){
            switch(player) {
                case 1:{
                    if(gameMode == GameMode.singlePlayer) {
                        game.getPostGameMenu().setTitle("You Win!");
                    }
                    else {
                        game.getPostGameMenu().setTitle("Player 1 Won!");
                    }
                    break;
                }
                case 2:{
                    if(gameMode == GameMode.singlePlayer) {
                        game.getPostGameMenu().setTitle("You Lose!");
                    }
                    else {
                        game.getPostGameMenu().setTitle("Player 2 Won!");
                    }
                    break;
                }
            }
        }
        else {
            game.getPostGameMenu().setTitle("Tie Game");
        }
        
        gameEnded = true;
        
        for(Cell cell : grid.getCells()) cell.setHovered(false);
        
        game.setState(State.postGame);
    }

    public GameMode getGameMode() {
        return gameMode;
    }
    
    
}
