package io.github.hufghani.ntsp_uom.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import io.github.hufghani.ntsp_uom.R;
import io.github.hufghani.ntsp_uom.databinding.ActivityMainBinding;

public class MainActivity extends Activity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_main);

        binding.btnemergencyAlgorithms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,
                        AlgorithmsActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
