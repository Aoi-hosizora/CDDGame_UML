package com.oosad.cddgame.UI.GamingAct;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.R;
import com.oosad.cddgame.UI.GamingAct.presenter.GamingPresenterCompl;
import com.oosad.cddgame.UI.GamingAct.presenter.IGamingPresenter;
import com.oosad.cddgame.Util.CardUtil;
import com.oosad.cddgame.UI.Widget.CardLayout;
import com.oosad.cddgame.UI.Widget.CascadeLayout;
import com.oosad.cddgame.UI.GamingAct.view.IGamingView;

import java.util.Locale;

public class GamingActivity extends AppCompatActivity implements IGamingView, View.OnClickListener {

    IGamingPresenter m_gamingPresenter;

    TextView m_UserNameDownTextView, m_UserNameLeftTextView, m_UserNameRightTextView, m_UserNameUpTextView;
    CascadeLayout m_ShowCardSetDownLayout, m_ShowCardSetUpLayout, m_ShowCardSetLeftLayout, m_ShowCardSetRightLayout;
    TextView m_UserUpCardCntTextView, m_UserDownCardCntTextView, m_UserLeftCardCntTextView, m_UserRightCardCntTextView;

    CascadeLayout m_CardSetLayout;

    Button m_PauseGameButton;
    Button m_ExitGameButton;
    Button m_PushOrDistributeCardButton;
    Button m_JumpShowCardButton;

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
        // 用户名
        m_UserNameDownTextView = findViewById(R.id.id_GamingAct_UserNameDownTextView);
        m_UserNameLeftTextView = findViewById(R.id.id_GamingAct_UserNameLeftTextView);
        m_UserNameRightTextView = findViewById(R.id.id_GamingAct_UserNameRightTextView);
        m_UserNameUpTextView = findViewById(R.id.id_GamingAct_UserNameUpTextView);

        // 牌数
        m_UserUpCardCntTextView = findViewById(R.id.id_GamingAct_UserUpCardCntTextView);
        m_UserDownCardCntTextView = findViewById(R.id.id_GamingAct_UserDownCardCntTextView);
        m_UserLeftCardCntTextView = findViewById(R.id.id_GamingAct_UserLeftCardCntTextView);
        m_UserRightCardCntTextView = findViewById(R.id.id_GamingAct_UserRightCardCntTextView);

        // 出牌布局
        m_CardSetLayout = findViewById(R.id.id_GamingAct_CardSetCascadeLayout);
        m_ShowCardSetDownLayout = findViewById(R.id.id_GamingAct_ShowCardSetDownCascadeLayout);
        m_ShowCardSetUpLayout = findViewById(R.id.id_GamingAct_ShowCardSetUpCascadeLayout);
        m_ShowCardSetLeftLayout = findViewById(R.id.id_GamingAct_ShowCardSetLeftCascadeLayout);
        m_ShowCardSetRightLayout = findViewById(R.id.id_GamingAct_ShowCardSetRightCascadeLayout);

        // 各种按钮
        m_PauseGameButton = findViewById(R.id.id_GamingAct_PauseGameButton);
        m_ExitGameButton = findViewById(R.id.id_GamingAct_ExitGameButton);
        m_PushOrDistributeCardButton = findViewById(R.id.id_GamingAct_PushOrDistributeCardButton);
        m_JumpShowCardButton = findViewById(R.id.id_GamingAct_JumpShowCardButton);

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // 按钮事件订阅 显示
        m_PauseGameButton.setOnClickListener(this);
        m_ExitGameButton.setOnClickListener(this);
        m_PushOrDistributeCardButton.setOnClickListener(this);
        m_JumpShowCardButton.setOnClickListener(this);

        m_PushOrDistributeCardButton.setText(R.string.str_GamingAct_DistributeCardButton);
        m_JumpShowCardButton.setVisibility(View.GONE);

