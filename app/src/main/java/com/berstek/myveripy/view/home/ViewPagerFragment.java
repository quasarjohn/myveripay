package com.berstek.myveripy.view.home;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.myveripy.R;
import com.berstek.myveripy.view.payment_history.PaymentHistoryFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {

  private View view;

  private TabLayout tab;
  private ViewPager viewPager;

  private ArrayList<Fragment> fragments;


  public ViewPagerFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragments = new ArrayList<>();


    view = inflater.inflate(R.layout.fragment_view_pager, container, false);
    tab = view.findViewById(R.id.tab);
    viewPager = view.findViewById(R.id.viewpager);

    HomeFragment homeFragment = new HomeFragment();
    fragments.add(homeFragment);
    tab.addTab(tab.newTab());

    PaymentHistoryFragment paymentHistoryFragment = new PaymentHistoryFragment();
    fragments.add(paymentHistoryFragment);
    tab.addTab(tab.newTab());

    viewPager.setAdapter(new ViewpagerAdapter(getActivity().getSupportFragmentManager()));
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
    tab.setupWithViewPager(viewPager);

    for (int i = 0; i < fragments.size(); i++) {
      if (i == 0) {
        tab.getTabAt(i).setCustomView(getActivity().getLayoutInflater().inflate(R.layout.tablayout_view, null));
      } else {
        tab.getTabAt(i).setCustomView(getActivity().getLayoutInflater().inflate(R.layout.tablayout_view_deselected, null));
      }
    }

    tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        tab.setCustomView(null);
        tab.setCustomView(getActivity().getLayoutInflater().inflate(R.layout.tablayout_view, null));
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {
        tab.setCustomView(null);
        tab.setCustomView(getLayoutInflater().inflate(R.layout.tablayout_view_deselected, null));
      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });

    viewPager.setOffscreenPageLimit(fragments.size());

    return view;
  }

  private class ViewpagerAdapter extends FragmentStatePagerAdapter {

    public ViewpagerAdapter(FragmentManager fm) {
      super(fm);
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

}
