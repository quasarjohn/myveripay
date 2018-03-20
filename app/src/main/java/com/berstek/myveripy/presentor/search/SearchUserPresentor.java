package com.berstek.myveripy.presentor.search;

import android.support.annotation.NonNull;
import android.util.Log;

import com.berstek.myveripy.data_access.DA;
import com.berstek.myveripy.data_access.UserDA;
import com.berstek.myveripy.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchUserPresentor {

  private UserDA userDA;


  private SearchUserPresentorCallback searchUserPresentorCallback;

  public SearchUserPresentor() {
    userDA = new UserDA();
  }

  public void queryUser(String param) {
    //TODO check if the param is a user's name, email or a pay ID, for now, I will use pay ID
    userDA.queryUserByPayID(param).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
          searchUserPresentorCallback.onUserQueried(task.getResult().toObjects(User.class));
        }
      }
    });

  }

  public interface SearchUserPresentorCallback {
    void onUserQueried(List<User> users);
  }

  public void setSearchUserPresentorCallback(SearchUserPresentorCallback searchUserPresentorCallback) {
    this.searchUserPresentorCallback = searchUserPresentorCallback;
  }
}
