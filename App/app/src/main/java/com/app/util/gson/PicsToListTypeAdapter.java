package com.app.util.gson;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PicsToListTypeAdapter extends TypeAdapter<List<String>> {

    @Override
    public void write(JsonWriter out, List<String> value) throws IOException {

    }

    @Override
    public List<String> read(JsonReader in) throws IOException {
        String content = in.nextString();
        String[] strings = content.split(",");
        if (strings.length == 1 && TextUtils.isEmpty(strings[0])) {
            return null;
        }
        return Arrays.asList(strings);
    }

}
