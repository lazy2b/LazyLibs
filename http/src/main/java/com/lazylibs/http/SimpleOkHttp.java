package com.lazylibs.http;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SimpleOkHttp implements Callback {
    private String retryUrl = "";

    public OkHttpClient getRetry(String url) {
        retryUrl = url;
        OkHttpClient client = getHttpBuilder().build();
        try {
            client.newCall(new Request.Builder().url(url).get().build()).enqueue(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return client;
    }

    public OkHttpClient get(String url) {
        OkHttpClient client = getHttpBuilder().build();
        try {
            client.newCall(new Request.Builder().url(url).get().build()).enqueue(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return client;
    }

    public OkHttpClient.Builder getHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Override
    public final void onFailure(@NonNull Call call, @NonNull IOException e) {
        if (TextUtils.isEmpty(retryUrl)) {
            onFailure(e);
        } else {
            e.printStackTrace();
            Log.e("SimpleOkhttp", "retry:"+retryUrl);
            get(retryUrl);
        }
    }

    @Override
    public final void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        ResponseBody body = response.body();
        if (response.code() == 200) {
            if (body != null && body.contentLength() != 0) {
                String bodyString = body.string();
                Log.d("OkHttp", bodyString);
                onSuccess(bodyString);
            }
        } else {
            onFailure(new Exception(String.format("Code:%s, Message: %s", response.code(), response.message())));
        }
    }

    public void onFailure(@NonNull Exception exception) {
        exception.printStackTrace();
    }

    public void onSuccess(@Nullable String data) {

    }
}
