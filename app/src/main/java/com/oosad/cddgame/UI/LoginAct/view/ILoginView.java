package com.oosad.cddgame.UI.LoginAct.view;

import com.oosad.cddgame.UI.LoginAct.LoginActivity;

public interface ILoginView {

    void onShowNoUserNameOrPassWordAlert();
    void onShowNoUserAlert();
    void onShowAlwaysExistUserAlert();
    void onShowErrorPassWordAlert();
    void onShowErrorPassWordFormatAlert();
    void onShowErrorRegisterAlert();
    void onShowRegisterSuccessAlert();
    void onShowLoginSuccessToast(String username);
    void onClearPassWord();

    LoginActivity getThisPtr();
}
