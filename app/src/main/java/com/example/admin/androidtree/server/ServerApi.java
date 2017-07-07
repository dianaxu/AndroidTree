package com.example.admin.androidtree.server;


import com.example.admin.androidtree.model.callback.NBACallack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 服务器访问接口列表
 */
public interface ServerApi {

    String SERVER_URL = "http://op.juhe.cn/";
    String DEV_SERVER_URL = "http://op.juhe.cn/";


    @GET("onebox/basketball/nba")
    Call<NBACallack> getBlog2(@Query("key") String key);
}
