package com.berstek.myveripy.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class User implements Serializable {

  private String email, last_name, first_name, photo_url;
  private long phone_number;
  private long date_joined;

  private String pay_id;

  private Address address;

  private String key;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getPhoto_url() {
    return photo_url;
  }

  public void setPhoto_url(String photo_url) {
    this.photo_url = photo_url;
  }

  public long getPhone_number() {
    return phone_number;
  }

  public void setPhone_number(long phone_number) {
    this.phone_number = phone_number;
  }

  public long getDate_joined() {
    return date_joined;
  }

  public void setDate_joined(long date_joined) {
    this.date_joined = date_joined;
  }

  public String getPay_id() {
    return pay_id;
  }

  public void setPay_id(String pay_id) {
    this.pay_id = pay_id;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getFullName() {
    return first_name + " " + last_name;
  }
}
