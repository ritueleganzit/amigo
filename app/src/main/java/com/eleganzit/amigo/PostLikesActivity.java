package com.eleganzit.amigo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.model.CommentsDetails;
import com.eleganzit.amigo.model.PostLikes;
import com.eleganzit.amigo.model.PostLikesResponse;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.eleganzit.amigo.utils.TextViewRobotoRegular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostLikesActivity extends AppCompatActivity {
    RecyclerView rc_postlist;
    UserLoggedInSession userLoggedInSession;
    String user_id,post_id;
    LinearLayoutManager layoutManager;
    List<PostLikes> list;
    ImageView back;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_likes);
        progressbar=findViewById(R.id.progressbar);
        userLoggedInSession = new UserLoggedInSession(PostLikesActivity.this);
        rc_postlist=findViewById(R.id.rc_postlist);
        back=findViewById(R.id.back);
        HashMap<String, String> map = userLoggedInSession.getUserDetails();
        user_id = map.get(UserLoggedInSession.USER_ID);
        post_id=getIntent().getStringExtra("postid");
        //Toast.makeText(this, ""+post_id, Toast.LENGTH_SHORT).show();
        layoutManager=new LinearLayoutManager(PostLikesActivity.this, LinearLayoutManager.VERTICAL,false);

        rc_postlist.setLayoutManager(layoutManager);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        getPostLikes();


    }

    private void getPostLikes() {
        progressbar.setVisibility(View.VISIBLE);
        list=new ArrayList<>();
        RetrofitInterface myInterface= RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<PostLikesResponse> call=myInterface.getPostLike(post_id);
        call.enqueue(new Callback<PostLikesResponse>() {
            @Override
            public void onResponse(Call<PostLikesResponse> call, Response<PostLikesResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    if (response.body().getData()!=null)
                    {
                        list=response.body().getData();

                        rc_postlist.setAdapter(new PostLikesAdapter(list));
                    }
                }
                else
                {

                }

            }

            @Override
            public void onFailure(Call<PostLikesResponse> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
            }
        });


    }
    public class PostLikesAdapter extends RecyclerView.Adapter<PostLikesAdapter.MyViewHolder>
    {

        List<PostLikes> list;

        public PostLikesAdapter(List<PostLikes> list) {
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_likes, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            PostLikes postLikes=list.get(position);

            Log.d("POSTTT","sss"+postLikes.getFullname());

            if (postLikes.getFullname()!=null && !(postLikes.getFullname().isEmpty()))
            {
                String upperString = postLikes.getFullname().substring(0,1).toUpperCase() + postLikes.getFullname().substring(1);
                holder.like_user_fullname.setText(upperString);

            }
            holder.like_user_name.setText(postLikes.getUsername());
            Glide.with(PostLikesActivity.this).load(postLikes.getPhoto()).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .apply(RequestOptions.circleCropTransform()).into(holder.like_profile_image);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            ImageView like_profile_image;
            TextViewRobotoRegular like_user_fullname,like_user_name;

            public MyViewHolder(View itemView) {
                super(itemView);
                like_profile_image=itemView.findViewById(R.id.like_profile_image);
                like_user_fullname=itemView.findViewById(R.id.like_user_fullname);
                like_user_name=itemView.findViewById(R.id.like_user_name);

            }
        }
    }
}
