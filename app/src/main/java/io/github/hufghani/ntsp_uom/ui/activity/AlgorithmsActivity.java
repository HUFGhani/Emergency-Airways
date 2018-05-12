package io.github.hufghani.ntsp_uom.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import io.github.hufghani.ntsp_uom.R;

public class AlgorithmsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_algorithms);

        Button btntracheostomyAlgorithm = findViewById(R.id.btntracheostomy_algorithm);
        Button btnlaryngectomyAlgorithm = findViewById(R.id.btnlaryngectomy_algorithm);

            btntracheostomyAlgorithm.setOnClickListener((View view) -> {
                Bundle b = new Bundle();
                b.putString("ALGORITHM_NAME_KEY","Emergency tracheostomy management");
                b.putString("STEP_ID_KEY","2-step-1");


                Intent myIntent = new Intent(AlgorithmsActivity.this,
                        AlgorithmActivity.class);
                myIntent.putExtras(b);
                startActivity(myIntent);

            });

            btnlaryngectomyAlgorithm.setOnClickListener(view -> {
                Bundle b = new Bundle();
                b.putString("ALGORITHM_NAME_KEY","Emergency laryngectomy management");
                b.putString("STEP_ID_KEY","1-step-1");


                Intent myIntent = new Intent(AlgorithmsActivity.this,
                        AlgorithmActivity.class);
                myIntent.putExtras(b);
                startActivity(myIntent);
            });
    }
}

