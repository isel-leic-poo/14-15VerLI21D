package pt.isel.poo.counter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class CounterActivity extends Activity {

    private Button inc, dec;
    private Button save, load;
    private TextView ctr;
    private BallView ball;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(initLayout());
        setContentView(R.layout.counter_layout);
        save = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);
        inc = (Button) findViewById(R.id.inc);
        dec = (Button) findViewById(R.id.dec);
        ctr = (TextView) findViewById(R.id.ctr);
        ball = (BallView) findViewById(R.id.ball);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(true);
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(false);
            }
        });
        inc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ++counter;
                updateView();
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                --counter;
                updateView();
            }
        });
        ball.setOnDropListener(new BallView.OnDropListener() {
            public void onDrop(int deltaX) {
                counter = deltaX / 10;
                updateView();
            }
        });
    }

    private void updateView() {
        ctr.setText(String.valueOf(counter));
        ball.setPosition(counter * 10);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("counter");
        updateView();
        //loadFile();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter",counter);
        //saveFile();
    }

    private void saveFile(String fileName) {
        try {
            OutputStream out = openFileOutput(fileName,MODE_PRIVATE);
            PrintWriter bout = new PrintWriter(out);
            bout.println(counter);
            bout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFile(String fileName) {
        try {
            InputStream in = openFileInput(fileName);
            Scanner tin = new Scanner(in);
            counter = tin.nextInt();
            updateView();
            tin.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this,getString(R.string.file_error),Toast.LENGTH_LONG).show();
        }
    }

    private String lastFileName = "counter.txt";
    private EditText fileName;

    private void showDialog(final boolean save) {
        String txt = save ? "Save" : "Load";
        fileName = new EditText(this);  // Cada dialog com o seu EditText
        fileName.setText(lastFileName);
        new AlertDialog.Builder(this)
          .setMessage("File name to "+txt)
          .setPositiveButton(txt, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface d, int which) {
                lastFileName = fileName.getText().toString();
                if (save) saveFile(lastFileName);
                else loadFile(lastFileName);
            }
          })
          .setView(fileName)
          .setNegativeButton("Cancel",null)
          .show();
    }
}
