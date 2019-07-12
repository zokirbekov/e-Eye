package uz.zokirbekov.e_eye.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

public class Utils {

    private static Utils instance;
    private Context context;

    public static Utils getInstance(Context context) {
        if (instance == null)
            instance = new Utils();

        instance.context = context;

        return instance;
    }

    public void showSnackBar(View v, String msg)
    {
        Snackbar.make(v,msg,Snackbar.LENGTH_LONG).show();
    }

    public static void dispose()
    {
        if (instance != null) {
            instance.context = null;
            instance = null;
        }
    }

}
