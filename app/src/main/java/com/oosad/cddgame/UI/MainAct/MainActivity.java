package com.oosad.cddgame.UI.MainAct;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.stetho.Stetho;
import com.oosad.cddgame.UI.MainAct.presenter.IMainPresenter;
import com.oosad.cddgame.UI.MainAct.presenter.MainPresenterCompl;
import com.oosad.cddgame.UI.MainAct.view.IMainView;
import com.oosad.cddgame.R;

public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener {

    IMainPresenter m_mainPresenter;
    Button m_StartGameButton;
    Button m_SettingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        m_mainPresenter = new MainPresenterCompl(this);

        setupView();
    }

    private void setupView() {
        m_StartGameButton = findViewById(R.id.id_MainAct_StartGameButton);
        m_SettingButton = findViewById(R.id.id_MainAct_SettingButton);

        m_StartGameButton.setOnClickListener(this);
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
            case R.id.id_MainAct_StartGameButton: // 开始游戏
                m_mainPresenter.Handle_StartGameButton_Click();
            break;
            case R.id.id_MainAct_SettingButton: // 游戏设置
                m_mainPresenter.Handle_SettingButton_Click();
            break;
        }
    }

    /**
     * 没有设置用户就开始游戏
     */
    @Override
    public void ShowNoneUserAlert() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.str_MainAct_ShowNoneUserAlertMessage)
                .setPositiveButton(getString(R.string.str_MainAct_ShowNoneUserAlertPosButton), null)
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
