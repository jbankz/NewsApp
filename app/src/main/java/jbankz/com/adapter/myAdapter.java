package jbankz.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import jbankz.com.R;
import jbankz.com.ScrollingActivity;
import jbankz.com.model.pojo;

/**
 * Created by King Jaycee on 26/10/2017.
 */

public class myAdapter extends RecyclerView.Adapter<myAdapter.CnnViewHolder> {

    private ArrayList<pojo> pojoArrayList;
    private String title, imageUrl, mPublishedAt, description;

    public myAdapter(ArrayList<pojo> pojoArrayList) {
        this.pojoArrayList = pojoArrayList;
    }

    @Override
    public CnnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout, parent, false);
        return new CnnViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CnnViewHolder holder, int position) {
        pojo pojo = pojoArrayList.get(position);

        Context context = holder.circleImageView.getContext();

        imageUrl = pojo.getUrlToImage();
        mPublishedAt = pojo.getPublishedAt();
        title = pojo.getTitle();
        description = pojo.getDescription();

        holder.mTitle.setText(title);
        holder.mPostDate.setText(mPublishedAt);

        Picasso.with(context).load(imageUrl).placeholder(R.drawable.placeholder).into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return pojoArrayList.size();
    }

    class CnnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView circleImageView;
        TextView mTitle, mPostDate;

        public CnnViewHolder(View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.poster);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mPostDate = (TextView) itemView.findViewById(R.id.post_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ScrollingActivity.class);
            pojo data = pojoArrayList.get(getLayoutPosition());
            intent.putExtra("data", data);
            context.startActivity(intent);
        }
    }
}
