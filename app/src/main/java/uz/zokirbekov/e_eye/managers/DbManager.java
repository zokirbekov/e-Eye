package uz.zokirbekov.e_eye.managers;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import uz.zokirbekov.e_eye.models.Action;

public class DbManager {
    private static DbManager instance;

    private Context context;
    private Realm realm;

    public static final int CONFIRMED = 1;
    public static final int UNCONFIRMED = -1;
    public static final int IN_PROGRESS = 0;
    public static final int ALL = 99;

    public static DbManager getInstance(Context context) {
        if (instance == null)
            instance = new DbManager();
        instance.context = context;

        if (instance.realm == null)
            instance.initRealm();

        return instance;
    }

    private void addTest()
    {
        realm.beginTransaction();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,3);

        for (int i=0;i<100;i++)
        {

            Action action = realm.createObject(Action.class, UUID.randomUUID().toString());
            action.setTitle("test");
            action.setAdditional("test test test");
            action.setImage(null);
            action.setCreate_date(calendar.getTime());
            if (i % 3 == 0)
            {
                action.setStatus(DbManager.CONFIRMED);
            }
            else if (i % 4 == 0)
            {
                action.setStatus(DbManager.UNCONFIRMED);
            }
            else if (i % 5 == 0)
            {
                action.setStatus(DbManager.IN_PROGRESS);
            }
        }
        realm.commitTransaction();
    }

    private void initRealm()
    {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        //addTest();
    }

    public void addAction(String title, String additional, byte[] image)
    {
        realm.beginTransaction();
        Action action = realm.createObject(Action.class, UUID.randomUUID().toString());
        action.setTitle(title);
        action.setStatus(DbManager.IN_PROGRESS);
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
        instance = null;
    }

    private List<Action> getActionsByStatus(int val)
    {
        RealmResults<Action> resluts = realm.where(Action.class).equalTo("status", val).findAll();
        return realm.copyFromRealm(resluts);
    }
}
