package com.lixiaomi.openappkotlinmvvm.adapter

import android.support.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.TreeArticleListBean



/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */

class SystemActivityAdapter(layoutId: Int, @Nullable data: List<TreeArticleListBean.Data.DataX>) :
    BaseQuickAdapter<TreeArticleListBean.Data.DataX, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder?, item: TreeArticleListBean.Data.DataX?) {
        helper?.setText(R.id.item_sys_article_title, item?.title)
        helper?.setText(R.id.item_sys_article_author, "时间：${item?.niceDate}")
    }
}