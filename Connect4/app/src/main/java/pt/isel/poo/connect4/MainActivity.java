package pt.isel.poo.connect4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    public static final String LAST_GAME_KEY = "LAST_GAME";
    Button newGame, contGame, thoth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGame = (Button) findViewById(R.id.newGame);
        contGame = (Button) findViewById(R.id.contGame);
        thoth = (Button) findViewById(R.id.thoth);

        newGame.setOnClickListener(this);
        contGame.setOnClickListener(this);

        thoth.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent it = new Intent(Intent.ACTION_VIEW);
             it.setData(Uri.parse("https://adeetc.thothapp.com/classes/POO/1415v/LI21D/info"));
             startActivity(it);
         }
        });
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this,C4Activity.class);
        if (v==contGame)
            it.putExtra(LAST_GAME_KEY,true);
        startActivity(it);
        finish();
    }
}
