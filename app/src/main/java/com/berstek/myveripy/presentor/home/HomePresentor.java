package com.berstek.myveripy.presentor.home;

import com.berstek.myveripy.data_access.TransactionDA;
import com.berstek.myveripy.model.PayTransaction;
import com.berstek.myveripy.utils.Utils;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class HomePresentor {

  private TransactionDA transactionDA;

  public HomePresentor() {
    transactionDA = new TransactionDA();
  }


  public void init() {

    transactionDA.queryIncompleteTransactionsReceived(Utils.getUid()).
        addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
            if (snapshots != null)
              dispatchChange(snapshots.getDocumentChanges());
          }
        });

    transactionDA.queryIncompleteTransactionsSent(Utils.getUid()).
        addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
            if (snapshots != null)
              dispatchChange(snapshots.getDocumentChanges());
          }
        });
  }

  private HomePresentorCallback homePresentorCallback;

  public interface HomePresentorCallback {
    void onTransactionAdded(PayTransaction transaction);

    void onTransactionChanged(PayTransaction transaction);
  }

  public void setHomePresentorCallback(HomePresentorCallback homePresentorCallback) {
    this.homePresentorCallback = homePresentorCallback;
  }

  private void dispatchChange(List<DocumentChange> dcs) {
    for (DocumentChange dc : dcs) {

      PayTransaction transaction = dc.getDocument().toObject(PayTransaction.class);
      transaction.setKey(dc.getDocument().getId());

      switch (dc.getType()) {
        case ADDED:
          homePresentorCallback.onTransactionAdded(transaction);
          break;
        case MODIFIED:
          homePresentorCallback.onTransactionChanged(transaction);
          break;
        case REMOVED:
          break;
      }
    }
  }


}
