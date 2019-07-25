package com.example.randomuser.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.randomuser.R;
import com.example.randomuser.model.RandomUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<RandomUser> listUser;
    private Context context;

    private OnUserListener onUserListener;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public RecyclerViewAdapter(ArrayList<RandomUser> listUser, Context context, OnUserListener onUserListener) {
        this.listUser = listUser;
        this.context = context;
        this.onUserListener = onUserListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generated_user_layout, parent, false);
            return new ItemViewHolder(view, onUserListener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called.");

        if (holder instanceof ItemViewHolder) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(context)
                    .asBitmap()
                    .load(listUser.get(position).getMediumPictureURL())
                    .apply(options)
                    .into(((ItemViewHolder) holder).imageView);

            ((ItemViewHolder) holder).textView.setText(listUser.get(position).getName());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return listUser == null ? 0 : listUser.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listUser.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageView;
        OnUserListener onUserListener;
        RelativeLayout parentLayout;
        TextView textView;

        ItemViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.user_image);
            textView = itemView.findViewById(R.id.user_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            this.onUserListener = onUserListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onUserListener.onUserClick(getAdapterPosition());
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

         LoadingViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public interface OnUserListener {
        void onUserClick(int position);
    }
}
