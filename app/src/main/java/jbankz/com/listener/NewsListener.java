package jbankz.com.listener;


import jbankz.com.model.Website;

public interface NewsListener {
    void showSuccess(Website website);

    void showProgress();

    void hideProgress();

    void showErrorMessage(String message);

    void hideErrorMessage(String message);

    void setRefreshing(boolean b);
}
