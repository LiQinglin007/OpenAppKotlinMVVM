package com.lixiaomi.openappkotlinmvvm.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.ArticleBean

/**
 * @describe：文章列表<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class ArticleAdapter(layoutId: Int, data: List<ArticleBean.Data.DataX>) :
    BaseQuickAdapter<ArticleBean.Data.DataX, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder, item: ArticleBean.Data.DataX) {
        helper.setText(R.id.item_article_title, item.title)
            .setText(R.id.item_article_author, "${item.author}  ${item.niceDate}")
            .setText(R.id.item_article_type, item.superChapterName)
    }
}