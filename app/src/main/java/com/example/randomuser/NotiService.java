package com.example.randomuser;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

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
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

import static com.example.randomuser.MainApplication.CHANNEL_ID;

public class NotiService extends Service {

    private static final String TAG = "NotiService";

    private UserDatabase database;
    private int numberOfUser = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        database = UserDatabase.getInstance(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        addUser();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("TestDatabase Notification")
                .setContentText("Current number of user: " + numberOfUser)
                .setSmallIcon(R.drawable.ic_android)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    private void addUser() {
        UserInterface userInterface = RetrofitInstance.getRetrofitInstance().create(UserInterface.class);
        final Single<List<User>> singleLocal = database.userDao().getUserList();

        final Single<List<User>> singleRemote = userInterface.fetchUsers(1, "us")
                .flatMap(new Function<ApiResponse, SingleSource<List<User>>>() {
                    @Override
                    public SingleSource<List<User>> apply(ApiResponse response) {
                        List<User> users = response.getResults();
                        Log.w("TAG", "remote: " + users.size());

                        for (User user : users) {
                            database.userDao().insertUser(user);
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
                        numberOfUser = users.size();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
