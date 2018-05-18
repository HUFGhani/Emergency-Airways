package io.github.hufghani.emergencyairways.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import io.github.hufghani.emergencyairways.R;
import io.github.hufghani.emergencyairways.ui.fragment.AlgorithmStepFragment;

public class AlogrithmActivity extends FragmentActivity {

    private final String ALGORITHM_NAME_KEY = "ALGORITHM_NAME_KEY";
    private final String STEP_ID_KEY = "STEP_ID_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alogrithm);

        Intent in = getIntent();
        Bundle bundleReceived = in.getExtras();

        String algorithmName = bundleReceived != null ? bundleReceived.getString("ALGORITHM_NAME_KEY") : null;
        String stepId = bundleReceived != null ? bundleReceived.getString("STEP_ID_KEY") : null;

        Bundle bundle = new Bundle();
        bundle.putString(ALGORITHM_NAME_KEY, algorithmName);
        bundle.putString(STEP_ID_KEY, stepId);

        AlgorithmStepFragment firstFragment = new AlgorithmStepFragment();
        firstFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, firstFragment).commit();
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();

    }



    public void popAllFragmentsFromBackstack() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment ignored : fragments) {
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
