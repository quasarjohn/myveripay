package com.berstek.myveripy.data_access;

import com.berstek.myveripy.model.Payment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

public class PaymentDA extends DA {

  private CollectionReference payRef = db.collection("payments");

  public Query queryCredits(String userUid) {
    return payRef.whereEqualTo("to", userUid);
  }

  public Query queryDebits(String userUid) {
    return payRef.whereEqualTo("from", userUid);
  }

  public Task<DocumentReference> pushPayment(Payment payment) {
    return payRef.add(payment);
  }

}
