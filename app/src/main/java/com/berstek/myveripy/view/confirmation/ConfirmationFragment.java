package com.berstek.myveripy.view.confirmation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.berstek.myveripy.R;
import com.berstek.myveripy.custom_classes.CustomDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends CustomDialogFragment
    implements View.OnClickListener {

  private View view;

  public ConfirmationFragment() {
    // Required empty public constructor
  }

  private Button doneBtn;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    view = inflater.inflate(R.layout.fragment_confirmation, container, false);
    super.onCreateView(inflater, container, savedInstanceState);

    doneBtn = view.findViewById(R.id.doneBtn);
    doneBtn.setOnClickListener(this);


    return view;
  }

  @Override
  public void onClick(View view) {
    confirmationFragmentCallback.onConfirmationDone();
  }

  private ConfirmationFragmentCallback confirmationFragmentCallback;

  public interface ConfirmationFragmentCallback {
    void onConfirmationDone();
  }

  public void setConfirmationFragmentCallback(ConfirmationFragmentCallback confirmationFragmentCallback) {
    this.confirmationFragmentCallback = confirmationFragmentCallback;
  }
}
