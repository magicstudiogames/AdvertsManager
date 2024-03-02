package com.magicstudiogames.advertsmanager.adverts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatTextView;

public class AdView extends FrameLayout {

        public AdView(Context context) {
            super(context);
            init(context, null, 0);
        }

        public AdView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs, 0);
        }

        public AdView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context, attrs, defStyleAttr);
        }

        private void init(Context context, AttributeSet attrs, int defStyleAttr) {
            setPadding(2, 2, 2, 2);
        }

        public void setView(View contentView) {
            if (contentView != null) {
                addView(contentView);
            } else {
                addView(empty());
            }
        }

        public void addAnim() {
            setVisibility(VISIBLE);
            Animation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
            fadeInAnimation.setDuration(4000);
            this.startAnimation(fadeInAnimation);
        }

        public View empty() {
            AppCompatTextView adsTextView = new AppCompatTextView(getContext());
            adsTextView.setText("Ads by Unity");
            adsTextView.setGravity(Gravity.CENTER);
            adsTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return adsTextView;
        }

    }