package com.lixiaomi.openappkotlinmvvm.bean

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/21<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
open class BaseBean(errorCode: Int, errorMsg: String) {
    open var errorCode: Int? = null
    open var errorMsg: String? = null


    init {
        this.errorCode = errorCode
        this.errorMsg = errorMsg
    }
}