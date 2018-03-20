package com.berstek.myveripy.presentor.home;

import android.support.annotation.NonNull;

import com.berstek.myveripy.data_access.TransactionDA;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class TransactionFullViewPresentor {

  private TransactionDA transactionDA;

  public TransactionFullViewPresentor() {
    transactionDA = new TransactionDA();
  }

  public void acceptTransaction(String key) {
    writeTransactionUpdate(key, 1);
  }

  public void rejectTransaction(String key) {
    writeTransactionUpdate(key, 2);
  }

  public void writeTransactionUpdate(String key, int status) {
    transactionDA.updateTransactionStatus(key, status).
        addOnSuccessListener(new OnSuccessListener<Object>() {
          @Override
          public void onSuccess(Object o) {
            transactionFullViewPresentorCallback.onTransactionUpdateCompleted();
          }
        }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        transactionFullViewPresentorCallback.onTransactionUpdateFailed();
      }
    });
  }

  private TransactionFullViewPresentorCallback transactionFullViewPresentorCallback;

  public interface TransactionFullViewPresentorCallback {
    void onTransactionUpdateCompleted();

    void onTransactionUpdateFailed();
  }

  public void setTransactionFullViewPresentorCallback(TransactionFullViewPresentorCallback transactionFullViewPresentorCallback) {
    this.transactionFullViewPresentorCallback = transactionFullViewPresentorCallback;
  }
}
