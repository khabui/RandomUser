package com.example.randomuser.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.randomuser.R;
import com.example.randomuser.activities.MainActivity;
import com.example.randomuser.presenter.LoginContract;
import com.example.randomuser.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginPresenter loginPresenter;

    @BindView(R.id.loginMail)
    EditText loginMail;

    @BindView(R.id.loginPassword)
    EditText loginPassword;

    @BindView(R.id.loginButton)
    CardView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
    }

    public void loginBtnOnClick(View view) {
        String email = loginMail.getText().toString();
        String password = loginPassword.getText().toString();

        loginPresenter.validationLogin(email, password);
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFail() {
        Toast.makeText(LoginActivity.this, "Fail to login", Toast.LENGTH_SHORT).show();
    }
}
