package com.oosad.cddgame.UI.LoginAct.presenter;

public interface ILoginPresenter {

    void HandleLogin(String UserName, String PlainPassWord);
    void HandleRegister(String UserName, String PlainPassWord);
    String HandleGetLastLoginUser();
    void HandleJumpLogin();
}
