package com.register.me.view.fragments.Client.auctions;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.register.me.view.Adapter.ViewPagerAdapter;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Jennifer - AIT on 11-02-2020PM 06:35.
 */
public class AuctionFragment extends BaseFragment implements IFragment{

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @Inject
    Constants constants;

    public static IFragment newInstance() {
        return new AuctionFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_auction;
    }

    @Override
    public String getFragmentName()
    {
        return "Auction";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injector().inject(this);
        tabLayout.addTab(tabLayout.newTab().setText("AUCTIONS IN PROGRESS"));
        tabLayout.addTab(tabLayout.newTab().setText("BIDS READY TO EVALUATE"));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(),0);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("AUCTION DETAILS");
    }
}
