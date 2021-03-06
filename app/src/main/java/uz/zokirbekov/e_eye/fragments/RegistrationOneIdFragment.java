package uz.zokirbekov.e_eye.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import uz.zokirbekov.e_eye.MainActivity;
import uz.zokirbekov.e_eye.R;

public class RegistrationOneIdFragment extends Fragment{

    ViewPager pager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration_oneid,container,false);
        ButterKnife.bind(this,v);

        pager = getActivity().findViewById(R.id.viewPager);

        return v;
    }

    @OnClick(R.id.button_login)
    void loginClick()
    {
        ((MainActivity)getActivity()).switchFragment(new MainFragment());
    }

    @OnClick(R.id.layout_passport)
    void onOneIdClicked()
    {
        pager.setCurrentItem(RegistrationFragment.PASSPORT);
    }
}
