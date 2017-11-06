package jbankz.com.util;

import jbankz.com.model.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by King Jaycee on 26/10/2017.
 */

public interface APiService {

    @GET("articles?source=cnn&")
    Call<Response> getTop(@Query("sortBy") String sortBy, @Query("apiKey") String api_key);

    @GET("articles?source=talksport&")
    Call<Response> getSports(@Query("sortBy") String sortBy, @Query("apiKey") String api_key);

    @GET("articles?source=buzzfeed&")
    Call<Response> getBuzz(@Query("sortBy") String sortBy, @Query("apiKey") String api_key);

}
