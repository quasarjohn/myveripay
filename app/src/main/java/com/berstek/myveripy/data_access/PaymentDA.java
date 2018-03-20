package com.berstek.myveripy.data_access;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.Query;

public class PaymentDA extends DA {

  private CollectionReference payRef = db.collection("payments");

  public Query queryCredits(String userUid) {
    return  payRef.whereEqualTo("to", userUid);
  }

  public Query queryDebits(String userUid) {
    return  payRef.whereEqualTo("from", userUid);
  }

}
