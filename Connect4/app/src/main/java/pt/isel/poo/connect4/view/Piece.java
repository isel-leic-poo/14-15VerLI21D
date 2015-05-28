package pt.isel.poo.connect4.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import pt.isel.poo.lib.tile.Tile;

/**
 * Created by Palex on 27/05/2015.
 */
public class Piece implements Tile {

    private static Paint p = new Paint();
    private boolean slected;

    @Override
    public void draw(Canvas canvas, int side) {
        p.setColor(Color.BLUE);
        canvas.drawCircle(side/2,side/2,side/2-side/8,p);
        if (slected) {
            p.setColor(Color.BLACK);
            canvas.drawCircle(side / 2, side / 2, side / 8, p);
        }
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }

    public void toggleSelect() {
        slected = !slected;
    }
}
