package com.eleganzit.amigo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import android.content.Context;
import android.os.Bundle;
import android.telecom.Call;
import android.text.Editable;

import android.text.Html;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.amigo.adapter.LocationsAdapter;
import com.eleganzit.amigo.adapter.MentionsAdapter;
import com.eleganzit.amigo.adapter.MentionsRecyclerAdapter;
import com.eleganzit.amigo.adapter.SearchPlacesAdapter;
import com.eleganzit.amigo.adapter.ViewPhotosAdapter;
import com.eleganzit.amigo.api.RetrofitAPI;
import com.eleganzit.amigo.api.RetrofitInterface;
import com.eleganzit.amigo.databinding.ActivityCreatePostBinding;
import com.eleganzit.amigo.model.CreatePost;
import com.eleganzit.amigo.model.CreatePostResponse;
import com.eleganzit.amigo.model.PagesData;
import com.eleganzit.amigo.model.PhotosData;
import com.eleganzit.amigo.model.PlacesData;
import com.eleganzit.amigo.model.UsersData;
import com.eleganzit.amigo.session.UserLoggedInSession;
import com.eleganzit.amigo.uploadMultupleImage.CallAPiActivity;
import com.eleganzit.amigo.uploadMultupleImage.GetResponse;
import com.eleganzit.amigo.utils.KeyBoardEvent;
import com.eleganzit.amigo.utils.TextViewRobotoBold;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity implements MentionsRecyclerAdapter.ContactsAdapterListener {

    private static final String TAG = "CreatePostActivity";
    private ArrayList<String> mSelectPath;
    private static final int PLACE_PICKER_REQUEST2 = 1001;

ProgressDialog progressDialog;
    LinearLayout  add_tag;
    private static final int SELECT_PICTURE = 100;
    CoordinatorLayout parent;
    String imageFilePath;
    LinearLayout view_photos_layout, locations_layout;
    RecyclerView rc_view_photos, rc_locations;
    BottomSheetBehavior sheetBehavior;
    LinearLayout layoutBottomSheet;
    ArrayList<UsersData> addeduserslist = new ArrayList<>();
    RecyclerView rc_untagged, rc_tagged;
    RelativeLayout rel_tagged;
    CardView card_mentions;
    private RecyclerView recyclerView;
    private List<PagesData> contactList;
    private MentionsRecyclerAdapter mAdapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView txt_privacy, txt_with, with;
    LinearLayout add;
    RecyclerView rc_photos;
    ArrayList<PhotosData> ar_photos = new ArrayList<>();
    String mediapath,user_id,profilePic,fullname,post_status="",lat,lng;




    //
    private static final int REQUEST_IMAGE = 201;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 202;
    CallAPiActivity callAPiActivity;


    ArrayList<String> str_photo_array=new ArrayList<>();


    UserLoggedInSession userLoggedInSession;

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(CreatePostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(CreatePostActivity.this);
            selector.multi();
            selector.count(6);
            selector.showCamera(false);

            selector.origin(mSelectPath);
            selector.start(CreatePostActivity.this, REQUEST_IMAGE);
        }
    }
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(CreatePostActivity.this, permission)) {
            new AlertDialog.Builder(CreatePostActivity.this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CreatePostActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(CreatePostActivity.this, new String[]{permission}, requestCode);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        preferences = getSharedPreferences("post_data", MODE_PRIVATE);
        editor = preferences.edit();

        Log.d("preferences", preferences.getString("post_privacy", "Public") + "");
post_status=""+preferences.getString("post_privacy", "Public");
        txt_privacy.setText(preferences.getString("post_privacy", "Public") + "");



        binding.sendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(binding.layoutCreatePost.edStatus.getText().toString().isEmpty()) &&   (str_photo_array.size()>0)){


                    addPost();

                }

            }
        });
    }

    private void addPost() {
        progressDialog.show();
        HashMap<String, String> map = new HashMap<>();

        map.put("type", txt_privacy.getText().toString() + "");
        map.put("user_id", user_id);
        map.put("content", binding.layoutCreatePost.edStatus.getText().toString());
        map.put("tag_user_id", "1,2");
        callAPiActivity.doPostWithFiles(CreatePostActivity.this, map, "http://itechgaints.com/Volunteerify-API/addUserpost", str_photo_array, "photo_url[]", new GetResponse() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {
                progressDialog.dismiss();
                if (result.getString("status").equalsIgnoreCase("1"))
                {
                    finish();
                   // startActivity(new Intent(CreatePostActivity.this,));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    Toast.makeText(CreatePostActivity.this, "successfully uploaded", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailureResult(JSONObject result) throws JSONException {
                progressDialog.dismiss();
                Toast.makeText( CreatePostActivity.this, "Server Error"+result.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(String message) throws JSONException {

            }
        });
    }

    ActivityCreatePostBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(CreatePostActivity.this,R.layout.activity_create_post);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        userLoggedInSession=new UserLoggedInSession(CreatePostActivity.this);
        callAPiActivity = new CallAPiActivity(this);


        progressDialog=new ProgressDialog(CreatePostActivity.this);
progressDialog.setCancelable(false);
progressDialog.setCanceledOnTouchOutside(false);
progressDialog.setMessage("Please Wait");
        HashMap<String,String> map=userLoggedInSession.getUserDetails();
        user_id=map.get(UserLoggedInSession.USER_ID);
        profilePic=map.get(UserLoggedInSession.PHOTO);
fullname=map.get(UserLoggedInSession.FNAME);



binding.layoutCreatePost.userName.setText(fullname);
                Glide.with(CreatePostActivity.this)
                        .load(profilePic).apply(RequestOptions.circleCropTransform()).into( binding.layoutCreatePost.profilePic);

        imageFilePath = getIntent().getStringExtra("imageFilePath");

        Bundle extra = getIntent().getBundleExtra("imagesFilePath");
        if (ar_photos.size() > 0) {
            ar_photos.clear();
        }
        if (extra != null) {
            ar_photos = (ArrayList<PhotosData>) extra.getSerializable("objects");
        }


        txt_privacy = findViewById(R.id.txt_privacy);
        txt_with = findViewById(R.id.txt_with);

        with = findViewById(R.id.with);
        add = findViewById(R.id.add);
        rc_photos = findViewById(R.id.rc_photos);
        parent = findViewById(R.id.parent);
        add_tag = findViewById(R.id.add_tag);
        rc_untagged = findViewById(R.id.rc_untagged);
        rc_tagged = findViewById(R.id.rc_tagged);
        rel_tagged = findViewById(R.id.rel_tagged);

        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        view_photos_layout = findViewById(R.id.view_photos_layout);
        locations_layout = findViewById(R.id.locations_layout);
        rc_view_photos = findViewById(R.id.rc_view_photos);
        rc_locations = findViewById(R.id.rc_locations);
        //sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        rc_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sheetBehavior = BottomSheetBehavior.from(binding.createPostBottomSheet.persistantBottomSheet);

        FlexboxLayoutManager layoutManager1 = new FlexboxLayoutManager(this);
        layoutManager1.setFlexDirection(FlexDirection.ROW);
        layoutManager1.setJustifyContent(JustifyContent.FLEX_START);
        rc_photos.setLayoutManager(layoutManager1);

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_view_photos.setLayoutManager(layoutManager3);

        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_locations.setLayoutManager(layoutManager4);

        ArrayList<PlacesData> ar_locations = new ArrayList<>();

        PlacesData placesData = new PlacesData("", "", "", "", "");

        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);
        ar_locations.add(placesData);

        rc_locations.setAdapter(new LocationsAdapter(ar_locations, this));

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
                    case BottomSheetBehavior.STATE_DRAGGING: {
                        add.setVisibility(View.GONE);
                    }
                    break;
                    case BottomSheetBehavior.STATE_SETTLING: {
                        add.setVisibility(View.GONE);
                    }
                    break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        if (ar_photos != null) {
            if (ar_photos.size() == 0) {
                //post_photo.setVisibility(View.GONE);
                if (imageFilePath != null) {
                    PhotosData photosData = new PhotosData("", imageFilePath + "");
                    ar_photos.add(photosData);
                    rc_photos.setAdapter(new PhotosAdapter(ar_photos, CreatePostActivity.this));
                    rc_view_photos.setAdapter(new ViewPhotosAdapter(ar_photos, CreatePostActivity.this));
                }

            } else {

                rc_photos.setAdapter(new PhotosAdapter(ar_photos, CreatePostActivity.this));
                rc_view_photos.setAdapter(new ViewPhotosAdapter(ar_photos, CreatePostActivity.this));
            }
        }

        binding.sendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        binding.layoutCreatePost.edStatus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    add.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.layoutCreatePost.edStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                add.setVisibility(View.VISIBLE);
            }
        });

        binding.layoutCreatePost.privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreatePostActivity.this, SelectPrivacyActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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

        ArrayList<UsersData> arrayList = new ArrayList<>();
        UsersData usersData1 = new UsersData("1", "Zahir", "");
        UsersData usersData2 = new UsersData("2", "Ritu", "");
        UsersData usersData3 = new UsersData("3", "Ahmed", "");
        UsersData usersData4 = new UsersData("4", "Mala", "");
        UsersData usersData5 = new UsersData("5", "Uvais", "");
        UsersData usersData6 = new UsersData("6", "Nambath", "");
        UsersData usersData7 = new UsersData("7", "Tom", "");
        UsersData usersData8 = new UsersData("8", "Jerry", "");
        UsersData usersData9 = new UsersData("9", "Jack", "");
        UsersData usersData10 = new UsersData("10", "Peter", "");

        arrayList.add(usersData1);
        arrayList.add(usersData2);
        arrayList.add(usersData3);
        arrayList.add(usersData4);
        arrayList.add(usersData5);
        arrayList.add(usersData6);
        arrayList.add(usersData7);
        arrayList.add(usersData8);
        arrayList.add(usersData9);
        arrayList.add(usersData10);

        rc_untagged.setAdapter(new UsersTagAdapter(arrayList, this));

        add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                binding.createPostBottomSheet.persistantBottomSheet.setVisibility(View.GONE);
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

        binding.createPostBottomSheet.addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                //binding.createPostBottomSheet.persistantBottomSheet.setVisibility(View.GONE);
                /*add.setVisibility(View.VISIBLE);

                locations_layout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp)
                        .duration(300)
                        .repeat(0)
                        .playOn(locations_layout);*/
                Intent intent = null;
                try {
                    intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(CreatePostActivity.this);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent, PLACE_PICKER_REQUEST2);
              /*  BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(CreatePostActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.tags_bottom_sheet, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();*/
            }
        });


        PagesData pagesData = new PagesData("", "", "The Zahir", "", "");
        PagesData pagesData1 = new PagesData("", "", "Ahmed", "", "");
        PagesData pagesData2 = new PagesData("", "", "Mala", "", "");
        PagesData pagesData3 = new PagesData("", "", "Javed", "", "");
        PagesData pagesData4 = new PagesData("", "", "Ahmed Mala", "", "");
        PagesData pagesData5 = new PagesData("", "", "Uvais", "", "");
        PagesData pagesData6 = new PagesData("", "", "Xxxxxx", "", "");

        List<PagesData> items = new ArrayList<>();
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

        ArrayList<PagesData> arrayList2 = new ArrayList<>();
        arrayList2.add(pagesData);
        arrayList2.add(pagesData1);
        arrayList2.add(pagesData2);
        arrayList2.add(pagesData3);
        arrayList2.add(pagesData4);
        arrayList2.add(pagesData5);
        arrayList2.add(pagesData6);
        ArrayAdapter<PagesData> mentionAdapter = new MentionsAdapter(this, arrayList2);

        mentionAdapter.add(pagesData);
        mentionAdapter.add(pagesData1);
        mentionAdapter.add(pagesData2);
        mentionAdapter.add(pagesData3);
        mentionAdapter.add(pagesData4);
        mentionAdapter.add(pagesData5);
        mentionAdapter.add(pagesData6);

        binding.layoutCreatePost.edStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (!(charSequence.toString().isEmpty()) && i <= charSequence.toString().length()) {

                    Log.d("whereeeeeeeeee", "   first if");

                    if (charSequence.length() > 0 && charSequence.length() == 1) {
                        Log.d("charSequence.charAt(i)", i + "   " + charSequence.charAt(0) + "   " + charSequence.toString().length());

                        Log.d("whereeeeeeeeee", "   second if");

                        switch (charSequence.charAt(0)) {
                            case '@':
                                Log.d("whereeeeeeeeee", "   switch case '@'");

                                if (contactList.size() > 0) {
                                    Log.d("whereeeeeeeeee", "      switch case '@' in if");

                                    //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"    if switch open suggestion", Toast.LENGTH_SHORT).show();
                                    card_mentions.setVisibility(View.VISIBLE);
                                    mAdapter.getFilter().filter(charSequence);
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    Log.d("whereeeeeeeeee", "      switch case '@' in else");

                                    card_mentions.setVisibility(View.GONE);
                                }

                                break;


                            default:
                                Log.d("whereeeeeeeeee", "      switch case default ");

                        }
                    } else {
                        Log.d("whereeeeeeeeee", "   else of second if");
                        String charAtLast = String.valueOf(charSequence.charAt((charSequence.length() - 1)));
                        String charAtSecLast = String.valueOf(charSequence.charAt((charSequence.length() - 2)));
                        Log.d("chaarrrrrat", charAtSecLast + "    " + charAtLast);

                        String charString = charSequence.toString();

                        String[] filteredchar = charString.split(" ");
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (String ss : filteredchar) {


                            charString = ss;


                            arrayList.add(ss);
                            if (arrayList != null && !arrayList.isEmpty() && arrayList.size() > 1)
                                Log.d("arrayList", arrayList + "");

                        }

                        if (arrayList.size() > 1) {
                            charString = arrayList.get(arrayList.size() - 1);
                            Log.d("filteredchar", "" + arrayList.get(arrayList.size() - 1));
                        }
                        //Log.d("filteredchar",""+charString);


                        if (!String.valueOf(arrayList.get(arrayList.size() - 1).charAt(0)).equalsIgnoreCase("@")) {
                            Log.d("whereeeeeeeeee", "     not @ at 0");
                            card_mentions.setVisibility(View.GONE);
                        } else if (charAtLast.equalsIgnoreCase("@") && charAtSecLast.equalsIgnoreCase(" ")) {
                            Log.d("whereeeeeeeeee", "   last char is @ and sec last is blank");
                            if (contactList.size() > 0) {
                                Log.d("whereeeeeeeeee", "     contactList.size()>0");
                                //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"   if if switch open suggestion", Toast.LENGTH_SHORT).show();
                                card_mentions.setVisibility(View.VISIBLE);
                                Log.d("nnnnnnnnnnnnn", charSequence + "");
                                mAdapter.getFilter().filter(charSequence);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Log.d("whereeeeeeeeee", "     not contactList.size()>0");
                                card_mentions.setVisibility(View.GONE);
                            }
                        } else if (charAtLast.equalsIgnoreCase(" ")) {
                            Log.d("whereeeeeeeeee", "    last char is blank");
                            card_mentions.setVisibility(View.GONE);
                        } else {
                            Log.d("whereeeeeeeeee", "    last else   list size   " + contactList.size());
                            if (contactList.size() > 0) {
                                Log.d("whereeeeeeeeee", "    if in last else");
                                //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"   else if switch open suggestion", Toast.LENGTH_SHORT).show();
                                card_mentions.setVisibility(View.VISIBLE);
                                mAdapter.getFilter().filter(charSequence);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Log.d("whereeeeeeeeee", "     else in last else");
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


                } else {
                    card_mentions.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.createPostBottomSheet.addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

    }


    public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {
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

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_photo_layout, viewGroup, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

            if (photos.size() >= 5) {
                if (i < 2) {
                    holder.feed_photo.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 2;
                    holder.feed_photo.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 2;
                    holder.rel_main.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 2;
                    holder.rel_main.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 2;
                } else {
                    holder.feed_photo.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 3;
                    holder.feed_photo.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 3;
                    holder.rel_main.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 3;
                    holder.rel_main.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 3;
                }
            /*FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
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
*/
            }
            if (photos.size() == 1) {

                holder.feed_photo.getLayoutParams().width = getScreenWidthInPXs(context, activity);
                holder.feed_photo.getLayoutParams().height = getScreenWidthInPXs(context, activity);
                holder.rel_main.getLayoutParams().width = getScreenWidthInPXs(context, activity);
                holder.rel_main.getLayoutParams().height = getScreenWidthInPXs(context, activity);

            /*FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            holder.rel_main.setLayoutParams(params);*/
            }
            if (photos.size() == 2) {

                holder.feed_photo.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 2;
                holder.feed_photo.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 2;
                holder.rel_main.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 2;
                holder.rel_main.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 2;

           /* FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT,
                    FlexboxLayoutManager.LayoutParams.WRAP_CONTENT
            );
            Resources r = context.getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    1,
                    r.getDisplayMetrics()
            );
            params.setMargins(0, 0, px, 0);
            holder.rel_main.setLayoutParams(params);*/
            }
            if (photos.size() == 3) {

                if (i < 1) {
                    holder.feed_photo.getLayoutParams().width = getScreenWidthInPXs(context, activity);
                    holder.feed_photo.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 2;
                    holder.rel_main.getLayoutParams().width = getScreenWidthInPXs(context, activity);
                    holder.rel_main.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 2;
                } else {
                    holder.feed_photo.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 2;
                    holder.feed_photo.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 2;
                    holder.rel_main.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 2;
                    holder.rel_main.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 2;
                }
            /*FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
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
            holder.rel_main.setLayoutParams(params);*/
            }
            if (photos.size() == 4) {

                holder.feed_photo.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 2;
                holder.feed_photo.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 2;
                holder.rel_main.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 2;
                holder.rel_main.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 2;
            /*FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(
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
            holder.rel_main.setLayoutParams(params);*/
            }
            if (photos.size() > 5) {
                if (i == 4) {
                    holder.pframe.setVisibility(View.VISIBLE);
                    holder.plus_count.setText("+" + (photos.size() - 5));
                    holder.pframe.getLayoutParams().width = getScreenWidthInPXs(context, activity) / 3;
                    holder.pframe.getLayoutParams().height = getScreenWidthInPXs(context, activity) / 3;
                }
            }
            Glide
                    .with(context)
                    .load(photos.get(i).getPhoto())
                    .apply(new RequestOptions().centerCrop()).into(holder.feed_photo);

            holder.feed_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ar_photos.size() > 1) {
                        view_photos_layout.setVisibility(View.VISIBLE);
                        binding.createPostBottomSheet.persistantBottomSheet.setVisibility(View.GONE);
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
            if (photos.size() > 5) {
                return 5;
            } else {
                return photos.size();
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView feed_photo;
            TextViewRobotoBold plus_count;
            RelativeLayout rel_main, pframe;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                feed_photo = itemView.findViewById(R.id.feed_photo);
                rel_main = itemView.findViewById(R.id.rel_main);
                pframe = itemView.findViewById(R.id.pframe);
                plus_count = itemView.findViewById(R.id.plus_count);

            }
        }

        public int getScreenWidthInPXs(Context context, Activity activity) {

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
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Log.d("LOG_TAG", "Selected Images  1" + ar_photos.size());

                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        // Get the cursor
                        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        mediapath = cursor.getString(columnIndex);
                        Log.d("LOG_TAG", "Selected Images 1.5" + mediapath);
                        PhotosData photosData = new PhotosData("", mediapath);
                        ar_photos.add(photosData);
                        str_photo_array.add(mediapath);
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        add.setVisibility(View.VISIBLE);
                        rc_photos.setAdapter(new PhotosAdapter(ar_photos, CreatePostActivity.this));
                        rc_view_photos.setAdapter(new ViewPhotosAdapter(ar_photos, CreatePostActivity.this));
                        cursor.close();

                    }
                    Log.d("LOG_TAG", "Selected Images 2" + mArrayUri.size());
                } else if (data.getData() != null) {
                    Uri selectedImage = data.getData();

                    Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int clumnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediapath = cursor.getString(clumnIndex);

                    PhotosData photosData = new PhotosData("", mediapath);
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    add.setVisibility(View.VISIBLE);
                    ar_photos.add(photosData);
                    str_photo_array.add(mediapath);
                    rc_photos.setAdapter(new PhotosAdapter(ar_photos, CreatePostActivity.this));
                    rc_view_photos.setAdapter(new ViewPhotosAdapter(ar_photos, CreatePostActivity.this));
                    Log.d("LOG_TAG", "Selected Images" + ar_photos.size());

                }
                Log.d("LOG_TAG", "Selected Images 3" + ar_photos.size());

            }


                if (requestCode == REQUEST_IMAGE) {
                    if (resultCode == Activity.RESULT_OK) {
                        mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                        StringBuilder sb = new StringBuilder();
                        for (String p : mSelectPath) {
                            sb.append(p);
                            sb.append("\n");
                        }


                        mediapath = sb.toString().trim();
                        Log.d("LOG_TAG", "Selected Images 1.5" + mediapath);
                        for (int i = 0; i < mSelectPath.size(); i++) {
                            PhotosData photosData = new PhotosData("", mSelectPath.get(i));
                            ar_photos.add(photosData);
                            str_photo_array.add(mSelectPath.get(i));
                        }

                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        add.setVisibility(View.VISIBLE);
                        rc_photos.setAdapter(new PhotosAdapter(ar_photos, CreatePostActivity.this));
                        rc_view_photos.setAdapter(new ViewPhotosAdapter(ar_photos, CreatePostActivity.this));

                        Log.d("mediapathhhhhhhh", "" + mediapath);
                    }
                }
            if (requestCode == PLACE_PICKER_REQUEST2) {
                if (resultCode == RESULT_OK) {
                    binding.createPostBottomSheet.persistantBottomSheet.setVisibility(View.VISIBLE);

                    Place place = PlacePicker.getPlace(data, this);
                    String toastMsg = String.format("%s", place.getName());
                    LatLng latLng=place.getLatLng();

                    lat=""+latLng.latitude;
                    lng=""+latLng.longitude;
                    Log.d(TAG,""+latLng.latitude);
                    Log.d(TAG,""+latLng.longitude);
                    if (toastMsg.equalsIgnoreCase(""))
                    {

                    }
                    else {

                    }



                }
                else {
                    binding.createPostBottomSheet.persistantBottomSheet.setVisibility(View.VISIBLE);


                    //    Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show();
                }
            }
            }
            if (resultCode == RESULT_CANCELED) {
                binding.createPostBottomSheet.persistantBottomSheet.setVisibility(View.VISIBLE);

            }

    }

    @Override
    public void onBackPressed() {

        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            add.setVisibility(View.VISIBLE);
        } else if (layoutBottomSheet.getVisibility() == View.VISIBLE) {
            YoYo.with(Techniques.SlideOutDown)
                    .duration(400)
                    .repeat(0)
                    .playOn(layoutBottomSheet);
            layoutBottomSheet.setVisibility(View.GONE);
            binding.createPostBottomSheet.persistantBottomSheet.setVisibility(View.VISIBLE);
        } else if (view_photos_layout.getVisibility() == View.VISIBLE) {
            YoYo.with(Techniques.SlideOutDown)
                    .duration(400)
                    .repeat(0)
                    .playOn(view_photos_layout);
            view_photos_layout.setVisibility(View.GONE);
            binding.createPostBottomSheet.persistantBottomSheet.setVisibility(View.VISIBLE);
        } else if (locations_layout.getVisibility() == View.VISIBLE) {
            YoYo.with(Techniques.SlideOutDown)
                    .duration(400)
                    .repeat(0)
                    .playOn(locations_layout);
            locations_layout.setVisibility(View.GONE);
            binding.createPostBottomSheet.persistantBottomSheet.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }

    @Override
    public void onContactSelected(PagesData pagesData) {

        String path =  binding.layoutCreatePost.edStatus.getText().toString().substring( binding.layoutCreatePost.edStatus.getText().toString().lastIndexOf("@") + 1);

        Log.d("ccccccccccccc", "path    " + path + "    " + path.length());

        /*int charIndex = ed_status.getText().toString().length() - path.length();
        String text = ed_status.getText().toString();
        text = text.substring(charIndex, ed_status.getText().toString().length() - 1) + text.substring(charIndex+1);*/
        //ed_status.setText(text);

        //Log.d("ccccccccccccc","dataaa    "+text);
        int length =  binding.layoutCreatePost.edStatus.getText().length();
        if (length > 1) {
            binding.layoutCreatePost.edStatus.getText().delete(length - path.length(), length);
        }
        binding.layoutCreatePost.edStatus.append(pagesData.getTitle() + " ");
        Log.d("ccccccccccccc", "after    " +  binding.layoutCreatePost.edStatus.getText().toString().substring(0,  binding.layoutCreatePost.edStatus.getText().toString().indexOf("@")));
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

    public class UsersTagAdapter extends RecyclerView.Adapter<UsersTagAdapter.MyViewHolder> {
        ArrayList<UsersData> arrayList;
        Context context;
        boolean isSelectedAll;

        public UsersTagAdapter(ArrayList<UsersData> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.tag_users_layout, parent, false);

            MyViewHolder myViewHolder = new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final UsersData usersData = arrayList.get(position);

            holder.select_radioButton.setClickable(false);

            holder.username.setText(usersData.getName());

            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.select_radioButton.isChecked()) {
                        holder.select_radioButton.setChecked(false);
                    } else {
                        holder.select_radioButton.setChecked(true);
                    }
                }
            });

            holder.select_radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        addeduserslist.add(usersData);

                        rc_tagged.setAdapter(new UsersTaggedAdapter(addeduserslist, context));

                        if (addeduserslist.size() > 0) {
                            binding.layoutCreatePost.removeAll.setVisibility(View.VISIBLE);
                            txt_with.setVisibility(View.VISIBLE);
                            if (addeduserslist.size() == 1) {
                                String withWhom = "<b>" + addeduserslist.get(0).getName() + "</b> ";
                                with.setText(Html.fromHtml(withWhom));
                            } else if (addeduserslist.size() == 2) {
                                String withWhom = "<b>" + addeduserslist.get(0).getName() + "</b> " + " and " + "<b>" + addeduserslist.get(1).getName() + "</b> ";
                                with.setText(Html.fromHtml(withWhom));
                            } else {
                                String withWhom = "<b>" + addeduserslist.get(0).getName() + "</b> " + " and " + "<b>" + (addeduserslist.size() - 1) + " others" + "</b>";
                                with.setText(Html.fromHtml(withWhom));
                            }
                        } else {
                            binding.layoutCreatePost.removeAll.setVisibility(View.GONE);
                            txt_with.setVisibility(View.GONE);
                        }
                        //Toast.makeText(context, "added "+passengerData.getFname()+" "+passengerData.getLname()+" passengers", Toast.LENGTH_SHORT).show();
                    } else {
                        addeduserslist.remove(usersData);
                        rc_tagged.setAdapter(new UsersTaggedAdapter(addeduserslist, context));
                        if (addeduserslist.size() > 0) {
                            binding.layoutCreatePost.removeAll.setVisibility(View.VISIBLE);
                            txt_with.setVisibility(View.VISIBLE);
                            if (addeduserslist.size() == 1) {
                                String withWhom = "<b>" + addeduserslist.get(0).getName() + "</b> ";
                                with.setText(Html.fromHtml(withWhom));
                            } else if (addeduserslist.size() == 2) {
                                String withWhom = "<b>" + addeduserslist.get(0).getName() + "</b> " + " and " + "<b>" + addeduserslist.get(1).getName() + "</b> ";
                                with.setText(Html.fromHtml(withWhom));
                            } else {
                                String withWhom = "<b>" + addeduserslist.get(0).getName() + "</b> " + " and " + "<b>" + (addeduserslist.size() - 1) + " others" + "</b>";
                                with.setText(Html.fromHtml(withWhom));
                            }
                        } else {
                            binding.layoutCreatePost.removeAll.setVisibility(View.GONE);
                            txt_with.setVisibility(View.GONE);
                        }
                        //Toast.makeText(context, "removed "+passengerData.getFname()+" "+passengerData.getLname()+" passengers", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            binding.layoutCreatePost.removeAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectAll(false);
                    addeduserslist.clear();
                    binding.layoutCreatePost.removeAll.setVisibility(View.GONE);
                    with.setVisibility(View.GONE);
                    rc_tagged.setAdapter(new UsersTaggedAdapter(addeduserslist, CreatePostActivity.this));
                }
            });
            if (!isSelectedAll) holder.select_radioButton.setChecked(false);
            else holder.select_radioButton.setChecked(true);

        }

        public void selectAll(boolean status) {
            Log.e("onClickSelectAll", "yes");
            isSelectedAll = status;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            LinearLayout main;

            TextView username;
            CheckBox select_radioButton;

            public MyViewHolder(View itemView) {
                super(itemView);
                main = itemView.findViewById(R.id.main);
                username = itemView.findViewById(R.id.username);
                select_radioButton = itemView.findViewById(R.id.select_radioButton);

            }
        }
    }

    public class UsersTaggedAdapter extends RecyclerView.Adapter<UsersTaggedAdapter.MyViewHolder> {
        ArrayList<UsersData> arrayList;
        Context context;
        boolean isSelectedAll;

        public UsersTaggedAdapter(ArrayList<UsersData> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.tagged_users_layout, parent, false);

            MyViewHolder myViewHolder = new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final UsersData usersData = arrayList.get(position);
            holder.username.setText(usersData.getName());

        }

        public void selectAll(boolean status) {
            Log.e("onClickSelectAll", "yes");
            isSelectedAll = status;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView username;

            public MyViewHolder(View itemView) {
                super(itemView);
                username = itemView.findViewById(R.id.tagged_username);


            }
        }
    }

}

