package com.oosad.cddgame.Net;

import android.util.Log;

import com.oosad.cddgame.Util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class PostLoginRegister {

    private static void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "SocketHandlers";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    private static String RegisterURL = "http://api.gajon.xyz:8888/cdd/user/register";
    private static String LoginURL = "http://api.gajon.xyz:8888/cdd/user/login";

    private static String RetNull = "";

    /**
     * Http Post 辅助方法，获得映射
     * @param Url
     * @param UserName
     * @param PassWord
     * @return
     *      StatusCode
     *      Header
     *      Token
     *      Body
     */
    private static Map<String, String> PostHandler(String Url, String UserName, String PassWord) {
        JSONObject UserPassInfo;
        try {
            UserPassInfo = new JSONObject();
            UserPassInfo.put("username", UserName);
            UserPassInfo.put("password", PassWord);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

        Map<String, String> postret;
        try {
            postret = HttpUtil.HttpPost(new URL(Url), UserPassInfo.toString());
        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return postret;
    }

    /**
     * 处理状态码
     * @param codestr
     * @return -1 / 200 / ...
     */
    private static int HandleStatusCode(String codestr) {
        int code;
        if (codestr == null || codestr.isEmpty())
            return -1;

        try {
            code = Integer.parseInt(codestr);
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
            return -1;
        }

        return code;
    }

    /**
     * 判断用户是否相同
     * @param body
     * @param UserName
     * @return
     */
    private static boolean CheckSameUser(String body, String UserName) {

        // body: {"username":"testuser"}

        String userNameFromJson;
        try {
            JSONObject jsonHead = new JSONObject(body);
            userNameFromJson = jsonHead.getString("username");
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return false;
        }

        return userNameFromJson.equals(UserName);
    }

    /**
     * 登录 Post
     * @param UserName
     * @param PassWord
     * @return token / ""
     */
    public static String PostLogin(String UserName, String PassWord) {

        Map<String, String> ret = PostHandler(LoginURL, UserName, PassWord);
        // 空回应
        if (ret == null)
            return RetNull;

        String codestr = ret.get(HttpUtil.StatusCode);
        // !200
        if (HandleStatusCode(codestr) != 200)
            return RetNull;

        // 用户不匹配
       if (!CheckSameUser(ret.get(HttpUtil.Body), UserName))
           return RetNull;

        String token = ret.get(HttpUtil.Token);

        ShowLogE("PostLogin", "RET: " + UserName + " -> " + token.split(" ")[1]);

        return token;
    }

    /**
     * 注册 Post
     * @param UserName
     * @param PassWord
     * @return
     */
    public static boolean PostRegister(String UserName, String PassWord) {
        Map<String, String> ret = PostHandler(RegisterURL, UserName, PassWord);

        // 空回应
        if (ret == null)
            return false;

        String codestr = ret.get(HttpUtil.StatusCode);

        return HandleStatusCode(codestr) == 200;
    }

}
