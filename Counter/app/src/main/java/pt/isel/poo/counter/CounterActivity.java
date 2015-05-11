package pt.isel.poo.counter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);

        inc = new Button(this);
        inc.setText("+");
        dec = new Button(this);
        dec.setText("-");
        ctr = new TextView(this);
        ctr.setText("0");
        ctr.setTextSize(100);
        ctr.setGravity(Gravity.CENTER);
        ctr.setBackgroundColor(Color.YELLOW);
        ball = new BallView(this);

        save = new TxtButton("Save",buttons,new View.OnClickListener() {
            public void onClick(View v) {
                    showDialog(true);
            }
        });
        load = new TxtButton("Load",buttons,new View.OnClickListener() {
            public void onClick(View v) {
                    showDialog(false);
            }
        });
        //BUG: Aqui só era criado 1 objecto EditText para todas as dialog.
        //fileName = new EditText(this);

        root.addView(buttons);
        root.addView(inc);
        root.addView(ctr);
        root.addView(dec);
        root.addView(ball);

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
        setContentView(root);
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
            Toast.makeText(this,"Error loading file",Toast.LENGTH_LONG).show();
        }
    }

    private class TxtButton extends Button {
        public TxtButton(String txt, ViewGroup root, OnClickListener listener) {
            super(getApplicationContext());
            setText(txt);
            setOnClickListener(listener);
            root.addView(this);
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
