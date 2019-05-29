package uz.zokirbekov.e_eye;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import uz.zokirbekov.e_eye.fragments.HomeFragment;
import uz.zokirbekov.e_eye.fragments.MainFragment;
import uz.zokirbekov.e_eye.fragments.RegistrationFragment;
import uz.zokirbekov.e_eye.fragments.StatisticsFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.content)
    FrameLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        switchFragment(new MainFragment());
    }
    public void switchFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content,fragment);
        ft.commit();
    }
}
