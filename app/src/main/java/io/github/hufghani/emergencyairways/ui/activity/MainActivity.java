package io.github.hufghani.emergencyairways.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;

import io.github.hufghani.emergencyairways.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_main);

        Button btntracheostomyAlgorithm = findViewById(R.id.btntracheostomy_algorithm);
        Button btnlaryngectomyAlgorithm = findViewById(R.id.btnlaryngectomy_algorithm);

        btntracheostomyAlgorithm.setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putString("ALGORITHM_NAME_KEY","Emergency tracheostomy management");
            b.putString("STEP_ID_KEY","2-step-1");


            Intent myIntent = new Intent(MainActivity.this,
                    AlogrithmActivity.class);
            myIntent.putExtras(b);
            startActivity(myIntent);

        });

        btnlaryngectomyAlgorithm.setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putString("ALGORITHM_NAME_KEY","Emergency laryngectomy management");
            b.putString("STEP_ID_KEY","1-step-1");


            Intent myIntent = new Intent(MainActivity.this,
                    AlogrithmActivity.class);
            myIntent.putExtras(b);
            startActivity(myIntent);
        });
    }
}
