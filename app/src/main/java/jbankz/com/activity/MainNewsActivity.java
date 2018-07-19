package jbankz.com.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import jbankz.com.Interface.NewsService;
import jbankz.com.R;
import jbankz.com.adapter.ListSourceAdapter;
import jbankz.com.common.Common;
import jbankz.com.listener.NewsListener;
import jbankz.com.model.Website;
import jbankz.com.viewmodel.NewsSourcesViewmodel;

public class MainNewsActivity extends AppCompatActivity implements NewsListener {

    private static final String TAG = "MainNewsActivity";

    @BindView(R.id.news_rv)
    RecyclerView listWebsite;
    @BindView(R.id.swipe_to_refresh_news)
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    SpotsDialog dialog;
    NewsSourcesViewmodel newsSourcesViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init views
        ButterKnife.bind(this);

        // init cache
        Paper.init(this);

        // init viewmodel
        newsSourcesViewmodel = ViewModelProviders.of(this).get(NewsSourcesViewmodel.class);

        // set listeners
        newsSourcesViewmodel.setListener(this, this);

        // set onClickListener to swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsSourcesViewmodel.loadWebsiteSource(true);
            }
        });

        // init service
        mService = Common.getNewsService();

        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        newsSourcesViewmodel.loadWebsiteSource(false);

    }

    @Override
    public void showSuccess(Website website) {
        adapter = new ListSourceAdapter(getBaseContext(), website);
        adapter.notifyDataSetChanged();
        listWebsite.setAdapter(adapter);
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
    public void showErrorMessage(String message) {

    }

    @Override
    public void hideErrorMessage(String message) {

    }

    @Override
    public void setRefreshing(boolean b) {
        swipeRefreshLayout.setRefreshing(b);
    }
}
