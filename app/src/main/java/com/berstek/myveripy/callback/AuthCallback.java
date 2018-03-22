package com.berstek.myveripy.callback;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

public interface AuthCallback {
  void onAuthSuccess(FirebaseUser user, GoogleSignInAccount account);

  void onSignOutSuccess();

}
