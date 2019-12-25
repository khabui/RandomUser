package com.example.randomuser.presenter;

import com.example.randomuser.model.User;

import java.util.List;

// Interaction with View and
public interface UserListContract {
    interface Presenter {
        // limit is number items of page
        // offset is current last index of the list
        void getDataFromURL(int offset, int limit, String nation);

        void clearDatabase();

        void onDestroy();
    }

    interface View {
        void onGetDataSuccess(boolean isLoadMore, List<User> userList);

        void onGetDataFailure();

        void onSwipeToRefresh();

        void onShowLoadingBar();

        void onHideLoadingBar();
    }
}
