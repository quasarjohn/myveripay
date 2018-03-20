package com.berstek.myveripy.view.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.callback.RecviewItemClickCallback;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.utils.Utils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ListHolder> {

  private LayoutInflater inflater;
  private List<User> data;
  private Utils utils;

  private RecviewItemClickCallback itemClickCallback;

  public SearchResultAdapter(List<User> data, Context context) {
    this.data = data;
    inflater = LayoutInflater.from(context);
    utils = new Utils(context);
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.viewholder_search_result, parent, false));
  }

  @Override
  public void onBindViewHolder(ListHolder holder, int position) {
    User user = data.get(position);
    holder.title.setText(user.getFirst_name() + " " + user.getLast_name());
    utils.loadImage(user.getPhoto_url(), holder.dp);
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView title, subtitle;
    private CircleImageView dp;

    public ListHolder(View itemView) {
      super(itemView);

      title = itemView.findViewById(R.id.title);
      subtitle = itemView.findViewById(R.id.subtitle);

      dp = itemView.findViewById(R.id.dp);
      itemView.setOnClickListener(ListHolder.this);
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
