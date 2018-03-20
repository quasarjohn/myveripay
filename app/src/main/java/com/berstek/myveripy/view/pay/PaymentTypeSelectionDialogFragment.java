package com.berstek.myveripy.view.pay;


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
public class PaymentTypeSelectionDialogFragment extends CustomDialogFragment {


  public PaymentTypeSelectionDialogFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_payment_type_selection_dialog, container, false);
    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
