package com.example.randomuser.presenter;

import com.example.randomuser.model.User;

import java.util.ArrayList;

public interface UserListContract {
    interface Presenter {
        void clearUsers();

        void getDataFromURL(int numberOfUsers, String nation);

        void loadMoreData(int numberOfUsers, String nation);

        User getUserData(int position);
    }

    interface View {
        void onGetDataSuccess(ArrayList<User> userList);

        void onGetDataFailure();

        void onSwipeToRefresh();

        void onAddMore();

        void onShowLoadingBar();

        void onHideLoadingBar();

        void onNotLoading();
    }
}
