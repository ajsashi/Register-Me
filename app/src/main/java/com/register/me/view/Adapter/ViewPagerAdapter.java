package com.register.me.view.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.register.me.view.fragments.Client.activeProjects.CommentFragment;
import com.register.me.view.fragments.Client.auctions.ActiveAuctionFragment;
import com.register.me.view.fragments.Client.auctions.BidsToEvaluateFragment;
import com.register.me.view.fragments.Client.portfolio.addProducts.AddProductFragment;
import com.register.me.view.fragments.RRE.applicationSubmission.DocumentFragment;
import com.register.me.view.fragments.RRE.applicationSubmission.PersonalInfoFragment;

/**
 * Created by Jennifer - AIT on 11-02-2020PM 06:53.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final int viewCase;
    int totalTabs;


    public ViewPagerAdapter(FragmentManager childFragmentManager, int tabCount, int viewCase) {
        super(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.totalTabs = tabCount;
        this.viewCase = viewCase;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (viewCase) {
            case 0:
                switch (position) {
                    case 0:
                        return new ActiveAuctionFragment();
                    case 1:
                        return new BidsToEvaluateFragment();

                    default:
                        throw new IllegalStateException("Unexpected value: " + position);
                }
            case 1:
                switch (position) {
                    case 0:
                        return new PersonalInfoFragment();
                    case 1:
                        return new AddProductFragment();
                    case 2:
                        return new DocumentFragment();
                    case 3:
                        return new CommentFragment();

                    default:
                        throw new IllegalStateException("Unexpected value: " + position);
                }

            default:
                throw new IllegalStateException("Unexpected value: " + viewCase);
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
