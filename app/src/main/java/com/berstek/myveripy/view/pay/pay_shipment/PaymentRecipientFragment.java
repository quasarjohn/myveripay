package com.berstek.myveripy.view.pay.pay_shipment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.data_access.DA;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.presentor.search.SearchUserPresentor;
import com.berstek.myveripy.utils.Utils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentRecipientFragment extends Fragment implements TextWatcher,
    SearchUserPresentor.SearchUserPresentorCallback {

  private View view;
  private EditText payIdEdit;
  private Button contactsBtn;
  private TextView nameTxt, addressTxt;
  private CircleImageView dp;

  private String searchParam;

  private Handler handler = new Handler();
  private InputHandler inputHandler;

  private SearchUserPresentor searchUserPresentor;

  private PaymentRecipientFragmentCallback paymentRecipientFragmentCallback;

  public PaymentRecipientFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_payment_recipient, container, false);

    searchParam = getArguments().getString("searchParam");

    payIdEdit = view.findViewById(R.id.payIdEdit);
    contactsBtn = view.findViewById(R.id.contactsBtn);
    nameTxt = view.findViewById(R.id.nameTxt);
    addressTxt = view.findViewById(R.id.addressTxt);
    dp = view.findViewById(R.id.dp);

    inputHandler = new InputHandler();

    payIdEdit.addTextChangedListener(this);

    if (searchParam != null)
      payIdEdit.setText(searchParam);

    searchUserPresentor = new SearchUserPresentor();
    searchUserPresentor.setSearchUserPresentorCallback(this);

    return view;
  }

  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    handler.removeCallbacks(inputHandler);
  }

  @Override
  public void afterTextChanged(Editable editable) {
    handler.postDelayed(inputHandler, 1000);
  }

  @Override
  public void onUserQueried(List<User> users) {
    User u = users.get(0);
    new Utils(getActivity()).loadImage(u.getPhoto_url(), dp);
    nameTxt.setText(u.getFirst_name() + " " + u.getLast_name());

    if (paymentRecipientFragmentCallback != null)
      paymentRecipientFragmentCallback.onUserQueried(u);
  }

  class InputHandler implements Runnable {

    @Override
    public void run() {
      searchUserPresentor.queryUser(payIdEdit.getText().toString());
    }
  }

  public interface PaymentRecipientFragmentCallback {
    void onUserQueried(User user);
  }

  public void setPaymentRecipientFragmentCallback(PaymentRecipientFragmentCallback paymentRecipientFragmentCallback) {
    this.paymentRecipientFragmentCallback = paymentRecipientFragmentCallback;
  }
}
