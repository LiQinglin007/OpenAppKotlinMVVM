package com.lixiaomi.openappkotlinmvvm.adapter

import android.support.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.WXArticleListBean

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class SubscribeFragmentAdapter(layoutId: Int, @Nullable data: List<WXArticleListBean.Data.DataX>) :
    BaseQuickAdapter<WXArticleListBean.Data.DataX, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder?, item: WXArticleListBean.Data.DataX?) {
        helper?.setText(R.id.item_wxarticle_title, item?.title)
        helper?.setText(R.id.item_wxarticle_author, "时间：${item?.niceDate}")
    }
}