package com.berstek.myveripy.data_access;

import com.berstek.myveripy.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserDA extends DA {

  private CollectionReference usersRef = db.collection("users");

  public Task<Void> addUserCheckIfExists(User user) {
    String uid = auth.getCurrentUser().getUid();

    return usersRef.document(uid).set(user);
  }

  public Task<QuerySnapshot> queryUserByPayID(String param) {
    return usersRef.whereEqualTo("pay_id", param).get();
  }

  public DocumentReference queryUserByUid(String uid) {
    return usersRef.document(uid);
  }

  public Task<DocumentSnapshot> queryUserByUidOnce(String uid) {
    return usersRef.document(uid).get();
  }
}
