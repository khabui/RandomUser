package com.example.randomuser.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.randomuser.R;
import com.example.randomuser.adapters.RecyclerViewAdapter;
import com.example.randomuser.model.User;
import com.example.randomuser.presenter.UserListContract;
import com.example.randomuser.presenter.UserListPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnUserListener, UserListContract.View {

    private static final String TAG = "MainActivity";

    private UserListContract.Presenter userListPresenter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter adapter;

    private boolean isLoading = false;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.loadingBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        userListPresenter = new UserListPresenter(this);

        onSetupRecyclerView();
    }

    private void onSetupRecyclerView() {
        userListPresenter.getDataFromURL(20, "us");
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        onSwipeToRefresh();
        onLoadMoreData();
    }

    private void onLoadMoreData() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoading && (linearLayoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1)) {
                    userListPresenter.loadMoreData(5, "us");
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public void onShowLoadingBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoadingBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNotLoading() {
        isLoading = false;
    }

    @Override
    public void onUserClick(int position) {
        Log.d(TAG, "onUserClick: clicked." + position);

        User user = userListPresenter.getUserData(position);

        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("user_detail", user);
        startActivity(intent);
    }

    @Override
    public void onGetDataSuccess(ArrayList<User> userList) {
        adapter = new RecyclerViewAdapter(userList, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onGetDataFailure() {
        Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();

                userListPresenter.clearUsers();
                adapter.notifyDataSetChanged();

                userListPresenter.getDataFromURL(20, "us");

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onAddMore() {
        adapter.notifyDataSetChanged();
    }
}
