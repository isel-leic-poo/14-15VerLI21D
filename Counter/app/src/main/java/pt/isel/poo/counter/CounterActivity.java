package pt.isel.poo.counter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CounterActivity extends Activity {

    private Button inc, dec;
    private TextView ctr;
    private BallView ball;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        root.addView(inc);
        root.addView(ctr);
        root.addView(dec);
        root.addView(ball);

        inc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ++counter; updateView();
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                --counter; updateView();
            }
        });
        ball.setOnDropListener(new BallView.OnDropListener() {
            public void onDrop(int deltaX) {
                counter = deltaX/10; updateView();
            }
        });
        setContentView(root);
    }

    private void updateView() {
        ctr.setText(String.valueOf(counter));
        ball.setPosition(counter*10);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveFile();
    }

    private void saveFile() {
        try {
            OutputStream out = openFileOutput("counter.txt",MODE_PRIVATE);
            OutputStreamWriter dout = new OutputStreamWriter(out);
            BufferedWriter bout = new BufferedWriter(dout);
            bout.write(""+counter);
            bout.newLine();
            bout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
