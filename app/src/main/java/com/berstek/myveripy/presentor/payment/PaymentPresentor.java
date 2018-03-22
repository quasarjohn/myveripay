package com.berstek.myveripy.presentor.payment;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.berstek.myveripy.data_access.PaymentDA;
import com.berstek.myveripy.model.Payment;
import com.berstek.myveripy.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class PaymentPresentor {

  private PaymentDA paymentDA;

  private PaymentPresentorCallback paymentPresentorCallback;

  private Activity activity;

  public PaymentPresentor(Activity activity) {
    paymentDA = new PaymentDA();
    this.activity = activity;
  }

  public void init() {
    paymentDA.queryCredits(Utils.getUid()).addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
        for (DocumentChange dc : documentSnapshots.getDocumentChanges()) {
          Payment payment = dc.getDocument().toObject(Payment.class);
          paymentPresentorCallback.onCredit(payment);
        }
      }
    });

    paymentDA.queryDebits(Utils.getUid()).addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
        for (DocumentChange dc : documentSnapshots.getDocumentChanges()) {
          Payment payment = dc.getDocument().toObject(Payment.class);
          paymentPresentorCallback.onDebit(payment);
        }
      }
    });
  }

  public void pushPayment(final Payment payment) {
    paymentDA.pushPayment(payment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
      @Override
      public void onComplete(@NonNull Task<DocumentReference> task) {
        if (task.isSuccessful()) {
          paymentPresentorCallback.onPayment(payment);
        }
      }
    });
  }

  public interface PaymentPresentorCallback {
    void onDebit(Payment payment);

    void onCredit(Payment payment);

    void onPayment(Payment payment);
  }

  public void setPaymentPresentorCallback(PaymentPresentorCallback paymentPresentorCallback) {
    this.paymentPresentorCallback = paymentPresentorCallback;
  }
}
