package com.oosad.cddgame.UI.LoginAct.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.oosad.cddgame.DB.UserDAO;
import com.oosad.cddgame.Data.Boundary.GameSystem;
import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Entity.Player.User;
import com.oosad.cddgame.Net.PostLoginRegister;
import com.oosad.cddgame.UI.LoginAct.view.ILoginView;
import com.oosad.cddgame.UI.MainAct.MainActivity;


public class LoginPresenterCompl implements ILoginPresenter {

    private ILoginView m_loginView;
    private UserDAO userDAO;


    public LoginPresenterCompl(ILoginView loginView) {
        this.m_loginView = loginView;
        userDAO = new UserDAO(m_loginView.getThisPtr());
    }

    @Override
    public String HandleGetLastLoginUser() {
        return userDAO.queryLast();
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "LoginPresenterCompl";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    private final int RegisterSuccess = 0;
    private final int RegisterError = 1;
    private final int LoginSuccess = 2;
    private final int LoginError = 3;
    private final Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case RegisterSuccess:
                    m_loginView.onClearPassWord();
                    m_loginView.onShowRegisterSuccessAlert();
                break;
                case RegisterError:
                    m_loginView.onShowErrorRegisterAlert();
                break;
                case LoginSuccess:
                    m_loginView.onShowLoginSuccessToast(GameSystem.getInstance().getCurrUser().getName());
                    gotoMainActivity();
                break;
                case LoginError:
                    m_loginView.onShowErrorPassWordAlert();
                break;

            }
            return false;
        }
    });

    /**
     * 处理登陆，并判断有效性
     * @param UserName
     * @param PlainPassWord
     */
    @Override
    public void HandleLogin(final String UserName, final String PlainPassWord) {

        // 用户名为空
        if (UserName.isEmpty() || PlainPassWord.isEmpty()) {
            m_loginView.onShowNoUserNameOrPassWordAlert();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                String token = PostLoginRegister.PostLogin(UserName, PlainPassWord);
                if (!token.isEmpty()) {

                    GameSystem.getInstance().setCurrUserToken(token);
                    GameSystem.getInstance().setCurrUser(new User(UserName));
                    if (userDAO.queryUser(UserName) == null)
                        userDAO.insertUser(UserName);
                    userDAO.setIsLast(UserName);

                    Message message = new Message();
                    message.what = LoginSuccess;
                    handler.sendMessage(message);
                }
                else {
                    Message message = new Message();
                    message.what = LoginError;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    /**
     * 处理注册，并判断有效性
     * @param UserName
     * @param PlainPassWord
     */
    @Override
    public void HandleRegister(final String UserName, final String PlainPassWord) {

        // 用户名密码空
        if (UserName.isEmpty() || PlainPassWord.isEmpty()) {
            m_loginView.onShowNoUserNameOrPassWordAlert();
            return;
        }

        // 密码格式错
        if (PlainPassWord.length() < Constant.PassWordMinLen || PlainPassWord.length() > Constant.PassWordMaxLen) {
            m_loginView.onShowErrorPassWordFormatAlert();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                if (!PostLoginRegister.PostRegister(UserName, PlainPassWord)) {
                    Message message = new Message();
                    message.what = RegisterError;
                    handler.sendMessage(message);
                }
                else {
                    Message message = new Message();
                    message.what = RegisterSuccess;
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    /**
     * 登陆成功，跳转到主界面
     */
    private void gotoMainActivity() {
        Intent MainActIntent = new Intent(m_loginView.getThisPtr(), MainActivity.class);
        m_loginView.getThisPtr().startActivity(MainActIntent);
    }

    /**
     * 跳过登陆
     */
    @Override
    public void HandleJumpLogin() {
        GameSystem.getInstance().setCurrUser(new User("未登录用户"));
        GameSystem.getInstance().setCurrUserToken(""); // 空 token 判断用
        gotoMainActivity();
    }
}
