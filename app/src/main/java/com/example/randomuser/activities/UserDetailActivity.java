package com.example.randomuser.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.randomuser.R;
import com.example.randomuser.model.RandomUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        //-- Receive data
        Intent intent = getIntent();
        RandomUser user = intent.getParcelableExtra("user detail");

        assert user != null;
        String name = user.getName();
        String first = "About " + user.getName().split("\\s")[0];
        String avatarURL = user.getLargePictureURL();
        String mail = user.getEmail();
        String cell = user.getCell();
        String phone = user.getPhone();
        String location = user.getLocation();
        String dob = user.getDob();
        String registered = user.getRegistered();

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
