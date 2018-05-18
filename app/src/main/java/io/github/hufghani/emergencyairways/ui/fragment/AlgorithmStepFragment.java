package io.github.hufghani.emergencyairways.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.hufghani.emergencyairways.R;
import io.github.hufghani.emergencyairways.model.Algorithm;
import io.github.hufghani.emergencyairways.model.Option;
import io.github.hufghani.emergencyairways.ui.activity.AlogrithmActivity;
import io.github.hufghani.emergencyairways.utils.HtmlTagHandler;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlgorithmStepFragment extends Fragment {

    List<Algorithm> algorithms;

    private static final String ALGORITHM_NAME_KEY = "ALGORITHM_NAME_KEY";
    private static final String STEP_ID_KEY = "STEP_ID_KEY";
    private String algorithmName;
    private String stepId;

    private static final String YES = "yes";
    private static final String NO = "no";

    private TextView algorithmTitle, stepTitle, stepContent,stepQuestion;
    private Button btnBack, btnNo, btnYes;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.algorithmName = savedInstanceState.getString(ALGORITHM_NAME_KEY);
           this.stepId = savedInstanceState.getString(STEP_ID_KEY);
        }
        return inflater.inflate(R.layout.fragment_alogrithm, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        algorithmName = args != null ? args.getString(ALGORITHM_NAME_KEY) : null;
        stepId = args != null ? args.getString(STEP_ID_KEY) : null;
        loadAlgorithmsFromAssets();
        populateViews();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ALGORITHM_NAME_KEY, algorithmName);
        outState.putString(STEP_ID_KEY, stepId);
    }

    public static Fragment newInstance(String algorithmName, String stepId) {
        Bundle args = new Bundle();
        AlgorithmStepFragment algorithmStepFragment = new AlgorithmStepFragment();
        args.putString(ALGORITHM_NAME_KEY, algorithmName);
        args.putString(STEP_ID_KEY, stepId);
        algorithmStepFragment.setArguments(args);
        return algorithmStepFragment;
    }


    private void loadAlgorithmsFromAssets() {
        algorithms = new ArrayList<>(2);
        try {
            InputStream is = Objects.requireNonNull(getActivity()).getAssets().open("algorithms.json");
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

        algorithmTitle = Objects.requireNonNull(getView()).findViewById(R.id.algorithmTitle);
        stepTitle = getView().findViewById(R.id.stepTitle);
        stepContent = getView().findViewById(R.id.stepContent);
        stepQuestion = getView().findViewById(R.id.stepQuestion);

        btnBack = getView().findViewById(R.id.btnBack);
        btnNo = getView().findViewById(R.id.btnNo);
        btnYes = getView().findViewById(R.id.btnYes);

        for (int i = 0; i < algorithms.size(); i++) {
            if (algorithms.get(i).getName().equals(algorithmName)) {
                for (int j = 0; j < algorithms.get(i).getSteps().size(); j++) {
                    if (algorithms.get(i).getSteps().get(j).getId().equals(stepId)) {
                        if (!TextUtils.isEmpty(algorithms.get(i).getName())) {
                            algorithmTitle.setText(algorithms.get(i).getName());
                        }
                        if (TextUtils.isEmpty(algorithms.get(i).getSteps().get(j).getTitle())) {
                            stepTitle.setVisibility(View.INVISIBLE);
                        } else {
                            Spanned html = Html.fromHtml(algorithms.get(i).getSteps().get(j).getTitle(), null, new HtmlTagHandler());
                            stepTitle.setText(html);
                            stepTitle.setVisibility(View.VISIBLE);
                        }
                        if (!TextUtils.isEmpty(algorithms.get(i).getSteps().get(j).getContent())) {
                            stepContent.setText(Html.fromHtml(algorithms.get(i).getSteps().get(j).getContent(), null, new HtmlTagHandler()));

                        }

                        if (!TextUtils.isEmpty((CharSequence) algorithms.get(i).getSteps().get(j).getQuestion())) {
                            stepQuestion.setText(Html.fromHtml(String.valueOf(algorithms.get(i).getSteps().get(j).getQuestion()), null, new HtmlTagHandler()));

                        }
                        btnBack.setVisibility(View.VISIBLE);
                        btnNo.setVisibility(View.VISIBLE);
                        btnYes.setVisibility(View.VISIBLE);

                        if (!algorithms.get(i).getSteps().get(j).getOptions().isEmpty()){
                            for (Object o : algorithms.get(i).getSteps().get(j).getOptions()) {
                                configureOptionButton((Option) o);
                            }
                        }else {
                            btnNo.setVisibility(View.INVISIBLE);
                            btnYes.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        }

        btnBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());

    }

    private void configureOptionButton(final Option option) {

        View.OnClickListener onOptionClicked = v -> ((AlogrithmActivity) Objects.requireNonNull(AlgorithmStepFragment.this.getActivity())).replaceFragment(AlgorithmStepFragment.newInstance(algorithmName, option.getTarget()), true);

        if (option.getCaption().equalsIgnoreCase(NO)) {
            btnNo.setBackgroundDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ripple_button_red_curved));
            btnNo.setVisibility(View.VISIBLE);
            btnNo.setText(option.getCaption());
            btnNo.setOnClickListener(onOptionClicked);
        } else if (option.getCaption().equalsIgnoreCase(YES)) {
        if (option.getCaption().equalsIgnoreCase(YES)) {
            btnYes.setBackgroundDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ripple_button_green_curved));
        } else {
            btnYes.setBackgroundDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ripple_button_blue_curved));
        }
            btnYes.setVisibility(View.VISIBLE);
            btnYes.setText(option.getCaption());
            btnYes.setOnClickListener(onOptionClicked);
        } else {
            btnYes.setVisibility(View.INVISIBLE);
            btnNo.setVisibility(View.VISIBLE);
            btnNo.setBackgroundDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ripple_button_green_curved));
            btnNo.setText(option.getCaption());
            btnNo.setOnClickListener(onOptionClicked);
        }


    }
}
