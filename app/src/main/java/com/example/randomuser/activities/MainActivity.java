package com.example.randomuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.randomuser.adapters.RecyclerViewAdapter;
import com.example.randomuser.R;
import com.example.randomuser.model.RandomUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnUserListener {

    private static final String TAG = "MainActivity";

    private ArrayList<RandomUser> listUser = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;
    public static RecyclerView recyclerView;

    private boolean isLoading = false;
    private final int THREAD_SHOT = 5;

    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_view);

        dataRequest();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();

                listUser.clear();
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
        final String JSON_URL = "https://randomuser.me/api/?results=20&nat=us";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = (JSONArray) response.get("results");
                    parsingData(results);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setupRecyclerView(listUser);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: ", error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
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

                final String JSON_URL = "https://randomuser.me/api/?results=5&nat=us";

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = (JSONArray) response.get("results");
                            parsingData(results);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Error: ", error.getMessage());
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(request);
                isLoading = false;
            }
        }, 2000);
    }

    private void setupRecyclerView(ArrayList<RandomUser> listUser) {

        adapter = new RecyclerViewAdapter(listUser, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //-- Implement load more data
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

    private String toTitleCase(String string) {
        if (string == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(string);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    private void parsingData(JSONArray results) throws JSONException {

        JSONObject resultObject, temp;

        for (int i = 0; i < results.length(); i++) {
            resultObject = (JSONObject) results.get(i);

            RandomUser user = new RandomUser();

            // Set gender
            user.setGender(resultObject.get("gender").toString());

            // Set name
            temp = (JSONObject) resultObject.get("name");
            user.setName(toTitleCase(temp.get("first").toString() + " " + temp.get("last").toString()));

            // Set location
            temp = (JSONObject) resultObject.get("location");
            user.setLocation(toTitleCase(temp.get("street").toString() + ", " +temp.get("city").toString() + " Ct, " + temp.get("state").toString()));

            // Set email
            user.setEmail(resultObject.get("email").toString());

            // Set DoB
            temp = (JSONObject) resultObject.get("dob");
            user.setDob(temp.get("date").toString().split("T")[0] + " " + temp.get("date").toString().split("T")[1].split("Z")[0]);

            // Set registered date
            temp = (JSONObject) resultObject.get("registered");
            user.setRegistered(temp.get("date").toString().split("T")[0] + " " + temp.get("date").toString().split("T")[1].split("Z")[0]);

            // Set phone number
            user.setPhone(resultObject.get("phone").toString());

            // Set cell number
            user.setCell(resultObject.get("cell").toString());

            // Set picture URLs
            temp = (JSONObject) resultObject.get("picture");
            user.setLargePictureURL(temp.get("large").toString());
            user.setMediumPictureURL(temp.get("medium").toString());

            listUser.add(user);
            user = null;
        }
    }

    @Override
    public void onUserClick(int position) {
        Log.d(TAG, "onUserClick: clicked." + position);

        Intent intent = new Intent(this, UserDetailActivity.class);

        intent.putExtra("user_name", listUser.get(position).getName());
        intent.putExtra("user_first", listUser.get(position).getName().split(" ")[0]);
        intent.putExtra("user_avatar", listUser.get(position).getLargePictureURL());
        intent.putExtra("user_mail", listUser.get(position).getEmail());
        intent.putExtra("user_cell", listUser.get(position).getCell());
        intent.putExtra("user_phone", listUser.get(position).getPhone());
        intent.putExtra("user_location", listUser.get(position).getLocation());
        intent.putExtra("user_dob", listUser.get(position).getDob());
        intent.putExtra("user_registered", listUser.get(position).getRegistered());

        startActivity(intent);
    }
}
