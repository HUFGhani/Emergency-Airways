package io.github.hufghani.ntsp_uom.ui.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.hufghani.ntsp_uom.R;
import io.github.hufghani.ntsp_uom.databinding.AlogrithmStepBinding;
import io.github.hufghani.ntsp_uom.model.Algorithm;

/**
 * Created by hamzaghani on 02/03/2018.
 */

public class AlgorithmStepFragment extends Fragment {
    List<Algorithm> algorithms;
    private Gson gson;
    private AlogrithmStepBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.

        if (savedInstanceState != null) {

        }

        binding = DataBindingUtil.inflate(
                inflater, R.layout.alogrithm_step, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        //binding.setMarsdata(data);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        getJson();

        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment

    }


    public void getJson() {
        algorithms = new ArrayList<Algorithm>();
        try {
            InputStream is = getActivity().getAssets().open("algorithms.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            gson = new Gson();

            algorithms = gson.fromJson(new String(buffer, "UTF-8"), new TypeToken<List<Algorithm>>() {
            }.getType());
            Toast.makeText(getActivity(),algorithms.toString(),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
