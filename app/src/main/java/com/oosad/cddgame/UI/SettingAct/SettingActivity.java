package com.oosad.cddgame.UI.SettingAct;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.oosad.cddgame.Data.Setting;
import com.oosad.cddgame.Data.User;
import com.oosad.cddgame.R;
import com.oosad.cddgame.UI.SettingAct.presenter.ISettingPresenter;
import com.oosad.cddgame.UI.SettingAct.presenter.SettingPresenterCompl;
import com.oosad.cddgame.UI.SettingAct.view.ISettingView;

public class SettingActivity extends AppCompatActivity implements ISettingView, View.OnClickListener {

    ISettingPresenter m_settingPresenter;
    Button m_BackButton;
    Button m_OKButton;
    Button m_ResetButton;
    SeekBar m_GameBGMSeekBar;
    SeekBar m_GameOtoSeekBar;
    TextView m_GameBGMTextView;
    TextView m_GameOtoTextView;
    EditText m_UserNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();

        m_settingPresenter = new SettingPresenterCompl(this);

        setupView();
        m_settingPresenter.Handle_SetupBundle(getIntent());
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "SettingActivity";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    private void setupView() {
        m_BackButton = findViewById(R.id.id_SettingAct_BackButton);
        m_OKButton = findViewById(R.id.id_SettingAct_OKButton);
        m_ResetButton = findViewById(R.id.id_SettingAct_ResetButton);
        m_GameBGMSeekBar = findViewById(R.id.id_SettingAct_GameBGMSeekBar);
        m_GameOtoSeekBar = findViewById(R.id.id_SettingAct_GameOtoSeekBar);
        m_GameBGMTextView = findViewById(R.id.id_SettingAct_GameBGMTextView);
        m_GameOtoTextView = findViewById(R.id.id_SettingAct_GameOtoTextView);
        m_UserNameEditText = findViewById(R.id.id_SettingAct_UserNameEditText);

        m_BackButton.setOnClickListener(this);
        m_OKButton.setOnClickListener(this);
        m_ResetButton.setOnClickListener(this);

        m_GameBGMSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                m_GameBGMTextView.setText(String.format(getString(R.string.str_SettingAct_SeekBarChangeTextView), i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        m_GameOtoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                m_GameOtoTextView.setText(String.format(getString(R.string.str_SettingAct_SeekBarChangeTextView), i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_SettingAct_BackButton: // 返回主界面
                BackButton_Click();
            break;
            case R.id.id_SettingAct_OKButton: // 确定设置
                OKButton_Click();
            break;
            case R.id.id_SettingAct_ResetButton: // 重置设置
                ResetButton_Click();
            break;
        }
    }

    /**
     * 单击返回主界面按钮
     */
    private void BackButton_Click() {
        m_settingPresenter.Handle_BackButton_Click();
    }

    /**
     * 单击确定设置按钮
     */
    private void OKButton_Click() {
        String currUserName = m_UserNameEditText.getText().toString();
        if (currUserName.isEmpty()) {
            onShowNoneUserAlert();
            return;
        }

        int GameBGMVoloum = m_GameBGMSeekBar.getProgress();
        int GameOtoVoloum = m_GameOtoSeekBar.getProgress();

        m_settingPresenter.Handle_OKButton_Click(currUserName, GameBGMVoloum, GameOtoVoloum);
    }

    /**
     * 确定重置设置按钮
     */
    private void ResetButton_Click() {
        m_settingPresenter.Handle_ResetButton_Click();
    }

    /**
     * 退出当前界面，返回主界面
     */
    @Override
    public void onBackToMainActivity() {
        finish();
    }

    /**
     * 重置用户界面
     */
    @Override
    public void onResetUI() {
        m_GameBGMSeekBar.setProgress(50);
        m_GameOtoSeekBar.setProgress(50);
        m_UserNameEditText.setText("");
    }

    /**
     * 根据 Setting 设置用户界面
     * @param UserName
     * @param BGMVoloum
     * @param OtoVoloum
     */
    @Override
    public void onSetupUI(String UserName, int BGMVoloum, int OtoVoloum) {
        m_GameBGMSeekBar.setProgress(BGMVoloum);
        m_GameOtoSeekBar.setProgress(OtoVoloum);
        m_UserNameEditText.setText(UserName);
    }

    /**
     * 没有输入用户名
     */
    @Override
    public void onShowNoneUserAlert() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.str_SettingAct_ShowNoneUserAlertMessage)
                .setPositiveButton(getString(R.string.str_SettingAct_ShowNoneUserAlertPosButton), null)
                .show();
    }
}
