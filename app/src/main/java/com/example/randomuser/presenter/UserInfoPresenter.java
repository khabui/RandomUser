package com.example.randomuser.presenter;

import com.example.randomuser.model.User;

public class UserInfoPresenter implements UserInfoContract.Presenter {

    private UserInfoContract.View userInfoView;

    public UserInfoPresenter(UserInfoContract.View userInfoView) {
        this.userInfoView = userInfoView;
    }

    @Override
    public void getUserInfo(User user) {
        userInfoView.showUserInfo(user);
    }
}
