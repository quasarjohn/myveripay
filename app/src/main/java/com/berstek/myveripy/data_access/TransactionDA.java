package com.berstek.myveripy.data_access;

import com.berstek.myveripy.model.PayTransaction;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

public class TransactionDA extends DA {


  public Task<DocumentReference> pushTransaction(PayTransaction transaction) {
    return db.collection("transactions").add(transaction);
  }


  public Query queryIncompleteTransactionsSent(String uid) {
    return db.collection("transactions").
        whereLessThan("status", 4).
        whereEqualTo("sender_uid", uid);
  }

  public Query queryIncompleteTransactionsReceived(String uid) {
    return db.collection("transactions").
        whereEqualTo("recipient_uid", uid).
        whereLessThan("status", 4);
  }

  public Task<Object> updateTransactionStatus(String key, int status) {
    return super.updateDocument("transactions", key, "status", status);
  }
}
