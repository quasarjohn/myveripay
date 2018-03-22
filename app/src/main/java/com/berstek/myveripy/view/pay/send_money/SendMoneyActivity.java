package com.berstek.myveripy.view.pay.send_money;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.berstek.myveripy.R;
import com.berstek.myveripy.model.Payment;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.view.confirmation.ConfirmationFragment;
import com.berstek.myveripy.presentor.payment.PaymentPresentor;
import com.berstek.myveripy.utils.Utils;
import com.berstek.myveripy.view.home.MainActivity;
import com.berstek.myveripy.view.pay.pay_shipment.PayShipmentActivity;
import com.berstek.myveripy.view.pay.pay_shipment.PaymentRecipientFragment;

public class SendMoneyActivity extends AppCompatActivity
    implements PaymentRecipientFragment.PaymentRecipientFragmentCallback,
    PaymentPresentor.PaymentPresentorCallback, View.OnClickListener {

  private PaymentRecipientFragment paymentRecipientFragment;

  private PaymentPresentor paymentPresentor;
  private EditText priceEdit;

  private String recipient;

  private TextView actionTxt, moduleTitleTxt;

  private double cash = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_send_money);

    getSupportActionBar().hide();

    priceEdit = findViewById(R.id.priceEdit);
    actionTxt = findViewById(R.id.actionTxt);
    actionTxt.setOnClickListener(this);
    moduleTitleTxt = findViewById(R.id.moduleTitleTxt);

    moduleTitleTxt.setText("Send Money");

    paymentPresentor = new PaymentPresentor(this);
    paymentPresentor.setPaymentPresentorCallback(this);
    paymentPresentor.init();

    paymentRecipientFragment = new PaymentRecipientFragment();
    paymentRecipientFragment.setPaymentRecipientFragmentCallback(this);
    getSupportFragmentManager().beginTransaction().
        replace(R.id.fragment1, paymentRecipientFragment).commit();
  }

  @Override
  public void onUserQueried(User user) {
    recipient = user.getKey();
  }

  @Override
  public void onDebit(Payment payment) {
    cash -= payment.getAmount();
  }

  @Override
  public void onCredit(Payment payment) {
    cash += payment.getAmount();

  }

  @Override
  public void onPayment(Payment payment) {
    ConfirmationFragment confirmationFragment = new ConfirmationFragment();
    confirmationFragment.setCancelable(false);
    confirmationFragment.show(getFragmentManager(), null);

    confirmationFragment.setConfirmationFragmentCallback(new ConfirmationFragment.ConfirmationFragmentCallback() {
      @Override
      public void onConfirmationDone() {
        Intent intent = new Intent(SendMoneyActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
      }
    });
  }

  @Override
  public void onClick(View view) {

    double amount = Double.parseDouble(priceEdit.getText().toString());

    if (amount > cash) {
      Toast.makeText(this, "You have insufficient funds.", Toast.LENGTH_LONG).show();
    } else {
      if (recipient != null) {
        Payment payment = new Payment();
        payment.setFrom(Utils.getUid());
        payment.setTo(recipient);
        payment.setAmount(amount);
        payment.setDate(System.currentTimeMillis());
        paymentPresentor.pushPayment(payment);
      } else {
        Toast.makeText(this, "Please select a recipient.", Toast.LENGTH_LONG).show();
      }
    }

  }
}
