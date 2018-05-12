package io.github.hufghani.ntsp_uom.ui.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.hufghani.ntsp_uom.R;
import io.github.hufghani.ntsp_uom.databinding.AlogrithmStepBinding;
import io.github.hufghani.ntsp_uom.model.Algorithm;
import io.github.hufghani.ntsp_uom.model.Option;
import io.github.hufghani.ntsp_uom.ui.activity.AlgorithmActivity;
import io.github.hufghani.ntsp_uom.utils.HtmlTagHandler;

/**
 * Created by hamzaghani on 26/03/2018.
 */

public class AlgorithmStepFragment extends Fragment {
    List<Algorithm> algorithms;
    private AlogrithmStepBinding binding;
    private static final String ALGORITHM_NAME_KEY = "ALGORITHM_NAME_KEY";
    private static final String NO = "no";
    private static final String STEP_ID_KEY = "STEP_ID_KEY";
    private static final String YES = "yes";
    private String algorithmName;
    private String stepId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.alogrithm_step, container, false);
        View view = binding.getRoot();
        if (savedInstanceState != null) {
            algorithmName = savedInstanceState.getString(ALGORITHM_NAME_KEY);
            stepId = savedInstanceState.getString(STEP_ID_KEY);
        }
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        algorithmName = args.getString(ALGORITHM_NAME_KEY);
        stepId = args.getString(STEP_ID_KEY);
        loadAlgorithmsFromAssets();
        populateViews();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ALGORITHM_NAME_KEY, algorithmName);
        outState.putString(STEP_ID_KEY, stepId);
    }


    private void loadAlgorithmsFromAssets() {
        algorithms = new ArrayList<>(2);
        try {
            InputStream is = getActivity().getAssets().open("algorithms.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String bufferString = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            algorithms = gson.fromJson(bufferString, new TypeToken<ArrayList<Algorithm>>() {
            }.getType());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void populateViews() {

        for (int i = 0; i < algorithms.size(); i++) {
            if (algorithms.get(i).getName().equals(algorithmName)) {
                for (int j = 0; j < algorithms.get(i).getSteps().size(); j++) {
                    if (algorithms.get(i).getSteps().get(j).getId().equals(stepId)) {
                        if (!TextUtils.isEmpty(algorithms.get(i).getName())) {
                            binding.algorithmTitle.setText(algorithms.get(i).getName());
                        }
                        if (TextUtils.isEmpty(algorithms.get(i).getSteps().get(j).getTitle())) {
                            binding.stepTitle.setVisibility(View.INVISIBLE);
                        } else {
                            Spanned html = Html.fromHtml(algorithms.get(i).getSteps().get(j).getTitle(), null, new HtmlTagHandler());
                            binding.stepTitle.setText(html);
                            binding.stepTitle.setVisibility(View.VISIBLE);
                        }
                        if (!TextUtils.isEmpty(algorithms.get(i).getSteps().get(j).getContent())) {
                            binding.stepContent.setText(Html.fromHtml(algorithms.get(i).getSteps().get(j).getContent(), null, new HtmlTagHandler()));

                        }

                        if (!TextUtils.isEmpty((CharSequence) algorithms.get(i).getSteps().get(j).getQuestion())) {
                            binding.stepQuestion.setText(Html.fromHtml(String.valueOf(algorithms.get(i).getSteps().get(j).getQuestion()), null, new HtmlTagHandler()));

                        }
                        binding.btnNo.setVisibility(View.VISIBLE);
                        binding.btnYes.setVisibility(View.VISIBLE);

                        if (!algorithms.get(i).getSteps().get(j).getOptions().isEmpty()){
                            for (Object o : algorithms.get(i).getSteps().get(j).getOptions()) {

                                configureOptionButton((Option) o);

                            }
                        }
                    }
                }
            }
        }


    }

    private void configureOptionButton(final Option option) {

        View.OnClickListener onOptionClicked = new View.OnClickListener() {
            public void onClick(View v) {
                ((AlgorithmActivity) AlgorithmStepFragment.this.getActivity()).replaceFragment(newInstance(algorithmName, option.getTarget()), true);
            }
        };

        if (option.getCaption().equalsIgnoreCase(NO)) {
//            this.optionNoButton.setBackgroundDrawable(getActivity().getResources().getDrawable(C0231R.drawable.ripple_button_red_curved));
            binding.btnNo.setVisibility(View.VISIBLE);
            binding.btnNo.setText(option.getCaption());
            binding.btnNo.setOnClickListener(onOptionClicked);
        } else if (option.getCaption().equalsIgnoreCase(YES)) {
//        if (option.getCaption().equalsIgnoreCase(YES)) {
//            this.optionYesButton.setBackgroundDrawable(getActivity().getResources().getDrawable(C0231R.drawable.ripple_button_green_curved));
//        } else {
//            this.optionYesButton.setBackgroundDrawable(getActivity().getResources().getDrawable(C0231R.drawable.ripple_button_blue_curved));
//        }
            binding.btnYes.setVisibility(View.VISIBLE);
            binding.btnYes.setText(option.getCaption());
            binding.btnYes.setOnClickListener(onOptionClicked);
        } else {
            binding.btnYes.setVisibility(View.INVISIBLE);
            binding.btnNo.setVisibility(View.VISIBLE);
            binding.btnNo.setText(option.getCaption());
            binding.btnNo.setOnClickListener(onOptionClicked);
        }


    }

    public static AlgorithmStepFragment newInstance(String algorithmName, String stepId) {
        Bundle args = new Bundle();
        AlgorithmStepFragment algorithmStepFragment = new AlgorithmStepFragment();
        args.putString(ALGORITHM_NAME_KEY, algorithmName);
        args.putString(STEP_ID_KEY, stepId);
        algorithmStepFragment.setArguments(args);
        return algorithmStepFragment;
    }


}