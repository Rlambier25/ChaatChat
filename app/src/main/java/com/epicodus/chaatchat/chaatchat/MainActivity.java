package com.epicodus.chaatchat.chaatchat;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.logInButton) Button mLogInButton;
    @Bind(R.id.emailEditText) EditText mEmailEditText;
    @Bind(R.id.passwordEditText) EditText mPasswordEditText;
    @Bind(R.id.appNameTextView) TextView mAppNameTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        mLogInButton.setOnClickListener(this);

//        Typeface stockyFont = Typeface.createFromAsset(getAssets(), "fonts/stocky.ttf");
//        mAppNameTextView.setTypeface(stockyFont);
    }

    @Override
    public void onClick(View v) {
        if (v == mLogInButton) {
            String userName = mEmailEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        }
    }
}
