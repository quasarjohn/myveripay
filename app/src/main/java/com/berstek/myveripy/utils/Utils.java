package com.berstek.myveripy.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

  private Context context;

  public Utils(Context context) {
    this.context = context;
  }

  public void loadImage(String url, ImageView img) {
    Glide.with(context).load(url).skipMemoryCache(true).into(img);
  }

  public void loadImage(String url, ImageView img, int size) {
    Glide.with(context).load(url).skipMemoryCache(true).override(size, size).into(img);
  }

  public static String getUid() {
    return FirebaseAuth.getInstance().getCurrentUser().getUid();
  }

  public static String getPesoSign() {
    return "₱";
  }

  public static String formatDf(double d) {
    DecimalFormat df = new DecimalFormat("0.00");

    String str = df.format(d);

    if (str.endsWith(".00")) {
      return str.replace(".00", "");
    } else {
      return str;
    }
  }

  public static String formatDateWithTime(long date) {
    SimpleDateFormat hour = new SimpleDateFormat("hh:mm a");
    SimpleDateFormat d = new SimpleDateFormat("MMM/dd/yyyy");

    return hour.format(new Date(date)) + " on " + d.format(new Date(date));
  }

  public static int[] getDateInt(Date date) {
    int[] dates = new int[3];

    SimpleDateFormat year = new SimpleDateFormat("yyyy");
    SimpleDateFormat month = new SimpleDateFormat("MM");
    SimpleDateFormat day = new SimpleDateFormat("dd");


    dates[0] = Integer.parseInt(year.format(date));
    dates[1] = Integer.parseInt(month.format(date));
    dates[2] = Integer.parseInt(day.format(date));

    return dates;
  }

}
