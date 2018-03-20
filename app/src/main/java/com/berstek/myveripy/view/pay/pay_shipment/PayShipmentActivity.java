package com.berstek.myveripy.view.pay.pay_shipment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.model.PayTransaction;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.presentor.pay_shipment.ConfirmationFragment;
import com.berstek.myveripy.presentor.pay_shipment.PayShipmentPresentor;
import com.berstek.myveripy.presentor.pay_shipment.PayShipmentPresentor.PayShipmentPresentorCallback;
import com.berstek.myveripy.utils.Utils;
import com.berstek.myveripy.view.upload_image.PlainImageListAdapter;
import com.berstek.myveripy.view.upload_image.UploadImageFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class PayShipmentActivity extends AppCompatActivity implements
    PaymentRecipientFragment.PaymentRecipientFragmentCallback, View.OnClickListener,
    UploadImageFragment.ImageUploaderCallback, PayShipmentPresentorCallback {

  private PaymentRecipientFragment paymentRecipientFragment;
  private DateSelectionFragment dateSelectionFragment;
  private UploadImageFragment uploadImageFragment;

  private RecyclerView imagesRecview;

  private PayTransaction transaction = new PayTransaction();

  private String searchParam;

  private TextView actionTxt;
  private EditText titleEdit, detailsEdit, priceEdit;

  private ArrayList<String> imgsUrlList = new ArrayList<>();
  private PlainImageListAdapter plainImageListAdapter;

  private PayShipmentPresentor payShipmentPresentor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pay_shipment);

    searchParam = getIntent().getExtras().getString("searchParam");

    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

    payShipmentPresentor = new PayShipmentPresentor();
    payShipmentPresentor.setPayShipmentPresentorCallback(this);

    actionTxt = findViewById(R.id.actionTxt);
    titleEdit = findViewById(R.id.titleEdit);
    detailsEdit = findViewById(R.id.detailsEdit);
    priceEdit = findViewById(R.id.priceEdit);
    imagesRecview = findViewById(R.id.imagesRecview);
    imagesRecview.setLayoutManager(new LinearLayoutManager(this));

    plainImageListAdapter = new PlainImageListAdapter(imgsUrlList, this);
    imagesRecview.setAdapter(plainImageListAdapter);

    actionTxt.setOnClickListener(this);

    Bundle bundle = new Bundle();
    bundle.putString("searchParam", searchParam);
    paymentRecipientFragment = new PaymentRecipientFragment();
    paymentRecipientFragment.setArguments(bundle);
    paymentRecipientFragment.setPaymentRecipientFragmentCallback(this);

    getSupportFragmentManager().beginTransaction().replace(R.id.fragment1, paymentRecipientFragment).commit();

    dateSelectionFragment = new DateSelectionFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment2, dateSelectionFragment).commit();

    uploadImageFragment = new UploadImageFragment();
    uploadImageFragment.setImageUploaderCallback(this);
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment3, uploadImageFragment).commit();
  }

  @Override
  public void onUserQueried(User user) {
    transaction.setSender_uid(Utils.getUid());
    transaction.setRecipient_uid(user.getKey());
  }

  @Override
  public void onClick(View view) {
    transaction.setTitle(titleEdit.getText().toString());
    transaction.setPrice(Double.parseDouble(priceEdit.getText().toString()));
    transaction.setDetails(detailsEdit.getText().toString());
    transaction.setDate_created(System.currentTimeMillis());
    //TODO hardcoded due date in 2 days
    transaction.setDue_date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 2));

    transaction.setTransaction_id("TRN-" + UUID.randomUUID().toString().substring(0, 7).toUpperCase());
    transaction.setStatus(0);

    int[] dates = Utils.getDateInt(new Date());

    transaction.setYear(dates[0]);
    transaction.setMonth(dates[1]);
    transaction.setDay(dates[2]);

    payShipmentPresentor.pushTransaction(transaction);
  }

  @Override
  public void onImageUploaded(String url) {
    imgsUrlList.add(url);
    plainImageListAdapter.notifyItemInserted(imgsUrlList.size() - 1);
    transaction.setImgs_url(imgsUrlList);
  }

  @Override
  public void onUpdate() {
    //TODO, notify user about upload percentage
  }

  @Override
  public void onTransactionPushed() {
    ConfirmationFragment confirmationFragment = new ConfirmationFragment();
    confirmationFragment.show(getFragmentManager(), null);
  }
}
