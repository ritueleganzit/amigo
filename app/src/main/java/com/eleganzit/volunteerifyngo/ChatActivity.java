package com.eleganzit.volunteerifyngo;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eleganzit.volunteerifyngo.adapter.ChatsAdapter;
import com.eleganzit.volunteerifyngo.model.ChatsData;
import com.eleganzit.volunteerifyngo.model.DateItem;
import com.eleganzit.volunteerifyngo.model.User;
import com.eleganzit.volunteerifyngo.utils.ListItem;
import com.eleganzit.volunteerifyngo.viewHolders.ReceivedMessageHolder;
import com.eleganzit.volunteerifyngo.viewHolders.SentMessageHolder;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class ChatActivity extends AppCompatActivity {

    //RecyclerView rc_chats;
    ArrayList<ChatsData> ar_chats=new ArrayList<>();
    ArrayList<ListItem> listItems=new ArrayList<>();
    ImageView caution,options;
    EditText type_message;
    RelativeLayout relsend;
    //ChatsAdapter chatsAdapter;
    MessagesList messagesList;
    protected MessagesListAdapter<ChatsData> messagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        caution=findViewById(R.id.caution);
        options=findViewById(R.id.options);
        //rc_chats=findViewById(R.id.rc_chats);
        type_message=findViewById(R.id.type_message);
        relsend=findViewById(R.id.relsend);
        messagesList = findViewById(R.id.messagesList);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        //rc_chats.setLayoutManager(layoutManager);

        User zahir=new User("0","Zahir");
        final User ritu=new User("1","Ritu");

        ChatsData chatsData1=new ChatsData("1",msgDate("01-02-2018"),"","zahir","hi","",zahir);
        ChatsData chatsData2=new ChatsData("2",msgDate("01-02-2018"),"","","hello","",ritu);
        ChatsData chatsData3=new ChatsData("3",msgDate("02-02-2018"),"","zahir","how are you","",zahir);
        ChatsData chatsData4=new ChatsData("4",msgDate("02-02-2018"),"","zahir","im finne","",ritu);
        ChatsData chatsData5=new ChatsData("5",msgDate("05-02-2018"),"","","you","",ritu);
        ChatsData chatsData6=new ChatsData("6",msgDate("08-02-2018"),"","","me too","",zahir);
        ChatsData chatsData7=new ChatsData("7",msgDate("10-02-2018"),"","zahir","ok","",ritu);
        ChatsData chatsData8=new ChatsData("8",msgDate("10-02-2018"),"","","ok","",zahir);


        MessageHolders holdersConfig = new MessageHolders()
                .setIncomingTextConfig(
                        ReceivedMessageHolder.class,
                        R.layout.received_message_layout)
                .setOutcomingTextConfig(
                        SentMessageHolder.class,
                        R.layout.sent_message_layout);

        messagesAdapter = new MessagesListAdapter<>("0", holdersConfig, null);



        messagesAdapter.setLoadMoreListener(new MessagesListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Toast.makeText(ChatActivity.this, "Load more...", Toast.LENGTH_SHORT).show();
            }
        });
        messagesAdapter.setDateHeadersFormatter(new DateFormatter.Formatter() {
            @Override
            public String format(Date date) {
                if (DateFormatter.isToday(date)) {
                    return getString(R.string.date_header_today);
                } else if (DateFormatter.isYesterday(date)) {
                    return getString(R.string.date_header_yesterday);
                } else {
                    return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
                }
            }
        });

        messagesAdapter.addToStart(chatsData1, true);
        messagesAdapter.addToStart(chatsData2, true);
        messagesAdapter.addToStart(chatsData3, true);
        messagesAdapter.addToStart(chatsData4, true);
        messagesAdapter.addToStart(chatsData5, true);
        messagesAdapter.addToStart(chatsData6, true);
        messagesAdapter.addToStart(chatsData7, true);
        messagesAdapter.addToStart(chatsData8, true);

        ar_chats.add(chatsData1);
        ar_chats.add(chatsData2);
        ar_chats.add(chatsData3);
        ar_chats.add(chatsData4);
        ar_chats.add(chatsData5);
        ar_chats.add(chatsData6);
        ar_chats.add(chatsData7);
        ar_chats.add(chatsData8);


        messagesList.setAdapter(messagesAdapter);
//
//        HashMap<String, List<ChatsData>> groupedHashMap = groupDataIntoHashMap(ar_chats);
//
//        for (String date : groupedHashMap.keySet()) {
//            DateItem dateItem = new DateItem();
//            dateItem.setDate(date);
//            listItems.add(dateItem);
//
//            for (ChatsData pojoOfJsonArray : groupedHashMap.get(date)) {
//                GeneralItem generalItem = new GeneralItem();
//                generalItem.setPojoOfJsonArray(pojoOfJsonArray);//setBookingDataTabs(bookingDataTabs);
//                listItems.add(generalItem);
//            }
//        }
//
//        chatsAdapter=new ChatsAdapter(listItems,ChatActivity.this);
//
//       /* rc_chats.setAdapter(chatsAdapter);
//        rc_chats.scrollToPosition(ar_chats.size()-1);
//        type_message.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus)
//                {
//                    rc_chats.scrollToPosition(ar_chats.size()-1);
//                    Toast.makeText(ChatActivity.this, "focused", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//*/
        relsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type_message.getText().toString().equalsIgnoreCase(""))
                {

                }
                else
                {

                    User zahir=new User("0","Zahir");
                    ChatsData chatsData1=new ChatsData("0",new Date(),"","zahir",type_message.getText().toString(),"",zahir);

                    messagesAdapter.addToStart(chatsData1, true);
                    type_message.setText("");


                    /*ChatsData chatsData=new ChatsData("09/01/2019","","zahir",type_message.getText().toString(),"");

                    ar_chats.add(chatsData);
                    chatsAdapter=new ChatsAdapter(ar_chats,ChatActivity.this);
                    chatsAdapter.notifyItemInserted(ar_chats.size()-1);
                    rc_chats.scrollToPosition(ar_chats.size()-1);
                    type_message.setText("");*/
                }

            }
        });

        caution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog=new Dialog(ChatActivity.this);
                dialog.setContentView(R.layout.caution_layout);

                dialog.show();
            }
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ChatActivity.this, options);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.chat_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        //Toast.makeText(ChatActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

    }

    public Date msgDate(String sdate)
    {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = format.parse(sdate);
            System.out.println(date);
            Log.d("exccccccc"," try "+date);
        } catch (ParseException e) {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();

            Log.d("exccccccc","catch "+e);
            e.printStackTrace();
        }

        return date;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
