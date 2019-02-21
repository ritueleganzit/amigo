package com.eleganzit.volunteerifyngo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.MediaStore;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.volunteerifyngo.adapter.MentionsAdapter;
import com.eleganzit.volunteerifyngo.adapter.MentionsRecyclerAdapter;
import com.eleganzit.volunteerifyngo.model.PagesData;
import com.eleganzit.volunteerifyngo.model.Tweet;
import com.eleganzit.volunteerifyngo.model.UsersData;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.hendraanggrian.appcompat.socialview.Mention;
import com.hendraanggrian.appcompat.widget.MentionArrayAdapter;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static java.security.AccessController.getContext;

public class CreatePostActivity extends AppCompatActivity implements MentionsRecyclerAdapter.ContactsAdapterListener{

    SocialAutoCompleteTextView ed_status;
    LinearLayout add_photo,add_tag;
    private static final int SELECT_PICTURE = 100;

    String imageFilePath;
    //BottomSheetBehavior sheetBehavior;
    LinearLayout layoutBottomSheet,privacy;
    ArrayList<UsersData> addeduserslist=new ArrayList<>();
    RecyclerView rc_untagged,rc_tagged;
    ImageView remove_all,post_photo,send_post;
    RelativeLayout rel_tagged;
    CardView card_mentions;
    private RecyclerView recyclerView;
    private List<PagesData> contactList;
    private MentionsRecyclerAdapter mAdapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView txt_privacy;

    @Override
    protected void onResume() {
        super.onResume();

        preferences=getSharedPreferences("post_data",MODE_PRIVATE);
        editor=preferences.edit();

        Log.d("preferences",preferences.getString("post_privacy","Public")+"");

        txt_privacy.setText(preferences.getString("post_privacy","Public")+"");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageFilePath=getIntent().getStringExtra("imageFilePath");
        send_post=findViewById(R.id.send_post);
        post_photo=findViewById(R.id.post_photo);
        txt_privacy=findViewById(R.id.txt_privacy);

        if(imageFilePath!=null)
        {
            if(imageFilePath.equalsIgnoreCase(""))
            {
                post_photo.setVisibility(View.GONE);
            }
            else
            {
                post_photo.setVisibility(View.VISIBLE);
                Glide
                        .with(this)
                        .load(imageFilePath)
                        .into(post_photo);
            }
        }

        send_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        ed_status=findViewById(R.id.ed_status);
        add_photo=findViewById(R.id.add_photo);
        add_tag=findViewById(R.id.add_tag);
        rc_untagged=findViewById(R.id.rc_untagged);
        rc_tagged=findViewById(R.id.rc_tagged);
        remove_all=findViewById(R.id.remove_all);
        rel_tagged=findViewById(R.id.rel_tagged);
        privacy=findViewById(R.id.privacy);
        layoutBottomSheet=findViewById(R.id.bottom_sheet);
        //sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreatePostActivity.this,SelectPrivacyActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_untagged.setLayoutManager(layoutManager);

        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(this);
        layoutManager2.setFlexDirection(FlexDirection.ROW);
        layoutManager2.setJustifyContent(JustifyContent.FLEX_START);
        rc_tagged.setLayoutManager(layoutManager2);

        card_mentions = findViewById(R.id.card_mentions);
        recyclerView = findViewById(R.id.rc_mentions);
        contactList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<UsersData> arrayList=new ArrayList<>();
        UsersData usersData=new UsersData("","","");

        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);
        arrayList.add(usersData);

        rc_untagged.setAdapter(new UsersTagAdapter(arrayList,this));

