package com.example.randomuser.presenter;

import com.example.randomuser.model.User;

public interface UserInfoContract {
    interface Presenter {
        void getUserInfo();
    }

    interface View {
        void showUserInfo(User user);
    }
}
