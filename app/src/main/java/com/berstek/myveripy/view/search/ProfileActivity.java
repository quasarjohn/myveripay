package com.berstek.myveripy.view.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.utils.CustomImageUtils;
import com.berstek.myveripy.utils.Utils;
import com.berstek.myveripy.view.pay.pay_shipment.PayShipmentActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

  private User user;

  private TextView nameTxt, phoneTxt, addressTxt;
  private CircleImageView dp;
  private ImageView dpBlurred;

  private Button payBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    dp = findViewById(R.id.dp);
    dpBlurred = findViewById(R.id.dp_blurred);

    nameTxt = findViewById(R.id.nameTxt);
    phoneTxt = findViewById(R.id.addressTxt);
    phoneTxt = findViewById(R.id.addressTxt);

    user = (User) getIntent().getSerializableExtra("user");

    nameTxt.setText(user.getFirst_name() + " " + user.getLast_name());
    new Utils(this).loadImage(user.getPhoto_url(), dp);
    new CustomImageUtils().blurImage(this, user.getPhoto_url(), dpBlurred, true);

    payBtn = findViewById(R.id.payBtn);
    payBtn.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    Bundle bundle = new Bundle();
    bundle.putString("searchParam", user.getPay_id());
    Intent intent = new Intent(this, PayShipmentActivity.class);
    intent.putExtras(bundle);
    startActivity(intent);
  }
}
