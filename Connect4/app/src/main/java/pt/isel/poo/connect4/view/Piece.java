package pt.isel.poo.connect4.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import pt.isel.poo.connect4.model.Player;
import pt.isel.poo.lib.tile.Tile;

/**
 * Created by Palex on 27/05/2015.
 */
public class Piece implements Tile {

    private static Paint p = new Paint();
    private boolean selected;
    private int color;

    public Piece(Player p) {
        color = p==Player.A ? Color.BLUE : Color.RED;
    }

    @Override
    public void draw(Canvas canvas, int side) {
        p.setColor(color);
        canvas.drawCircle(side/2,side/2,side/2-side/8,p);
        if (selected) {
            p.setColor(Color.BLACK);
            canvas.drawCircle(side / 2, side / 2, side / 8, p);
        }
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }

    public void toggleSelect() {
        selected = !selected;
    }

    private static Piece A = new Piece(Player.A);
    private static Piece B = new Piece(Player.B);

    public static Piece valueOf(Player player) {
        if (player==null) return null;
        return new Piece(player);
        //return player==null ? null : (player==Player.A ? A : B);
    }
}
