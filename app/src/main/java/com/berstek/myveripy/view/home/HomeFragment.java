package com.berstek.myveripy.view.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.callback.RecviewItemClickCallback;
import com.berstek.myveripy.model.PayTransaction;
import com.berstek.myveripy.model.Payment;
import com.berstek.myveripy.presentor.home.HomePresentor;
import com.berstek.myveripy.presentor.payment.PaymentPresentor;
import com.berstek.myveripy.utils.Utils;
import com.berstek.myveripy.view.partners.PartnersFragment;
import com.berstek.myveripy.view.pay.PaymentTypeSelectionDialogFragment;
import com.berstek.myveripy.view.pay.pay_shipment.PayShipmentActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener,
    HomePresentor.HomePresentorCallback, RecviewItemClickCallback,
    PaymentPresentor.PaymentPresentorCallback,
    PaymentTypeSelectionDialogFragment.PaymentTypeCallback{

  private View view;
  private TextView balanceTxt;


  private FloatingActionButton payFab, cashInFab, receiveFab;
  private FloatingActionMenu fam;
  private RecyclerView transactionsRecview;

  private PaymentTypeSelectionDialogFragment paymentTypeSelectionDialogFragment;

  private HomePresentor homePresentor;
  private PaymentPresentor paymentPresentor;

  private ArrayList<PayTransaction> transactions;
  private HomeTransactionsAdapter adapter;

  private double balance = 0;

  public HomeFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    view = inflater.inflate(R.layout.fragment_home, container, false);

    balanceTxt = view.findViewById(R.id.balanceTxt);

    transactions = new ArrayList<>();
    adapter = new HomeTransactionsAdapter(transactions, getContext());
    adapter.setItemClickCallback(this);

    homePresentor = new HomePresentor();
    homePresentor.setHomePresentorCallback(this);
    homePresentor.init();

    paymentPresentor = new PaymentPresentor(getActivity());
    paymentPresentor.setPaymentPresentorCallback(this);
    paymentPresentor.init();

    payFab = view.findViewById(R.id.payFab);
    cashInFab = view.findViewById(R.id.cashInFab);
    receiveFab = view.findViewById(R.id.receiveFab);
    fam = view.findViewById(R.id.fam);
    transactionsRecview = view.findViewById(R.id.transactionsRecview);
    transactionsRecview.setLayoutManager(new LinearLayoutManager(getContext()));
    transactionsRecview.setAdapter(adapter);

    payFab.setOnClickListener(this);
    cashInFab.setOnClickListener(this);
    receiveFab.setOnClickListener(this);
    fam.setOnClickListener(this);

    return view;
  }

  @Override
  public void onClick(View view) {

    int id = view.getId();

    if (id == R.id.payFab) {
      paymentTypeSelectionDialogFragment = new PaymentTypeSelectionDialogFragment();
      paymentTypeSelectionDialogFragment.show(getActivity().getFragmentManager(), null);
      paymentTypeSelectionDialogFragment.setPaymentTypeCallback(this);

    } else if (id == R.id.cashInFab) {
      PartnersFragment partnersFragment = new PartnersFragment();
      partnersFragment.show(getActivity().getFragmentManager(), null);

    }

    fam.close(false);
  }

  @Override
  public void onTransactionAdded(PayTransaction transaction) {
    transactions.add(transaction);
    adapter.notifyItemInserted(transactions.size() - 1);
  }

  @Override
  public void onTransactionChanged(PayTransaction transaction) {

    for (int i = 0; i < transactions.size(); i++) {
      if (transactions.get(i).getKey().equals(transaction.getKey())) {
        transactions.set(i, transaction);
        adapter.notifyItemChanged(i);
        break;
      }
    }
  }

  @Override
  public void onRecviewItemClick(View view, int position) {
    PayTransaction transaction = transactions.get(position);
    Bundle bundle = new Bundle();
    bundle.putSerializable("transaction", transaction);

    TransactionFullViewDialogFragment dialogFragment = new TransactionFullViewDialogFragment();
    dialogFragment.setArguments(bundle);
    dialogFragment.show(getActivity().getFragmentManager(), null);
  }

  @Override
  public void onDebit(Payment payment) {
    balance -= payment.getAmount();
    updateBalanceTxt();
  }

  @Override
  public void onCredit(Payment payment) {
    balance += payment.getAmount();
    updateBalanceTxt();
  }

  private void updateBalanceTxt() {
    balanceTxt.setText(String.format("%s %s", Utils.getPesoSign(), Utils.formatDf(balance)));
  }

  @Override
  public void onSendMoney() {

  }

  @Override
  public void onPayShipment() {
    Intent intent = new Intent(getActivity(), PayShipmentActivity.class);
    startActivity(intent);
  }
}
