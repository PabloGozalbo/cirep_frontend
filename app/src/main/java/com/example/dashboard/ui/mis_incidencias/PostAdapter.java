package com.example.dashboard.ui.mis_incidencias;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cirep_frontend.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context mContext;
    private List<Post> mPosts;
    private int lastPosition = -1;

    public PostAdapter(Context context, List<Post> posts) {
        mContext = context;
        mPosts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_incidencias, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = mPosts.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(post.getImage(), 0, post.getImage().length);
        holder.imagePost.setImageBitmap(bitmap);
        holder.titlePost.setText(post.getTitle());
        setFadeAnimation(holder.itemView, position); // función para la animación
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePost;
        TextView titlePost;

        public PostViewHolder(View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.image_post);
            titlePost = itemView.findViewById(R.id.title_post);
        }
    }

    // función para la animación
    private void setFadeAnimation(View view, int position) {
        if (position > lastPosition) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(300);
            view.startAnimation(anim);
            lastPosition = position;
        }
    }

    public class Post {
        private byte[] image;
        private String title;

        public Post(byte[] image, String title) {
            this.image = image;
            this.title = title;
        }

        public byte[] getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }
    }

}
