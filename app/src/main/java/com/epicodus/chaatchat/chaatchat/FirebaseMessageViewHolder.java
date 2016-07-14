package com.epicodus.chaatchat.chaatchat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Guest on 7/14/16.
 */
public class FirebaseMessageViewHolder extends RecyclerView.ViewHolder {
    private View mView;
    private Context mContext;

    public FirebaseMessageViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindMessage(Message message) {
        TextView usernameTextView = (TextView) mView.findViewById(R.id.usernameTextView);
        TextView messageTextView = (TextView) mView.findViewById(R.id.messageTextView);

        usernameTextView.setText(message.getUser());
        messageTextView.setText(message.getMessage());
    }

}
