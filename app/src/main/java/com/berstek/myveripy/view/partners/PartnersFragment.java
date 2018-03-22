package com.berstek.myveripy.view.partners;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.myveripy.R;
import com.berstek.myveripy.custom_classes.CustomDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnersFragment extends CustomDialogFragment {

  private View view;

  public PartnersFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_partners, container, false);
    super.onCreateView(inflater, container, savedInstanceState);

    return view;
  }

}