        add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                layoutBottomSheet.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp)
                        .duration(300)
                        .repeat(0)
                        .playOn(layoutBottomSheet);
              /*  BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(CreatePostActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.tags_bottom_sheet, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();*/
            }
        });

        PagesData pagesData=new PagesData("","","The Zahir","","");
        PagesData pagesData1=new PagesData("","","Ahmed","","");
        PagesData pagesData2=new PagesData("","","Mala","","");
        PagesData pagesData3=new PagesData("","","Javed","","");
        PagesData pagesData4=new PagesData("","","Ahmed Mala","","");
        PagesData pagesData5=new PagesData("","","Uvais","","");
        PagesData pagesData6=new PagesData("","","Xxxxxx","","");

        List<PagesData> items=new ArrayList<>();
        items.add(pagesData);
        items.add(pagesData1);
        items.add(pagesData2);
        items.add(pagesData3);
        items.add(pagesData4);
        items.add(pagesData5);
        items.add(pagesData6);

        contactList.clear();
        contactList.addAll(items);
        mAdapter = new MentionsRecyclerAdapter(this, contactList, this);
        recyclerView.setAdapter(mAdapter);

        ArrayList<PagesData> arrayList2=new ArrayList<>();
        arrayList2.add(pagesData);
        arrayList2.add(pagesData1);
        arrayList2.add(pagesData2);
        arrayList2.add(pagesData3);
        arrayList2.add(pagesData4);
        arrayList2.add(pagesData5);
        arrayList2.add(pagesData6);
        ArrayAdapter<PagesData> mentionAdapter = new MentionsAdapter(this,arrayList2);

        mentionAdapter.add(pagesData);
        mentionAdapter.add(pagesData1);
        mentionAdapter.add(pagesData2);
        mentionAdapter.add(pagesData3);
        mentionAdapter.add(pagesData4);
        mentionAdapter.add(pagesData5);
        mentionAdapter.add(pagesData6);

        ed_status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.d("whereeeeeeeeee","   onTextChanged   "+i+"      "+charSequence.toString().length());

                if (!(charSequence.toString().isEmpty()) && i <= charSequence.toString().length())
                    {

                        Log.d("whereeeeeeeeee","   first if");

                        if(charSequence.length()>0 && charSequence.length()==1)
                        {
                            Log.d("charSequence.charAt(i)",i+"   "+charSequence.charAt(0)+"   "+charSequence.toString().length());

                            Log.d("whereeeeeeeeee","   second if");

                            switch (charSequence.charAt(0))
                            {
                                case '@' :
                                    Log.d("whereeeeeeeeee","   switch case '@'");

                                    if(contactList.size()>0)
                                    {
                                        Log.d("whereeeeeeeeee","      switch case '@' in if");

                                        //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"    if switch open suggestion", Toast.LENGTH_SHORT).show();
                                        card_mentions.setVisibility(View.VISIBLE);
                                        mAdapter.getFilter().filter(charSequence);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                    else
                                    {
                                        Log.d("whereeeeeeeeee","      switch case '@' in else");

                                        card_mentions.setVisibility(View.GONE);
                                    }

                                    break;


                                default:
                                    Log.d("whereeeeeeeeee","      switch case default ");

                            }
                        }
                        else
                        {
                            Log.d("whereeeeeeeeee","   else of second if");
                            String charAtLast=String.valueOf(charSequence.charAt((charSequence.length()-1)));
                            String charAtSecLast=String.valueOf(charSequence.charAt((charSequence.length()-2)));
                            Log.d("chaarrrrrat",charAtSecLast+"    "+charAtLast);
                            if(charAtLast.equalsIgnoreCase("@") && charAtSecLast.equalsIgnoreCase(" "))
                            {
                                Log.d("whereeeeeeeeee","   last char is @ and sec last is blank");
                                if(contactList.size()>0)
                                {
                                    Log.d("whereeeeeeeeee","     contactList.size()>0");
                                    //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"   if if switch open suggestion", Toast.LENGTH_SHORT).show();
                                    card_mentions.setVisibility(View.VISIBLE);
                                    Log.d("nnnnnnnnnnnnn",charSequence+"");
                                    mAdapter.getFilter().filter(charSequence);
                                    mAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    Log.d("whereeeeeeeeee","     not contactList.size()>0");
                                    card_mentions.setVisibility(View.GONE);
                                }
                            }
                            else if(charAtLast.equalsIgnoreCase(" "))
                            {
                                Log.d("whereeeeeeeeee","    last char is blank");
                                card_mentions.setVisibility(View.GONE);
                            }
                            else
                            {
                                Log.d("whereeeeeeeeee","    last else   list size   "+contactList.size());
                                if(contactList.size()>0)
                                {
                                    Log.d("whereeeeeeeeee","    if in last else");
                                    //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"   else if switch open suggestion", Toast.LENGTH_SHORT).show();
                                    card_mentions.setVisibility(View.VISIBLE);
                                    mAdapter.getFilter().filter(charSequence);
                                    mAdapter.notifyDataSetChanged();
                                }
                                else
                                {
                                    Log.d("whereeeeeeeeee","     else in last else");
                                    //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"   else else switch open suggestion", Toast.LENGTH_SHORT).show();
                                    card_mentions.setVisibility(View.VISIBLE);
                                    mAdapter.getFilter().filter(charSequence);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                            /*boolean foundMatch = false;
                    Pattern regex = Pattern.compile("\\b(?:@)\\b");
                    Matcher regexMatcher = regex.matcher("zahir ");
                    foundMatch = regexMatcher.find();
                    if(charSequence.toString().matches("(?<=^|(?<=[^a-zA-Z0-9-\\.]))@[A-Za-z0-9-]+(?=[^a-zA-Z0-9-_\\.])"))
                    {
                        Toast.makeText(CreatePostActivity.this, "open ", Toast.LENGTH_SHORT).show();
                    }
*/


                    }
                    else
                    {
                        card_mentions.setVisibility(View.GONE);
                    }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

    }


    public boolean isMention(String text) {

        boolean foundMatch = false;
        Pattern regex = Pattern.compile("\\b(?:@)\\b");
        Matcher regexMatcher = regex.matcher(text);
        foundMatch = regexMatcher.matches();
        return foundMatch;
    }

    private boolean parseText(String text) {
        String[] words = text.split("[ \\.]");
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 0
                    && words[i].charAt(0) == '@') {
                System.out.println(words[i]);
                return true;
            }
        }
        return false;
    }

    void openImageChooser() {
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onBackPressed() {

        if(layoutBottomSheet.getVisibility()==View.VISIBLE)
        {
            YoYo.with(Techniques.SlideOutDown)
                    .duration(400)
                    .repeat(0)
                    .playOn(layoutBottomSheet);
            layoutBottomSheet.setVisibility(View.GONE);
        }
        else
        {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }

    }

    @Override
    public void onContactSelected(PagesData pagesData) {

        String path=ed_status.getText().toString().substring(ed_status.getText().toString().lastIndexOf("@")+1);

        Log.d("ccccccccccccc","path    "+path+"    "+path.length());

        /*int charIndex = ed_status.getText().toString().length() - path.length();
        String text = ed_status.getText().toString();
        text = text.substring(charIndex, ed_status.getText().toString().length() - 1) + text.substring(charIndex+1);*/
        //ed_status.setText(text);

        //Log.d("ccccccccccccc","dataaa    "+text);
        int length = ed_status.getText().length();
        if (length > 1) {
            ed_status.getText().delete(length - path.length(), length);
        }
        ed_status.append(pagesData.getTitle()+" ");
        Log.d("ccccccccccccc","after    "+ed_status.getText().toString().substring(0, ed_status.getText().toString().indexOf("@")));
        card_mentions.setVisibility(View.GONE);

    }


    public class UsersTagAdapter extends RecyclerView.Adapter<UsersTagAdapter.MyViewHolder>
    {
        ArrayList<UsersData> arrayList;
        Context context;
        boolean isSelectedAll;

        public UsersTagAdapter(ArrayList<UsersData> arrayList, Context context)
        {
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(context).inflate(R.layout.tag_users_layout,parent,false);

            MyViewHolder myViewHolder=new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final UsersData usersData=arrayList.get(position);

            holder.select_radioButton.setClickable(false);

            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.select_radioButton.isChecked())
                    {
                        holder.select_radioButton.setChecked(false);
                    }
                    else
                    {
                        holder.select_radioButton.setChecked(true);
                    }
                }
            });

            holder.select_radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        addeduserslist.add(usersData);

                        rc_tagged.setAdapter(new UsersTaggedAdapter(addeduserslist,context));

                        if(addeduserslist.size()>0)
                        {
                            remove_all.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            remove_all.setVisibility(View.GONE);
                        }
                        //Toast.makeText(context, "added "+passengerData.getFname()+" "+passengerData.getLname()+" passengers", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        addeduserslist.remove(usersData);
                        rc_tagged.setAdapter(new UsersTaggedAdapter(addeduserslist,context));
                        if(addeduserslist.size()>0)
                        {
                            remove_all.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            remove_all.setVisibility(View.GONE);
                        }
                        //Toast.makeText(context, "removed "+passengerData.getFname()+" "+passengerData.getLname()+" passengers", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            remove_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectAll(false);
                    addeduserslist.clear();
                    remove_all.setVisibility(View.GONE);
                    rc_tagged.setAdapter(new UsersTaggedAdapter(addeduserslist,CreatePostActivity.this));
                }
            });
            if (!isSelectedAll) holder.select_radioButton.setChecked(false);
            else holder.select_radioButton.setChecked(true);

        }

        public void selectAll(boolean status){
            Log.e("onClickSelectAll","yes");
            isSelectedAll=status;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public  class MyViewHolder extends RecyclerView.ViewHolder {

            LinearLayout main;

            TextView p_name;
            CheckBox select_radioButton;

            public MyViewHolder(View itemView) {
                super(itemView);
                main=itemView.findViewById(R.id.main);
                select_radioButton=itemView.findViewById(R.id.select_radioButton);

            }
        }
    }

    public class UsersTaggedAdapter extends RecyclerView.Adapter<UsersTaggedAdapter.MyViewHolder>
    {
        ArrayList<UsersData> arrayList;
        Context context;
        boolean isSelectedAll;

        public UsersTaggedAdapter(ArrayList<UsersData> arrayList, Context context)
        {
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(context).inflate(R.layout.tagged_users_layout,parent,false);

            MyViewHolder myViewHolder=new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final UsersData usersData=arrayList.get(position);

        }

        public void selectAll(boolean status){
            Log.e("onClickSelectAll","yes");
            isSelectedAll=status;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public  class MyViewHolder extends RecyclerView.ViewHolder {



            TextView username;

            public MyViewHolder(View itemView) {
                super(itemView);
                username=itemView.findViewById(R.id.tagged_username);


            }
        }
    }

}
