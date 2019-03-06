package com.eleganzit.amigo.viewHolders;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleganzit.amigo.R;
import com.eleganzit.amigo.model.ChatsData;
import com.stfalcon.chatkit.messages.MessageHolders;

public class SentMessageHolder extends MessageHolders.BaseMessageViewHolder<ChatsData>{
    TextView messageText,sent_time,dateText;
    RelativeLayout date;

    public SentMessageHolder(View itemView, Object payload) {
        super(itemView, payload);

        messageText=itemView.findViewById(R.id.messageText);
        sent_time=itemView.findViewById(R.id.sent_time);
        dateText=itemView.findViewById(R.id.dateText);

    }

    @Override
    public void onBind(ChatsData chatsData) {

        messageText.setText(chatsData.getText());
        sent_time.setText(chatsData.getTime());

    }
}