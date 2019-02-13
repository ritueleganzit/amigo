package com.eleganzit.volunteerifyngo.adapter;

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
import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.PagesData;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MentionsRecyclerAdapter extends RecyclerView.Adapter<MentionsRecyclerAdapter .MyViewHolder>
        implements Filterable {
    private Context context;
    private List<PagesData> contactList;
    private List<PagesData> contactListFiltered;
    private ContactsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public MentionsRecyclerAdapter(Context context, List<PagesData> contactList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_pages_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PagesData pagesData = contactListFiltered.get(position);
        holder.name.setText(pagesData.getTitle());
        //holder.phone.setText(pagesData.getPhone());
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().replace("@","");

                String path=String.valueOf(charSequence);

                charString=path.substring(path.lastIndexOf("@")+1);

                Log.d("mmmmmmmmmm",charString+"");
                /*String[] filteredchar = charString.split(" ");
                ArrayList<String> arrayList=new ArrayList<>();
                for ( String ss : filteredchar) {
*//*

                    charString=ss;
*//*

                    arrayList.add(ss);
                    if(arrayList!=null && !arrayList.isEmpty() && arrayList.size()>1)
                    Log.d("arrayList",arrayList+"");

                }

                if(arrayList.size()>1)
                {
                    charString=arrayList.get(arrayList.size()-1);
                    Log.d("filteredchar",""+arrayList.get(arrayList.size()-1));
                }
*/
                //Log.d("filteredchar",""+charString);

                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                    Log.d("zzzzzzzzzz","is empty");
                } else {
                    Log.d("zzzzzzzzzz","not empty  "+charString);
                    List<PagesData> filteredList = new ArrayList<>();
                    for (PagesData row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        Log.d("contactListFiltered",row.getTitle().toLowerCase()+"    "+charSequence);
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getTitle().contains(charString)) {
                            filteredList.add(row);

                        }
                    }

                    contactListFiltered = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<PagesData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(PagesData pagesData);
    }
}
