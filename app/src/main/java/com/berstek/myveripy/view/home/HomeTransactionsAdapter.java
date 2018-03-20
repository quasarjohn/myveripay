package com.berstek.myveripy.view.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.callback.RecviewItemClickCallback;
import com.berstek.myveripy.data_access.UserDA;
import com.berstek.myveripy.model.PayTransaction;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class HomeTransactionsAdapter extends RecyclerView.Adapter<HomeTransactionsAdapter.ListHolder> {

  private List<PayTransaction> data;
  private LayoutInflater inflater;
  private Context context;


  private UserDA userDA;

  private Utils utils;

  private RecviewItemClickCallback itemClickCallback;

  public HomeTransactionsAdapter(List<PayTransaction> data, Context context) {

    this.data = data;
    this.context = context;
    inflater = LayoutInflater.from(context);
    userDA = new UserDA();
    utils = new Utils(context);
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.viewholder_transaction_list, parent, false));
  }

  @Override
  public void onBindViewHolder(final ListHolder holder, int position) {
    final PayTransaction transaction = data.get(position);
    holder.titleTxt.setText(transaction.getTitle());

    switch (transaction.getStatus()) {
      case 0:
        holder.statusTxt.setText("Pending");
        break;
      case 1:
        holder.statusTxt.setText("Waiting Shipment");
        break;
      case 2:
        holder.statusTxt.setText("Rejected");
      case 3:
        holder.statusTxt.setText("Completed");
        break;
      default:
        break;
    }

    String uid;
    if (Utils.getUid().equals(transaction.getSender_uid())) {
      uid = transaction.getRecipient_uid();
      holder.typeTxt.setText("Sent Payment for ");
      holder.flowTxt.setText("To ");
      //set color to red
      holder.priceTxt.setTextColor(context.getResources().getColor(R.color.redText));
    } else {
      uid = transaction.getSender_uid();
      holder.typeTxt.setText("Received Payment for ");
      holder.flowTxt.setText("From ");
      holder.priceTxt.setTextColor(context.getResources().getColor(R.color.greenText));

    }

    userDA.queryUserByUidOnce(uid).
        addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            User u = task.getResult().toObject(User.class);
            utils.loadImage(u.getPhoto_url(), holder.dp, 100);
            holder.nameTxt.setText(String.format("%s %s", u.getFirst_name(), u.getLast_name()));
          }
        });

    holder.priceTxt.setText(String.format("%s %s", Utils.getPesoSign(),
        Utils.formatDf(transaction.getPrice())));
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView priceTxt, nameTxt, dateTxt, statusTxt, titleTxt, typeTxt, flowTxt;
    private ImageView dp;

    public ListHolder(View itemView) {
      super(itemView);

      typeTxt = itemView.findViewById(R.id.typeTxt);
      flowTxt = itemView.findViewById(R.id.flowTxt);
      priceTxt = itemView.findViewById(R.id.priceTxt);
      nameTxt = itemView.findViewById(R.id.nameTxt);
      dateTxt = itemView.findViewById(R.id.dateTxt);
      statusTxt = itemView.findViewById(R.id.statusTxt);
      titleTxt = itemView.findViewById(R.id.titleTxt);
      dp = itemView.findViewById(R.id.dp);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      itemClickCallback.onRecviewItemClick(view, getAdapterPosition());
    }
  }

  public void setItemClickCallback(RecviewItemClickCallback itemClickCallback) {
    this.itemClickCallback = itemClickCallback;
  }
}
