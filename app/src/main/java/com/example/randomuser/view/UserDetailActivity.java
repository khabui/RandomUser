package com.example.randomuser.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.randomuser.R;
import com.example.randomuser.model.User;
import com.example.randomuser.presenter.UserInfoContract;
import com.example.randomuser.util.TextUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends AppCompatActivity implements UserInfoContract.View {

    @BindView(R.id.user_detail_name)
    TextView userName;

    @BindView(R.id.user_about)
    TextView userAbout;

    @BindView(R.id.user_detail_mail)
    TextView userMail;

    @BindView(R.id.user_detail_cell)
    TextView userCell;

    @BindView(R.id.user_detail_phone)
    TextView userPhone;

    @BindView(R.id.user_detail_location)
    TextView userLocation;

    @BindView(R.id.user_detail_dob)
    TextView userDoB;

    @BindView(R.id.user_detail_registered)
    TextView userRegistered;

    @BindView(R.id.user_avatar)
    CircleImageView userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        // add back button to view
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // bind the view using ButterKnife
        ButterKnife.bind(this);

        // receive data
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("user_detail");
        if (user != null) {
            showUserInfo(user);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showUserInfo(User user) {
        // set value to view
        userName.setText(TextUtil.getFullName(user));
        userAbout.setText(TextUtil.about(user));
        userMail.setText(user.getEmail());
        userCell.setText(user.getCell());
        userPhone.setText(user.getPhone());
        userLocation.setText(TextUtil.getFullLocation(user));
        userDoB.setText(TextUtil.dateAndTime(user.getDob().getDate()));
        userRegistered.setText(TextUtil.dateAndTime(user.getRegistered().getDate()));

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this)
                .asBitmap()
                .load(user.getPicture().getLarge())
                .apply(options)
                .into(userAvatar);
    }
}
