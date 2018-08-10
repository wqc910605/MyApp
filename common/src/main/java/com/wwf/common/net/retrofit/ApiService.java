package com.wwf.common.net.retrofit;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiService {
    /**
     * get 请求方式
     * @param path
     * @param params
     * @return
     */
    @GET
    Observable<String> get(@Url String path, @FieldMap Map<String, String> params);

    /**
     * post 请求方式
     * @param path
     * @param params
     * @return
     */
    @POST
    Observable<String> post(@Url String path, @Body Map<String, Object> params);

    /*@FormUrlEncoded
    @POST("{path}")
    Observable<String> post(@Path("path") String path, @FieldMap Map<String, String> params);

    @POST("{path}")
    Observable<String> post(@Path("path") String path);*/

    //上传文件
    @Multipart
    @POST()
    Observable<String> uploadFile(@Url String url, @FieldMap Map<String, Object> params, @Part MultipartBody.Part file);

    //上传单张图片
    @Multipart
    @POST()
    Observable<String> uploadImg(@Url String url, @FieldMap Map<String, Object> params, @Part("description") RequestBody description, @Part MultipartBody.Part file);

    //上传多个文件
    //上传多张图片
    @Multipart
    @POST()
    Observable<String> uploadMuti(@Url String url, @PartMap() Map<String, RequestBody> maps);
}
