package com.berstek.myveripy.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class PayTransaction implements Serializable {

  private String recipient_uid, sender_uid;
  private long date_created, due_date, date_completed;

  private int year, month, day;


  /*
  0 - waiting acceptance
  1 - accepted
  2 - rejected
  3 - completed
   */
  private int status;

  private String transaction_id;

  private String title, details;
  private double price;

  @Exclude
  private String key;

  private ArrayList<String> imgs_url;

  public String getRecipient_uid() {
    return recipient_uid;
  }

  public void setRecipient_uid(String recipient_uid) {
    this.recipient_uid = recipient_uid;
  }

  public String getSender_uid() {
    return sender_uid;
  }

  public void setSender_uid(String sender_uid) {
    this.sender_uid = sender_uid;
  }

  public long getDate_created() {
    return date_created;
  }

  public void setDate_created(long date_created) {
    this.date_created = date_created;
  }

  public long getDue_date() {
    return due_date;
  }

  public void setDue_date(long due_date) {
    this.due_date = due_date;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public ArrayList<String> getImgs_url() {
    return imgs_url;
  }

  public void setImgs_url(ArrayList<String> imgs_url) {
    this.imgs_url = imgs_url;
  }

  public String getTransaction_id() {
    return transaction_id;
  }

  public void setTransaction_id(String transaction_id) {
    this.transaction_id = transaction_id;
  }

  public void setDate_completed(long date_completed) {
    this.date_completed = date_completed;
  }


  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public long getDate_completed() {
    return date_completed;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }
}
