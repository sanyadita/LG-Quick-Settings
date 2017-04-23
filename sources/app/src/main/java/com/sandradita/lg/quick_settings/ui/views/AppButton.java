package com.sandradita.lg.quick_settings.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sandradita.lg.quick_settings.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author aditkovskaya
 */

public class AppButton extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.tvBtnText)
    TextView tvBtnText;
    @BindView(R.id.btnIcon)
    ImageButton btnIcon;

    private OnClickListener onClickListener;

    public AppButton(Context context) {
        super(context);
        init();
    }

    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.onClickListener = l;
    }

    public void setText(CharSequence text) {
        tvBtnText.setText(text);
        tvBtnText.setVisibility(text == null ? GONE : VISIBLE);
    }

    public void setText(@StringRes int textRes) {
        tvBtnText.setText(textRes);
    }

    public void setIconResource(@DrawableRes int iconRes) {
        btnIcon.setImageResource(iconRes);
    }

    public void setIconDrawable(Drawable drawable) {
        btnIcon.setImageDrawable(drawable);
    }

    public void setButtonBackgroundResource(@DrawableRes int iconRes) {
        btnIcon.setBackgroundResource(iconRes);
    }

    public void setButtonBackgroundDrawable(Drawable drawable) {
        btnIcon.setBackground(drawable);
    }

    private void init() {
        inflate(getContext(), R.layout.view_app_button, this);
        ButterKnife.bind(this);
        btnIcon.setOnClickListener(this);
    }

    private void init(AttributeSet attrs) {
        init();
        TypedArray attributes = getContext().getTheme()
                .obtainStyledAttributes(attrs, R.styleable.AppButton, 0, 0);
        setText(attributes.getString(R.styleable.AppButton_text));
        setIconDrawable(attributes.getDrawable(R.styleable.AppButton_iconSrc));
    }

    @Override
    public void onClick(View v) {
        onClickListener.onClick(this);
    }

}
