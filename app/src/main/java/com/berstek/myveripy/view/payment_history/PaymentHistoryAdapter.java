package com.berstek.myveripy.view.payment_history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.data_access.DA;
import com.berstek.myveripy.data_access.UserDA;
import com.berstek.myveripy.model.Payment;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.utils.PartnersImg;
import com.berstek.myveripy.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ListHolder> {

  private ArrayList<Payment> data;
  private Context context;
  private LayoutInflater inflater;
  private UserDA userDA;

  public PaymentHistoryAdapter(ArrayList<Payment> data, Context context) {
    this.data = data;
    this.context = context;
    inflater = LayoutInflater.from(context);
    userDA = new UserDA();
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.viewholder_payment_history, parent, false));
  }

  @Override
  public void onBindViewHolder(final ListHolder holder, int position) {
    final Payment payment = data.get(position);

    //means the user is the sender. query the data of the recipient instead
    if (Utils.getUid().equals(payment.getFrom())) {
      userDA.queryUserByUidOnce(payment.getTo()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
          if (task.isSuccessful()) {
            if (task.getResult().exists()) {
              User user = task.getResult().toObject(User.class);
              updateUI(holder, user, payment, true);
            } else {
              updateUI(holder, null, payment, true);
            }
          }
        }
      });
    } else {
      userDA.queryUserByUidOnce(payment.getFrom()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
          if (task.isSuccessful()) {
            if (task.getResult().exists()) {
              User user = task.getResult().toObject(User.class);
              updateUI(holder, user, payment, false);
            } else {
              updateUI(holder, null, payment, false);
            }

          }
        }
      });
    }
  }

  private void updateUI(ListHolder holder, User user, Payment payment, boolean sender) {
    holder.amountTxt.setText(Utils.getPesoSign() + Utils.formatDf(payment.getAmount()));
    holder.dateTxt.setText(Utils.formatDateWithTime(payment.getDate()));

    if (user != null) {
      Glide.with(context).load(user.getPhoto_url()).skipMemoryCache(true).into(holder.dp);
      holder.nameTxt.setText(user.getFullName());
    } else {
      Glide.with(context).load(PartnersImg.LBC).skipMemoryCache(true).into(holder.dp);
      holder.nameTxt.setText("LBC Express - King of Shipment");
    }

    if (sender) {
      holder.amountTxt.setTextColor(context.getResources().getColor(R.color.redText));
    } else {
      holder.amountTxt.setTextColor(context.getResources().getColor(R.color.greenText));
    }
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  class ListHolder extends RecyclerView.ViewHolder {

    private ImageView dp;
    private TextView nameTxt, dateTxt, amountTxt;

    public ListHolder(View itemView) {
      super(itemView);

      dp = itemView.findViewById(R.id.dp);
      nameTxt = itemView.findViewById(R.id.nameTxt);
      dateTxt = itemView.findViewById(R.id.dateTxt);
      amountTxt = itemView.findViewById(R.id.amountTxt);
    }
  }
}
