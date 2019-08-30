package com.lixiaomi.openappkotlinmvvm.bean

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lixiaomi.openappkotlinmvvm.adapter.TYPE_LEVEL_0
import com.lixiaomi.openappkotlinmvvm.adapter.TYPE_LEVEL_1


/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class MyTreeBean : AbstractExpandableItem<MyTreeBean.ChildrenBean>(), MultiItemEntity {

    var courseId: Int? = null
    var id: Int? = null
    var name: String? = null
    var order: Int? = null
    var parentChapterId: Int? = null
    var userControlSetTop: Boolean? = null
    var visible: Int? = null
    /**
     * 第几个
     */
    var position: Int? = null
    var children: List<ChildrenBean>? = null

    override fun getLevel(): Int {
        return 0
    }

    override fun getItemType(): Int {
        return TYPE_LEVEL_0
    }


    class ChildrenBean : MultiItemEntity {

        var courseId: Int? = null
        var id: Int? = null
        var name: String? = null
        var order: Int? = null
        var parentChapterId: Int? = null
        var userControlSetTop: Boolean? = null
        var visible: Int? = null
        /**
         * 父类在父类的第几个
         */
        var parentPosition: Int? = null
        /**
         * 自己在父类的第几个
         */
        var position: Int? = null
        var children: List<Any>? = null


        override fun getItemType(): Int {
            return TYPE_LEVEL_1
        }

    }
}