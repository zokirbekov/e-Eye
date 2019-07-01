package uz.zokirbekov.e_eye.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

public class AnimationHelper {
    private static AnimationHelper instance;

    private long duration = 200;

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
    }

    public void fadeOut(View v)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ALPHA, 1, 0);
        animator.setDuration(duration);
        animator.start();
    }
}
