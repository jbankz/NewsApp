package jbankz.com.listener;

import java.util.List;

import jbankz.com.model.NewsList;


public interface LoadNewsListener {
    void showProgress();

    void hideProgress();

    void getAuthor(String author);

    void getTitle(String title);

    void getHost(String host);

    void setRefreshing(boolean b);

    void errorMessage(String message);

    void setArrayList(List<NewsList> articleList);

    void getUrlToImage(String urlToImage);

}
