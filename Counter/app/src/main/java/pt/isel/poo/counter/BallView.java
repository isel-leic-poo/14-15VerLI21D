package pt.isel.poo.counter;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class BallView extends View {
    public BallView(Context context) {
        super(context);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    private Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(0,0,100,paint);
    }
}
