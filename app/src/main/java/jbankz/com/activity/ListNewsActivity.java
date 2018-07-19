package jbankz.com.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import jbankz.com.Interface.NewsService;
import jbankz.com.R;
import jbankz.com.adapter.ListNewsAdapter;
import jbankz.com.common.Common;
import jbankz.com.listener.LoadNewsListener;
import jbankz.com.model.NewsList;
import jbankz.com.viewmodel.LoadNewsViewmodel;

public class ListNewsActivity extends AppCompatActivity implements LoadNewsListener {

    private static final String TAG = "ListNewsActivity";

    @BindView(R.id.news_list)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.top_image)
    KenBurnsView kbv;
    @BindView(R.id.top_author)
    TextView author;
    @BindView(R.id.top_title)
    TextView title;
    @BindView(R.id.list_news)
    RecyclerView listNews;
    @BindView(R.id.diagonal_layout)
    DiagonalLayout diagonalLayout;

    NewsService mService;
    String source = "";
    ListNewsAdapter listNewsAdapter;
    RecyclerView.LayoutManager layoutManager;
    SpotsDialog dialog;
    String webHostUrl;
    private LoadNewsViewmodel loadNewsViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // init views
        ButterKnife.bind(this);
        // service
        mService = Common.getNewsService();
        // init loadNewsViewmodel
        loadNewsViewmodel = ViewModelProviders.of(this).get(LoadNewsViewmodel.class);
        // set listener
        loadNewsViewmodel.setLoadNewsListener(this);
        // init dialog
        dialog = new SpotsDialog(this);
        // diagonal
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListNewsActivity.this, DetailsActivity.class);
                intent.putExtra("webURL", webHostUrl);
                startActivity(intent);
            }
        });

        // refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNewsViewmodel.loadNews(source);
            }
        });

        // Intent
        if (getIntent() != null) {
            source = getIntent().getStringExtra("sources");
            if (!source.isEmpty()) {
                loadNewsViewmodel.loadNews(source);
            }
        }
        listNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listNews.setLayoutManager(layoutManager);
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void getAuthor(String author) {
        this.author.setText(author);
    }

    @Override
    public void getTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void getHost(String host) {
        webHostUrl = host;
    }

    @Override
    public void setRefreshing(boolean b) {
        swipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void errorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setArrayList(List<NewsList> articleList) {
        List<NewsList> removeFromItems = articleList;
        removeFromItems.remove(0);
        listNewsAdapter = new ListNewsAdapter(removeFromItems, getBaseContext());
        listNewsAdapter.notifyDataSetChanged();
        listNews.setAdapter(listNewsAdapter);
    }

    @Override
    public void getUrlToImage(String urlToImage) {
        Picasso.with(getBaseContext())
                .load(urlToImage)
                .placeholder(R.drawable.news_placeholder)
                .error(R.drawable.image_placeholder_broken)
                .into(kbv);
    }
}
