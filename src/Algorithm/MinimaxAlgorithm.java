
package Algorithm;

import Model.Cell;
import Model.CellContent;
import Model.Grid;
import java.util.ArrayList;
import java.util.Random;

public class MinimaxAlgorithm {
    
    private class Move {
        int cellIndex;
        int score;
        
        Move(int score) {
            this.score = score;
        }
        
    }
    
    private Grid grid;
    private Random rand;
    
    public MinimaxAlgorithm(Grid grid) {
        this.grid = grid;
        rand = new Random();
    }
    
    public int determineBestMove() {
        
        ArrayList<Cell> currentBoard = new ArrayList<>();
        
        for(Cell cell : grid.getCells()) currentBoard.add(cell.copy());
        
        return minimax(currentBoard, 0, CellContent.player2).cellIndex;
        
    }
    
    private Move minimax(ArrayList<Cell> newBoard, int depth, CellContent player) {
                
        ArrayList<Cell> freeCells = freeCells(newBoard);
        
        if(grid.simulatedCheckWin(newBoard, CellContent.player1)) { 
            return new Move(-10 + depth);
        }
        else if(grid.simulatedCheckWin(newBoard, CellContent.player2)) { 
            return new Move(10 - depth);
        }
        else if(freeCells.isEmpty()){
            return new Move(0);
        }
        
        ArrayList<Move> moves = new ArrayList<>();
        
        for(Cell cell : freeCells) {
            
            Move move = null;
            newBoard.get(newBoard.indexOf(cell)).setContent(player);
            
            if(player == CellContent.player2) {
                move = minimax(newBoard, depth + 1, CellContent.player1);
            }
            else if(player == CellContent.player1) {
                move = minimax(newBoard, depth + 1, CellContent.player2);
            }
            
            move.cellIndex = cell.getIndex();
            newBoard.get(newBoard.indexOf(cell)).setContent(CellContent.empty);
            
            moves.add(move);
        }
        
        ArrayList<Move> bestMoves = new ArrayList<>();
        
        if(player == CellContent.player2) {
            
            int bestScore = Integer.MIN_VALUE;
            
            for(Move move : moves) {
                if(move.score > bestScore) {
                    bestScore = move.score;
                    bestMoves.clear();
                    bestMoves.add(move);
                }
                else if(move.score == bestScore) {
                    bestMoves.add(move);
                }
            }
            
        }
        else if(player == CellContent.player1) {
            
            int bestScore = Integer.MAX_VALUE;
            
            for(Move move : moves) {
                if(move.score < bestScore) {
                    bestScore = move.score;
                    bestMoves.clear();
                    bestMoves.add(move);
                }
                else if(move.score == bestScore) {
                    bestMoves.add(move);
                }
            }
            
        }
        
        return bestMoves.get(rand.nextInt(bestMoves.size()));
    }
    
    private ArrayList<Cell> freeCells(ArrayList<Cell> currentBoard) {
        ArrayList<Cell> freeCells = new ArrayList<>();
        
        for(Cell cell : currentBoard) {
            if(cell.getContent() == CellContent.empty) freeCells.add(cell);
        }
        
        return freeCells;
    }
    

}
