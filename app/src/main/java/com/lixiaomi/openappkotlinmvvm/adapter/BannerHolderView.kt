package com.lixiaomi.openappkotlinmvvm.adapter

import android.view.View
import android.widget.ImageView
import com.bigkoo.convenientbanner.holder.Holder
import com.lixiaomi.baselib.utils.loadImageUtils.MiLoadImageUtil
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.BannerBean

/**
 * @describe：首页Banner列表<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class BannerHolderView(itemView: View) : Holder<BannerBean.Data>(itemView) {
    var imageView: ImageView? = null

    override fun updateUI(data: BannerBean.Data?) {
        MiLoadImageUtil.loadImage(imageView?.context, data?.imagePath, imageView)
    }

    override fun initView(itemView: View) {
        imageView = itemView.findViewById(R.id.ivPost)
    }
}