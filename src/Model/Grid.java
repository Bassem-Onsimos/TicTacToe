
package Model;

import Algorithm.MinimaxAlgorithm;
import Game.Controller;
import Game.Game;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Grid {

    private Game game;
    private Controller controller;
    //
    private ArrayList<Cell> cells;
    //
    private MinimaxAlgorithm minimax;
    //
    private Random rand;
    //
    private int[][] winningCombinations = { {0, 1, 2} , 
                              {3, 4, 5} , 
                              {6, 7, 8} ,
                              {0, 3, 6} ,
                              {1, 4, 7} ,
                              {2, 5, 8} ,
                              {0, 4, 8} ,
                              {2, 4, 6} };
    
    
    public Grid(Game game) {
        this.game = game;
        this.controller = game.getController();
        
        cells = new ArrayList<>();
        
        for(int i = 0; i < 9; i++) {
            cells.add(new Cell(game, i));
        } 
        
        rand = new Random();
        minimax = new MinimaxAlgorithm(this);
    }
    
    public void update() {
        for(Cell cell : cells) {
            if(cell.getBounds().contains(game.getInput().getMouseX(), game.getInput().getMouseY())) cell.setHovered(true);
            else cell.setHovered(false);
        }
    }
    
    public void render(Graphics2D g) {
        int side = (int) Cell.getSide();
        float fontSize = (int) (Cell.getSide() / 3);
        Font defaultFont = g.getFont();
        
        g.setStroke(new BasicStroke(3));
        g.setFont(new Font("Comic Sans MS", Font.BOLD, (int)fontSize));
        
        for(Cell cell : cells) cell.render(g, fontSize);
        
        g.setColor(Color.black);        
        g.drawRect(side, side, side * 3, side * 3);
        
        g.setFont(defaultFont);
    }
    
    public int fillCell(Cell cell, int player) {
        switch(player) {
            case 1:{
                cell.setContent(CellContent.player1);
                if(checkWin(CellContent.player1)) controller.endGame(true);
                else if(checkTie()) controller.endGame(false);
                return 2;
            }
            case 2:{
                cell.setContent(CellContent.player2);
                if(checkWin(CellContent.player2)) controller.endGame(true);
                else if(checkTie()) controller.endGame(false);
                return 1;
            }
            default:
                return 0;
        }
    }
    
    private boolean checkWin(CellContent player) {
        ArrayList<Integer> playerCells = new ArrayList<>();
        
        for(Cell cell : cells) {
            if(cell.getContent() == player) playerCells.add(cell.getIndex());
        }
        
        for (int[] winningCombination : winningCombinations) {
            
            int foundIndices = 0;
            
            for(int index : winningCombination) {
                if(playerCells.contains(index)) foundIndices++;
                else break;
            }
            
            if(foundIndices == 3){
                for(int index : winningCombination) cells.get(index).setHighlighted(true);
                return true;
            }
        }
        
        return false;
    }
    
    public boolean simulatedCheckWin(ArrayList<Cell> currentCells, CellContent player) {
        ArrayList<Integer> playerCells = new ArrayList<>();
        
        for(Cell cell : currentCells) {
            if(cell.getContent() == player) playerCells.add(cell.getIndex());
        }
        
        for (int[] winningCombination : winningCombinations) {
            
            int foundIndices = 0;
            
            for(int index : winningCombination) {
                if(playerCells.contains(index)) foundIndices++;
                else break;
            }
            
            if(foundIndices == 3) return true;
            
        }
        
        return false;
    }
    
    public boolean checkTie() {
        for(Cell cell : cells) 
            if(cell.getContent() == CellContent.empty) return false;
        
        return true;
    }
    
    public int minimaxAiPlay() {
        return fillCell(cells.get(minimax.determineBestMove()), 2);

    }
    
    public int randomAiPlay() {
        ArrayList<Cell> freeCells = new ArrayList<>();
        
        for(Cell cell : cells) {
            if(cell.getContent() == CellContent.empty) freeCells.add(cell);
        }
                
        return fillCell(freeCells.get(rand.nextInt(freeCells.size())), 2);
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }
    
}
