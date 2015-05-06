package pt.isel.poo.counter;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class BallView extends View {
    public BallView(Context context) {
        super(context);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    private Paint paint = new Paint();
    private int deltaX = 0;                  // use if centerX==MAX_VALUE
    private int centerX = Integer.MAX_VALUE; // used only if !=MAX_VALUE
    private boolean firstCall = true;


    @Override
    protected void onDraw(Canvas canvas) {
        if (centerX==Integer.MAX_VALUE)
            centerX = getWidth()/2 + deltaX;
        canvas.drawCircle(centerX,getHeight()/2,100,paint);
    }

    public void setPosition(int deltaX) {
        this.deltaX = deltaX;
        centerX = Integer.MAX_VALUE;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                centerX = (int)event.getX();
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (listener!=null) {
                    listener.onDrop(centerX-getWidth()/2);
                }
                return true;
        }
        return false;
    }

    private OnDropListener listener;

    public void setOnDropListener(OnDropListener lst) {
        listener = lst;
    }

    public interface OnDropListener {
        void onDrop(int delta);
    }
}
