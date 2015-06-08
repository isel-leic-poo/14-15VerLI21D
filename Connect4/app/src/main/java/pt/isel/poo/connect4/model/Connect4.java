package pt.isel.poo.connect4.model;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import pt.isel.poo.connect4.C4Activity;

/**
 * Created by Palex on 27/05/2015.
 */
public class Connect4 {


    public static final int COLS = 7, LINES = 6;

    private Player[][] grid = new Player[COLS][LINES];
    private Player currPlayer = Player.A;
    private boolean emptyGame = true;
    private OnChangeListener changeListener;

    public boolean dropPiece(int col) {
        if (grid[col][0]!=null) return false;
        int y;
        for(y=LINES-1 ; y>=0 && grid[col][y]!=null ; --y  )
            ;
        grid[col][y] = currPlayer;
        currPlayer = currPlayer.other();
        emptyGame = false;
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

    public boolean isEmptyGame() { return emptyGame; }

    public void save(Writer out) throws IOException {
        out.write(currPlayer.toChar());
        for (int x = 0; x < COLS; x++) {
            for (int y = LINES-1 ; y>=0 && grid[x][y]!=null ; y--) {
                out.write(grid[x][y].toChar());
            }
            out.write('|');
        }
    }

    public void load(Reader in) throws IOException {
        char c = (char) in.read();
        currPlayer = Player.valueOf(c);
        c = (char) in.read();
        for (int x = 0; x < COLS; x++) {
            int y;
            for (y = LINES-1 ; y>=0 && c!='|' ; y--) {
                grid[x][y] = Player.valueOf(c);
                c = (char) in.read();
            }
            for( ; y>=0 ; y--)
                grid[x][y] = null;
            c = (char) in.read();
        }
    }
}
