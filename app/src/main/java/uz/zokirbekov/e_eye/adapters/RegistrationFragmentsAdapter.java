package uz.zokirbekov.e_eye.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class RegistrationFragmentsAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public RegistrationFragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    public RegistrationFragmentsAdapter(FragmentManager fm, List<Fragment> fragments)
    {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
