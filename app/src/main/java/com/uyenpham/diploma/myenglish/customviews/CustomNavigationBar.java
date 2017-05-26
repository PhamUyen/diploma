package com.uyenpham.diploma.myenglish.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uyenpham.diploma.myenglish.R;
import com.uyenpham.diploma.myenglish.interfaces.INavigationOnClick;

import static com.uyenpham.diploma.myenglish.R.id.imvLeft;


/**
 * Custom navigationbar
 *
 * @author MinhNH
 */
public class CustomNavigationBar extends RelativeLayout implements View.OnClickListener {
    private INavigationOnClick iNavigationOnClick;
    private View navi;
    private ImageView imgLeft;
    private ImageView imgRight;
    private TextView tvTitle;
    private RelativeLayout rltnavi;

    /**
     * Constructor
     *
     * @param context This is context of view
     */
    public CustomNavigationBar(Context context) {
        super(context);
        initialView();
    }

    /**
     * Constructor
     *
     * @param context  This is context of view
     * @param attrs    This is attribute of view
     * @param defStyle This is style of view
     */
    public CustomNavigationBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialView();
    }

    /**
     * Constructor
     *
     * @param context This is context of view
     * @param attrs   This is attribute of view
     */
    public CustomNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // if click on icon left => set event onLeftClick
            case imvLeft:
                if (iNavigationOnClick != null) {
                    iNavigationOnClick.onLeftClick();
                }
                break;

            // if click on icon right => set event onRightClick
            case R.id.imvRight:
                if (iNavigationOnClick != null) {
                    iNavigationOnClick.onRightClick();
                }
                break;

            default:
                // DO NOTHING
                break;
        }
    }

    /**
     * Set title of navigationbar
     *
     * @param title This is title of navigationbar
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * Set icon left for navigationbar
     *
     * @param drawable image for button left
     */
    public void setIconLeft(int drawable) {
        imgLeft.setImageResource(drawable);
    }

    /**
     * Set icon right for navigationbar
     *
     * @param drawable image for button right
     */
    public void setIconRight(int drawable) {
        imgRight.setImageResource(drawable);
    }

    /**
     * Set background for navigationbar
     *
     * @param resID This is color of background
     */
    public void setBackgroudNavi(int resID) {
        rltnavi.setBackgroundColor(resID);
    }

    /**
     * Reset all view in navigationbar
     */
    public void reSetAll() {
        setVisibility(View.VISIBLE);
    }

    /**
     * Hidden navigationbar
     */
    public void hiddenAll() {
        setVisibility(View.GONE);
    }


    /**
     * Show right navigationbar
     */
    public void setShowLeft() {
        imgLeft.setVisibility(View.VISIBLE);
    }

    /**
     * hide imageView right
     */
    public void hideImgLeft() {
        imgLeft.setVisibility(View.GONE);
    }

    /**
     * Show right navigationbar
     */
    public void setShowRight() {
        imgRight.setVisibility(View.VISIBLE);
    }

    /**
     * hide imageView right
     */
    public void hideImgright() {
        imgRight.setVisibility(View.GONE);
    }

    /**
     * set event clcick on navigation
     *
     * @param iNavigationOnClick interface INavigationOnClick
     */
    public void setINavigationOnClick(INavigationOnClick iNavigationOnClick) {
        this.iNavigationOnClick = iNavigationOnClick;
    }

    /**
     * Define base view
     */
    private void initialView() {
        if (isInEditMode()) {
            return;
        }

        navi = View.inflate(getContext(), R.layout.view_navigation,
                null);
        if (navi != null) {
            addView(navi, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            imgLeft = (ImageView) navi.findViewById(R.id.imvLeft);
            imgRight = (ImageView) navi.findViewById(R.id.imvRight);
            tvTitle = ((TextView) navi.findViewById(R.id.tvTitleScreen));
            rltnavi = (RelativeLayout) navi.findViewById(R.id.rlt_nav);

            imgRight.setOnClickListener(this);
            imgLeft.setOnClickListener(this);
        }
    }
}
