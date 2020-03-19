package com.example.randomuser.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.randomuser.R;
import com.example.randomuser.model.User;
import com.example.randomuser.util.TextUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private List<User> listUser;
    private OnUserListener onUserListener;

    public RecyclerViewAdapter(List<User> listUser, OnUserListener onUserListener) {
        this.listUser = listUser;
        this.onUserListener = onUserListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generated_user_layout, parent, false);
        return new ItemViewHolder(view, onUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called.");

        User item = listUser.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.user_image)
        CircleImageView imageView;

        @BindView(R.id.parent_layout)
        RelativeLayout parentLayout;

        @BindView(R.id.user_name)
        TextView textView;

        OnUserListener onUserListener;

        ItemViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);

            this.onUserListener = onUserListener;
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onUserListener.onUserClick(getAdapterPosition());
        }

        void bind(User item) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(parentLayout.getContext())
                    .asBitmap()
                    .load(item.getPicture().getMedium())
                    .apply(options)
                    .into(imageView);

            textView.setText(TextUtil.toTitleCase(item.getName().getFirst() + " " + item.getName().getLast()));
        }
    }

    public interface OnUserListener {
        void onUserClick(int position);
    }
}
