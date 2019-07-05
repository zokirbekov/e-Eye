package uz.zokirbekov.e_eye.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uz.zokirbekov.e_eye.R;
import uz.zokirbekov.e_eye.managers.DbManager;
import uz.zokirbekov.e_eye.models.Action;

public class MainFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottomNavBar)
    BottomNavigationView navBar;

    HomeFragment homeFragment;
    NewActionFragment newActionFragment;
    StatisticsFragment statisticsFragment;
    AboutFragment aboutFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this,v);
        navBar.setOnNavigationItemSelectedListener(this);

        switchFragment(homeFragment);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeFragment = new HomeFragment();
        newActionFragment = new NewActionFragment();
        statisticsFragment = new StatisticsFragment();
        aboutFragment = new AboutFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.home : switchFragment(homeFragment); break;
            case R.id.newAction : switchFragment(newActionFragment); break;
            case R.id.statistics : switchFragment(statisticsFragment); break;
            case R.id.about : switchFragment(aboutFragment); break;
        }
        return true;

    }
    public void switchFragment(Fragment fragment)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_in_animation,R.anim.fragment_out_animation);
        ft.replace(R.id.main_content,fragment);
        ft.commit();
    }
}
