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

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

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
        final Single<List<User>> singleLocal = userDatabase.userDao().getUserList();

        final Single<List<User>> singleRemote = userInterface.fetchUsers(limit, nation)
                .flatMap(new Function<ApiResponse, SingleSource<List<User>>>() {
                    @Override
                    public SingleSource<List<User>> apply(ApiResponse response) {
                        List<User> users = response.getResults();
                        Log.w("TAG", "remote: " + users.size());

                        for (User user : users) {
                            userDatabase.userDao().insertUser(user);
                        }
                        return Single.just(users);
                    }
                });


        List<Single<List<User>>> singleObservableList = new ArrayList<>();
        singleObservableList.add(singleLocal);
        singleObservableList.add(singleRemote);

        Single.merge(singleObservableList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<List<User>>() {
                    @Override
                    public void onNext(List<User> users) {
                        Log.w("TAG", "onNext: " + users.size());
                        boolean isLoadMore = index != 0;
                        userListView.onGetDataSuccess(isLoadMore, users);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        userListView.onGetDataFailure();
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
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
