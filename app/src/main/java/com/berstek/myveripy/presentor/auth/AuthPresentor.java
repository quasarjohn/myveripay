package com.berstek.myveripy.presentor.auth;

import com.berstek.myveripy.data_access.DA;
import com.berstek.myveripy.data_access.UserDA;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

public class AuthPresentor {

  private UserDA userDA;

  private OnUserAddedCallback onUserAddedCallback;

  public AuthPresentor() {
    userDA = new UserDA();
  }

  public void addUser(FirebaseUser fbUser, GoogleSignInAccount account) {
    User user = new User();
    user.setLast_name(account.getFamilyName());
    user.setFirst_name(account.getGivenName());
    user.setPhoto_url(account.getPhotoUrl().toString());
    user.setDate_joined(System.currentTimeMillis());
    user.setKey(Utils.getUid());
    user.setEmail(account.getEmail());
    user.setPay_id(UUID.randomUUID().toString().substring(0, 8).toUpperCase());

    userDA.addUserCheckIfExists(user).addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void aVoid) {
        onUserAddedCallback.onUserAdded();
      }
    });
  }

  public interface OnUserAddedCallback {
    void onUserAdded();
  }

  public void setOnUserAddedCallback(OnUserAddedCallback onUserAddedCallback) {
    this.onUserAddedCallback = onUserAddedCallback;
  }
}
