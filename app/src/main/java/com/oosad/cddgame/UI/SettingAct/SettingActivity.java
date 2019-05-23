package com.oosad.cddgame.UI.SettingAct;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();

        m_settingPresenter = new SettingPresenterCompl(this);

        setupView();
    }

    private void setupView() {
        m_BackButton = findViewById(R.id.id_SettingAct_BackButton);
        m_OKButton = findViewById(R.id.id_SettingAct_OKButton);
        m_ResetButton = findViewById(R.id.id_SettingAct_ResetButton);
        m_GameBGMSeekBar = findViewById(R.id.id_SettingAct_GameBGMSeekBar);
        m_GameOtoSeekBar = findViewById(R.id.id_SettingAct_GameOtoSeekBar);
        m_GameBGMTextView = findViewById(R.id.id_SettingAct_GameBGMTextView);
        m_GameOtoTextView = findViewById(R.id.id_SettingAct_GameOtoTextView);

        m_BackButton.setOnClickListener(this);
        m_OKButton.setOnClickListener(this);
        m_ResetButton.setOnClickListener(this);

        m_GameBGMSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                m_GameBGMTextView.setText(String.format("%d%%", i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        m_GameOtoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                m_GameOtoTextView.setText(String.format("%d%%", i));
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
            case R.id.id_SettingAct_BackButton:
                m_settingPresenter.Handle_BackButton_Click();
            break;
            case R.id.id_SettingAct_OKButton:
                int GameBGMVoloum = m_GameBGMSeekBar.getProgress();
                int GameOtoVoloum = m_GameOtoSeekBar.getProgress();
                String currUserName = "NEWUSER";
                m_settingPresenter.Handle_OKButton_Click(currUserName, GameBGMVoloum, GameOtoVoloum) ;
            break;
            case R.id.id_SettingAct_ResetButton:
                m_settingPresenter.Handle_ResetButton_Click();
            break;
        }
    }

    @Override
    public void onBackToMainActivity() {
        finish();
    }

    @Override
    protected void onResume() {
        // 默认横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onResume();
    }

    @Override
    public void onResetSetting() {
        // set UI
    }

}
