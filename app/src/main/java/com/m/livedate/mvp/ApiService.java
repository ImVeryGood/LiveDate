package com.m.livedate.mvp;

import com.m.livedate.mvp.ui.bean.ArticleBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * createDate:2020/8/6
 *
 * @author:spc
 * @describe：
 */
public interface ApiService {
    @GET("article/listproject/0/json")
    Observable<ArticleBean> getArticleBean();

    @Streaming//大文件要加不然会oom
    @GET
    Observable<ResponseBody> dowmLoadFile(@Url String fileUrl);

    @Multipart
    @POST("http://192.168.10.221:8080/SpringMvc/file/uploadspring")
    Observable<String> upload(@Part MultipartBody.Part image);

}
