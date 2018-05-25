package io.github.hufghani.emergencyairways.speechRecognition;

import java.util.ArrayList;

public interface OnResultListener {
    public void OnResult(ArrayList<String> commands);
}
