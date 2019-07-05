package uz.zokirbekov.e_eye.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.view.View;

public class AnimationHelper {
    private static AnimationHelper instance;

    private long duration = 200;
    private Handler handler = new Handler();

    public static AnimationHelper getInstance() {

        if (instance == null)
            instance = new AnimationHelper();

        return instance;
    }

    public void fadeIn(View v)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ALPHA, 0, 1);
        animator.setDuration(duration);
        animator.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.VISIBLE);
            }
        },duration);
    }

    public void fadeOut(View v)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ALPHA, 1, 0);
        animator.setDuration(duration);
        animator.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.INVISIBLE);
            }
        },duration);
    }
}
