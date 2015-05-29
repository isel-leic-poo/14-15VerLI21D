package pt.isel.poo.connect4;

import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pt.isel.poo.connect4.view.Piece;
import pt.isel.poo.lib.tile.Animator;
import pt.isel.poo.lib.tile.OnFinishAnimationListener;
import pt.isel.poo.lib.tile.OnTileTouchListener;
import pt.isel.poo.lib.tile.TilePanel;


public class C4Activity extends ActionBarActivity implements OnTileTouchListener, OnFinishAnimationListener {

    TilePanel panel;
    Animator anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c4);
        panel = (TilePanel) findViewById(R.id.panel);

        panel.setTile(2, 3, new Piece());
        panel.setListener(this);
        anim = panel.getAnimator();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_c4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onClick(int x, int y) {
        Piece p = (Piece) panel.getTile(x, y);
        if (p==null) {
            anim.entryTile(x, -1, x, y, (y+1) * 200, new Piece());
            anim.triggerOnFinishAnimations(new Point(x,y),this);
            return true;
        }
        else {
            p.toggleSelect();
            panel.invalidate(x,y);
        }
        return false;
    }

    @Override
    public boolean onDrag(int x, int y, int xTo, int yTo) {
        Piece p = (Piece) panel.getTile(x, y);
        if (p==null) return false;
        //System.out.println("onDrag: "+x+","+y+","+xTo+","+yTo);
        anim.floatTile(x, y, xTo, yTo, 500);
        return true;
    }

    @Override
    public void onDragEnd(int x, int y) {
    }

    int gridLine = 3;

    @Override
    public void onDragCancel() {
    }

    @Override
    public void onFinish(Object tag) {
        Point pos = (Point) tag;
        int x = pos.x, y = pos.y+1;
        if (y>=panel.getHeightInTiles())
            return;
        Piece p = (Piece) panel.getTile(x, y);
        if (p!=null)
            anim.exitTile(x,y,x,panel.getHeightInTiles(),200);
    }
}
