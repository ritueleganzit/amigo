package com.eleganzit.volunteerifyngo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;

import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.volunteerifyngo.adapter.MentionsAdapter;
import com.eleganzit.volunteerifyngo.adapter.MentionsRecyclerAdapter;
import com.eleganzit.volunteerifyngo.adapter.ViewPhotosAdapter;
import com.eleganzit.volunteerifyngo.model.PagesData;
import com.eleganzit.volunteerifyngo.model.PhotosData;
import com.eleganzit.volunteerifyngo.model.UsersData;
import com.eleganzit.volunteerifyngo.utils.KeyBoardEvent;
import com.eleganzit.volunteerifyngo.utils.TextViewRobotoBold;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreatePostActivity extends AppCompatActivity implements MentionsRecyclerAdapter.ContactsAdapterListener{

    SocialAutoCompleteTextView ed_status;
    LinearLayout add_photo,add_tag,add_location;
    private static final int SELECT_PICTURE = 100;
    CoordinatorLayout parent;
    String imageFilePath;
    LinearLayout persistantBottomSheet;
    LinearLayout view_photos_layout,locations_layout;
    RecyclerView rc_view_photos,rc_locations;
    BottomSheetBehavior sheetBehavior;
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
    LinearLayout add;
    RecyclerView rc_photos;
    ArrayList<PhotosData> ar_photos=new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();

        preferences=getSharedPreferences("post_data",MODE_PRIVATE);
        editor=preferences.edit();

        Log.d("preferences",preferences.getString("post_privacy","Public")+"");

        txt_privacy.setText(preferences.getString("post_privacy","Public")+"");

    }

    @SuppressLint("ClickableViewAccessibility")
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
        add=findViewById(R.id.add);
        rc_photos=findViewById(R.id.rc_photos);
        parent=findViewById(R.id.parent);
        ed_status=findViewById(R.id.ed_status);
        add_photo=findViewById(R.id.add_photo);
        add_tag=findViewById(R.id.add_tag);
        add_location=findViewById(R.id.add_location);
        rc_untagged=findViewById(R.id.rc_untagged);
        rc_tagged=findViewById(R.id.rc_tagged);
        remove_all=findViewById(R.id.remove_all);
        rel_tagged=findViewById(R.id.rel_tagged);
        privacy=findViewById(R.id.privacy);
        layoutBottomSheet=findViewById(R.id.bottom_sheet);
        view_photos_layout=findViewById(R.id.view_photos_layout);
        locations_layout=findViewById(R.id.locations_layout);
        rc_view_photos=findViewById(R.id.rc_view_photos);
        rc_locations=findViewById(R.id.rc_locations);
        //sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        persistantBottomSheet=findViewById(R.id.persistantBottomSheet);

        rc_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sheetBehavior = BottomSheetBehavior.from(persistantBottomSheet);

        FlexboxLayoutManager layoutManager1 = new FlexboxLayoutManager(this);
        layoutManager1.setFlexDirection(FlexDirection.ROW);
        layoutManager1.setJustifyContent(JustifyContent.FLEX_START);
        rc_photos.setLayoutManager(layoutManager1);

        RecyclerView.LayoutManager layoutManager3=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_view_photos.setLayoutManager(layoutManager3);

        RecyclerView.LayoutManager layoutManager4=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rc_locations.setLayoutManager(layoutManager4);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {

                    setupUI(parent);

                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    add.setVisibility(View.GONE);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    add.setVisibility(View.VISIBLE);
                }
            }
        });

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN: {
                        add.setVisibility(View.VISIBLE);
                    }
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        add.setVisibility(View.GONE);

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        add.setVisibility(View.VISIBLE);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:{
                        add.setVisibility(View.GONE);
                    }
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:{
                        add.setVisibility(View.GONE);
                    }
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        if(imageFilePath!=null)
        {
            if(imageFilePath.equalsIgnoreCase(""))
            {
                //post_photo.setVisibility(View.GONE);
            }
            else
            {
                PhotosData photosData=new PhotosData("",imageFilePath);
                ar_photos.add(photosData);
                rc_photos.setAdapter(new PhotosAdapter(ar_photos,CreatePostActivity.this));
                rc_view_photos.setAdapter(new ViewPhotosAdapter(ar_photos,CreatePostActivity.this));
            }
        }

        send_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        ed_status.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    add.setVisibility(View.VISIBLE);
                }

            }
        });

        ed_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                add.setVisibility(View.VISIBLE);
            }
        });

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
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                add.setVisibility(View.VISIBLE);

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

        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                add.setVisibility(View.VISIBLE);

                layoutBottomSheet.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp)
                        .duration(300)
                        .repeat(0)
                        .playOn(locations_layout);
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


    public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder>
    {
        ArrayList<PhotosData> photos;
        Context context;
        Activity activity;

        public PhotosAdapter(ArrayList<PhotosData> photos, Context context) {
            this.photos = photos;
            this.context = context;
            activity = (Activity) context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_photo_layout,viewGroup,false);
            MyViewHolder myViewHolder=new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

            if(photos.size()>=5)
            {
                if(i<2)
                {
                    holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
                    holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
                    holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
                    holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
                }
                else
                {
                    holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/304;
                    holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/304;
                    holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/304;
                    holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/304;
                }
                FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                        FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                        FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
                );
                Resources r = context.getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        1,
                        r.getDisplayMetrics()
                );
                params.setMargins(px, 0, px, px);
                holder.rel_main.setLayoutParams(params);

            }
            if(photos.size()==1)
            {

                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity);
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity);
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity);
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity);

                FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                        FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                        FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 0);
                holder.rel_main.setLayoutParams(params);
            }
            if(photos.size()==2)
            {

                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/201;
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/201;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/201;
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/201;

                FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                        FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                        FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
                );
                Resources r = context.getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        12/10,
                        r.getDisplayMetrics()
                );
                params.setMargins(0, 0, px, 0);
                holder.rel_main.setLayoutParams(params);
            }
            if(photos.size()==3)
            {

                if(i<1)
                {
                    holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity);
                    holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
                    holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity);
                    holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
                }
                else
                {
                    holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
                    holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
                    holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/202;
                    holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/202;
                }
                FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                        FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                        FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
                );
                Resources r = context.getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        1,
                        r.getDisplayMetrics()
                );
                params.setMargins(px, 0, px, px);
                holder.rel_main.setLayoutParams(params);
            }
            if(photos.size()==4)
            {

                holder.feed_photo.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/201;
                holder.feed_photo.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/201;
                holder.rel_main.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/201;
                holder.rel_main.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/201;
                FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                        FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                        FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
                );
                Resources r = context.getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        12/10,
                        r.getDisplayMetrics()
                );
                params.setMargins(px, 0, px, px);
                holder.rel_main.setLayoutParams(params);
            }
            if(photos.size()>5)
            {
                if(i==4)
                {
                    holder.pframe.setVisibility(View.VISIBLE);
                    holder.plus_count.setText("+"+(photos.size()-5));
                    holder.pframe.getLayoutParams().width=getScreenWidthInPXs(context,activity)*100/304;
                    holder.pframe.getLayoutParams().height=getScreenWidthInPXs(context,activity)*100/304;
                }
            }
            Glide
                    .with(context)
                    .load(photos.get(i).getPhoto())
                    .apply(new RequestOptions().centerCrop()).into(holder.feed_photo);

            holder.feed_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ar_photos.size()>1)
                    {
                        view_photos_layout.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInUp)
                                .duration(300)
                                .repeat(0)
                                .playOn(view_photos_layout);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            if(photos.size()>5)
            {
                return 5;
            }
            else
            {
                return photos.size();
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView feed_photo;
            TextViewRobotoBold plus_count;
            RelativeLayout rel_main,pframe;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                feed_photo=itemView.findViewById(R.id.feed_photo);
                rel_main=itemView.findViewById(R.id.rel_main);
                pframe=itemView.findViewById(R.id.pframe);
                plus_count=itemView.findViewById(R.id.plus_count);

            }
        }

        public int getScreenWidthInPXs(Context context, Activity activity){

            DisplayMetrics dm = new DisplayMetrics();

            WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);
            int widthInDP = Math.round(dm.widthPixels / dm.density);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            return width;
        }

    }

    void openImageChooser() {
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int clumnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String mediapath = cursor.getString(clumnIndex);

                PhotosData photosData=new PhotosData("",mediapath);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                add.setVisibility(View.VISIBLE);
                ar_photos.add(photosData);
                rc_photos.setAdapter(new PhotosAdapter(ar_photos,CreatePostActivity.this));
                rc_view_photos.setAdapter(new ViewPhotosAdapter(ar_photos,CreatePostActivity.this));

            }
        }
        if (resultCode==RESULT_CANCELED)
        {

        }

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
        else if(view_photos_layout.getVisibility()==View.VISIBLE)
        {
            YoYo.with(Techniques.SlideOutDown)
                    .duration(400)
                    .repeat(0)
                    .playOn(view_photos_layout);
            view_photos_layout.setVisibility(View.GONE);
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

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    KeyBoardEvent.hideKeyboard(CreatePostActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
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
