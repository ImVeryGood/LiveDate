package com.m.mframe_mvp.demo.p;

import com.m.mframe_mvp.basic.mvp.BasePresenter;
import com.m.mframe_mvp.basic.net.MObserver;
import com.m.mframe_mvp.basic.net.MRetrofit;
import com.m.mframe_mvp.basic.utils.LoadingDialog;
import com.m.mframe_mvp.demo.ArticleBean;
import com.m.mframe_mvp.demo.v.MainView;

/**
 * createDate:2020/10/16
 *
 * @author:spc
 * @describe：
 */
public class MainPresenter extends BasePresenter<MainView> {
    public MainPresenter(MainView mView) {
        super(mView);
    }

    public void getData(LoadingDialog loadingDialog) {
        MRetrofit.getInstance().getData()
                .compose(MRetrofit.toMainThread())
                .subscribe(new MObserver<ArticleBean>() {
                    @Override
                    protected void success(ArticleBean articleBean) {
                        loadingDialog.hideDialog();
                        mView.setArticle(articleBean);
                    }

                    @Override
                    protected void error(String error) {
                        loadingDialog.hideDialog();

                    }
                });
    }
}