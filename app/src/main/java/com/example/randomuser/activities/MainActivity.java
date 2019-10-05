package com.example.randomuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.randomuser.R;
import com.example.randomuser.adapters.RecyclerViewAdapter;
import com.example.randomuser.model.ApiResponse;
import com.example.randomuser.model.User;
import com.example.randomuser.network.RetrofitInstance;
import com.example.randomuser.network.UserInterface;
import com.example.randomuser.view.UserDetailActivity;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnUserListener {

    private static final String TAG = "MainActivity";

    private ArrayList<User> listUser = new ArrayList<>();

    private boolean isLoading = false;
    private final int THREAD_SHOT = 5;

    private RecyclerViewAdapter adapter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        dataRequest();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();

                if (!listUser.isEmpty()) {
                    listUser.clear();
                    adapter.notifyDataSetChanged();
                }
                dataRequest();

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

    private void dataRequest() {
        // retrofit
        UserInterface userInterface = RetrofitInstance.getRetrofitInstance().create(UserInterface.class);
        userInterface.fetchUsers(20, "us").enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse results = response.body();
                    listUser = Objects.requireNonNull(results).getResults();
                    setupRecyclerView(listUser);
                } else {
                    Toast.makeText(MainActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMoreData() {
        listUser.add(null);
        adapter.notifyItemInserted(listUser.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listUser.remove(listUser.size() - 1);
                int scrollPosition = listUser.size();
                adapter.notifyItemRemoved(scrollPosition);

                // retrofit load more data
                UserInterface userInterface = RetrofitInstance.getRetrofitInstance().create(UserInterface.class);
                userInterface.fetchUsers(5, "us").enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull retrofit2.Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            ApiResponse results = response.body();
                            listUser.addAll(Objects.requireNonNull(results).getResults());
                            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                        Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                });

                isLoading = false;
            }
        }, 2000);
    }

    private void setupRecyclerView(ArrayList<User> listUser) {

        adapter = new RecyclerViewAdapter(listUser, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // load more data
        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                assert layoutManager != null;
                if (!isLoading && layoutManager.getItemCount() - THREAD_SHOT == layoutManager.findLastVisibleItemPosition()) {
                    loadMoreData();
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public void onUserClick(int position) {
        Log.d(TAG, "onUserClick: clicked." + position);

        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("user_detail", listUser.get(position));
        startActivity(intent);
    }
}
