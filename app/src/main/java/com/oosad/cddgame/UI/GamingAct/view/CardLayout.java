package com.oosad.cddgame.UI.GamingAct.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.oosad.cddgame.R;
import com.oosad.cddgame.UI.GamingAct.model.Card;
import com.oosad.cddgame.UI.GamingAct.model.CardSuit;

public class CardLayout extends View {
    /**
     * 是否选中(上移)
     */
    boolean IsUp = false;
    /**
     * 是否为最后一张，与重叠油管
     */
    boolean IsFin = false;
    private int VerticalMovingSpacing;
    private boolean canCardSelected = false;

    public boolean isCanCardSelected() {
        return canCardSelected;
    }

    public void setCanCardSelected(boolean canCardSelected) {
        this.canCardSelected = canCardSelected;
    }

    private Card card = new Card(1, CardSuit.Spade);

    public CardLayout(Context context) {
        this(context, null);
    }

    public CardLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CardLayout);

        VerticalMovingSpacing = a.getDimensionPixelSize(R.styleable.CardLayout_vertical_padding, (int) getResources().getDimension(R.dimen.Card_Vertical_Padding));
        canCardSelected = a.getBoolean(R.styleable.CardLayout_can_selected, false);
        a.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (canCardSelected && checkIsInView(event)) {
                if (IsUp)
                    SetViewMoveDown();
                else
                    SetViewMoveUp();
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        SetViewMoveDown();
    }

    /**
     * 修改 View 尺寸大小
     * @param width 单位 px/dp
     * @param height 单位 px/dp
     * @param metrics context.getResources().getDisplayMetrics()
     */
    public void setLayoutSize(int width, int height, DisplayMetrics metrics) {
        ViewGroup.LayoutParams linearParams = getLayoutParams();
        if (linearParams == null)
            linearParams = new ViewGroup.LayoutParams(width, height);

        linearParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, height, metrics));
        linearParams.width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, width, metrics));
        setLayoutParams(linearParams);
    }

    private void ShowLogE(String FunctionName, String data) {
        String TAG = "CDDGame";
        String CN = "CardLayout";
        String msg = CN + "###" + FunctionName + "(): " + data;
        Log.e(TAG, msg);
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setFin(boolean fin) {
        IsFin = fin;
    }

    public boolean getIsFin() {
        return IsFin;
    }

    public boolean getIsUp() {
        return IsUp;
    }

    /**
     * 将扑克牌下移，取消选中
     */
    private void SetViewMoveDown() {
        IsUp = false;
        setTop(getTop() + VerticalMovingSpacing);
        setBottom(getBottom() + VerticalMovingSpacing);
    }

    /**
     * 将扑克牌上移，选中
     */
    private void SetViewMoveUp() {
        IsUp = true;
        setTop(getTop() - VerticalMovingSpacing);
        setBottom(getBottom() - VerticalMovingSpacing);
    }

    /**
     * 判断当前是否选中该扑克牌，考虑重叠
     * @param event
     * @return
     */
    private boolean checkIsInView(MotionEvent event) {
        // ShowLogE("checkIsInView", "IsFin: " + IsFin);
        boolean IsWidthIn, IsHeightIn;
        IsHeightIn = event.getY() > 0 && event.getY() < getHeight();
        if (IsFin) {
            IsWidthIn = event.getX() > 0 && event.getX() < getWidth();
            return IsHeightIn && IsWidthIn;
        }
        IsWidthIn = event.getX() > 0 && event.getX() < getResources().getDimension(R.dimen.Card_Horizontal_Spacing);
        return IsHeightIn && IsWidthIn;
    }


}
