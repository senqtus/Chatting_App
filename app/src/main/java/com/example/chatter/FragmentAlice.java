package com.example.chatter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chatter.data.Chat;
import com.example.chatter.data.Storage;
import com.example.chatter.data.StorageImplementation;
import com.example.chatter.data.TextMessage;

import java.util.ArrayList;

public class FragmentAlice extends Fragment {

    public static String NOTIFICATION = "NOTIFICATION";
    public static String NOTIFICATION_DATA = "DATA";
    private static FragmentAliceMCBroadcast fragmentAliceMCBroadcast;
    private static AliceChatArrayAdapter aliceChatArrayAdapter;
    private static ArrayList<TextMessage> chat;
    private static Context context;
    private static ListView aliceListView;
    private EditText messageInput;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment_alice, container, false);
        messageInput = view.findViewById(R.id.messageA);
        context = getContext();
        aliceListView = view.findViewById(R.id.aliceChat);
        aliceChatArrayAdapter = new AliceChatArrayAdapter(context, 0, new ArrayList<TextMessage>());
        chat = new ArrayList<TextMessage>();
        view.findViewById(R.id.sendToB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat.addMessage(getContext(), messageInput.getText().toString(), true);
                Intent intent = new Intent();
                intent.setAction(NOTIFICATION);
                intent.putExtra(NOTIFICATION_DATA, "Hello Fragment Bob");
                getActivity().sendBroadcast(intent);
                messageInput.setText("");
                getData();
                fillData();
            }
        });
        registerBroadCast();
        getData();
        fillData();
        return view;
    }

    private static void getData()
    {
        Storage storage = new StorageImplementation();
        Object storageAsObject = storage.getObject(context, Chat.STORAGE_KEY, Chat.class);

        Chat chatStorage;
        if (storageAsObject != null) {
            chatStorage = (Chat) storageAsObject;
        } else {
            chatStorage = new Chat();
        }
        chat = chatStorage.getMessages();
    }

    private static void fillData()
    {
        aliceChatArrayAdapter.clear();
        aliceListView.setAdapter(aliceChatArrayAdapter);
        aliceChatArrayAdapter.addAll(chat);
    }

    private void registerBroadCast()
    {
        fragmentAliceMCBroadcast = new FragmentAliceMCBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(FragmentBob.NOTIFICATION);
        getActivity().registerReceiver(fragmentAliceMCBroadcast, filter);
    }


    public static class FragmentAliceMCBroadcast extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.hasExtra(FragmentBob.NOTIFICATION_DATA))
            {
                Storage storage = new StorageImplementation();
                Object storageAsObject = storage
                        .getObject(context, Chat.STORAGE_KEY, Chat.class);

                Chat chatStorage;
                if (storageAsObject != null) {
                    chatStorage = (Chat) storageAsObject;
                } else {
                    chatStorage = new Chat();
                }
                getData();
                fillData();
            }
        }
    }

    public class AliceChatArrayAdapter extends ArrayAdapter<TextMessage>
    {
        private Context context;

        public AliceChatArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TextMessage> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_alice, parent, false);
            final TextMessage textMessage = getItem(position);

            TextView message = view.findViewById(R.id.aliceMessage);
            message.setText(textMessage.getMessage());
            if (textMessage.isSender()) {
                message.setTextColor(getResources().getColor(R.color.aliceText));
                message.setGravity(Gravity.LEFT);
            }
            else {
                message.setTextColor(getResources().getColor(R.color.bobText));
                message.setGravity(Gravity.RIGHT);
            }
            return view;
        }
    }
}
