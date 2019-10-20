package com.example.randomuser.presenter;

import com.example.randomuser.model.ApiResponse;
import com.example.randomuser.model.User;
import com.example.randomuser.network.RetrofitInstance;
import com.example.randomuser.network.UserInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListPresenter implements UserListContract.Presenter {

    private UserListContract.View userListView;

    public UserListPresenter(UserListContract.View userListView) {
        this.userListView = userListView;
    }

    @Override
    public void getDataFromURL(final int index, int limit, String nation) {
        UserInterface userInterface = RetrofitInstance.getRetrofitInstance().create(UserInterface.class);
        userInterface.fetchUsers(limit, nation).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    List<User> userList = apiResponse.getResults();

                    boolean isLoadMore = (index != 0);
                    userListView.onGetDataSuccess(isLoadMore, userList);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                userListView.onGetDataFailure();
            }
        });
    }

}
