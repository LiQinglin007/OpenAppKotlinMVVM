package com.lixiaomi.openappkotlinmvvm.adapter

import android.support.annotation.Nullable
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lixiaomi.baselib.utils.loadImageUtils.MiLoadImageUtil
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.ProjectBean

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class ProjectAdapter(layoutId: Int, @Nullable data: List<ProjectBean.Data.DataX>) :
    BaseQuickAdapter<ProjectBean.Data.DataX, BaseViewHolder>(layoutId, data) {

    override fun convert(helper: BaseViewHolder?, item: ProjectBean.Data.DataX?) {
        helper?.setText(R.id.item_project_title, item?.title)
        helper?.setText(R.id.item_project_content, item?.desc)
        helper?.setText(R.id.item_project_author, "${item?.author}  ${item?.niceDate}")
        val view = helper?.getView<ImageView>(R.id.item_project_img)
        MiLoadImageUtil.loadImage(view?.context, item?.envelopePic, view)
    }
}