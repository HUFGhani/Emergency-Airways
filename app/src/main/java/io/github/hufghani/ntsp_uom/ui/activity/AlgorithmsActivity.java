package io.github.hufghani.ntsp_uom.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import io.github.hufghani.ntsp_uom.R;
import io.github.hufghani.ntsp_uom.databinding.ActivityAlgorithmsBinding;

public class AlgorithmsActivity extends Activity {

    private ActivityAlgorithmsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_algorithms);

        binding.btntracheostomyAlgorithm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("ALGORITHM_NAME_KEY","Emergency tracheostomy management");
                b.putString("STEP_ID_KEY","2-step-1");


                Intent myIntent = new Intent(AlgorithmsActivity.this,
                        AlgorithmActivity.class);
                myIntent.putExtras(b);
                startActivity(myIntent);

            }
        });

        binding.btnlaryngectomyAlgorithm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("ALGORITHM_NAME_KEY","Emergency laryngectomy management");
                b.putString("STEP_ID_KEY","1-step-1");


                Intent myIntent = new Intent(AlgorithmsActivity.this,
                        AlgorithmActivity.class);
                myIntent.putExtras(b);
                startActivity(myIntent);
            }
        });
    }
}
