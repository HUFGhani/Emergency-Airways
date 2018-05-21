package io.github.hufghani.emergencyairways.ui.fragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.github.hufghani.emergencyairways.R;
import io.github.hufghani.emergencyairways.model.Algorithm;
import io.github.hufghani.emergencyairways.model.Option;
import io.github.hufghani.emergencyairways.ui.activity.AlogrithmActivity;
import io.github.hufghani.emergencyairways.utils.HtmlTagHandler;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlgorithmStepFragment extends Fragment implements
        TextToSpeech.OnInitListener {

    List<Algorithm> algorithms;

    private static final String ALGORITHM_NAME_KEY = "ALGORITHM_NAME_KEY";
    private static final String STEP_ID_KEY = "STEP_ID_KEY";
    private static final String TEXT_TO_SPEECH_KEY = "TEXT_TO_SPEECH_KEY";
    private static final String YES = "yes";
    private static final String NO = "no";
    private String algorithmName, stepId;
    private Button btnNo, btnYes, btnBack;
    private FloatingActionButton textToSpeach;
    private TextToSpeech tts;
    private boolean mute = false;
    private String text = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            algorithmName = savedInstanceState.getString(ALGORITHM_NAME_KEY);
            stepId = savedInstanceState.getString(STEP_ID_KEY);
            mute = savedInstanceState.getBoolean(TEXT_TO_SPEECH_KEY);
        }
        return inflater.inflate(R.layout.fragment_alogrithm, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        algorithmName = args != null ? args.getString(ALGORITHM_NAME_KEY) : null;
        stepId = args != null ? args.getString(STEP_ID_KEY) : null;
        mute = args != null && args.getBoolean(TEXT_TO_SPEECH_KEY, false);
        loadAlgorithmsFromAssets();
        populateViews();
        tts = new TextToSpeech(getActivity(), this);



    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ALGORITHM_NAME_KEY, algorithmName);
        outState.putString(STEP_ID_KEY, stepId);
        outState.putBoolean(TEXT_TO_SPEECH_KEY, mute);
    }

    public static Fragment newInstance(String algorithmName, String stepId, Boolean textToSpeach) {
        Bundle args = new Bundle();
        AlgorithmStepFragment algorithmStepFragment = new AlgorithmStepFragment();
        args.putString(ALGORITHM_NAME_KEY, algorithmName);
        args.putString(STEP_ID_KEY, stepId);
        args.putBoolean(TEXT_TO_SPEECH_KEY, textToSpeach);

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

        TextView algorithmTitle = Objects.requireNonNull(getView()).findViewById(R.id.algorithmTitle);
        TextView stepTitle = getView().findViewById(R.id.stepTitle);
        TextView stepContent = getView().findViewById(R.id.stepContent);
        TextView stepQuestion = getView().findViewById(R.id.stepQuestion);

        btnBack = getView().findViewById(R.id.btnBack);
        btnNo = getView().findViewById(R.id.btnNo);
        btnYes = getView().findViewById(R.id.btnYes);

        textToSpeach = getView().findViewById(R.id.textToSpeach);
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < algorithms.size(); i++) {
            if (algorithms.get(i).getName().equals(algorithmName)) {
                for (int j = 0; j < algorithms.get(i).getSteps().size(); j++) {
                    if (algorithms.get(i).getSteps().get(j).getId().equals(stepId)) {
                        if (!TextUtils.isEmpty(algorithms.get(i).getName())) {
                            algorithmTitle.setText(algorithms.get(i).getName());
                            sb.append(algorithms.get(i).getName());
                        }
                        if (TextUtils.isEmpty(algorithms.get(i).getSteps().get(j).getTitle())) {
                            stepTitle.setVisibility(View.INVISIBLE);
                        } else {
                            Spanned html = Html.fromHtml(algorithms.get(i).getSteps().get(j).getTitle(), null, new HtmlTagHandler());
                            stepTitle.setText(html);
                            sb.append(html);
                            stepTitle.setVisibility(View.VISIBLE);
                        }
                        if (!TextUtils.isEmpty(algorithms.get(i).getSteps().get(j).getContent())) {
                            stepContent.setText(Html.fromHtml(algorithms.get(i).getSteps().get(j).getContent(), null, new HtmlTagHandler()));
                            sb.append(algorithms.get(i).getSteps().get(j).getContent());
                        }

                        if (!TextUtils.isEmpty((CharSequence) algorithms.get(i).getSteps().get(j).getQuestion())) {
                            stepQuestion.setText(Html.fromHtml(String.valueOf(algorithms.get(i).getSteps().get(j).getQuestion()), null, new HtmlTagHandler()));
                            sb.append("Question").append(algorithms.get(i).getSteps().get(j).getQuestion());
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

        text = sb.toString();
        btnBack.setBackgroundDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ripple_button_blue_curved));
        btnBack.setOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());

        if(mute){
            btnNo.setEnabled(true);
            btnYes.setEnabled(true);
            btnBack.setEnabled(true);
            textToSpeach.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
        }
        textToSpeach.setVisibility(View.VISIBLE);
        textToSpeach.setOnClickListener(v -> {
            if (textToSpeach.isPressed()){
                mute = !mute;
            }
            if (mute){
                tts.stop();
                btnNo.setEnabled(true);
                btnYes.setEnabled(true);
                btnBack.setEnabled(true);
                textToSpeach.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
            }else {
                textToSpeach.setImageResource(android.R.drawable.ic_lock_silent_mode);
                speakOut(); 
            }
        });

    }

    private void configureOptionButton(final Option option) {

        View.OnClickListener onOptionClicked = v -> ((AlogrithmActivity) Objects.requireNonNull(AlgorithmStepFragment.this.getActivity())).replaceFragment(AlgorithmStepFragment.newInstance(algorithmName, option.getTarget(),mute), true);

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

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                @Override
                public void onStart(String utteranceId) {
                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                        btnNo.setEnabled(false);
                        btnYes.setEnabled(false);
                        btnBack.setEnabled(false);

                    });
                }

                @Override
                public void onDone(String utteranceId) {
                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                            btnNo.setEnabled(true);
                            btnYes.setEnabled(true);
                            btnBack.setEnabled(true);
                    });
                }

                @Override
                public void onError(String utteranceId) {

                }
            });

            int result = tts.setLanguage(Locale.UK);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();

            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    private void speakOut() {
        if (mute) {
            tts.stop();
        }else {
            HashMap<String, String> map = new HashMap<>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
            tts.speak(html2text(text), TextToSpeech.QUEUE_FLUSH, map);
        }

    }


}
