package com.berstek.myveripy.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.callback.AuthCallback;
import com.berstek.myveripy.data_access.TransactionDA;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.presentor.auth.GoogleAuthPresentor;
import com.berstek.myveripy.presentor.home.MainActivityPresentor;
import com.berstek.myveripy.utils.CustomImageUtils;
import com.berstek.myveripy.utils.Utils;
import com.berstek.myveripy.view.auth.AuthActivity;
import com.berstek.myveripy.view.search.SearchUserFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener, MainActivityPresentor.MainActivityPresentorCallback, AuthCallback {

  private ImageView searchBtn;
  private MainActivityPresentor mainActivityPresentor;

  private ConstraintLayout navHeader;

  private CircleImageView dp;
  private TextView nameTxt, phoneTxt, addressTxt, payIdTxt;
  private ImageView dpBlurred;

  private CustomImageUtils customImageUtils;
  private GoogleAuthPresentor authPresentor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FirebaseApp.initializeApp(this);

    authPresentor = new GoogleAuthPresentor(this, FirebaseAuth.getInstance());
    authPresentor.setGoogleAuthCallback(this);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    searchBtn = findViewById(R.id.searchBtn);
    searchBtn.setOnClickListener(this);

    getSupportFragmentManager().beginTransaction().
        replace(R.id.main_container, new ViewPagerFragment()).commit();

    navHeader = (ConstraintLayout) navigationView.getHeaderView(0);

    nameTxt = navHeader.findViewById(R.id.nameTxt);
    phoneTxt = navHeader.findViewById(R.id.phoneTxt);
    addressTxt = navHeader.findViewById(R.id.addressTxt);
    payIdTxt = navHeader.findViewById(R.id.payIdTxt);
    dp = navHeader.findViewById(R.id.dp);
    dpBlurred = navHeader.findViewById(R.id.dpBlurred);


    customImageUtils = new CustomImageUtils();

    mainActivityPresentor = new MainActivityPresentor(this);
    mainActivityPresentor.setMainActivityPresentorCallback(this);
    mainActivityPresentor.init();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_logout) {
      authPresentor.signOut();
    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void onClick(View view) {
    SearchUserFragment searchUserFragment = new SearchUserFragment();
    searchUserFragment.show(getFragmentManager(), null);
  }

  @Override
  public void onUserDataLoaded(User user) {
    nameTxt.setText(user.getFirst_name() + " " + user.getLast_name());
    payIdTxt.setText(user.getPay_id());
    new Utils(this).loadImage(user.getPhoto_url(), dp);
    customImageUtils.blurImage(this, user.getPhoto_url(), dpBlurred, false);
  }

  @Override
  public void onAuthSuccess(FirebaseUser user, GoogleSignInAccount account) {

  }

  @Override
  public void onSignOutSuccess() {
    Intent intent = new Intent(this, AuthActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);

  }

  @Override
  protected void onDestroy() {

    super.onDestroy();
  }
}
