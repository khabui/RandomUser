package com.example.randomuser.presenter;

import android.content.Context;
import android.util.Log;

import com.example.randomuser.database.UserDatabase;
import com.example.randomuser.model.ApiResponse;
import com.example.randomuser.model.User;
import com.example.randomuser.network.RetrofitInstance;
import com.example.randomuser.network.UserInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserListPresenter implements UserListContract.Presenter {
    private static final String TAG = "UserListPresenter";

    private UserListContract.View userListView;
    private UserDatabase userDatabase;
    private CompositeDisposable disposable;

    public UserListPresenter(UserListContract.View userListView, Context context) {
        this.userListView = userListView;
        userDatabase = UserDatabase.getInstance(context);
        disposable = new CompositeDisposable();
    }

    @Override
    public void getDataFromURL(final int index, int limit, String nation) {
        UserInterface userInterface = RetrofitInstance.getRetrofitInstance().create(UserInterface.class);
//        userInterface.fetchUsers(limit, nation).enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (response.body() != null) {
//                    ApiResponse apiResponse = response.body();
//                    for (User user : apiResponse.getResults()) {
//                        userDatabase.userDao().insertUser(user);
//                    }
//
//                    boolean isLoadMore = (index != 0);
//                    List<User> userList = new ArrayList<>(userDatabase.userDao().getUserList());
//                    userListView.onGetDataSuccess(isLoadMore, userList);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                userListView.onGetDataFailure();
//            }
//        });

//        userInterface.fetchUsers(limit, nation)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<ApiResponse>() {
//                    @Override
//                    public void accept(ApiResponse apiResponse) throws Exception {
//                        handleData(index, apiResponse);
//                    }
//                });

//        userInterface.fetchUsers(limit, nation)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableObserver<ApiResponse>() {
//                    @Override
//                    public void onNext(ApiResponse response) {
//                        handleData(index, response);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: ", e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        userInterface.fetchUsers(limit, nation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse response) {
                        handleData(index, response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: called.");
                    }
                });
    }

    private void handleData(int index, ApiResponse response) {
        if (response != null) {
            for (User user : response.getResults()) {
                userDatabase.userDao().insertUser(user);
            }

            boolean isLoadMore = (index != 0);
            List<User> userList = new ArrayList<>(userDatabase.userDao().getUserList());
            userListView.onGetDataSuccess(isLoadMore, userList);
        } else {
            userListView.onGetDataFailure();
        }
    }

    @Override
    public void clearDatabase() {
        userDatabase.userDao().deleteAll();
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
    }
}
