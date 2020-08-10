package com.m.livedate.mvp.ui.presenter;

import android.util.Log;

import com.m.livedate.mvp.ApiManage;
import com.m.livedate.mvp.base.BasePresenter;
import com.m.livedate.mvp.base.MBasePresenter;
import com.m.livedate.mvp.base.net.MObserver;
import com.m.livedate.mvp.base.net.downfile.DownFileCallback;
import com.m.livedate.mvp.base.net.downfile.DownLoadManager;
import com.m.livedate.mvp.base.net.downfile.DownModel;
import com.m.livedate.mvp.ui.bean.ArticleBean;
import com.m.livedate.mvp.ui.view.MView;
import com.m.livedate.utils.ApkUtil;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * createDate:2020/8/6
 *
 * @author:spc
 * @describe：
 */
public class MPresenter extends MBasePresenter<MView> {

    private DownModel downModel;

    public MPresenter(MView view) {
        super(view);
    }

    @Override
    protected void init() {
        getData();
    }

    public void getData() {
        mView.showLoadingDialog("加载中...");
        ApiManage.getInstance().getArticleBean()
                .compose(toMainThread())
                .compose(mView.bindToLifecycle())
                .subscribe(new MObserver<ArticleBean>() {
                    @Override
                    protected void success(ArticleBean articleBean) {
                        mView.showArticle(articleBean);
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    protected void error(String error) {
                        mView.dismissLoadingDialog();

                    }
                });
    }

    /**
     * 下载文件
     */
    public void downFile() {
        String url = ApkUtil.apkUrl;
        if (downModel == null) {
            downModel = new DownModel();
            downModel.setUrl(url);
        }
        DownLoadManager.getInstance().downFile(downModel, new DownFileCallback() {
            @Override
            public void onSuccess(String path) {
                Log.d("SSSSSSSSS", "onSuccess: " + path);
            }

            @Override
            public void onFail(String msg) {

            }

            @Override
            public void onProgress(long totalSize, long downSize) {
                Log.d("SSSSSSSSS", "onProgress: downSize==" + downSize);
                if (downModel.getTotalSize() == 0) {
                    downModel.setTotalSize(totalSize);
                }
                downModel.setCurrentTotalSize(totalSize);

                downModel.setDownSize(downSize + downModel.getTotalSize() - downModel.getCurrentTotalSize());
            }
        });
    }

    /**
     * 上传文件
     *
     * @param path 文件路径
     */
    public void upLoad(String path) {
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestBody);
        ApiManage.getInstance().upload(part)
                .compose(ApiManage.<String>toMainThread())
                .subscribe(new MObserver<String>() {
                    @Override
                    protected void success(String s) {
                        Log.d("SSSSSSSSSS", "success: " + s);
                    }

                    @Override
                    protected void error(String error) {
                        Log.d("SSSSSS", "error: " + error);
                    }
                });
    }
}
