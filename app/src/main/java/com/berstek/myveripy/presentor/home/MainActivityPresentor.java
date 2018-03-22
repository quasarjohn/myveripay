package com.berstek.myveripy.presentor.home;


import android.app.Activity;

import com.berstek.myveripy.data_access.UserDA;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.utils.Utils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivityPresentor {

  private MainActivityPresentorCallback mainActivityPresentorCallback;

  private UserDA userDA;

  private DocumentReference ref;

  private Activity activity;

  public MainActivityPresentor(Activity activity) {
    userDA = new UserDA();
    this.activity = activity;
  }

  public void init() {
    ref = userDA.queryUserByUid(Utils.getUid());
    ref.addSnapshotListener(activity, eventListener);

  }

  private EventListener eventListener = new EventListener<DocumentSnapshot>() {
    @Override
    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
      if (documentSnapshot.exists())
        mainActivityPresentorCallback.onUserDataLoaded(documentSnapshot.toObject(User.class));
    }
  };

  public interface MainActivityPresentorCallback {
    void onUserDataLoaded(User user);
  }

  public void setMainActivityPresentorCallback(MainActivityPresentorCallback mainActivityPresentorCallback) {
    this.mainActivityPresentorCallback = mainActivityPresentorCallback;
  }

  public void detachListeners() {

  }
}
