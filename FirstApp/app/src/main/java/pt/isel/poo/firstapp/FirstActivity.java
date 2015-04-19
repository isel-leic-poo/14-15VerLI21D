package pt.isel.poo.firstapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button ok = new Button(this);
        ok.setText("OLÁ ISEL");
        ok.setOnClickListener(this);
        setContentView(ok);
    }

    @Override
    public void onClick(View v) {
        System.out.println("Olá Isel");
    }
}

