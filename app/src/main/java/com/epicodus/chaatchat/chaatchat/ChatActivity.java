package com.epicodus.chaatchat.chaatchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = ChatActivity.class.getSimpleName();
    @Bind(R.id.messageEditText) EditText mMessageEditText;
    @Bind(R.id.sendButton) Button mSendButton;

    private DatabaseReference mSentMessage;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private MessageListAdapter mAdapter;
    public ArrayList<Message> mMessages = new ArrayList<>();

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        mSentMessage = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SENT_MESSAGE);

        mSentMessage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                mMessages.clear();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, messageSnapshot.toString());
                    mMessages.add(messageSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setUpFireBaseAdapter();

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        mSendButton.setOnClickListener(this);

        mAdapter = new MessageListAdapter(getApplicationContext(), mMessages);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    private void setUpFireBaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Message, FirebaseMessageViewHolder>(Message.class, R.layout.activity_chat_message_view_list, FirebaseMessageViewHolder.class, mSentMessage) {
            @Override
            protected void populateViewHolder(FirebaseMessageViewHolder viewHolder, Message model, int position) {
                viewHolder.bindMessage(model);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == mSendButton) {
            String messageText = mMessageEditText.getText().toString();
            if (messageText.equals("")) {
                Log.d(TAG, "Empty message");
            } else {
                Message message = new Message(userName, messageText);
                saveMessageToFirebase(message);
                Log.d(TAG, "Message: " + message.getMessage());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    public void saveMessageToFirebase(Message message) {
        mSentMessage = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_SENT_MESSAGE);
        mSentMessage.push().setValue(message);
    }
//    private void getMessages(String message) {
//        public void run() {
//        }
//    }
}
