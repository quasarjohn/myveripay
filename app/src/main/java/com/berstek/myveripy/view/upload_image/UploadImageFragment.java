package com.berstek.myveripy.view.upload_image;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.berstek.myveripy.R;
import com.berstek.myveripy.data_access.DA;
import com.berstek.myveripy.data_access.ImageUploader;
import com.berstek.myveripy.utils.RequestCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageFragment extends Fragment implements View.OnClickListener {

  private StorageReference storageRef = FirebaseStorage.getInstance().getReference();


  private View view;
  private Button uploadBtn;

  private ImageUploader imageUploader;

  private ImageUploaderCallback imageUploaderCallback;

  public UploadImageFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_upload_image, container, false);

    imageUploader = new ImageUploader();

    uploadBtn = view.findViewById(R.id.uploadBtn);
    uploadBtn.setOnClickListener(this);

    return view;
  }

  @Override
  public void onClick(View view) {
    openFileChooser();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RequestCodes.PICK_FILE) {
      new DA().log2("UPLOADING");
      uploadImage(data, UUID.randomUUID().toString(), getActivity());
    }
  }

  public void uploadImage(Intent data, String title, Activity activity) {
    StorageReference menuRef = storageRef.child("img_products/" + title + ".jpg");

    UploadTask uploadTask = null;

    ContentResolver cr = activity.getContentResolver();
    try {
      InputStream is = cr.openInputStream(data.getData());
      uploadTask = menuRef.putStream(is);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
      @Override
      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        Uri downloadUri = taskSnapshot.getDownloadUrl();
        imageUploaderCallback.onImageUploaded(downloadUri.toString());
        new DA().log2(downloadUri.toString());

      }
    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
      @Override
      public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {

      }
    });
  }


  private void openFileChooser() {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    Intent chooserIntent = Intent.createChooser(intent, "Select File");
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
    startActivityForResult(chooserIntent, RequestCodes.PICK_FILE);
  }

  public interface ImageUploaderCallback {
    void onImageUploaded(String url);

    void onUpdate();
  }

  public void setImageUploaderCallback(ImageUploaderCallback imageUploaderCallback) {
    this.imageUploaderCallback = imageUploaderCallback;
  }
}
