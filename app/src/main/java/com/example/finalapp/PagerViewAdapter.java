package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter {

    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                return  profileFragment;
            case 1:
                UsersFragment usersfragment = new UsersFragment();
                return usersfragment;
            case 3:
                NotificationFragment notificationFragment =  new NotificationFragment();
                return notificationFragment;
            case 2:
                fragment_free fragment_free_f =  new fragment_free();
                return fragment_free_f;

                default:
                    return  null;


        }

    }

    @Override
    public int getCount() {

        return 4;
    }
}
