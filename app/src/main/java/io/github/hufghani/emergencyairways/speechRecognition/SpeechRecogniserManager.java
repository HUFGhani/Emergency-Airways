package io.github.hufghani.emergencyairways.speechRecognition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;


import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

public class SpeechRecogniserManager {
    /* Named searches allow to quickly reconfigure the decoder */
    private static final String KWS_SEARCH = "wakeup";
    /* Keyword we are looking for to activate menu */
    private static final String KEYPHRASE = "oh mighty computer";

    private static final String TAG = SpeechRecogniserManager.class.getSimpleName();

    private Intent mSpeechRecognizerIntent;
    private android.speech.SpeechRecognizer mGoogleSpeechRecognizer;
    private edu.cmu.pocketsphinx.SpeechRecognizer mPocketSphinxRecognizer;

    private Context mContext;
    private OnResultListener mOnResultListener;


    public SpeechRecogniserManager(Context context) {
        this.mContext = context;
        initPockerSphinx();
        initGoogleSpeechRecognizer();

    }

    public SpeechRecognizer getmPocketSphinxRecognizer() {
        return mPocketSphinxRecognizer;
    }

    public static String getKwsSearch() {
        return KWS_SEARCH;
    }

    public void stop(){
        mPocketSphinxRecognizer.cancel();
    }

    public void start(){
        mPocketSphinxRecognizer.startListening(KWS_SEARCH);
    }

    @SuppressLint("StaticFieldLeak")
    private void initPockerSphinx() {

        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(mContext);

                    //Performs the synchronization of assets in the application and external storage
                    File assetDir = assets.syncAssets();

                    //Creates a new SpeechRecognizer builder with a default configuration
                    SpeechRecognizerSetup speechRecognizerSetup = defaultSetup();

                    //Set Dictionary and Acoustic Model files
                    speechRecognizerSetup.setAcousticModel(new File(assetDir, "en-us-ptm"));
                    speechRecognizerSetup.setDictionary(new File(assetDir, "cmudict-en-us.dict"));

                    // Threshold to tune for keyphrase to balance between false positives and false negatives
                    speechRecognizerSetup.setKeywordThreshold(1e-45f);

                    //Creates a new SpeechRecognizer object based on previous set up.
                    mPocketSphinxRecognizer = speechRecognizerSetup.getRecognizer();

                    mPocketSphinxRecognizer.addListener(new PocketSphinxRecognitionListener() {
                        @Override
                        public void onBeginningOfSpeech() {
                        }

                        @Override
                        public void onPartialResult(Hypothesis hypothesis) {
                            if (hypothesis == null){
                                Log.d(TAG,"null");
                                return;
                            }
                            String text = hypothesis.getHypstr();
                            Log.d(TAG, "What you said " + text);
                            if (text.equals(KEYPHRASE)) {
                                mPocketSphinxRecognizer.cancel();
                                mGoogleSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                                //Toast.makeText(mContext, "You said: "+text, Toast.LENGTH_SHORT).show();
                            }
                            hypothesis.delete();
                        }

                        @Override
                        public void onResult(Hypothesis hypothesis) {

                        }

                        @Override
                        public void onEndOfSpeech() {

                        }

                        public void onError(Exception error) {
                        }

                        @Override
                        public void onTimeout() {
                        }
                    });

                    // Create keyword-activation search.
                    mPocketSphinxRecognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
                } catch (IOException e) {
                    System.err.print(e.getMessage());
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                //System.err.println("Something is fucking wrong\n" + result.getMessage());
                if (result != null) {
                    Toast.makeText(mContext, "Failed to init mPocketSphinxRecognizer ", Toast.LENGTH_SHORT).show();
                } else {
                    restartSearch(KWS_SEARCH);
                }
            }
        }.execute();

    }

    private void initGoogleSpeechRecognizer() {

        mGoogleSpeechRecognizer = android.speech.SpeechRecognizer.createSpeechRecognizer(mContext);
        mGoogleSpeechRecognizer.setRecognitionListener(new GoogleRecognitionListener() {

            String TAG = GoogleRecognitionListener.class.getSimpleName();
            @Override
            public void onResults(Bundle results) {
                if ((results != null) && results.containsKey(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)) {
                    ArrayList<String> heard = results.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION);
                    float[] scores = results.getFloatArray(android.speech.SpeechRecognizer.CONFIDENCE_SCORES);

                    for (int i = 0; i < (heard != null ? heard.size() : 0); i++) {
                        Log.d(TAG, "onResultsheard:" + heard.get(i)
                                + " confidence:" + (scores != null ? scores[i] : 0));

                    }
                    //send list of words to activity
                    if (mOnResultListener!=null){
                        mOnResultListener.OnResult(heard);
                    }
                    Objects.requireNonNull(heard).clear();

                }

            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
                assert results != null;
                results.clear();
                mPocketSphinxRecognizer.startListening(KWS_SEARCH);

            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                Log.d(TAG, "onPartialResultsheard:");
            }

            @Override
            public void onError(int error) {
                Log.e(TAG, "onError:" + error);
                mPocketSphinxRecognizer.startListening(KWS_SEARCH);
            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }

            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }
        });
        mSpeechRecognizerIntent = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra( RecognizerIntent. EXTRA_CONFIDENCE_SCORES, true);
    }


    public void destroy() {
        if (mPocketSphinxRecognizer != null) {
            mPocketSphinxRecognizer.cancel();
            mPocketSphinxRecognizer.shutdown();
            mPocketSphinxRecognizer = null;
        }

        if (mGoogleSpeechRecognizer != null) {
            mGoogleSpeechRecognizer.cancel();
            mGoogleSpeechRecognizer.destroy();
            mPocketSphinxRecognizer = null;
        }

    }

    private void restartSearch(String searchName) {
        mPocketSphinxRecognizer.stop();
        mPocketSphinxRecognizer.startListening(searchName);

    }

    public void setOnResultListner(OnResultListener onResultListener){
        mOnResultListener=onResultListener;
    }


}
