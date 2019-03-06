package com.eleganzit.amigo.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.model.ChatsData;
import com.eleganzit.amigo.model.ReceivedItem;
import com.eleganzit.amigo.utils.ListItem;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter
{
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_DATE = 3;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    ArrayList<ListItem> arrayList;
    Context context;
    private String old_date,new_date;

    public ChatsAdapter(ArrayList<ListItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position).getType();
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sent_message_layout, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.received_message_layout, parent, false);
            return new ReceivedMessageHolder(view);
        }
        /*else if (viewType == VIEW_TYPE_DATE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.date_layout, parent, false);
            return new DateHolder(view);
        }
*/
        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int size=arrayList.size();

        ReceivedItem message= (ReceivedItem) arrayList.get(position);

        switch (holder.getItemViewType()) {
          /*  case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_DATE:
                ((DateHolder) holder).bind(message);*/

        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText,sent_time,dateText;
        RelativeLayout date;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.messageText);
            sent_time = itemView.findViewById(R.id.sent_time);
            dateText = itemView.findViewById(R.id.dateText);
            date = itemView.findViewById(R.id.date);
        }

        /*void bind(ReceivedItem message) {
            if(old_date!=null)
            {
                if(old_date.equalsIgnoreCase(message.getPojoOfJsonArray().getDate()))
                {
                    Log.d("whereeee","ifff "+old_date);
                    date.setVisibility(View.GONE);
                }
                else
                {
                    Log.d("whereeee","elseee "+old_date);
                    date.setVisibility(View.VISIBLE);
                }
                old_date=message.getPojoOfJsonArray().getDate();

            }

            old_date=message.getPojoOfJsonArray().getDate();

            messageText.setText(message.getPojoOfJsonArray().getMessage());
            dateText.setText(message.getPojoOfJsonArray().getDate());
            // Format the stored timestamp into a readable String using method.
            //timeText.setText(message.getTime());
        }*/
    }

   /* private class DateHolder extends RecyclerView.ViewHolder {
        TextView dateText;

        DateHolder(View itemView) {
            super(itemView);
            dateText = (TextView) itemView.findViewById(R.id.dateText);
        }

        void bind(ChatsData message) {

            // Format the stored timestamp into a readable String using method.
            dateText.setText(message.getDate());
        }
    }*/

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText,received_time,dateText;
        RelativeLayout date;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.messageText);
            received_time = itemView.findViewById(R.id.received_time);
            dateText = itemView.findViewById(R.id.dateText);
            date = itemView.findViewById(R.id.date);
        }

        /*void bind(ReceivedItem message) {
            if(old_date!=null)
            {
                if(old_date.equalsIgnoreCase(message.getDate()))
                {
                    Log.d("whereeee","ifff "+old_date);
                    date.setVisibility(View.GONE);
                }
                else
                {
                    Log.d("whereeee","elseee "+old_date);
                    date.setVisibility(View.VISIBLE);
                }
                old_date=message.getDate();

            }

            old_date=message.getDate();

            messageText.setText(message.getMessage());
            dateText.setText(message.getDate());
            // Format the stored timestamp into a readable String using method.
            //timeText.setText(message.getTime());

            //nameText.setText(message.getName());


        }*/
    }
}
