package uz.zokirbekov.e_eye.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uz.zokirbekov.e_eye.R;
import uz.zokirbekov.e_eye.adapters.RegistrationFragmentsAdapter;

public class RegistrationFragment extends Fragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration,container,false);
        ButterKnife.bind(this,v);
        init();
        return v;
    }
    private void init()
    {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new RegistrationERIFragment());
        fragments.add(new RegistrationOneIdFragment());
        RegistrationFragmentsAdapter adapter = new RegistrationFragmentsAdapter(getFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }
}
