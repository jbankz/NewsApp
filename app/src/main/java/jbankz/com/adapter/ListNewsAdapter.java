package jbankz.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jbankz.com.Interface.ItemClickListener;
import jbankz.com.R;
import jbankz.com.activity.DetailsActivity;
import jbankz.com.common.ISO8601DateParser;
import jbankz.com.model.NewsList;

class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CircleImageView articleImage;
    TextView articleTitle;
    RelativeTimeTextView articleTime;
    ItemClickListener itemClickListener;

    public ListNewsViewHolder(View itemView) {
        super(itemView);
        articleImage = itemView.findViewById(R.id.article_image);
        articleTitle = itemView.findViewById(R.id.article_title);
        articleTime = itemView.findViewById(R.id.article_time);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder> {

    private List<NewsList> articleList;
    private Context context;

    public ListNewsAdapter(List<NewsList> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_layout, parent, false);
        return new ListNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNewsViewHolder holder, int position) {
        Picasso.with(context)
                .load(articleList.get(position).getUrlToImage())
                .placeholder(R.drawable.news_placeholder)
                .into(holder.articleImage);

        if (articleList.get(position).getTitle().length() > 65)
            holder.articleTitle.setText(articleList.get(position).getTitle().substring(0, 65) + "...");
        else
            holder.articleTitle.setText(articleList.get(position).getTitle());

        Date date = null;
        try {
            date = ISO8601DateParser.parse(articleList.get(position).getPublishedAt());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        holder.articleTime.setReferenceTime(date.getTime());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("webURL", articleList.get(position).getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (articleList == null)
            return 0;
        return articleList.size();
    }
}
