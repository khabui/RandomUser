package com.example.randomuser.presenter;

import android.os.Handler;

import com.example.randomuser.model.ApiResponse;
import com.example.randomuser.model.User;
import com.example.randomuser.network.RetrofitInstance;
import com.example.randomuser.network.UserInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListPresenter implements UserListContract.Presenter {

    private UserListContract.View userListView;
    private ArrayList<User> userList;

    public UserListPresenter(UserListContract.View userListView) {
        this.userListView = userListView;
    }


    @Override
    public void clearUsers() {
        userList.clear();
    }

    @Override
    public void getDataFromURL(int numberOfUsers, String nation) {
        UserInterface userInterface = RetrofitInstance.getRetrofitInstance().create(UserInterface.class);
        userInterface.fetchUsers(numberOfUsers, nation).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    userList = apiResponse.getResults();
                    userListView.onGetDataSuccess(userList);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                userListView.onGetDataFailure();
            }
        });
    }

    @Override
    public void loadMoreData(int numberOfUsers, String nation) {
        userListView.onShowLoadingBar();
        final int users = numberOfUsers;
        final String nat = nation;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UserInterface userInterface = RetrofitInstance.getRetrofitInstance().create(UserInterface.class);
                userInterface.fetchUsers(users, nat).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.body() != null) {
                            ApiResponse apiResponse = response.body();
                            userList.addAll(apiResponse.getResults());
                            userListView.onAddMore();
                            userListView.onHideLoadingBar();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        userListView.onGetDataFailure();
                    }
                });

                userListView.onNotLoading();
            }
        }, 1000);
    }

    @Override
    public User getUserData(int position) {
        User user = userList.get(position);
        if (user != null) {
            return user;
        }
        return null;
    }

}
