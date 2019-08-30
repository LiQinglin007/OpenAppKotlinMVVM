package com.lixiaomi.openappkotlinmvvm.bean
import com.google.gson.annotations.SerializedName


/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/21<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
data class BannerBean(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("errorCode")
    var errorCode: Int,
    @SerializedName("errorMsg")
    var errorMsg: String
){
    data class Data(
        @SerializedName("desc")
        var desc: String,
        @SerializedName("id")
        var id: Int,
        @SerializedName("imagePath")
        var imagePath: String,
        @SerializedName("isVisible")
        var isVisible: Int,
        @SerializedName("order")
        var order: Int,
        @SerializedName("title")
        var title: String,
        @SerializedName("type")
        var type: Int,
        @SerializedName("url")
        var url: String
    )
}

