package com.lixiaomi.openappkotlinmvvm.bean

import com.google.gson.annotations.SerializedName
import com.lixiaomi.baselib.ui.dialog.dialoglist.MiListInterface


/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
data class WXArticleAuthorlistBean(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("errorCode")
    var errorCode: Int,
    @SerializedName("errorMsg")
    var errorMsg: String
) {

    data class Data(
        @SerializedName("children")
        var children: List<Any>,
        @SerializedName("courseId")
        var courseId: Int,
        @SerializedName("id")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("order")
        var order: Int,
        @SerializedName("parentChapterId")
        var parentChapterId: Int,
        @SerializedName("userControlSetTop")
        var userControlSetTop: Boolean,
        @SerializedName("visible")
        var visible: Int
    ) : MiListInterface {
        override fun getMiDialigListShowData(): String {
            return name
        }
    }
}