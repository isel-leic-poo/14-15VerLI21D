package pt.isel.poo.connect4;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

import pt.isel.poo.connect4.model.Connect4;
import pt.isel.poo.connect4.model.OnChangeListener;
import pt.isel.poo.connect4.model.Player;
import pt.isel.poo.connect4.view.Piece;
import pt.isel.poo.connect4.view.PieceView;
import pt.isel.poo.lib.tile.Animator;
import pt.isel.poo.lib.tile.OnFinishAnimationListener;
import pt.isel.poo.lib.tile.OnTileTouchListener;
import pt.isel.poo.lib.tile.TilePanel;


public class C4Activity extends ActionBarActivity implements OnTileTouchListener, OnFinishAnimationListener, OnChangeListener {
    public static final String LAST_GAME = "LAST_GAME";
    /* VIEW */
    TilePanel panel;
    Animator anim;
    PieceView player;
    /* MODEL */
    Connect4 model = new Connect4();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c4);
        panel = (TilePanel) findViewById(R.id.panel);
        player = (PieceView) findViewById(R.id.player);

        panel.setListener(this);
        anim = panel.getAnimator();

        Intent it = getIntent();
        if ( it.getBooleanExtra(MainActivity.LAST_GAME_KEY,false)){
            Toast.makeText(this,"LAST_GAME",Toast.LENGTH_LONG).show();
            try ( Reader in = new InputStreamReader( openFileInput(LAST_GAME) ) ) {
                model.load(in);
                updateView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //initState();
        model.setOnChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause(); // Na aula esqueci-me de fazer esta chamada.
        try (Writer out = new OutputStreamWriter(openFileOutput(LAST_GAME, MODE_PRIVATE))) {
            model.save(out);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void initState() {
        try {
            Scanner in = new Scanner(getAssets().open("initState.txt"));
            int moves = in.nextInt();
            in.nextLine();
            for (; moves>0 ; --moves)
                model.dropPiece(in.nextInt());
            updateView();
            in.close();
        } catch (IOException e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
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
        if (model.dropPiece(x)) {
            //updateView();
            return true;
        }
        /*
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
        */
        return false;
    }

    private void updateView() {
        for (int x = 0; x < Connect4.COLS; x++) {
            for (int y = 0; y < Connect4.LINES; y++) {
                panel.setTile(x,y,Piece.valueOf(model.getPlayer(x,y)));
            }
        }
        player.setPiece(Piece.valueOf(model.getCurrentPlayer()));
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

    @Override
    public void onDrop(int x, int y, Player p) {
        anim.entryTile(x,-1,x,y,200*(y+1),Piece.valueOf(p));
        player.setPiece(Piece.valueOf(model.getCurrentPlayer()));
    }
}
