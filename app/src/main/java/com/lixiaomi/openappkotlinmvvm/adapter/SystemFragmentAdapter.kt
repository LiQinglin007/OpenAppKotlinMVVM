package com.lixiaomi.openappkotlinmvvm.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.MyTreeBean

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
/**
 * 一级分类
 */
val TYPE_LEVEL_0 = 0
/**
 * 二级分类
 */
val TYPE_LEVEL_1 = 1

class SystemFragmentAdapter(data: List<MultiItemEntity>) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(TYPE_LEVEL_0, R.layout.item_tree1)
        addItemType(TYPE_LEVEL_1, R.layout.item_tree2)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
        when (helper?.itemViewType) {
            TYPE_LEVEL_0 -> {
                val myTreeBean = item as MyTreeBean
                helper.setText(R.id.item_tree_lv0, myTreeBean.name)
                helper.setImageResource(
                    R.id.item_tree_lv0_img,
                    if (myTreeBean.isExpanded) R.drawable.arrow_r else R.drawable.arrow_b
                )
                helper.setVisible(R.id.item_tree_img, if (myTreeBean.isExpanded) true else false)

                helper.itemView.setOnClickListener {
                    val adapterPosition = helper.adapterPosition
                    if (myTreeBean.isExpanded) {
                        collapse(adapterPosition)
                    } else {
                        expand(adapterPosition)
                    }
                }
            }

            TYPE_LEVEL_1 -> {
                helper.setText(R.id.item_tree_lv1, "· ${(item as MyTreeBean.ChildrenBean).name}")
            }
        }
    }

}