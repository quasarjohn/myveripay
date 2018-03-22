package com.berstek.myveripy.view.pay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.custom_classes.CustomDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentTypeSelectionDialogFragment
    extends CustomDialogFragment implements View.OnClickListener {


  public PaymentTypeSelectionDialogFragment() {
    // Required empty public constructor
  }

  private ImageView sendMoneyImg, payShipmentImg;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_payment_type_selection_dialog, container, false);

    sendMoneyImg = view.findViewById(R.id.sendMoneyImg);
    payShipmentImg = view.findViewById(R.id.payShipmentImg);

    sendMoneyImg.setOnClickListener(this);
    payShipmentImg.setOnClickListener(this);

    return super.onCreateView(inflater, container, savedInstanceState);
  }

  private PaymentTypeCallback paymentTypeCallback;

  @Override
  public void onClick(View view) {
    int id = view.getId();

    if (id == R.id.sendMoneyImg) {
      paymentTypeCallback.onSendMoney();
    } else if (id == R.id.payShipmentImg) {
      paymentTypeCallback.onPayShipment();
    }
  }


  public interface PaymentTypeCallback {
    void onSendMoney();

    void onPayShipment();
  }

  public void setPaymentTypeCallback(PaymentTypeCallback paymentTypeCallback) {
    this.paymentTypeCallback = paymentTypeCallback;
  }
}
