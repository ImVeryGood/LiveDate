package com.m.mframe_mvp.demo.v;

import com.m.mframe_mvp.basic.mvp.BaseView;
import com.m.mframe_mvp.demo.ArticleBean;

/**
 * createDate:2020/10/16
 *
 * @author:spc
 * @describe：
 */
public interface MainView extends BaseView {
    void setArticle(ArticleBean article);
} 