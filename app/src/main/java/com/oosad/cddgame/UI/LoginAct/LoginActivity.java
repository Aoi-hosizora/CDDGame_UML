package com.oosad.cddgame.UI.LoginAct;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.oosad.cddgame.R;
import com.oosad.cddgame.UI.LoginAct.presenter.ILoginPresenter;
import com.oosad.cddgame.UI.LoginAct.presenter.LoginPresenterCompl;
import com.oosad.cddgame.UI.LoginAct.view.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    ILoginPresenter m_loginPresenter;
    private Button m_LoginButton;
    private Button m_RegisterButton;
    private Button m_CancelButton;
    private EditText m_UserNameEditText;
    private EditText m_PassWordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        // 监听服务
        Stetho.initializeWithDefaults(this);

        m_loginPresenter = new LoginPresenterCompl(this);

        setupView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_LoginAct_CancelButton:
                CancelButton_Click();
            break;
            case R.id.id_LoginAct_LoginButton:
                LoginButton_Click();
            break;
            case R.id.id_LoginAct_RegisterButton:
                RegisterButton_Click();
            break;
        }
    }

    @Override
    public LoginActivity getThisPtr() {
        return this;
    }

    private void setupView() {
        m_LoginButton = findViewById(R.id.id_LoginAct_LoginButton);
        m_RegisterButton = findViewById(R.id.id_LoginAct_RegisterButton);
        m_CancelButton = findViewById(R.id.id_LoginAct_CancelButton);

        m_UserNameEditText = findViewById(R.id.id_LoginAct_UserNameEditText);
        m_PassWordEditText = findViewById(R.id.id_LoginAct_PassWordEditText);

        m_LoginButton.setOnClickListener(this);
        m_RegisterButton.setOnClickListener(this);
        m_CancelButton.setOnClickListener(this);
    }

    private void CancelButton_Click() {
        finish();
    }

    private void LoginButton_Click() {
        String UserName = m_UserNameEditText.getText().toString();
        String PlainPassWord = m_PassWordEditText.getText().toString();
        m_loginPresenter.HandleLogin(UserName, PlainPassWord);
    }

    private void RegisterButton_Click() {
        String UserName = m_UserNameEditText.getText().toString();
        String PlainPassWord = m_PassWordEditText.getText().toString();
        m_loginPresenter.HandleRegister(UserName, PlainPassWord);
    }

    @Override
    public void onShowNoUserNameOrPassWordAlert() {
        onShowAlert("提醒", "没有输入用户名或密码。", "确定");
    }

    @Override
    public void onShowNoUserAlert() {
        onShowAlert("提醒", "没有该用户。", "确定");
    }

    @Override
    public void onShowErrorPassWordAlert() {
        onShowAlert("提醒", "密码错误。", "确定");
    }

    @Override
    public void onShowAlwaysExistUserAlert() {
        onShowAlert("提醒", "已经存在该用户。", "确定");
    }

    @Override
    public void onShowErrorPassWordFormatAlert() {
        onShowAlert("提醒", "密码格式错误。", "确定");
    }

    @Override
    public void onShowErrorRegisterAlert() {
        onShowAlert("提醒", "注册失败。", "确定");
    }

    @Override
    public void onShowRegisterSuccessAlert() {
        onShowAlert("提醒", "注册成功，请登录。", "确定");
    }

    @Override
    public void onShowLoginSuccessToast(String username) {
        Toast.makeText(this, "用户 " + username +  " 登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClearPassWord() {
        m_PassWordEditText.setText("");
    }

    /**
     * 显示提醒框 字面值
     * @param title
     * @param Message
     * @param PositiveButtonText
     */
    private void onShowAlert(String title, String Message, String PositiveButtonText) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(Message)
                .setPositiveButton(PositiveButtonText, null)
                .show();
    }

    /**
     * 显示提醒框 资源值
     * @param titleResId
     * @param MessageResId
     * @param PositiveButtonTextResId
     */
    private void onShowAlert(int titleResId, int MessageResId, int PositiveButtonTextResId) {
        new AlertDialog.Builder(this)
                .setTitle(titleResId)
                .setMessage(MessageResId)
                .setPositiveButton(PositiveButtonTextResId, null)
                .show();
    }
}
