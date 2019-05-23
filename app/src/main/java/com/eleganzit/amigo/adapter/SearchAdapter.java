package com.eleganzit.amigo.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.amigo.R;
import com.eleganzit.amigo.model.PagesData;
import com.eleganzit.amigo.model.SearchData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable
{

    private List<SearchData> searchList;
    private List<SearchData> searchListFiltered;
    Context context;
    Activity activity;
    private SearchAdapterListener listener;

    public SearchAdapter(List<SearchData> searches, Context context, SearchAdapterListener listener) {
        this.searchList = searches;
        this.searchListFiltered = searches;
        this.listener = listener;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_layout,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        final SearchData searchData = searchList.get(i);
        holder.name.setText(searchData.getFullname());
        Glide
                .with(context)
                .asBitmap()
                .load(searchData.getPhoto())
                .thumbnail(.1f)
                .apply(new RequestOptions().override(80,80).centerCrop())
                .into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return searchListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    searchListFiltered = searchList;
                } else {
                    List<SearchData> filteredList = new ArrayList<>();
                    for (SearchData row : searchList) {
                        Log.d("wwwwwwwwwwwwww",charString.toLowerCase()+"");
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFullname().toLowerCase().contains(charString.toLowerCase()) || row.getUsername().contains(charSequence) || row.getCity().contains(charSequence) || row.getEmail().contains(charSequence) || row.getHometown().contains(charSequence) || row.getState().contains(charSequence) ) {
                            filteredList.add(row);
                        }
                    }

                    searchListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = searchListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                searchListFiltered = (ArrayList<SearchData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            photo=itemView.findViewById(R.id.pagePhoto);
            name=itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onSearchSelected(searchListFiltered.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface SearchAdapterListener {
        void onSearchSelected(SearchData searchData);
    }
}