package com.berstek.myveripy.view.search;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.berstek.myveripy.R;
import com.berstek.myveripy.callback.RecviewItemClickCallback;
import com.berstek.myveripy.custom_classes.CustomDialogFragment;
import com.berstek.myveripy.data_access.DA;
import com.berstek.myveripy.model.User;
import com.berstek.myveripy.presentor.search.SearchUserPresentor;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchUserFragment extends CustomDialogFragment implements TextWatcher,
    SearchUserPresentor.SearchUserPresentorCallback, RecviewItemClickCallback {

  private EditText searchEdit;
  private long delay = 1000, lastEdited = 0;
  private Handler handler = new Handler();

  private RecyclerView resultsRecview;
  private SearchResultAdapter searchResultAdapter;

  private List<User> users;

  private SearchUserPresentor searchUserPresentor;


  public SearchUserFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_search_user, container, false);

    searchUserPresentor = new SearchUserPresentor();

    searchEdit = view.findViewById(R.id.searchEdit);
    searchEdit.addTextChangedListener(this);

    resultsRecview = view.findViewById(R.id.resultsRecview);
    resultsRecview.setLayoutManager(new LinearLayoutManager(getActivity()));

    searchUserPresentor.setSearchUserPresentorCallback(this);

    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    handler.removeCallbacks(inputListener);
  }

  @Override
  public void afterTextChanged(Editable editable) {
    handler.postDelayed(inputListener, delay);
  }

  private Runnable inputListener = new Runnable() {
    @Override
    public void run() {
      if (System.currentTimeMillis() > (lastEdited + delay - 500)) {
        searchUserPresentor.queryUser(searchEdit.getText().toString());
      }
    }
  };

  @Override
  public void onUserQueried(List<User> users) {
    this.users = users;
    searchResultAdapter = new SearchResultAdapter(users, getActivity());
    searchResultAdapter.setItemClickCallback(this);
    resultsRecview.setAdapter(searchResultAdapter);
  }

  @Override
  public void onRecviewItemClick(View view, int position) {
    Bundle bundle = new Bundle();
    bundle.putSerializable("user", users.get(position));

    Intent intent = new Intent(getActivity(), ProfileActivity.class);
    intent.putExtras(bundle);
    startActivity(intent);
  }
}
