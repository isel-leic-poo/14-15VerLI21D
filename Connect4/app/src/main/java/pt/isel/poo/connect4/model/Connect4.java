package pt.isel.poo.connect4.model;

/**
 * Created by Palex on 27/05/2015.
 */
public class Connect4 {


    public static final int COLS = 7, LINES = 6;

    private Piece[][] grid = new Piece[COLS][LINES];

    public boolean dropPiece(int col, boolean player) {
        if (grid[col][0]!=null) return false;
        //...
        return true;
    }

    public boolean hasLine() {
        return false;
    }

}
