package pt.isel.poo.connect4.model;

import pt.isel.poo.connect4.C4Activity;

/**
 * Created by Palex on 27/05/2015.
 */
public class Connect4 {


    public static final int COLS = 7, LINES = 6;

    private Player[][] grid = new Player[COLS][LINES];
    private Player currPlayer = Player.A;
    private OnChangeListener changeListener;

    public boolean dropPiece(int col) {
        if (grid[col][0]!=null) return false;
        int y;
        for(y=LINES-1 ; y>=0 && grid[col][y]!=null ; --y  )
            ;
        grid[col][y] = currPlayer;
        currPlayer = currPlayer.other();
        if (changeListener!=null)
            changeListener.onDrop(col,y,grid[col][y]);
        return true;
    }

    public boolean hasLine() {
        return false;
    }

    public Player getPlayer(int x, int y) {
        return grid[x][y];
    }

    public Player getCurrentPlayer() {
        return currPlayer;
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        changeListener = onChangeListener;
    }
}
