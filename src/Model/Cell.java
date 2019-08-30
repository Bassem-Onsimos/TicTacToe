
package Model;

import Game.Game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Cell {
    
    private Game game;
    //
    private int index;
    //
    private float x, y;
    private static float side;
    //
    private boolean hovered;
    private boolean highlighted;
    //
    private CellContent content;
    private String display;
    
    public Cell(Game game, int index) {
        this.game = game;
        this.index = index;
        
        side = game.getWidth() / 5;
        
        y = (index / 3) * side + side;
        x = (index % 3) * side + side;
        
        setContent(CellContent.empty);
        setHighlighted(false);
    }
    
    public void render(Graphics2D g, float fontSize) {
        if(highlighted) {
            g.setColor(Color.red);
            g.fillRect((int)x, (int)y, (int)side, (int)side);
        }
        else if(hovered) {
            g.setColor(Color.gray);
            g.fillRect((int)x, (int)y, (int)side, (int)side);
        }
        
        g.setColor(Color.white);
        g.drawRect((int)x, (int)y, (int)side, (int)side);
        g.drawString(display, x + side/2 - fontSize/3f, y + side/2 + fontSize/3f);
    }

    public CellContent getContent() {
        return content;
    }

    public void setContent(CellContent content) {
        this.content = content;
        
        switch(content) {
            case empty:
                display = "";
                break;
            case player1:
                display = "O";
                break;
            case player2:
                display = "X";
                break;
        }
    }
    
    public Cell copy() {
        Cell copy = new Cell(game, index);
        copy.setContent(content);
        return copy;
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)side, (int)side);
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
    
    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
    
    public static float getSide() {
        return side;
    }

    public int getIndex() {
        return index;
    }
    
    
    
}
