package jbankz.com.common;

import jbankz.com.Interface.IconBetterIdeaService;
import jbankz.com.Interface.NewsService;
import jbankz.com.remote.IconBetterIdeaClient;
import jbankz.com.remote.RetrofitClient;

public class Common {
    private static final String BASE_URL = "https://newsapi.org/";
    public static final String API_KEY = "042457be13e648bdb77d7f01ca5daf0d";

    public static NewsService getNewsService() {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }


    public static IconBetterIdeaService getIconService() {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }


}
