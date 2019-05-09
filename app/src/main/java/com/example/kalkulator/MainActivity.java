package com.example.kalkulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OptionMenu(View view) {
        Button pressedButton = (Button) view;
        Log.i("PRESSED:",pressedButton.getTag().toString());

        Intent simpleIntent = new Intent(this, Simple.class);
        Intent advancedIntent = new Intent(this, Advanced.class);
        switch (pressedButton.getTag().toString()){

            case "simple" :  MainActivity.this.startActivity(simpleIntent);
                break;
            case "advanced" :
                MainActivity.this.startActivity(advancedIntent);
                break;
            case "about" :
                Toast.makeText(MainActivity.this.getBaseContext(),"Author: Magdalena Klimaszewska"+"\n"+"Application made as project"+"\n"+"for 'Technologie Mobilne' class", Toast.LENGTH_LONG).show();
                break;
            case "exit" :
                finish();
                System.exit(0);
                break;
        }

    }
}
