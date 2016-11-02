package com.pasha.efebudak.stringcounter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by efebudak on 02/11/2016.
 */

public interface RedditService {

    @GET("r/anddev")
    Call<ResponseBody> getAnddevResponseBodyCall();
}
