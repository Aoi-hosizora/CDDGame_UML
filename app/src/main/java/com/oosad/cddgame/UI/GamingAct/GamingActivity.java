package com.oosad.cddgame.UI.GamingAct;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.oosad.cddgame.Data.Boundary.GameSystem;
import com.oosad.cddgame.Data.Constant;
import com.oosad.cddgame.Data.Entity.Card;
import com.oosad.cddgame.Net.SocketHandlers;
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
    TextView m_UserUpPassTextView, m_UserDownPassTextView, m_UserLeftPassTextView, m_UserRightPassTextView;
    CascadeLayout m_CardSetLayout;

    TextView m_SingleOrOnlineTextView;
    TextView m_OnlineCountDownTextView;

    Button m_ExitGameButton;
    Button m_PushOrDistributeCardButton;
    Button m_PassShowCardButton;
    Button m_PrePareButton;

    ProgressDialog m_prepareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming);
        getSupportActionBar().hide();

        m_gamingPresenter = new GamingPresenterCompl(this);

        setupView();
        m_gamingPresenter.Handle_SetupBundle(getIntent());
        setupPlayerLayout();
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

        // Pass
        m_UserDownPassTextView = findViewById(R.id.id_GamingAct_ShowCardSetDownPass);
        m_UserUpPassTextView = findViewById(R.id.id_GamingAct_ShowCardSetUpPass);
        m_UserLeftPassTextView = findViewById(R.id.id_GamingAct_ShowCardSetLeftPass);
        m_UserRightPassTextView = findViewById(R.id.id_GamingAct_ShowCardSetRightPass);

        // 各种按钮
        m_ExitGameButton = findViewById(R.id.id_GamingAct_ExitGameButton);
        m_PushOrDistributeCardButton = findViewById(R.id.id_GamingAct_PushOrDistributeCardButton);
        m_PassShowCardButton = findViewById(R.id.id_GamingAct_PassShowCardButton);
        m_PrePareButton = findViewById(R.id.id_GamingAct_PrepareButton);

        // 单机联机标志
        m_SingleOrOnlineTextView = findViewById(R.id.id_GamingAct_SingleOrOnlineTextView);

        // 倒计时
        m_OnlineCountDownTextView = findViewById(R.id.id_GamingAct_OnlineCountDown);
        m_OnlineCountDownTextView.setVisibility(View.GONE);

        // Progress Diag
        m_prepareDialog = new ProgressDialog(this);
        m_prepareDialog.setMessage(String.format(getString(R.string.str_GamingAct_PrePareProgressDialog), 1));
        m_prepareDialog.setCanceledOnTouchOutside(true);
        m_gamingPresenter.Handle_SetupPrepareDialogCancel(m_prepareDialog);

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // 按钮事件订阅 显示
        m_ExitGameButton.setOnClickListener(this);
        m_PushOrDistributeCardButton.setOnClickListener(this);
        m_PassShowCardButton.setOnClickListener(this);
        m_PrePareButton.setOnClickListener(this);

        m_PushOrDistributeCardButton.setText(R.string.str_GamingAct_DistributeCardButton);
        m_PassShowCardButton.setVisibility(View.GONE);
        m_PrePareButton.setVisibility(View.GONE);


        // 默认牌数隐藏
        m_UserUpCardCntTextView.setVisibility(View.GONE);
        m_UserDownCardCntTextView.setVisibility(View.GONE);
        m_UserLeftCardCntTextView.setVisibility(View.GONE);
        m_UserRightCardCntTextView.setVisibility(View.GONE);

        // Pass 隐藏
        m_UserDownPassTextView.setVisibility(View.GONE);
        m_UserUpPassTextView.setVisibility(View.GONE);
        m_UserLeftPassTextView.setVisibility(View.GONE);
        m_UserRightPassTextView.setVisibility(View.GONE);

        // 出牌布局事件订阅
        m_CardSetLayout.setOnClickListener(this);
    }

    /**
     * SG 设置 布局 和 机器人/玩家 绑定
     * OG 每次都访问服务器获得数据
     */
    private void setupPlayerLayout() {
        if (m_gamingPresenter.Handle_GetIsSingle()) {
            // 单机游戏，直接将布局和机器人绑定
            m_gamingPresenter.Handle_SetupRobotShowCardLayout(m_ShowCardSetLeftLayout, Constant.PLAYER_ROBOT_1);
            m_gamingPresenter.Handle_SetupRobotShowCardLayout(m_ShowCardSetUpLayout, Constant.PLAYER_ROBOT_2);
            m_gamingPresenter.Handle_SetupRobotShowCardLayout(m_ShowCardSetRightLayout, Constant.PLAYER_ROBOT_3);
        }
        else {
            // TODO: 联机游戏，将布局和服务器访问关联

        }
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
     * @param isSingle
     */
    @Override
    public void onSetupUI(String UserName, boolean isSingle) {
        this.m_UserNameDownTextView.setText(UserName);

        m_SingleOrOnlineTextView.setText(isSingle?R.string.str_GamingAct_SingleTextView:R.string.str_GamingAct_OnlineTextView);

        m_PushOrDistributeCardButton.setVisibility(isSingle?View.VISIBLE:View.GONE);
        m_PrePareButton.setVisibility(isSingle?View.GONE:View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_GamingAct_ExitGameButton: // 退出游戏
                ExitGameButton_Click();
            break;
            case R.id.id_GamingAct_PushOrDistributeCardButton: // 出发牌
                PushOrDistributeCardButton_Click();
            break;
            case R.id.id_GamingAct_PassShowCardButton: // 跳过出牌
                PassShowCardButton_Click();
            break;
            case R.id.id_GamingAct_PrepareButton:
                PrepareButton_Click();
            break;

            case R.id.id_GamingAct_CardSetCascadeLayout: // 点击牌
                // 当前为出牌阶段，判断是否有选择牌
                RefreshShowCardButton_Enabled();
            break;
        }
    }

    /**
     * 更新人数显示
     * @param cnt
     */
    @Override
    public void onUpdateProgressDialog(int cnt) {
        String msg = String.format(getString(R.string.str_GamingAct_PrePareProgressDialog), cnt);

        ShowLogE("onUpdateProgressDialog", "" + m_prepareDialog.isShowing() + msg);
        if (m_prepareDialog.isShowing()) {
//            m_prepareDialog.dismiss();
//            m_prepareDialog.cancel();
//            m_prepareDialog.setMessage(msg);
//            m_prepareDialog.show();
        }
        else
            m_prepareDialog.setMessage(msg);
    }

    @Override
    public void onShowProgressDialog() {
//        m_prepareDialog.show();
    }

    /**
     * 根据是否有选择牌来更新出牌按钮的 enable
     */
    private void RefreshShowCardButton_Enabled() {
        // ShowLogE("onClick", CardLayout.CardUpCnt + "");
        if (getString(R.string.str_GamingAct_PushCardButton).equals(m_PushOrDistributeCardButton.getText().toString()))
            m_PushOrDistributeCardButton.setEnabled(CardLayout.HasSelectCardUp());
    }

    private void PrepareButton_Click() {
        m_gamingPresenter.Handle_PrepareButton_Click();
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
                        m_gamingPresenter.Handle_onBackToMainActivity();
                        onBackToMainActivity();
                    }
                })
                .setNegativeButton(getString(R.string.str_GamingAct_ShowConfirmExitGameAlertNegButtonForCancel), null)
                .show();
    }

    @Override
    protected void onPause() {
        SocketHandlers.DisConnect();
        super.onPause();
    }

    /**
     * 分牌 出牌 重要
     */
    private void PushOrDistributeCardButton_Click() {
        if (getString(R.string.str_GamingAct_DistributeCardButton).equals(m_PushOrDistributeCardButton.getText().toString())) {
            m_gamingPresenter.Handle_DistributeCard();
            m_PushOrDistributeCardButton.setText(R.string.str_GamingAct_PushCardButton);

            RefreshShowCardButton_Enabled();

            m_PassShowCardButton.setVisibility(View.VISIBLE);
            m_UserUpCardCntTextView.setVisibility(View.VISIBLE);
            m_UserDownCardCntTextView.setVisibility(View.VISIBLE);
            m_UserLeftCardCntTextView.setVisibility(View.VISIBLE);
            m_UserRightCardCntTextView.setVisibility(View.VISIBLE);
        }
        else {
            m_gamingPresenter.Handle_PushCard(m_CardSetLayout.getAllCards());
        }
    }

    public void PassShowCardButton_Click() {
        m_gamingPresenter.Handle_UserPassShowCard();
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
     * 删除用户所有的牌的布局，OG用
     */
    @Override
    public void onRemoveAllCards() {
        m_CardSetLayout.removeAllViews();
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

            // 隐藏 PASS
            m_UserDownPassTextView.setVisibility(View.GONE);

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

    @Override
    public void onPassShowCard(int PlayerId) {
        ShowLogE("onPassShowCard", "ID: " + PlayerId);
        switch (PlayerId) {
            case Constant.PLAYER_USER:
                m_ShowCardSetDownLayout.removeAllViews();
                m_UserDownPassTextView.setVisibility(View.VISIBLE);
            break;
            case Constant.PLAYER_ROBOT_1:
                m_ShowCardSetLeftLayout.removeAllViews();
                m_UserLeftPassTextView.setVisibility(View.VISIBLE);
            break;
            case Constant.PLAYER_ROBOT_2:
                m_ShowCardSetUpLayout.removeAllViews();
                m_UserUpPassTextView.setVisibility(View.VISIBLE);
            break;
            case Constant.PLAYER_ROBOT_3:
                m_ShowCardSetRightLayout.removeAllViews();
                m_UserRightPassTextView.setVisibility(View.VISIBLE);
            break;
        }
    }


    @Override
    public void onHidePassShowCard(int PlayerId) {
        switch (PlayerId) {
            case Constant.PLAYER_USER:
                m_UserDownPassTextView.setVisibility(View.GONE);
            break;
            case Constant.PLAYER_ROBOT_1:
                m_UserLeftPassTextView.setVisibility(View.GONE);
            break;
            case Constant.PLAYER_ROBOT_2:
                m_UserUpPassTextView.setVisibility(View.GONE);
            break;
            case Constant.PLAYER_ROBOT_3:
                m_UserRightPassTextView.setVisibility(View.GONE);
            break;
        }
    }

    /**
     * Handle_PushCard() 判断当前没有选择牌
     */
    @Override
    public void onShowNoSelectCardAlert() {
        onShowAlert(R.string.alert_title, R.string.str_GamingAct_ShowNoSelectCardAlertMsg, R.string.alert_PosOK);
    }

    /**
     * Handle_PushCard() 后判断不符合规则
     */
    @Override
    public void onShowCantShowCardForRuleAlert() {
        onShowAlert(R.string.alert_title, R.string.str_GamingAct_ShowCantShowCardForRuleAlertMsg, R.string.alert_PosOK);
    }

    /**
     * Handle_PushCard() 后判断还没轮到
     */
    @Override
    public void onShowCantShowCardForRoundAlert() {
        onShowAlert(R.string.alert_title, R.string.str_GamingAct_ShowCantShowCardForRoundAlertMsg, R.string.alert_PosOK);
    }

    /**
     * onUserShowCardSet() 后判断当前已经没有拥有牌了
     * 或者 System 判断出机器人已经没有拥有牌了
     */
    @Override
    public void onShowWinAlert() {
        onShowAlert("提醒", "有人赢了", "确定");
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

    ////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// Online ////////////////////////////////////////


    /**
     * 准备完成，已经发牌并初始化布局和玩家名字
     */
    @Override
    public void onSetUpOnlinePlayingLayout(String UserNameLeft, String UserNameUp, String UserNameRight) {
        m_PrePareButton.setVisibility(View.GONE);
        m_PushOrDistributeCardButton.setVisibility(View.VISIBLE);
        m_PassShowCardButton.setVisibility(View.VISIBLE);
        m_PushOrDistributeCardButton.setText(R.string.str_GamingAct_PushCardButton);

        m_UserNameLeftTextView.setText(UserNameLeft);
        m_UserNameUpTextView.setText(UserNameUp);
        m_UserNameRightTextView.setText(UserNameRight);

        m_UserLeftCardCntTextView.setText(String.format(Locale.CHINA, "%d", Constant.PlayerCardCnt));
        m_UserUpCardCntTextView.setText(String.format(Locale.CHINA, "%d", Constant.PlayerCardCnt));
        m_UserRightCardCntTextView.setText(String.format(Locale.CHINA, "%d", Constant.PlayerCardCnt));
        m_UserDownCardCntTextView.setText(String.format(Locale.CHINA, "%d", Constant.PlayerCardCnt));

        m_OnlineCountDownTextView.setVisibility(View.VISIBLE);
    }

    /**
     * OG 高亮当前的玩家
     * @param idx
     */
    @Override
    public void onHighLightCurrPlayer(int idx) {
        m_UserNameLeftTextView.setTextColor(idx == Constant.Left_Player ? Color.RED : Color.WHITE);
        m_UserNameRightTextView.setTextColor(idx == Constant.Right_Player ? Color.RED : Color.WHITE);
        m_UserNameUpTextView.setTextColor(idx == Constant.Up_Player ? Color.RED : Color.WHITE);
        m_UserNameDownTextView.setTextColor(idx == Constant.Down_Player ? Color.RED : Color.WHITE);
    }

    /**
     * 设置倒计时时间
     * @param num
     */
    @Override
    public void onSetCountDownNumber(int num) {
        m_OnlineCountDownTextView.setText(String.format(Locale.CHINA, "%d", num));
    }

    /**
     * 时间超时，下个用户
     */
    @Override
    public void onShowTimeoutToast() {
        Toast.makeText(this, R.string.str_GamingAct_OnlineCountDownTimeOutToast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHideCountDownTextView() {
        m_OnlineCountDownTextView.setVisibility(View.GONE);
    }

    /**
     * 出牌，更新布局
     */
    @Override
    public void onUpdateOnlinePlayingLayout(int idx, Card[] cards) {
        if (cards == null || cards.length == 0) {
            this.onPassShowCard(idx);
        }
        else {
            this.onHidePassShowCard(idx);

            switch (idx) {
                case Constant.Left_Player:
                    // Left
                    m_gamingPresenter.Handle_OthersShowCard(m_ShowCardSetLeftLayout, cards);
                    m_UserLeftCardCntTextView.setText(String.format(Locale.CHINA, "%d", Integer.parseInt(m_UserLeftCardCntTextView.getText().toString()) - cards.length));

                    break;
                case Constant.Up_Player:
                    // Up
                    m_gamingPresenter.Handle_OthersShowCard(m_ShowCardSetUpLayout, cards);
                    m_UserUpCardCntTextView.setText(String.format(Locale.CHINA, "%d", Integer.parseInt(m_UserUpCardCntTextView.getText().toString()) - cards.length));

                    break;
                case Constant.Right_Player:
                    // Right
                    m_gamingPresenter.Handle_OthersShowCard(m_ShowCardSetRightLayout, cards);
                    m_UserRightCardCntTextView.setText(String.format(Locale.CHINA, "%d", Integer.parseInt(m_UserRightCardCntTextView.getText().toString()) - cards.length));
                    break;
                default:
                    // ME
                    // onUserShowCardSet() 处理
                    break;
            }
        }
    }
}
