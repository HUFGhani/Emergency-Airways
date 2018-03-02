package io.github.hufghani.ntsp_uom.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import io.github.hufghani.ntsp_uom.R;
import io.github.hufghani.ntsp_uom.ui.fragment.AlgorithmStepFragment;


/**
 * Created by hamzaghani on 02/03/2018.
 */

public class AlgorithmActivity extends FragmentActivity {
    /** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alogrithm);

    // Check whether the activity is using the layout version with
    // the fragment_container FrameLayout. If so, we must add the first fragment
    if (findViewById(R.id.fragment_container) != null) {

        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }

        // Create an instance of ExampleFragment
        AlgorithmStepFragment firstFragment = new AlgorithmStepFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        firstFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstFragment).commit();
    }
}

}
