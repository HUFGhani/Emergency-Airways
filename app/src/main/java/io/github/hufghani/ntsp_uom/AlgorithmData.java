package io.github.hufghani.ntsp_uom;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.github.hufghani.ntsp_uom.model.Algorithm;

/**
 * Created by hamzaghani on 02/03/2018.
 */

public class AlgorithmData {
    private List<Algorithm> algorithms;
    Gson gson;
    Context context;

    public AlgorithmData() {
        loadAlgorithmsFromAssets();
    }

    private void loadAlgorithmsFromAssets() {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open("algorithms.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            List<Algorithm> newAlgorithms = (List) this.gson.fromJson(new String(buffer, "UTF-8"), Algorithm.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Algorithm> getAlgorithms() {
        return algorithms;
    }
}
