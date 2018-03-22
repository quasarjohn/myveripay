package com.berstek.myveripy.view.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.custom_classes.CustomDialogFragment;
import com.berstek.myveripy.model.PayTransaction;
import com.berstek.myveripy.presentor.home.TransactionFullViewPresentor;
import com.berstek.myveripy.utils.Utils;
import com.berstek.myveripy.view.upload_image.PlainImageListAdapterHorizontal;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFullViewDialogFragment extends CustomDialogFragment
    implements View.OnClickListener,
    TransactionFullViewPresentor.TransactionFullViewPresentorCallback {

  TransactionFullViewPresentor presentor;

  private PayTransaction transaction;

  private TextView directionTxt, nameTxt, dateTxt, dueTxt,
      priceTxt, statusTxt, titleTxt, detailsTxt, statusBelowTxt, trnoTxt;

  private LinearLayout btnsLinearLayout;

  private Button acceptBtn, rejectBtn;

  private RecyclerView imagesRecview;

  private PlainImageListAdapterHorizontal adapter;

  public TransactionFullViewDialogFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_transaction_full_view_dialog, container, false);

    transaction = (PayTransaction) getArguments().getSerializable("transaction");
    presentor = new TransactionFullViewPresentor();
    presentor.setTransactionFullViewPresentorCallback(this);

    trnoTxt = view.findViewById(R.id.trnoTxt);
    directionTxt = view.findViewById(R.id.directionTxt);
    nameTxt = view.findViewById(R.id.nameTxt);
    dateTxt = view.findViewById(R.id.dateTxt);
    dueTxt = view.findViewById(R.id.dueTxt);
    priceTxt = view.findViewById(R.id.priceTxt);
    statusTxt = view.findViewById(R.id.statusTxt);
    titleTxt = view.findViewById(R.id.titleTxt);
    detailsTxt = view.findViewById(R.id.detailsTxt);
    imagesRecview = view.findViewById(R.id.imagesRecview);
    acceptBtn = view.findViewById(R.id.acceptBtn);
    acceptBtn.setOnClickListener(this);
    rejectBtn = view.findViewById(R.id.rejectBtn);
    rejectBtn.setOnClickListener(this);
    statusBelowTxt = view.findViewById(R.id.statusBelowTxt);

    btnsLinearLayout = view.findViewById(R.id.btnsLinearLayout);
    btnsLinearLayout.setVisibility(View.GONE);


    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    imagesRecview.setLayoutManager(linearLayoutManager);

    if (transaction.getImgs_url() != null)
      adapter = new PlainImageListAdapterHorizontal(transaction.getImgs_url(), getActivity());
    imagesRecview.setAdapter(adapter);

    titleTxt.setText(transaction.getTitle());

    trnoTxt.setText(transaction.getTransaction_id());

    //check if we are in the seller or buyer's perspective
    String direction;
    //this is the buyer's perspective
    if (transaction.getSender_uid().equals(Utils.getUid())) {
      direction = "Sent Pending Payment to ";

      if (transaction.getStatus() == 0) {
        statusBelowTxt.setText("");
      } else if (transaction.getStatus() == 1) {
        statusTxt.setText("Waiting for Seller to Ship Item");
        statusBelowTxt.setText("This transaction has been accepted.");
        statusBelowTxt.setTextColor(getActivity().getResources().getColor(R.color.greenText));

      } else if (transaction.getStatus() == 2) {
        statusTxt.setText("This transaction  is no longer valid");
        statusBelowTxt.setTextColor(getActivity().getResources().getColor(R.color.redText));
        statusBelowTxt.setText("The seller declined this transaction.");
      } else if (transaction.getStatus() == 3) {
        statusTxt.setText("The seller has received payment for this item.");
        statusBelowTxt.setText("This transaction is complete.");
        statusBelowTxt.setTextColor(getActivity().getResources().getColor(R.color.greenText));
      }

      //this is the seller's perspective
    } else {
      direction = "Received Pending Payment from ";
      priceTxt.setTextColor(getActivity().getResources().getColor(R.color.greenText));

      if (transaction.getStatus() == 0) {
        statusBelowTxt.setText("");
        btnsLinearLayout.setVisibility(View.VISIBLE);
      } else if (transaction.getStatus() == 1) {
        statusTxt.setText("Please ship the item to receive the payment.");
        statusBelowTxt.setText("This transaction has been accepted.");
        statusBelowTxt.setTextColor(getActivity().getResources().getColor(R.color.greenText));
      } else if (transaction.getStatus() == 2) {
        statusTxt.setText("This transaction is no longer valid.");
        statusBelowTxt.setTextColor(getActivity().getResources().getColor(R.color.redText));
        statusBelowTxt.setText("You declined this transaction.");
      } else if (transaction.getStatus() == 3) {
        statusTxt.setText("You have received payment for this item.");
        statusBelowTxt.setText("This transaction is complete.");
        statusTxt.setTextColor(getActivity().getResources().getColor(R.color.greenText));

      }
    }


    directionTxt.setText(direction);
    dateTxt.setText(Utils.formatDateWithTime(transaction.getDate_created()));
    dueTxt.setText(String.format("Due on %s", Utils.formatDateWithTime(transaction.getDue_date())));

    return super.onCreateView(inflater, container, savedInstanceState);
  }

  private boolean accepted;

  @Override
  public void onClick(View view) {
    int id = view.getId();

    if (id == R.id.acceptBtn) {
      presentor.acceptTransaction(transaction.getKey());
      accepted = true;
    } else {
      presentor.rejectTransaction(transaction.getKey());
      accepted = false;
    }
  }

  @Override
  public void onTransactionUpdateCompleted() {
    btnsLinearLayout.setVisibility(View.GONE);
    statusTxt.setText("Waiting for Seller to Ship Item");
    statusBelowTxt.setTextColor(getActivity().getResources().getColor(R.color.primaryTextColor));
    if (accepted) {
      statusBelowTxt.setText("This transaction has been accepted.");
    } else {
      statusBelowTxt.setText("This transaction has been declined.");
      statusBelowTxt.setTextColor(getActivity().getResources().getColor(R.color.redText));
    }
  }

  @Override
  public void onTransactionUpdateFailed() {

  }
}
