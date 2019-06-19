package uz.zokirbekov.e_eye.managers;

import android.content.Context;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import uz.zokirbekov.e_eye.models.Action;

public class DbManager {
    private static DbManager instance;

    private Context context;
    private Realm realm;

    public static int CONFIRMED = 1;
    public static int UNCONFIRMED = -1;
    public static int IN_PROGRESS = 0;

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

    public void addAction(String title, byte[] image, String additional)
    {
        realm.beginTransaction();
        Action action = realm.createObject(Action.class);
        action.setTitle(title);
        action.setAdditional(additional);
        action.setImage(image);
        action.setCreate_date(new Date());

        realm.commitTransaction();
    }

    public List<Action> getAllActions()
    {
         RealmResults<Action> resluts = realm.where(Action.class).findAll();
         return realm.copyFromRealm(resluts);
    }


    public void close()
    {
        realm.close();
    }

    private List<Action> getActionsByStatus(int val)
    {
        RealmResults<Action> resluts = realm.where(Action.class).equalTo("status", val).findAll();
        return realm.copyFromRealm(resluts);
    }
}
