package jbankz.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import jbankz.com.Interface.IconBetterIdeaService;
import jbankz.com.Interface.ItemClickListener;
import jbankz.com.R;
import jbankz.com.activity.ListNewsActivity;
import jbankz.com.common.Common;
import jbankz.com.model.IconBetterIdea;
import jbankz.com.model.Website;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;

    TextView source_title;
    CircleImageView source_image;

    public ListSourceViewHolder(View itemView) {
        super(itemView);
        source_title = itemView.findViewById(R.id.source_name);
        source_image = itemView.findViewById(R.id.source_image);

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

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder> {

    private static final String TAG = "ListSourceAdapter";

    private Context context;
    private Website website;
    private IconBetterIdeaService mService;

    public ListSourceAdapter(Context context, Website website) {
        this.context = context;
        this.website = website;

        mService = Common.getIconService();
    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.source_layout, parent, false);
        return new ListSourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSourceViewHolder holder, int position) {
        StringBuilder iconBetterAPI = new StringBuilder("https://besticon-demo.herokuapp.com/allicons.json?url=");
        iconBetterAPI.append(website.getSources().get(position).getUrl());
        mService.getIconUrl(iconBetterAPI.toString()).enqueue(new Callback<IconBetterIdea>() {
            @Override
            public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
                try {
                    if (response.body().getIcons().size() > 0) {
                        Picasso.with(context)
                                .load(response.body().getIcons().get(0).getUrl())
                                .placeholder(R.drawable.news_placeholder)
                                .into(holder.source_image);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<IconBetterIdea> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        holder.source_title.setText(website.getSources().get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, ListNewsActivity.class);
                intent.putExtra("sources", website.getSources().get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (website.getSources() == null)
            return 0;
        return website.getSources().size();
    }
}