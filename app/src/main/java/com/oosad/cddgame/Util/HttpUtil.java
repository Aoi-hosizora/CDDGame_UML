package com.oosad.cddgame.Util;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static final String Header = "Header";
    public static final String Body = "Body";
    public static final String Token = "Authorization";
    public static final String StatusCode = "StatusCode";

    /**
     * OKHttp Post
     * @param url
     * @param json
     * @return
     *      StatusCode
     *      Header
     *      Token
     *      Body
     * @throws IOException
     */
    public static Map<String, String> HttpPost(URL url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            HashMap<String, String> ret = new HashMap<>();

            ret.put(StatusCode, String.valueOf(response.code()));
            ret.put(Header, response.headers().toString());
            ret.put(Token, response.header(Token, ""));
            ret.put(Body, response.body().string());

            return ret;
        }
    }
}
