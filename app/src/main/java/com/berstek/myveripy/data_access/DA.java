package com.berstek.myveripy.data_access;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Transaction;

public class DA {

  FirebaseAuth auth = FirebaseAuth.getInstance();
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

  private FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
      .setPersistenceEnabled(false)
      .build();

  public DA() {
    db.setFirestoreSettings(settings);
  }

  public void log(Object object) {
    db.collection("logs").add(object);
  }

  public void log2(Object o) {
    rootRef.child("logs").push().setValue(o);
  }

  public Task<Object> updateDocument(String collection, String key, final String field, final Object value) {
    final DocumentReference reference = db.collection(collection).document(key);

    return db.runTransaction(new Transaction.Function<Object>() {

      @Override
      public Object apply(@NonNull Transaction transaction)
          throws FirebaseFirestoreException {

        DocumentSnapshot documentSnapshot = transaction.get(reference);
        transaction.update(reference, field, value);
        return value;
      }
    });
  }

}
