package com.eleganzit.volunteerifyngo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.adapter.GroupAddedUsersAdapter;
import com.eleganzit.volunteerifyngo.adapter.NewGroupUsersAdapter;
import com.eleganzit.volunteerifyngo.model.GUsersdata;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddNewGroupActivity extends AppCompatActivity {

    RecyclerView rc_gusers,rc_added_users;
    ArrayList<GUsersdata> ar_users=new ArrayList<>();
    ArrayList<GUsersdata> ar_addedusers=new ArrayList<>();
    FloatingActionButton next;
    RelativeLayout title_layout;
    Animation pop_in,pop_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_group);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pop_in = AnimationUtils.loadAnimation(this, R.anim.pop_in);
        pop_out = AnimationUtils.loadAnimation(this, R.anim.pop_out);

        next=findViewById(R.id.next);
        rc_gusers=findViewById(R.id.rc_gusers);
        rc_added_users=findViewById(R.id.rc_added_gusers);
        title_layout=findViewById(R.id.title_layout);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_gusers.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rc_added_users.setLayoutManager(layoutManager2);

        GUsersdata gUsersdata1=new GUsersdata("1","","Zahir","","","false");
        GUsersdata gUsersdata2=new GUsersdata("2","","Ahmed","","","false");
        GUsersdata gUsersdata3=new GUsersdata("3","","Mala","","","false");
        GUsersdata gUsersdata4=new GUsersdata("4","","Ritu","","","false");
        GUsersdata gUsersdata5=new GUsersdata("5","","Nambath","","","false");
        GUsersdata gUsersdata6=new GUsersdata("6","","Salman","","","false");

        ar_users.add(gUsersdata1);
        ar_users.add(gUsersdata2);
        ar_users.add(gUsersdata3);
        ar_users.add(gUsersdata4);
        ar_users.add(gUsersdata5);
        ar_users.add(gUsersdata6);

        if(ar_addedusers.size()>0)
        {
            title_layout.setVisibility(View.GONE);
        }

        rc_gusers.setAdapter(new NewGroupUsersAdapter(ar_users,this));

        rc_added_users.setAdapter(new GroupAddedUsersAdapter(ar_addedusers,AddNewGroupActivity.this));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNewGroupActivity.this,NewGroupActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }


    public class NewGroupUsersAdapter extends RecyclerView.Adapter<NewGroupUsersAdapter.MyViewHolder>
    {

        ArrayList<GUsersdata> users;
        Context context;
        Activity activity;
        boolean liked=false;

        public NewGroupUsersAdapter(ArrayList<GUsersdata> users, Context context) {
            this.users = users;
            this.context = context;
            activity = (Activity) context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_group_user_layout,viewGroup,false);
            MyViewHolder myViewHolder=new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

            final GUsersdata gUsersdata=users.get(i);

            holder.name.setText(gUsersdata.getUsername());

            holder.add_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.mark.setVisibility(View.VISIBLE);
                }
            });

            final String[] isAdded = {gUsersdata.getIsAdded()};

            for(int j=0;j<ar_addedusers.size();j++)
            {
                if(ar_addedusers.contains(gUsersdata))
                {
                    isAdded[0] ="true";
                    holder.mark.setVisibility(View.VISIBLE);
                }
                else
                {
                    isAdded[0] ="false";
                    holder.mark.setVisibility(View.GONE);
                }
            }
            holder.add_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isAdded[0].equalsIgnoreCase("true"))
                    {
                        isAdded[0] ="false";
                        holder.mark.setVisibility(View.GONE);
                        ar_addedusers.remove(gUsersdata);

                        rc_added_users.setAdapter(new GroupAddedUsersAdapter(ar_addedusers,AddNewGroupActivity.this));

                        new GroupAddedUsersAdapter(ar_addedusers,AddNewGroupActivity.this).notifyItemRemoved(i);
                        if(ar_addedusers.size()>0)
                        {
                            title_layout.setVisibility(View.GONE);
                        }
                        else
                        {
                            title_layout.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        isAdded[0] ="true";
                        holder.mark.setVisibility(View.VISIBLE);
                        ar_addedusers.add(gUsersdata);

                        rc_added_users.setAdapter(new GroupAddedUsersAdapter(ar_addedusers,AddNewGroupActivity.this));

                        new GroupAddedUsersAdapter(ar_addedusers,AddNewGroupActivity.this).notifyItemInserted(i);
                        if(ar_addedusers.size()>0)
                        {
                            title_layout.setVisibility(View.GONE);
                        }
                        else
                        {
                            title_layout.setVisibility(View.VISIBLE);
                        }
                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            RelativeLayout add_user;
            ImageView mark;
            TextView name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                add_user=itemView.findViewById(R.id.add_user);
                mark=itemView.findViewById(R.id.mark);
                name=itemView.findViewById(R.id.name);

            }
        }
    }


    public class GroupAddedUsersAdapter extends RecyclerView.Adapter<GroupAddedUsersAdapter.MyViewHolder>
    {

        ArrayList<GUsersdata> users;
        Context context;
        Activity activity;
        boolean liked=false;

        public GroupAddedUsersAdapter(ArrayList<GUsersdata> users, Context context) {
            this.users = users;
            this.context = context;
            activity = (Activity) context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_added_user_layout,viewGroup,false);
            MyViewHolder myViewHolder=new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

            final GUsersdata gUsersdata=users.get(i);

            holder.name.setText(gUsersdata.getUsername());

            holder.rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(i,gUsersdata);
                    rc_gusers.setAdapter(new NewGroupUsersAdapter(ar_users,AddNewGroupActivity.this));

                    if(ar_addedusers.size()>0)
                    {
                        title_layout.setVisibility(View.GONE);
                    }
                    else
                    {
                        title_layout.setVisibility(View.VISIBLE);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView mark;
            RelativeLayout rel;
            TextView name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                mark=itemView.findViewById(R.id.mark);
                rel=itemView.findViewById(R.id.rel);
                name=itemView.findViewById(R.id.name);

            }
        }

        public void removeAt(int position,GUsersdata gUsersdata) {
            //ar_addedusers.remove(position);
            ar_addedusers.remove(gUsersdata);
            rc_added_users.removeViewAt(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, users.size());
            notifyItemChanged(position);

        }
    }


}
