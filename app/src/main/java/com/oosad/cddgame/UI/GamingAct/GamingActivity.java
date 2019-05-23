package com.oosad.cddgame.UI.GamingAct;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oosad.cddgame.R;
import com.oosad.cddgame.UI.GamingAct.presenter.GamingPresenterCompl;
import com.oosad.cddgame.UI.GamingAct.presenter.IGamingPresenter;
import com.oosad.cddgame.UI.GamingAct.view.IGamingView;

public class GamingActivity extends AppCompatActivity implements IGamingView, View.OnClickListener {

    IGamingPresenter m_gamingPresenter;
    TextView m_UserNameDownTextView;
    TextView m_UserNameLeftTextView;
    TextView m_UserNameRightTextView;
    TextView m_UserNameUpTextView;
    Button m_PauseGameButton;
    Button m_ExitGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming);
        getSupportActionBar().hide();

        m_gamingPresenter = new GamingPresenterCompl(this);

        setupView();
        m_gamingPresenter.Handle_SetupBundle(getIntent());
    }

    private void setupView() {
        m_UserNameDownTextView = findViewById(R.id.id_GamingAct_UserNameDownTextView);
        m_UserNameLeftTextView = findViewById(R.id.id_GamingAct_UserNameLeftTextView);
        m_UserNameRightTextView = findViewById(R.id.id_GamingAct_UserNameRightTextView);
        m_UserNameUpTextView = findViewById(R.id.id_GamingAct_UserNameUpTextView);

        m_PauseGameButton = findViewById(R.id.id_GamingAct_PauseGameButton);
        m_ExitGameButton = findViewById(R.id.id_GamingAct_ExitGameButton);

        m_PauseGameButton.setOnClickListener(this);
        m_ExitGameButton.setOnClickListener(this);
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "GamingActivity";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    /**
     * 设置用户界面
     * @param UserName
     */
    @Override
    public void onSetupUI(String UserName) {
        this.m_UserNameDownTextView.setText(UserName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_GamingAct_PauseGameButton: // 暂停游戏
                PauseGameButton_Click();
            break;
            case R.id.id_GamingAct_ExitGameButton: // 退出游戏
                ExitGameButton_Click();
            break;
        }
    }

    /**
     * 单击退出游戏按钮，并判断
     */
    private void ExitGameButton_Click() {

        new AlertDialog.Builder(this)
                .setTitle("提醒")
                .setMessage("确定退出游戏？")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackToMainActivity();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 单击暂停游戏按钮
     */
    private void PauseGameButton_Click() {
        m_gamingPresenter.Handle_PauseGameButton_Click();
    }

    /**
     * 用户退出游戏并经确认，返回主界面
     */
    @Override
    public void onBackToMainActivity() {
        finish();
    }

}
