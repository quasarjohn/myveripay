package com.berstek.myveripy.presentor.pay_shipment;

import android.support.annotation.NonNull;

import com.berstek.myveripy.data_access.TransactionDA;
import com.berstek.myveripy.model.PayTransaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class PayShipmentPresentor {

  private TransactionDA transactionDA;
  private PayShipmentPresentorCallback payShipmentPresentorCallback;

  public PayShipmentPresentor() {
    transactionDA = new TransactionDA();
  }

  public void pushTransaction(PayTransaction transaction) {
    transactionDA.pushTransaction(transaction).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
      @Override
      public void onComplete(@NonNull Task<DocumentReference> task) {
        if (task.isSuccessful()) {
          payShipmentPresentorCallback.onTransactionPushed();
        }
      }
    });
  }

  public interface PayShipmentPresentorCallback {
    void onTransactionPushed();
  }

  public void setPayShipmentPresentorCallback(PayShipmentPresentorCallback payShipmentPresentorCallback) {
    this.payShipmentPresentorCallback = payShipmentPresentorCallback;
  }
}
