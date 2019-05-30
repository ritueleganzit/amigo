package com.eleganzit.amigo;

import android.content.Intent;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.amigo.adapter.SearchAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.SearchData;
import com.eleganzit.amigo.model.SearchDataResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.eleganzit.amigo.utils.LinearLayoutManagerWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.SearchAdapterListener {

    RecyclerView rc_search;
    ArrayList<SearchData> ar_search =new ArrayList<>();
    SearchAdapter searchAdapter;
    ImageView chat;
    EditText ed_search;
    String userInput;
    UserLoggedInSession userSessionManager;
    HashMap<String, String> userData;
    List<SearchData> searchList;
    ImageView search_img;
    ProgressBar search_progress;
    private Timer timer;

    TextView no_results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        userSessionManager=new UserLoggedInSession(this);
        userData=userSessionManager.getUserDetails();

        rc_search=findViewById(R.id.rc_search);

        no_results=findViewById(R.id.no_results);
        chat=findViewById(R.id.chat);
        ed_search=findViewById(R.id.ed_search);
        search_img=findViewById(R.id.search_img);
        search_progress=findViewById(R.id.search_progress);

        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManagerWrapper(this,LinearLayoutManager.VERTICAL,false);

        rc_search.setLayoutManager(layoutManager1);
        rc_search.setItemAnimator(new DefaultItemAnimator());

        searchList = new ArrayList<>();

        searchAdapter = new SearchAdapter(searchList, SearchActivity.this,this);
        rc_search.setAdapter(searchAdapter);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SearchActivity.this,MessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, int i1, int i2) {

                // user is typing: reset already started timer (if existing)
                if (timer != null) {
                    timer.cancel();
                }

                userInput=charSequence.toString();
/*
                if(!userInput.isEmpty())
                {
                    rc_search.setVisibility(View.VISIBLE);
                    search_img.setVisibility(View.GONE);
                    search_progress.setVisibility(View.VISIBLE);
                    MyInterface myInterface = RetrofitApiClient.getRetrofit().create(MyInterface.class);
                    Call<SearchDataResponse> call=myInterface.searchAll(userData.get(UserSessionManager.KEY_USER_ID),ed_search.getText().toString(),"0");
                    call.enqueue(new Callback<SearchDataResponse>() {
                        @Override
                        public void onResponse(Call<SearchDataResponse> call, final Response<SearchDataResponse> response) {
                            search_progress.setVisibility(View.GONE);
                            if(response.isSuccessful()) {

                                //Toast.makeText(SearchActivity.this, ""+response.body().getStatus().toString(), Toast.LENGTH_SHORT).show();

                                if(response.body()!=null)
                                {
                                    if(searchList.size()>0)
                                    {
                                        searchList.clear();
                                    }
                                    if(response.body().getData()!=null)
                                    {
                                        for(int i=0; i<response.body().getData().size();i++)
                                        {
                                            Log.d("responseseeee "+i,""+response.body().getData().get(i).getFullname());
                                            SearchData searchData=new SearchData(response.body().getData().get(i).getPhoto(),response.body().getData().get(i).getFullname(),response.body().getData().get(i).getUsername(),response.body().getData().get(i).getEmailId(),response.body().getData().get(i).getCity(),response.body().getData().get(i).getHometown(),response.body().getData().get(i).getState());
                                            searchList.add(searchData);

                                        }
                                    }
                                    if (searchList.size() > 0) {
                                        Log.d("whereeeeeeeeee", "      "+searchList.size());

                                        //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"    if switch open suggestion", Toast.LENGTH_SHORT).show();
                                        searchAdapter = new SearchAdapter(searchList,SearchActivity.this, new SearchAdapter.SearchAdapterListener() {
                                            @Override
                                            public void onSearchSelected(SearchData searchData) {
                                                ed_search.setText("");
                                                ed_search.append(searchData.getFullname()+" ");

                                            }
                                        });

                                        if(!userInput.isEmpty())
                                        {

                                            Log.d("wwwwwwwwwwwwww",userInput+"");
                                            rc_search.getRecycledViewPool().clear();
                                            rc_search.setAdapter(searchAdapter);
                                            searchAdapter.getFilter().filter(userInput);
                                            searchAdapter.notifyDataSetChanged();
                                        }

                                    } else {
                                        Log.d("whereeeeeeeeee", "      switch case '@' in else");

                                    }

                                }

                            }
                            else
                            {
                                Log.d("whereeeeeeeeee", "   "+response.errorBody());

                            }
                        }

                        @Override
                        public void onFailure(Call<SearchDataResponse> call, Throwable t) {
                            Toast.makeText(SearchActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                            search_progress.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {
                    search_progress.setVisibility(View.GONE);
                    search_img.setVisibility(View.VISIBLE);
                    searchList.clear();
                    searchAdapter = new SearchAdapter(searchList,SearchActivity.this, new SearchAdapter.SearchAdapterListener() {
                        @Override
                        public void onSearchSelected(SearchData searchData) {
                            ed_search.setText("");
                            ed_search.append(searchData.getFullname()+" ");

                        }
                    });
                    rc_search.getRecycledViewPool().clear();
                    rc_search.setAdapter(searchAdapter);
                    searchAdapter.getFilter().filter(userInput);
                    searchAdapter.notifyDataSetChanged();
                    rc_search.setVisibility(View.GONE);
                }*/
                //Toast.makeText(SearchActivity.this, "onTextChanged  "+ed_search.getText().toString().isEmpty(), Toast.LENGTH_SHORT).show();
                Log.d("whrrrrrr","onTextChanged  "+ed_search.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(final Editable editable) {


                // user typed: start the timer
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        SearchActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // do your actual work here
                                userInput=editable.toString();

                                if(!userInput.isEmpty())
                                {
                                    rc_search.setVisibility(View.VISIBLE);
                                    search_img.setVisibility(View.GONE);
                                    search_progress.setVisibility(View.VISIBLE);
                                    no_results.setVisibility(View.GONE);
                                    RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
                                    Call<SearchDataResponse> call=myInterface.searchAll(userData.get(UserLoggedInSession.USER_ID),ed_search.getText().toString(),"0");
                                    call.enqueue(new Callback<SearchDataResponse>() {
                                        @Override
                                        public void onResponse(Call<SearchDataResponse> call, final Response<SearchDataResponse> response) {
                                            search_progress.setVisibility(View.GONE);

                                            if(response.isSuccessful()) {
                                                Log.d("whereeeeeeeeee", "     onresponse ");

                                                //Toast.makeText(SearchActivity.this, ""+response.body().getStatus().toString(), Toast.LENGTH_SHORT).show();

                                                if(response.body()!=null)
                                                {
                                                    Log.d("whereeeeeeeeee", "   response is not null   ");

                                                    if(searchList.size()>0)
                                                    {
                                                        Log.d("whereeeeeeeeee", "   list size was greater than 0   ");

                                                        searchList.clear();
                                                    }
                                                    if(response.body().getData()!=null)
                                                    {
                                                        Log.d("whereeeeeeeeee", "   response body data list is not null   ");
                                                        no_results.setVisibility(View.GONE);
                                                        if(response.body().getStatus().toString().equalsIgnoreCase("1"))
                                                        {
                                                            for(int i=0; i<response.body().getData().size();i++)
                                                            {
                                                                Log.d("responseseeee "+i,""+response.body().getData().get(i).getFullname());
                                                                SearchData searchData=new SearchData(response.body().getData().get(i).getUserId(),response.body().getData().get(i).getPhoto(),response.body().getData().get(i).getFullname(),response.body().getData().get(i).getUsername(),response.body().getData().get(i).getEmailId(),response.body().getData().get(i).getCity(),response.body().getData().get(i).getHometown(),response.body().getData().get(i).getState());
                                                                searchList.add(searchData);
                                                                Log.d("whereeeeeeeeee", "      "+searchList.size());

                                                            }
                                                        }
                                                        else
                                                        {
                                                            no_results.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                    else
                                                    {
                                                        no_results.setVisibility(View.VISIBLE);
                                                        //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"    if switch open suggestion", Toast.LENGTH_SHORT).show();
                                                        searchAdapter = new SearchAdapter(searchList,SearchActivity.this, new SearchAdapter.SearchAdapterListener() {
                                                            @Override
                                                            public void onSearchSelected(SearchData searchData) {
                                                                ed_search.setText("");
                                                                ed_search.append(searchData.getFullname()+"");
                                                                startActivity(new Intent(SearchActivity.this,SearchResultsActivity.class).putExtra("search",ed_search.getText().toString()));
                                                                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                                                            }
                                                        });

                                                        if(!userInput.isEmpty())
                                                        {

                                                            Log.d("wwwwwwwwwwwwww",userInput+"");
                                                            rc_search.getRecycledViewPool().clear();
                                                            rc_search.setAdapter(searchAdapter);
                                                            searchAdapter.getFilter().filter(userInput);
                                                            searchAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                    if (searchList.size() > 0) {
                                                        Log.d("whereeeeeeeeee", "      "+searchList.size());

                                                        //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"    if switch open suggestion", Toast.LENGTH_SHORT).show();
                                                        searchAdapter = new SearchAdapter(searchList,SearchActivity.this, new SearchAdapter.SearchAdapterListener() {
                                                            @Override
                                                            public void onSearchSelected(SearchData searchData) {
                                                                ed_search.setText("");
                                                                ed_search.append(searchData.getFullname()+"");
                                                                startActivity(new Intent(SearchActivity.this,SearchResultsActivity.class).putExtra("search",ed_search.getText().toString()));
                                                                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                                                            }
                                                        });

                                                        if(!userInput.isEmpty())
                                                        {

                                                            Log.d("wwwwwwwwwwwwww",userInput+"");
                                                            rc_search.getRecycledViewPool().clear();
                                                            rc_search.setAdapter(searchAdapter);
                                                            searchAdapter.getFilter().filter(userInput);
                                                            searchAdapter.notifyDataSetChanged();
                                                        }

                                                    } else {
                                                        Log.d("whereeeeeeeeee", "      switch case '@' in else");

                                                    }

                                                }

                                            }
                                            else
                                            {
                                                Log.d("whereeeeeeeeee", "   "+response.errorBody());
                                                Toast.makeText(SearchActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SearchDataResponse> call, Throwable t) {
                                            Toast.makeText(SearchActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                                            search_progress.setVisibility(View.GONE);
                                        }
                                    });

                                }
                                else
                                {
                                    Toast.makeText(SearchActivity.this, "empty", Toast.LENGTH_SHORT).show();
                                    search_progress.setVisibility(View.GONE);
                                    search_img.setVisibility(View.VISIBLE);
                                    searchList.clear();
                                    searchAdapter = new SearchAdapter(searchList,SearchActivity.this, new SearchAdapter.SearchAdapterListener() {
                                        @Override
                                        public void onSearchSelected(SearchData searchData) {

                                        }
                                    });
                                    rc_search.getRecycledViewPool().clear();
                                    rc_search.setAdapter(searchAdapter);
                                    searchAdapter.getFilter().filter(userInput);
                                    searchAdapter.notifyDataSetChanged();
                                    rc_search.setVisibility(View.GONE);
                                }
                            }
                        });


                    }
                }, 600); // 600ms delay before the timer executes the „run“ method from TimerTask

            }
        });


        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
    }

    private void performSearch() {
        if(!ed_search.getText().toString().isEmpty())
        {
            startActivity(new Intent(SearchActivity.this,SearchResultsActivity.class).putExtra("search",ed_search.getText().toString()));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    @Override
    public void onSearchSelected(SearchData searchData) {

        ed_search.setText(searchData.getFullname());

    }
}