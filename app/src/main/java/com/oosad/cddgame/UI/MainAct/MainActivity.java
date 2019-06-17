package com.oosad.cddgame.UI.MainAct;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.oosad.cddgame.UI.MainAct.presenter.IMainPresenter;
import com.oosad.cddgame.UI.MainAct.presenter.MainPresenterCompl;
import com.oosad.cddgame.UI.MainAct.view.IMainView;
import com.oosad.cddgame.R;

public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener {

    IMainPresenter m_mainPresenter;
    Button m_StartSingleGameButton;
    Button m_StartOnlineGameButton;
    Button m_SettingButton;
    TextView m_WelcomeUserTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        m_mainPresenter = new MainPresenterCompl(this);

        setupView();
    }

    private void setupView() {
        m_StartSingleGameButton = findViewById(R.id.id_MainAct_StartSingleGameButton);
        m_StartOnlineGameButton = findViewById(R.id.id_MainAct_StartOnlineGameButton);
        m_SettingButton = findViewById(R.id.id_MainAct_SettingButton);
        m_WelcomeUserTextView = findViewById(R.id.id_MainAct_WelcomeUserTextView);

        m_WelcomeUserTextView.setText(String.format(getString(R.string.str_MainAct_WelcomeUserForFormat), m_mainPresenter.Handle_GetUserName()));

        m_StartSingleGameButton.setOnClickListener(this);
        m_StartOnlineGameButton.setOnClickListener(this);
        m_SettingButton.setOnClickListener(this);
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "MainActivity";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_MainAct_StartSingleGameButton: // 单机游戏
                m_mainPresenter.Handle_StartGameButton_Click(true);
            break;
            case R.id.id_MainAct_StartOnlineGameButton: // 联机游戏
                m_mainPresenter.Handle_StartGameButton_Click(false);
            break;
            case R.id.id_MainAct_SettingButton: // 游戏设置
                m_mainPresenter.Handle_SettingButton_Click();
            break;
        }
    }

    /**
     * 没有登录就开始联机游戏
     */
    @Override
    public void ShowNoLoginAlert() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.str_MainAct_ShowNoLoginAlertMessage)
                .setPositiveButton(getString(R.string.alert_PosOK), null)
                .show();
    }

    /**
     * 返回 this 指针用于 MainActivity.this
     * @return
     */
    @Override
    public MainActivity getThisPtr() {
        return this;
    }
}