        // 默认牌数隐藏
        m_UserUpCardCntTextView.setVisibility(View.GONE);
        m_UserDownCardCntTextView.setVisibility(View.GONE);
        m_UserLeftCardCntTextView.setVisibility(View.GONE);
        m_UserRightCardCntTextView.setVisibility(View.GONE);

        // 出牌布局事件订阅
        m_CardSetLayout.setOnClickListener(this);
        m_gamingPresenter.Handle_SetupRobotShowCardLayout(m_ShowCardSetLeftLayout, Constant.PLAYER_ROBOT_1);
        m_gamingPresenter.Handle_SetupRobotShowCardLayout(m_ShowCardSetUpLayout, Constant.PLAYER_ROBOT_2);
        m_gamingPresenter.Handle_SetupRobotShowCardLayout(m_ShowCardSetRightLayout, Constant.PLAYER_ROBOT_3);
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "GamingActivity";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    @Override
    public GamingActivity getThisPtr() {
        return this;
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
            case R.id.id_GamingAct_PushOrDistributeCardButton: // 出发牌
                PushOrDistributeCardButton_Click();
            break;
            case R.id.id_GamingAct_JumpShowCardButton: // 跳过出牌
                JumpShowCardButton_Click();
            break;

            case R.id.id_GamingAct_CardSetCascadeLayout: // 点击牌
                // 当前为出牌阶段，判断是否有选择牌
                RefreshShowCardButton_Enabled();
            break;
        }
    }

    /**
     * 根据是否有选择牌来更新出牌按钮的 enable
     */
    private void RefreshShowCardButton_Enabled() {
        // ShowLogE("onClick", CardLayout.CardUpCnt + "");
        if (getString(R.string.str_GamingAct_PushCardButton).equals(m_PushOrDistributeCardButton.getText().toString()))
            m_PushOrDistributeCardButton.setEnabled(CardLayout.HasSelectCardUp());
    }

    /**
     * 更新界面的牌数
     */
    @Override
    public void onRefreshShowCardCnt() {
        m_UserDownCardCntTextView.setText(String.format(Locale.CHINA, "%d", m_gamingPresenter.Handle_GetCardCnt(Constant.PLAYER_USER)));
        m_UserLeftCardCntTextView.setText(String.format(Locale.CHINA, "%d", m_gamingPresenter.Handle_GetCardCnt(Constant.PLAYER_ROBOT_1)));
        m_UserUpCardCntTextView.setText(String.format(Locale.CHINA, "%d", m_gamingPresenter.Handle_GetCardCnt(Constant.PLAYER_ROBOT_2)));
        m_UserRightCardCntTextView.setText(String.format(Locale.CHINA, "%d", m_gamingPresenter.Handle_GetCardCnt(Constant.PLAYER_ROBOT_3)));
    }

    /**
     * 单击退出游戏按钮，并判断
     */
    private void ExitGameButton_Click() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.str_GamingAct_ShowConfirmExitGameAlertMessage)
                .setPositiveButton(getString(R.string.str_GamingAct_ShowConfirmExitGameAlertPosButtonForExit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackToMainActivity();
                    }
                })
                .setNegativeButton(getString(R.string.str_GamingAct_ShowConfirmExitGameAlertNegButtonForCancel), null)
                .show();
    }

    /**
     * 单击暂停游戏按钮
     */
    private void PauseGameButton_Click() {
        m_gamingPresenter.Handle_PauseGameButton_Click();
    }

    /**
     * 分牌 出牌 重要
     */
    private void PushOrDistributeCardButton_Click() {
        if (getString(R.string.str_GamingAct_DistributeCardButton).equals(m_PushOrDistributeCardButton.getText().toString())) {
            m_gamingPresenter.Handle_DistributeCard();
            m_PushOrDistributeCardButton.setText(R.string.str_GamingAct_PushCardButton);

            RefreshShowCardButton_Enabled();

            m_JumpShowCardButton.setVisibility(View.VISIBLE);
            m_UserUpCardCntTextView.setVisibility(View.VISIBLE);
            m_UserDownCardCntTextView.setVisibility(View.VISIBLE);
            m_UserLeftCardCntTextView.setVisibility(View.VISIBLE);
            m_UserRightCardCntTextView.setVisibility(View.VISIBLE);
        }
        else {
            m_gamingPresenter.Handle_PushCard(m_CardSetLayout.getAllCards());
        }
    }

    public void JumpShowCardButton_Click() {
        m_gamingPresenter.Handle_JumpShowCard();
    }

    /**
     * 用户退出游戏并经确认，返回主界面
     */
    @Override
    public void onBackToMainActivity() {
        finish();
    }

    /**
     * 添加到 主CardSet 内，扑克牌可选
     * @param cardLayout
     */
    @Override
    public void onAddCardLayout(View cardLayout) {
        m_CardSetLayout.addView(cardLayout);
    }

    /**
     * 刷新 CardSet 布局
     */
    @Override
    public void onRefreshCardLayout() {
        m_CardSetLayout.refreshLayout();

        m_ShowCardSetDownLayout.refreshLayout();
        m_ShowCardSetUpLayout.refreshLayout();
        m_ShowCardSetLeftLayout.refreshLayout();
        m_ShowCardSetRightLayout.refreshLayout();
    }

    /**
     * 用户出牌，从 主CardSet 内删除，并添加到 出牌处，不可选
     * @param cardLayouts
     */
    @Override
    public void onUserShowCardSet(CardLayout[] cardLayouts) {
        // 有出牌
        if (cardLayouts.length != 0) {
            CardLayout[] showcardLayouts = new CardLayout[cardLayouts.length];
            int idx = 0;
            for (CardLayout c : cardLayouts) {
                if (c != null) {
                    m_CardSetLayout.removeView(c); // 出牌，从自己拥有的牌删除
                    onRefreshCardLayout();

                    c = CardUtil.getCardLayoutFromCard(this, c.getCard(), true); // 从拥有的牌转化成展示的牌
                    showcardLayouts[idx++] = c; // 记录进 showcardLayouts
                }
            }
            // 添加 ShowCardSetDownLayout
            m_ShowCardSetDownLayout.removeAllViews();
            for (CardLayout c : showcardLayouts) {
                if (c != null)
                    m_ShowCardSetDownLayout.addView(c);
            }
        }

        // 出牌后删除 Up 并更新 enable
        CardLayout.clearCardUpCnt();

        onRefreshShowCardCnt();
        RefreshShowCardButton_Enabled();
        // 拥有的牌为空
        if (m_CardSetLayout.getAllCards().length == 0)
            onShowWinAlert();
    }

    /**
     * Handle_PushCard() 判断当前没有选择牌
     */
    @Override
    public void onShowNoSelectCardAlert() {
        onShowAlert(R.string.alert_title, R.string.str_GamingAct_ShowNoSelectCardAlertMsg, R.string.str_GamingAct_ShowNoSelectCardAlertPosButton);
    }

    /**
     * Handle_PushCard() 后判断不符合规则
     */
    @Override
    public void onShowCantShowCardForRuleAlert() {
        onShowAlert(R.string.alert_title, R.string.str_GamingAct_ShowCantShowCardForRuleAlertMsg, R.string.str_GamingAct_ShowCantShowCardForRuleAlertPosButton);
    }

    /**
     * Handle_PushCard() 后判断还没轮到
     */
    @Override
    public void onShowCantShowCardForRoundAlert() {
        onShowAlert(R.string.alert_title, R.string.str_GamingAct_ShowCantShowCardForRoundAlertMsg, R.string.str_GamingAct_ShowCantShowCardForRoundAlertPosButton);
    }

    /**
     * onUserShowCardSet() 后判断当前已经没有拥有牌了
     * 或者 System 判断出机器人已经没有拥有牌了
     */
    @Override
    public void onShowWinAlert() {
        onShowAlert("提醒", "赢了", "确定");
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
