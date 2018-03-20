package com.berstek.myveripy.presentor.payment;

import com.berstek.myveripy.data_access.PaymentDA;
import com.berstek.myveripy.model.Payment;
import com.berstek.myveripy.utils.Utils;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class PaymentPresentor {

  private PaymentDA paymentDA;

  private PaymentPresentorCallback paymentPresentorCallback;

  public PaymentPresentor() {
    paymentDA = new PaymentDA();
  }

  public void init() {
    paymentDA.queryCredits(Utils.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
        for (DocumentChange dc : documentSnapshots.getDocumentChanges()) {
          Payment payment = dc.getDocument().toObject(Payment.class);
          paymentPresentorCallback.onCredit(payment);
        }
      }
    });

    paymentDA.queryDebits(Utils.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
        for (DocumentChange dc : documentSnapshots.getDocumentChanges()) {
          Payment payment = dc.getDocument().toObject(Payment.class);
          paymentPresentorCallback.onDebit(payment);
        }
      }
    });
  }

  public interface PaymentPresentorCallback {
    void onDebit(Payment payment);

    void onCredit(Payment payment);
  }

  public void setPaymentPresentorCallback(PaymentPresentorCallback paymentPresentorCallback) {
    this.paymentPresentorCallback = paymentPresentorCallback;
  }
}
