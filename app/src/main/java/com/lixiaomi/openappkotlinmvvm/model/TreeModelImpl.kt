package com.lixiaomi.openappkotlinmvvm.model

import com.lixiaomi.baselib.config.AppConfigInIt
import com.lixiaomi.baselib.net.okhttp.MiOkHttpCallBack
import com.lixiaomi.baselib.net.okhttp.MiSendRequestOkHttp
import com.lixiaomi.baselib.utils.LogUtils
import com.lixiaomi.mvvmbaselib.base.MyPresenterCallBack
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.http.HttpData
import com.lixiaomi.openappkotlinmvvm.http.MyUrl

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/21<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class TreeModelImpl : TreeModel {
    override fun getTreeTypeList(myPresenterCallBack: MyPresenterCallBack) {
        MiSendRequestOkHttp.sendGet(
            null,
            null,
            MyUrl.TREE_GET,
            600,
            object : MiOkHttpCallBack() {
                override fun onSuccess(code: Int, response: String?) {
                    LogUtils.loge("code:${code} response:${response}")
                    if (code != HttpData.HTTP_SUCCESS_CODE) {
                        myPresenterCallBack.error(AppConfigInIt.getApplicationContext().getResources().getString(R.string.http_ServiceError));
                        return
                    }
                    if (response!!.contains(HttpData.SERVER_ERROR_STR)) {
                        myPresenterCallBack.error(AppConfigInIt.getApplicationContext().getResources().getString(R.string.http_ServiceError))
                        return
                    }
                    myPresenterCallBack.success(code, response)
                }

                override fun onFailure(e: Throwable?) {
                    myPresenterCallBack.failure(e)
                }
            })
    }

    override fun getTreeList(page: Int, cId: String, myPresenterCallBack: MyPresenterCallBack) {
        MiSendRequestOkHttp.sendGet(
            null,
            null,
            MyUrl.TREE_LIST_GET + page + "/json",
            600,
            object : MiOkHttpCallBack() {
                override fun onSuccess(code: Int, response: String?) {
                    LogUtils.loge("code:${code} response:${response}")
                    if (code != HttpData.HTTP_SUCCESS_CODE) {
                        myPresenterCallBack.error(AppConfigInIt.getApplicationContext().getResources().getString(R.string.http_ServiceError));
                        return
                    }
                    if (response!!.contains(HttpData.SERVER_ERROR_STR)) {
                        myPresenterCallBack.error(AppConfigInIt.getApplicationContext().getResources().getString(R.string.http_ServiceError))
                        return
                    }
                    myPresenterCallBack.success(code, response)
                }

                override fun onFailure(e: Throwable?) {
                    myPresenterCallBack.failure(e)
                }
            })
    }
}