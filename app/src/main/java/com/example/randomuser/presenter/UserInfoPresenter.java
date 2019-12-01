package com.example.randomuser.presenter;

import com.example.randomuser.model.User;

public class UserInfoPresenter implements UserInfoContract.Presenter {

    private UserInfoContract.View userInfoView;

    public UserInfoPresenter(UserInfoContract.View userInfoView) {
        this.userInfoView = userInfoView;
    }

    @Override
    public void getUserInfo() {
        // TODO: Get user from database / shared preferences / api_network
//        User user = getUserFromDataSource();
        User user = new User();
        userInfoView.showUserInfo(user);
    }
}
