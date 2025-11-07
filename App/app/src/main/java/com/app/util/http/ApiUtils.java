package com.app.util.http;

import com.app.bean.Ini;
import com.app.bean.Msg;
import com.app.util.ToolUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;

import okhttp3.Response;

public class ApiUtils {

    public static Ini ini() throws Throwable {
        Response response = OkGo.<String>post(ToolUtils.setApi("ini"))
                .execute();
        String content = response.body().string();
        response.close();
        Msg<Ini> msg = new Gson()
                .fromJson(content, new TypeToken<Msg<Ini>>(){}.getType());
        Ini ini = msg.msg;
        return ini;
    }

}
