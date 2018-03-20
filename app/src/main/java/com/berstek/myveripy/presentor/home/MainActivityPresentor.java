package com.berstek.myveripy.presentor.home;


import com.berstek.myveripy.data_access.UserDA;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.utils.Utils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivityPresentor {

  private MainActivityPresentorCallback mainActivityPresentorCallback;

  private UserDA userDA;

  public MainActivityPresentor() {
    userDA = new UserDA();
  }

  public void init() {
    userDA.queryUserByUid(Utils.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
      @Override
      public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
        if (documentSnapshot.exists())
          mainActivityPresentorCallback.onUserDataLoaded(documentSnapshot.toObject(User.class));
      }
    });

  }

  public interface MainActivityPresentorCallback {
    void onUserDataLoaded(User user);
  }

  public void setMainActivityPresentorCallback(MainActivityPresentorCallback mainActivityPresentorCallback) {
    this.mainActivityPresentorCallback = mainActivityPresentorCallback;
  }
}
