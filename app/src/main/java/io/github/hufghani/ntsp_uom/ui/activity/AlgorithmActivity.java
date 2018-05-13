package io.github.hufghani.ntsp_uom.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import io.github.hufghani.ntsp_uom.R;
import io.github.hufghani.ntsp_uom.databinding.ActivityAlogrithmBinding;
import io.github.hufghani.ntsp_uom.ui.fragment.AlgorithmStepFragment;


/**
 * Created by hamzaghani on 02/03/2018.
 */

public class AlgorithmActivity extends FragmentActivity {

    @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        /* Called when the activity is first created. */
        ActivityAlogrithmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_alogrithm);

    if (binding.fragmentContainer != null) {

        if (savedInstanceState != null) {
            return;
        }

        Intent in = getIntent();
        Bundle b = in.getExtras();


        String algorithmName = b != null ? b.getString("ALGORITHM_NAME_KEY") : null;
        String stepId = b != null ? b.getString("STEP_ID_KEY") : null;


        Bundle bundle = new Bundle();
        bundle.putString("ALGORITHM_NAME_KEY", algorithmName);
        bundle.putString("STEP_ID_KEY", stepId);

        AlgorithmStepFragment firstFragment = new AlgorithmStepFragment();
        firstFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .add(binding.fragmentContainer.getId(), firstFragment).commit();
    }
}

    public void replaceFragment(AlgorithmStepFragment fragment, boolean addToBackStack) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }



    public void popAllFragmentsFromBackstack() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        popAllFragmentsFromBackstack();
        super.onBackPressed();
    }
}
