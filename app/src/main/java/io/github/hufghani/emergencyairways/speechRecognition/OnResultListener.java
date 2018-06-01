package io.github.hufghani.emergencyairways.speechRecognition;

import java.util.ArrayList;

public interface OnResultListener {
    void OnResult(ArrayList<String> commands);
}
