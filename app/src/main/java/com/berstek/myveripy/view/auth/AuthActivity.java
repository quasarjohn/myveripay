package com.berstek.myveripy.view.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.callback.AuthCallback;
import com.berstek.myveripy.data_access.DA;
import com.berstek.myveripy.data_access.UserDA;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.presentor.auth.AuthPresentor;
import com.berstek.myveripy.presentor.auth.GoogleAuthPresentor;
import com.berstek.myveripy.utils.RequestCodes;
import com.berstek.myveripy.view.home.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity implements AuthCallback,
    AuthPresentor.OnUserAddedCallback {

  private ImageView googleImg;
  private GoogleAuthPresentor googleAuthPresentor;
  private AuthPresentor authPresentor;
  private FirebaseAuth auth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);

    googleImg = findViewById(R.id.googleImg);

    auth = FirebaseAuth.getInstance();
    googleAuthPresentor = new GoogleAuthPresentor(this, auth);
    googleAuthPresentor.setGoogleAuthCallback(this);

    authPresentor = new AuthPresentor();
    authPresentor.setOnUserAddedCallback(this);

    googleImg.setOnClickListener(googleAuthPresentor);

    getSupportActionBar().hide();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RequestCodes.SIGN_IN) {
      googleAuthPresentor.loginToFirebase(data);
    }
  }

  @Override
  public void onAuthSuccess(FirebaseUser user, GoogleSignInAccount account) {
    authPresentor.addUser(user,account);
  }

  @Override
  protected void onStart() {
    super.onStart();

    DA da = new DA();

    if (auth.getCurrentUser() != null) {
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
    }
  }

  @Override
  public void onUserAdded() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }
}
