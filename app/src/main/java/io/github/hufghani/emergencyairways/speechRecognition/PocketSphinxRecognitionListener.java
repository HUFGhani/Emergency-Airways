package io.github.hufghani.emergencyairways.speechRecognition;

import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;

interface PocketSphinxRecognitionListener extends RecognitionListener {

    @Override
    void onBeginningOfSpeech();

    @Override
    void onEndOfSpeech();

    @Override
    void onPartialResult(Hypothesis hypothesis);

    @Override
    void onResult(Hypothesis hypothesis);

    @Override
    void onError(Exception e);

    @Override
    void onTimeout();
}
