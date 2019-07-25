package com.example.randomuser.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.randomuser.R;
import com.example.randomuser.adapters.RecyclerViewAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        //-- Receive data
        String name = getIntent().getExtras().getString("user_name");
        String first = "About " + getIntent().getExtras().getString("user_first");
        String avatarURL = getIntent().getExtras().getString("user_avatar");
        String mail = getIntent().getExtras().getString("user_mail");
        String cell = getIntent().getExtras().getString("user_cell");
        String phone = getIntent().getExtras().getString("user_phone");
        String location = getIntent().getExtras().getString("user_location");
        String dob = getIntent().getExtras().getString("user_dob");
        String registered = getIntent().getExtras().getString("user_registered");

        //-- Init views
        TextView userName = findViewById(R.id.user_detail_name);
        TextView userFirst = findViewById(R.id.user_about);
        TextView userMail = findViewById(R.id.user_detail_mail);
        TextView userCell = findViewById(R.id.user_detail_cell);
        TextView userPhone = findViewById(R.id.user_detail_phone);
        TextView userLocation = findViewById(R.id.user_detail_location);
        TextView userDoB = findViewById(R.id.user_detail_dob);
        TextView userRegistered = findViewById(R.id.user_detail_registered);

        CircleImageView userAvatar = findViewById(R.id.user_avatar);

        //-- Setting value to view
        userName.setText(name);
        userFirst.setText(first);
        userMail.setText(mail);
        userCell.setText(cell);
        userPhone.setText(phone);
        userLocation.setText(location);
        userDoB.setText(dob);
        userRegistered.setText(registered);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this)
                .asBitmap()
                .load(avatarURL)
                .apply(options)
                .into(userAvatar);
    }
}
