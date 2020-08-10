package com.m.livedate.mvp.ui.view;

import com.m.livedate.mvp.base.BaseView;
import com.m.livedate.mvp.base.MBaseView;
import com.m.livedate.mvp.ui.bean.ArticleBean;

/**
 * createDate:2020/8/6
 *
 * @author:spc
 * @describe：
 */
public interface MView extends MBaseView {
    void showArticle(ArticleBean articleBean);
}
