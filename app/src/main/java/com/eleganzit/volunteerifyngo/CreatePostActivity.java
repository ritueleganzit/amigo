package com.eleganzit.volunteerifyngo;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.eleganzit.volunteerifyngo.model.Tweet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreatePostActivity extends AppCompatActivity {

    MultiAutoCompleteTextView ed_status;
    LinearLayout add_photo,add_tag;
    private static final int SELECT_PICTURE = 100;

    BottomSheetBehavior sheetBehavior;
    LinearLayout layoutBottomSheet;

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

        ed_status=findViewById(R.id.ed_status);
        add_photo=findViewById(R.id.add_photo);
        add_tag=findViewById(R.id.add_tag);

        add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(CreatePostActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.tags_bottom_sheet, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();*/
            }
        });
        /*ed_status.setTokenizer(new MultiAutoCompleteTextView.Tokenizer() {
            @Override
            public CharSequence terminateToken(CharSequence text) {
                int i = text.length();

                while (i > 0 && text.charAt(i - 1) == ' ') {
                    i--;
                }

                if (i > 0 && text.charAt(i - 1) == ' ') {
                    return text;
                } else {
                    if (text instanceof Spanned) {
                        SpannableString sp = new SpannableString(text + " ");
                        TextUtils.copySpansFrom((Spanned) text, 0, text.length(), Object.class, sp, 0);
                        return sp;
                    } else {
                        return text + " ";
                    }
                }
            }

            @Override
            public int findTokenStart(CharSequence text, int cursor) {
                int i = cursor;

                while (i > 0 && text.charAt(i - 1) != '@') {
                    i--;
                }

                //Check if token really started with @, else we don't have a valid token
                if (i < 1 || text.charAt(i - 1) != '@') {
                    Toast.makeText(CreatePostActivity.this, "do not open suggestion", Toast.LENGTH_SHORT).show();
                    return cursor;
                }
                else
                {
                    Toast.makeText(CreatePostActivity.this, "open suggestion", Toast.LENGTH_SHORT).show();
                }

                return i;
            }

            @Override
            public int findTokenEnd(CharSequence text, int cursor) {
                int i = cursor;
                int len = text.length();

                while (i < len) {
                    if (text.charAt(i) == ' ') {
                        return i;
                    } else {
                        i++;
                    }
                }

                return len;
            }
        });
*/
        ed_status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {


                    Log.d("dataaaaaaaaaaaaaaa","charSequence  : "+charSequence+",    i : "+i+",   i1 : "+i1+",   i2 : "+i2);
                    //Toast.makeText(CreatePostActivity.this, "charSequence  : "+charSequence+",    i : "+i+",   i1 : "+i1+",   i2 : "+i2, Toast.LENGTH_SHORT).show();
                    // Todo: query mentions
                   /* Matcher mentionMatcher = Pattern.compile("@([A-Za-z0-9_-]+)").matcher(charSequence.toString());
                    // while matching
                    while (mentionMatcher.find()) {
                        String yourSearchText = charSequence.toString().substring(mentionMatcher.start() + 1, mentionMatcher.end());
                        // do query with yourSearchText below

                        Toast.makeText(CreatePostActivity.this, yourSearchText+"", Toast.LENGTH_SHORT).show();
                    }*/

                    if(isMention(charSequence.toString()))
                    {
                        Toast.makeText(CreatePostActivity.this, "is mention ", Toast.LENGTH_SHORT).show();
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
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
