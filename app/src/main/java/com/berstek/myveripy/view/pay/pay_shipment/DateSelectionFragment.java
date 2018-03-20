package com.berstek.myveripy.view.pay.pay_shipment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.myveripy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateSelectionFragment extends Fragment {


  public DateSelectionFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_date_selection, container, false);
  }

}
