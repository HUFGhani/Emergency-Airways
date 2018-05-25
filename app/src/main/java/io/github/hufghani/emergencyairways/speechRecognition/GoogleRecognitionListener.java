package io.github.hufghani.emergencyairways.speechRecognition;

import android.os.Bundle;
import android.speech.RecognitionListener;

public interface GoogleRecognitionListener extends RecognitionListener {

    String TAG = GoogleRecognitionListener.class.getSimpleName();

    @Override
    void onReadyForSpeech(Bundle params);

    @Override
    void onBeginningOfSpeech();

    @Override
    void onRmsChanged(float rmsdB);

    @Override
    void onBufferReceived(byte[] buffer);

    @Override
    void onEndOfSpeech();

    @Override
    void onError(int error);

    @Override
    void onResults(Bundle results);

    @Override
    void onPartialResults(Bundle partialResults);

    @Override
    void onEvent(int eventType, Bundle params);
}
