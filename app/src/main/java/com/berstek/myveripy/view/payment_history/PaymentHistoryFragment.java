package com.berstek.myveripy.view.payment_history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.myveripy.R;
import com.berstek.myveripy.model.Payment;
import com.berstek.myveripy.presentor.payment.PaymentPresentor;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentHistoryFragment extends Fragment
    implements PaymentPresentor.PaymentPresentorCallback {

  private View view;
  private RecyclerView paymentsRecview;

  private PaymentPresentor paymentPresentor;

  private ArrayList<Payment> payments;

  private PaymentHistoryAdapter adapter;


  public PaymentHistoryFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_payment_history, container, false);

    paymentsRecview = view.findViewById(R.id.paymentsRecview);
    paymentsRecview.setLayoutManager(new LinearLayoutManager(getContext()));

    payments = new ArrayList<>();
    adapter = new PaymentHistoryAdapter(payments, getContext());

    paymentsRecview.setAdapter(adapter);

    paymentPresentor = new PaymentPresentor(getActivity());
    paymentPresentor.setPaymentPresentorCallback(this);
    paymentPresentor.init();


    return view;
  }

  @Override
  public void onDebit(Payment payment) {
    payments.add(payment);
    adapter.notifyItemInserted(payments.size() - 1);
  }

  @Override
  public void onCredit(Payment payment) {
    payments.add(payment);
    adapter.notifyItemInserted(payments.size() - 1);
  }

  @Override
  public void onPayment(Payment payment) {

  }
}
