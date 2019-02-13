package com.eleganzit.volunteerifyngo.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SocialArrayAdapter<PagesData> extends ArrayAdapter<PagesData> {

    private Filter filter;
    private final List<PagesData> items = new ArrayList<>();
    private final List<PagesData> tempItems = new ArrayList<>();

    public SocialArrayAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override
    public void add(@Nullable PagesData object) {
        add(object, true);
        Log.d("whereeeee","add(@Nullable PagesData object)");
    }

    @Override
    public void addAll(@NonNull Collection<? extends PagesData> collection) {
        super.addAll(collection);
        tempItems.addAll(collection);
        Log.d("whereeeee","addAll(@NonNull Collection<? extends PagesData> collection)");
    }

    @SafeVarargs
    @Override
    public final void addAll(PagesData... items) {
        super.addAll(items);
        Collections.addAll(tempItems, items);
        Log.d("whereeeee","addAll(PagesData... items)");
    }

    @Override
    public void remove(@Nullable PagesData object) {
        super.remove(object);
        tempItems.remove(object);
        Log.d("whereeeee","remove(@Nullable PagesData object)");
    }

    @Override
    public void clear() {
        clear(true);
        Log.d("whereeeee","clear()");
    }

    @NonNull
    public CharSequence convertToString(PagesData object) {

        Log.d("convertToString",object.toString()+"");
        Log.d("whereeeee","convertToString(PagesData object)");
        return object.toString();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    if (constraint == null) {
                        return new FilterResults();
                    } else {
                        items.clear();
                        int i=0;
                        for (final PagesData item : tempItems) {

                            i++;
                            Log.d("performFiltering",i+"     "+constraint.toString().toLowerCase(Locale.getDefault())+"      "+convertResultToString(item).toString().toLowerCase(Locale.getDefault())+"");
                            if (convertResultToString(item).toString()
                                    .toLowerCase(Locale.getDefault())
                                    .contains(constraint.toString().toLowerCase(Locale.getDefault()))) {
                                items.add(item);

                            }

                        }
                        Log.d("FilterResults1","    "+items.size());
                        final FilterResults results = new FilterResults();
                        results.values = items;
                        results.count = items.size();
                        Log.d("FilterResults2","    "+items.size());
                        notifyDataSetChanged();
                        Log.d("FilterResults3","    "+items.size());
                        Log.d("FilterResults4",results.values+"    "+items);


                        return results;
                    }
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results.values!=null)
                    {
                        Log.d("publishResults",results.values.toString()+"    "+results.count);
                    }

                    if (results instanceof List) {
                        final List<PagesData> list = (List<PagesData>) results;
                        if (results.count > 0) {
                            clear(false);
                            for (final PagesData object : list) {
                                add(object, true);
                            }
                            notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public CharSequence convertResultToString(Object resultValue) {
                    return convertToString((PagesData) resultValue);
                }
            };
        }
        return filter;
    }

    private void add(PagesData object, boolean affectTempItems) {
        super.add(object);

        if (affectTempItems) {
            Log.d("whereeeee","add(PagesData object, boolean affectTempItems)");
            tempItems.add(object);
            notifyDataSetChanged();
        }
    }

    private void clear(boolean affectTempItems) {
        super.clear();
        Log.d("whereeeee","clear(boolean affectTempItems)");
        if (affectTempItems) {
            tempItems.clear();
        }
    }
}