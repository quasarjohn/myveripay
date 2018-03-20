package com.berstek.myveripy.view.upload_image;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.berstek.myveripy.R;
import com.berstek.myveripy.utils.Utils;

import java.util.ArrayList;

public class PlainImageListAdapterHorizontal extends RecyclerView.Adapter<PlainImageListAdapterHorizontal.ListHolder> {

  private ArrayList<String> data;
  private LayoutInflater inflater;
  private Utils utils;

  public PlainImageListAdapterHorizontal(ArrayList<String> data, Context context) {
    this.data = data;
    inflater = LayoutInflater.from(context);
    utils = new Utils(context);
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.viewholder_plain_image_horizontal, parent, false));
  }

  @Override
  public void onBindViewHolder(ListHolder holder, int position) {
    String url = data.get(position);
    utils.loadImage(url, holder.img);
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  class ListHolder extends RecyclerView.ViewHolder {

    private ImageView img;

    public ListHolder(View itemView) {
      super(itemView);

      img = itemView.findViewById(R.id.img);
    }
  }
}
