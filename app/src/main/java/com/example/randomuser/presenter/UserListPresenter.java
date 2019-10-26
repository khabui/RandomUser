package com.example.randomuser.presenter;

import android.content.Context;

import com.example.randomuser.database.UserDatabase;
import com.example.randomuser.model.ApiResponse;
import com.example.randomuser.model.User;
import com.example.randomuser.network.RetrofitInstance;
import com.example.randomuser.network.UserInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListPresenter implements UserListContract.Presenter {

    private UserListContract.View userListView;
    private UserDatabase userDatabase;

    public UserListPresenter(UserListContract.View userListView, Context context) {
        this.userListView = userListView;
        userDatabase = UserDatabase.getInstance(context);
    }

    @Override
    public void getDataFromURL(final int index, int limit, String nation) {
        UserInterface userInterface = RetrofitInstance.getRetrofitInstance().create(UserInterface.class);
        userInterface.fetchUsers(limit, nation).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    for (User user : apiResponse.getResults()) {
                        userDatabase.userDao().insertUser(user);
                    }

                    boolean isLoadMore = (index != 0);
                    List<User> userList = new ArrayList<>(userDatabase.userDao().getUserList());
                    userListView.onGetDataSuccess(isLoadMore, userList);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                userListView.onGetDataFailure();
            }
        });
    }

    @Override
    public void clearDatabase() {
        userDatabase.userDao().deleteAll();
    }
}
