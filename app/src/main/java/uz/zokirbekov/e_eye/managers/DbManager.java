package uz.zokirbekov.e_eye.managers;

import android.content.Context;

import java.util.Date;

import io.realm.Realm;
import uz.zokirbekov.e_eye.models.Action;

public class DbManager {
    private static DbManager instance;

    private Context context;
    private Realm realm;

    public static DbManager getInstance(Context context) {
        if (instance == null)
            instance = new DbManager();
        instance.context = context;

        if (instance.realm == null)
            instance.initRealm();

        return instance;
    }
    private void initRealm()
    {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void addAction(String title, String image, String additional)
    {
        realm.beginTransaction();
        Action action = realm.createObject(Action.class);

        action.setTitle(title);
        action.setAdditional(additional);
        action.setImage(image);
        action.setCreate_date(new Date());

        realm.commitTransaction();
    }

    public void close()
    {
        realm.close();
    }
}
