package io.github.hufghani.ntsp_uom.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import io.github.hufghani.ntsp_uom.R;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Button btnemergencyAlgorithms = findViewById(R.id.btnemergency_algorithms);

        btnemergencyAlgorithms.setOnClickListener(view -> {
            Intent myIntent = new Intent(MainActivity.this,
                    AlgorithmsActivity.class);
            startActivity(myIntent);
        });

    }
}
