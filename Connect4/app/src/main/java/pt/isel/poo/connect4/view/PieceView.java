package pt.isel.poo.connect4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import pt.isel.poo.connect4.model.Player;

/**
 * Created by Palex on 03/06/2015.
 */
public class PieceView extends View {
    
    private Piece piece = new Piece(Player.A);

    public PieceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PieceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        piece.draw(canvas, getWidth());
    }

    public void setPiece(Piece p) {
        piece = p;
        invalidate();
    }
}
