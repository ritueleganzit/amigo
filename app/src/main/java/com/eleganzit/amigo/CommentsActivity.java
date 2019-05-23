package com.eleganzit.amigo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.adapter.EducationsAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityCommentsBinding;
import com.eleganzit.amigo.databinding.EducationsRowLayoutBinding;
import com.eleganzit.amigo.databinding.RowCommentBinding;
import com.eleganzit.amigo.model.AddCommentResponse;
import com.eleganzit.amigo.model.Comments;
import com.eleganzit.amigo.model.CommentsDetails;
import com.eleganzit.amigo.model.GetWorkEduResponse;
import com.eleganzit.amigo.model.ListPostCommentResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {

    UserLoggedInSession userLoggedInSession;

    ActivityCommentsBinding binding;
    String user_id,userid,postid;
    LinearLayoutManager layoutManager;
    List<CommentsDetails> list;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(CommentsActivity.this,R.layout.activity_comments);
        userLoggedInSession = new UserLoggedInSession(CommentsActivity.this);
progressDialog=new ProgressDialog(CommentsActivity.this);
progressDialog.setMessage("Please Wait");
progressDialog.setCancelable(false);
progressDialog.setCanceledOnTouchOutside(false);
        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);
        userid=getIntent().getStringExtra("userid");
        postid=getIntent().getStringExtra("postid");
        layoutManager=new LinearLayoutManager(CommentsActivity.this, LinearLayoutManager.VERTICAL,false);
        binding.commentslist.setLayoutManager(layoutManager);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.typeMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()==0){
                    binding.send.setImageDrawable(getResources().getDrawable(R.mipmap.inactive_comment));

                    binding.send.setEnabled(false);
                } else {
                    binding.send.setImageDrawable(getResources().getDrawable(R.mipmap.active_comment));

                    binding.send.setEnabled(true);                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //enableSubmitIfReady();
            }
        });
binding.send.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


binding.send.setEnabled(false);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.typeMessage.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
        saveComments();

       /* Comments comments=new Comments();
        comments.setComment(binding.typeMessage.getText().toString());
        comments.setCommentBy("18");
        list.add(comments);
        binding.commentslist.setAdapter(new CommentsAdapter(list));*/


    }
});

        getPostComments();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder>{
       List<CommentsDetails> list;

        public CommentsAdapter(List<CommentsDetails> list) {
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RowCommentBinding rowCommentBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_comment,parent,false);



            return new MyViewHolder(rowCommentBinding);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            CommentsDetails comments=list.get(position);
            //holder.rowCommentBinding.comment.setText();
            holder.rowCommentBinding.commentUsername.setText(Html.fromHtml("<b>" + comments.getFullname() + "</b> " +comments.getComment()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

            try {
                long time = sdf.parse(comments.getCreatedDate()).getTime();
                long now = System.currentTimeMillis();

                CharSequence ago =
                        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);

                if (ago.toString().equalsIgnoreCase("0 minutes ago"))
                {
                    holder.rowCommentBinding.commentTimePosted.setText("Just Now");
                }
                else
                {
                    holder.rowCommentBinding.commentTimePosted.setText(ago+"");

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(CommentsActivity.this).load(comments.getPhoto()).apply(RequestOptions.circleCropTransform()).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)).into(holder.rowCommentBinding.commentProfileImage);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            RowCommentBinding rowCommentBinding;

            public MyViewHolder(@NonNull  RowCommentBinding rowCommentBinding) {
                super(rowCommentBinding.getRoot());
                this.rowCommentBinding=rowCommentBinding  ;

            }
        }
    }

    public void enableSubmitIfReady() {

        boolean isReady = binding.typeMessage.getText().toString().length() > 0;
        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        if (isReady) {
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.send.setEnabled(false);
    }

    public void saveComments()
    {
        binding.progressbar.setVisibility(View.VISIBLE);

        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);


        Call<AddCommentResponse> addCommentResponseCall=myInterface.savePostComment(postid,userid,user_id,binding.typeMessage.getText().toString());
        addCommentResponseCall.enqueue(new Callback<AddCommentResponse>() {
            @Override
            public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
                binding.progressbar.setVisibility(View.GONE);
                binding.send.setEnabled(true);
                binding.typeMessage.setText("");
                if (response.isSuccessful())
                {

                    getPostComments();


                }
                else
                {

                }

            }

            @Override
            public void onFailure(Call<AddCommentResponse> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);

                binding.send.setEnabled(true);

            }
        });


    }

    private void getPostComments() {
        progressDialog.show();
        list=new ArrayList<>();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);


        Call<ListPostCommentResponse> addCommentResponseCall=myInterface.listPostComment(postid);
        addCommentResponseCall.enqueue(new Callback<ListPostCommentResponse>() {
            @Override
            public void onResponse(Call<ListPostCommentResponse> call, Response<ListPostCommentResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {

                    if (response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            CommentsDetails commentsDetails=response.body().getData().get(i);
                            list.add(commentsDetails);

                        }
                        binding.commentslist.setAdapter(new CommentsAdapter(list));
                        binding.commentslist.scrollToPosition(list.size() - 1);


                    }
                }
            }

            @Override
            public void onFailure(Call<ListPostCommentResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }


}
