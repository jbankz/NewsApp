package jbankz.com.Interface;

import jbankz.com.model.Response;
import jbankz.com.model.Website;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("v1/sources?language=en")
    Call<Website> getSourses();

    @GET("v2/everything")
    Call<Response> getListOfNews(@Query("sources") String source, @Query("apiKey") String api_key);
}
