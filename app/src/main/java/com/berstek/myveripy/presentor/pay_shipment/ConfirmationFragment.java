package com.berstek.myveripy.presentor.pay_shipment;


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
public class ConfirmationFragment extends CustomDialogFragment {

  private View view;

  public ConfirmationFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    view = inflater.inflate(R.layout.fragment_confirmation, container, false);
    super.onCreateView(inflater, container, savedInstanceState);




    return view;
  }

}
