package io.github.hufghani.ntsp_uom.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import io.github.hufghani.ntsp_uom.R;
import io.github.hufghani.ntsp_uom.databinding.ActivityAlogrithmBinding;
import io.github.hufghani.ntsp_uom.ui.fragment.AlgorithmStepFragment;


/**
 * Created by hamzaghani on 02/03/2018.
 */

public class AlgorithmActivity extends FragmentActivity {
    /** Called when the activity is first created. */


    private ActivityAlogrithmBinding binding;
    private String algorithmName;
    private String stepId;

    @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_alogrithm);

    if (binding.fragmentContainer != null) {

        if (savedInstanceState != null) {
            return;
        }

        Intent in = getIntent();
        Bundle b = in.getExtras();


        this.algorithmName = b.getString("ALGORITHM_NAME_KEY");
        this.stepId = b.getString("STEP_ID_KEY");


        Bundle bundle = new Bundle();
        bundle.putString("ALGORITHM_NAME_KEY", this.algorithmName);
        bundle.putString("STEP_ID_KEY", this.stepId);

        AlgorithmStepFragment firstFragment = new AlgorithmStepFragment();
        firstFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(binding.fragmentContainer.getId(), firstFragment).commit();
    }
}

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(binding.fragmentContainer.getId(), fragment).commit();

    }


    public void popFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack();
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
