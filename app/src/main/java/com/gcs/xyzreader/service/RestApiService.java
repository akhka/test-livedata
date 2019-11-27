package com.gcs.xyzreader.service;

import com.gcs.xyzreader.models.XYZJson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApiService {

    @GET("xyz-reader-json")
    Call<List<XYZJson>> getJson();

}
