package jbankz.com.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import io.paperdb.Paper;
import jbankz.com.Interface.IconBetterIdeaService;
import jbankz.com.Interface.NewsService;
import jbankz.com.common.Common;
import jbankz.com.listener.NewsListener;
import jbankz.com.model.Website;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsSourcesViewmodel extends ViewModel {

    private static final String TAG = "NewsSourcesViewmodel";
    static NewsListener newsListener;
    private NewsService mService;
    private IconBetterIdeaService mIconBetterIdeaService;

    public void setListener(Context context, NewsListener newsListener) {
        // init listener
        NewsSourcesViewmodel.newsListener = newsListener;
        // init service
        mService = Common.getNewsService();
        mIconBetterIdeaService = Common.getIconService();
        // init cache
        Paper.init(context);
    }

    public void loadWebsiteSource(boolean isRefreshed) {
        if (!isRefreshed) {
            String cache = Paper.book().read("cache");
            if (cache != null && !cache.isEmpty()) { // if have cache
                Website website = new Gson().fromJson(cache, Website.class); //converts cache from json to object
                newsListener.showSuccess(website);
            } else {
                newsListener.showProgress();
                // fetch new data
                mService.getSourses().enqueue(new Callback<Website>() {
                    @Override
                    public void onResponse(Call<Website> call, Response<Website> response) {
                        Log.d(TAG, "onResponse: " + response.raw());
                        newsListener.hideProgress();
                        if (response.isSuccessful()) {
                            newsListener.showSuccess(response.body());
                            // save to cache
                            Paper.book().write("cache", new Gson().toJson(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Website> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        } else { // if from swipe to refresh

            newsListener.showProgress();
            // fetch new data
            mService.getSourses().enqueue(new Callback<Website>() {
                @Override
                public void onResponse(Call<Website> call, Response<Website> response) {
                    Log.i(TAG, "onResponse: " + response.raw());
                    newsListener.hideProgress();
                    if (response.isSuccessful()) {
                        newsListener.showSuccess(response.body());
                        // save to cache
                        Paper.book().write("cache", new Gson().toJson(response.body()));
                        // dismiss refreshing layout
                        newsListener.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<Website> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    newsListener.setRefreshing(false);
                    newsListener.hideProgress();
                }
            });
        }
    }

}
