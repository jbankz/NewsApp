package jbankz.com.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;


import jbankz.com.Interface.NewsService;
import jbankz.com.common.Common;
import jbankz.com.listener.LoadNewsListener;
import jbankz.com.model.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class LoadNewsViewmodel extends ViewModel {
    private static final String TAG = "LoadNewsViewmodel";

    private static LoadNewsListener loadNewsListener;
    private NewsService mService;

    // init the listener
    public void setLoadNewsListener(LoadNewsListener loadNewsListener) {
        LoadNewsViewmodel.loadNewsListener = loadNewsListener;
        mService = Common.getNewsService();
    }


    public void loadNews(String query) {
        // show listener
        loadNewsListener.showProgress();
        // makes the query
        mService.getListOfNews(query, Common.API_KEY).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.i(TAG, "onResponse: " + response.raw());
                loadNewsListener.hideProgress();
                loadNewsListener.setRefreshing(false);

                if (response.isSuccessful()) {
                    // pass the image url
                    loadNewsListener.getUrlToImage(response.body().getArticles().get(0).getUrlToImage());
                    // pass the author name
                    loadNewsListener.getAuthor(response.body().getArticles().get(0).getAuthor());
                    // pass the title
                    loadNewsListener.getTitle(response.body().getArticles().get(0).getTitle());
                    // pass the image url
                    loadNewsListener.getHost(response.body().getArticles().get(0).getUrl());
                    // load remaining articles
                    loadNewsListener.setArrayList(response.body().getArticles());
                    // set refreshing to false
                    loadNewsListener.setRefreshing(false);
                } else {
                    loadNewsListener.errorMessage("unable to retrieve news at this time");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                loadNewsListener.hideProgress();
                loadNewsListener.errorMessage(t.getMessage());
            }
        });
    }

}
