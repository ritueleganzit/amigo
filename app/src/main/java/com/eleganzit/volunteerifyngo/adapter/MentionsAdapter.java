package com.eleganzit.volunteerifyngo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.PagesData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.Person;
import androidx.recyclerview.widget.RecyclerView;

public class MentionsAdapter extends SocialArrayAdapter<PagesData>
{

    ArrayList<PagesData> pages;
    Context context;
    Activity activity;
    boolean liked=false;

    public MentionsAdapter(@NonNull Context context,ArrayList<PagesData> pages) {
        super(context, R.layout.search_pages_layout, R.id.name);
        this.context=context;
        this.pages=pages;
    }

    @Override
    public CharSequence convertToString(PagesData object) {
        return object.getTitle();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PagesData pagesData=pages.get(position);
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.search_pages_layout,null);
/*

        TextView title=convertView.findViewById(R.id.name);

        title.setText(pagesData.getTitle());
*/

        return convertView;
    }
}
