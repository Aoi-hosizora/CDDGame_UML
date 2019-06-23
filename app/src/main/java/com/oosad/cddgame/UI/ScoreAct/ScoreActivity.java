package com.oosad.cddgame.UI.ScoreAct;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.R;
import com.oosad.cddgame.UI.ScoreAct.presenter.IScorePresenter;
import com.oosad.cddgame.UI.ScoreAct.presenter.ScorePresenterCompl;
import com.oosad.cddgame.UI.ScoreAct.view.IScoreView;
import com.oosad.cddgame.UI.Widget.CascadeLayout;

public class ScoreActivity extends AppCompatActivity implements IScoreView, View.OnClickListener {

    private IScorePresenter m_scorePresenter;
    private Button m_BackButton;
    private TextView m_SingleOrOnlineTextView;

    private CascadeLayout m_DownCascadeLayout;
    private CascadeLayout m_UpCascadeLayout;
    private CascadeLayout m_LeftCascadeLayout;
    private CascadeLayout m_RightCascadeLayout;

    private TextView m_DownUserNameTextView;
    private TextView m_UpUserNameTextView;
    private TextView m_LeftUserNameTextView;
    private TextView m_RightUserNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getSupportActionBar().hide();

        m_scorePresenter = new ScorePresenterCompl(this);

        setupView();
        m_scorePresenter.Handle_SetupBundle(getIntent());
    }

    private void setupView() {
        m_SingleOrOnlineTextView = findViewById(R.id.id_ScoreAct_SingleOrOnlineTextView);
        m_BackButton = findViewById(R.id.id_ScoreAct_BackButton);

        m_DownCascadeLayout = findViewById(R.id.id_ScoreAct_DownCascadeLayout);
        m_UpCascadeLayout = findViewById(R.id.id_ScoreAct_UpCascadeLayout);
        m_LeftCascadeLayout = findViewById(R.id.id_ScoreAct_LeftCascadeLayout);
        m_RightCascadeLayout = findViewById(R.id.id_ScoreAct_RightCascadeLayout);

        m_DownUserNameTextView = findViewById(R.id.id_ScoreAct_DownUserName);
        m_UpUserNameTextView = findViewById(R.id.id_ScoreAct_UpUserName);
        m_LeftUserNameTextView = findViewById(R.id.id_ScoreAct_LeftUserName);
        m_RightUserNameTextView = findViewById(R.id.id_ScoreAct_RightUserName);
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "ScoreActivity";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_ScoreAct_BackButton:
                onBackToMainActivity();
            break;
        }
    }

    /**
     * 返回主界面
     */
    @Override
    public void onBackToMainActivity() {
        finish();
    }

    @Override
    public ScoreActivity getThisPtr() {
        return this;
    }

    /**
     * 设置一些相关界面
     * @param isSingle
     */
    @Override
    public void onSetupFlag(boolean isSingle) {
        if (isSingle)
            m_SingleOrOnlineTextView.setText(R.string.str_ScoreAct_SingleTextView);
        else
            m_SingleOrOnlineTextView.setText(R.string.str_ScoreAct_OnlineTextView);
    }

    /**
     * 添加到 主CardSet 内，扑克牌不可选
     * @param idx
     * @param cardLayout
     */
    @Override
    public void onAddCardLayout(int idx, View cardLayout) {
        switch (idx) {
            case Constant.Left_Player:
                m_LeftCascadeLayout.addView(cardLayout);
                break;
            case Constant.Right_Player:
                m_RightCascadeLayout.addView(cardLayout);
                break;
            case Constant.Up_Player:
                m_UpCascadeLayout.addView(cardLayout);
                break;
            case Constant.Down_Player:
                m_DownCascadeLayout.addView(cardLayout);
                break;
        }
    }

    /**
     * 设置用户名和卡数
     * @param idx
     * @param UserName
     * @param CardCnt
     */
    @Override
    public void onSetupUserName(int idx, String UserName, int CardCnt) {
        switch (idx) {
            case Constant.Left_Player:
                // %s (左): %d
                m_LeftUserNameTextView.setText(String.format(getString(R.string.str_ScoreAct_LeftUserName), UserName, CardCnt));
                break;
            case Constant.Right_Player:
                m_RightUserNameTextView.setText(String.format(getString(R.string.str_ScoreAct_RightUserName), UserName, CardCnt));
                break;
            case Constant.Up_Player:
                m_UpUserNameTextView.setText(String.format(getString(R.string.str_ScoreAct_UpUserName), UserName, CardCnt));
                break;
            case Constant.Down_Player:
                m_DownUserNameTextView.setText(String.format(getString(R.string.str_ScoreAct_DownUserName), UserName, CardCnt));
                break;
        }
    }

    /**
     * 刷新 CardSet 布局
     */
    @Override
    public void onRefreshCardLayout() {
        m_LeftCascadeLayout.refreshLayout();
        m_RightCascadeLayout.refreshLayout();
        m_UpCascadeLayout.refreshLayout();
        m_DownCascadeLayout.refreshLayout();
    }
}
