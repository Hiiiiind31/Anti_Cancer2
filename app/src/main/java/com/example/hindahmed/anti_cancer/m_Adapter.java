package com.example.hindahmed.anti_cancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by hindahmed on 18/05/17.
 */

public class m_Adapter extends BaseAdapter {


     Context context;
     List<Post> posts;

    public m_Adapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }



    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.all_posts_list, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Post item = getItem(i);
        holder.AuthorView.setText(item.getAuthor());
        holder.BodyView.setText(item.getBody());
        return view;
    }

    class ViewHolder {

        TextView AuthorView, BodyView;

        ViewHolder(View v) {
            AuthorView = (TextView) v.findViewById(R.id.Author_id);
            BodyView = (TextView) v.findViewById(R.id.body_id);

        }
    }
}
