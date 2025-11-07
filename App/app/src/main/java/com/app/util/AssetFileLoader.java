package com.app.util;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetFileLoader {
    private AssetManager assetManager;

    public AssetFileLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public InputStream openAssetFile(String fileName) throws IOException {
        return assetManager.open(fileName);
    }

    public String loadAssetFile(String fileName) throws IOException {
        InputStream inputStream = assetManager.open(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }
}