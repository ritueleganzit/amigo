package com.eleganzit.volunteerifyngo.viewHolders;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleganzit.volunteerifyngo.R;
import com.eleganzit.volunteerifyngo.model.ChatsData;
import com.stfalcon.chatkit.messages.MessageHolders;

public class ReceivedMessageHolder extends MessageHolders.BaseMessageViewHolder<ChatsData>{

    TextView sender_name,messageText,received_time,dateText;
    RelativeLayout date;

    public ReceivedMessageHolder(View itemView, Object payload) {
        super(itemView, payload);


        sender_name=itemView.findViewById(R.id.sender_name);
        messageText=itemView.findViewById(R.id.messageText);
        dateText=itemView.findViewById(R.id.dateText);
        received_time=itemView.findViewById(R.id.received_time);

    }

    @Override
    public void onBind(ChatsData chatsData) {

        sender_name.setText(chatsData.getUser().getName());
        messageText.setText(chatsData.getText());
        received_time.setText(chatsData.getTime());

    }
}
