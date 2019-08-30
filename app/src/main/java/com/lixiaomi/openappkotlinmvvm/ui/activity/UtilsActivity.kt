package com.lixiaomi.openappkotlinmvvm.ui.activity

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.SwitchCompat
import android.view.View
import com.lixiaomi.baselib.net.DownloadListener
import com.lixiaomi.baselib.net.okhttp.DownloadUtil
import com.lixiaomi.baselib.ui.chooseDateUtils.MiDayTime
import com.lixiaomi.baselib.ui.chooseDateUtils.MiDiDiTime
import com.lixiaomi.baselib.ui.chooseDateUtils.MiMinTime
import com.lixiaomi.baselib.ui.dialog.MiDialog
import com.lixiaomi.baselib.ui.dialog.dialoglist.MiDialogList
import com.lixiaomi.baselib.ui.dialog.dialoglist.MiListInterface
import com.lixiaomi.baselib.utils.*
import com.lixiaomi.mvvmbaselib.base.BaseActivity
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.NoticeBean
import com.lixiaomi.openappkotlinmvvm.databinding.ActivityUtilsBinding
import com.lixiaomi.openappkotlinmvvm.mv.BaseViewModelImpl
import java.util.*
import kotlin.collections.ArrayList

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/29<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class UtilsActivity :
    BaseActivity<UtilsActivityLifecycle, ActivityUtilsBinding, BaseViewModelImpl>(),
    View.OnClickListener {


    lateinit var mMineChoosetime: AppCompatTextView
    lateinit var mMineChoosetimeSw: SwitchCompat
    lateinit var mMineChooseTimeMin: AppCompatTextView
    lateinit var mMineChooseTimeDidi: AppCompatTextView
    lateinit var mMineChoosesex: AppCompatTextView
    lateinit var mMineBottomList: AppCompatTextView
    lateinit var mMineBottomUserlist: AppCompatTextView
    lateinit var mMineSingOut: AppCompatTextView
    lateinit var mMineSingOutSw: SwitchCompat
    lateinit var mMineDownload: AppCompatTextView


    lateinit var mTopLeftLy: LinearLayoutCompat
    lateinit var mTopRightLy: LinearLayoutCompat
    lateinit var mTopTitleTv: AppCompatTextView

    var mBeanArrayList = ArrayList<MiListInterface>()


    internal var permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun initData() {
    }

    override fun setLayout(): Int {
        return R.layout.activity_utils
    }

    override fun initView(savedInstanceState: Bundle?) {
        mTopLeftLy = findViewById(R.id.top_back_ly)
        mTopRightLy = findViewById(R.id.top_right_ly)
        mTopTitleTv = findViewById(R.id.top_title_tv)

        mTopTitleTv?.text = "常用工具"
        mTopLeftLy?.setOnClickListener { finish() }
        mTopRightLy?.visibility = View.INVISIBLE

        mMineChoosetime = findViewById(R.id.mine_choosetime)
        mMineChoosetimeSw = findViewById(R.id.mine_choosetime_sw)
        mMineChooseTimeMin = findViewById(R.id.mine_choose_time_min)
        mMineChooseTimeDidi = findViewById(R.id.mine_choose_time_didi)
        mMineChoosesex = findViewById(R.id.mine_choosesex)
        mMineBottomList = findViewById(R.id.mine_bottom_list)
        mMineBottomUserlist = findViewById(R.id.mine_bottom_userlist)
        mMineSingOut = findViewById(R.id.mine_sing_out)
        mMineSingOutSw = findViewById(R.id.mine_sing_out_sw)
        mMineDownload = findViewById(R.id.mine_download)

        mMineChoosetime.setOnClickListener(this);
        mMineChooseTimeMin.setOnClickListener(this);
        mMineChooseTimeDidi.setOnClickListener(this);
        mMineChoosesex.setOnClickListener(this);
        mMineBottomList.setOnClickListener(this);
        mMineBottomUserlist.setOnClickListener(this);
        mMineSingOut.setOnClickListener(this);
        mMineDownload.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.top_back_ly ->
                finish()
            R.id.mine_choosetime -> {
                lateinit var startDate: Date
                lateinit var endDate: Date
                lateinit var selectDate: Date
                if (mMineChoosetimeSw.isChecked) {
                    startDate = Date()
                    selectDate = MiDateUtils.getDate(2020, 4, 28)
                    endDate = MiDateUtils.getDate(2038, 5, 30)
                } else {
                    selectDate = MiDateUtils.getDate(2018, 4, 28)
                    startDate = MiDateUtils.getDate(2018, 4, 28)
                    endDate = Date()
                }
                MiDayTime(this)
                    .builder()
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .setSelectDate(selectDate)
                    .setType(MiDayTime.YEAR_MONTH_DAY)
                    .setTvColor(R.color.warning_color5)
                    .setCallBack { year, month, day ->
                        mMineChoosetime.text =
                            if (mMineChoosetimeSw.isChecked) "今天以后：${year}${month}${day}" else "今天以前：${year}${month}${day}"
                    }
                    .show()
            }
            R.id.mine_choose_time_min -> {
                MiMinTime(this)
                    .builder()
                    .setSelectHour(Date().hours)
                    .setSelectMin(Date().minutes)
                    .setTimeDialogCallBack { min, hour ->
                        mMineChooseTimeMin.text = "选择时间(分钟)：${hour}:${min}"
                    }
                    .show()
            }

            R.id.mine_choose_time_didi -> {
                MiDiDiTime(this)
                    .builder()
                    .setStartHour(2)
                    .setEndHour(20)
                    .setDayNumber(5)
                    .setTextColor(R.color.warning_color3)
                    .setTimeDialogCallBack { dateWeek, date, time ->
                        mMineChooseTimeDidi.text = "选择预约时间(仿滴滴)：${dateWeek}${time}时"
                        LogUtils.loge("选择预约时间(仿滴滴)：${date}")
                    }
                    .show()
            }
            R.id.mine_choosesex -> {
                var sexList = ArrayList<String>()
                sexList.add("男")
                sexList.add("女")
                MiDialogList<String>(this)
                    .builder()
                    .setTitle("选择性别")
                    .setData(sexList)
                    .setGravity(MiDialogList.MILIST_DIALOG_BOTTOM)
                    .setReturnType(MiDialogList.MILIST_RETURN_SINGLE)
                    .setCallBack {
                        mMineChoosesex.text = "选择性别：${sexList.get(it.get(0))}"
                    }
                    .show()
            }
            R.id.mine_sing_out -> {
                var dialog = if (mMineSingOutSw.isChecked) MiDialog(this, MiDialog.MESSAGE_TYPE)
                else MiDialog(this, MiDialog.EDIT_TYPE)
                dialog.builder()
                    .setTitle("提示")
                    .setPassword(true)
                    .setMsg("确定要退出么？")
                    .setCannleButton("取消", null)
                    .setOkButton("退出", {
                        LogUtils.loge("弹框内容：${it}")
                        BaseAppManager.getInstance().clear()
                        finish()
                    })
                    .show()
            }
            R.id.mine_bottom_list -> {
                mBeanArrayList.clear()
                for (index in 0..20) {
                    mBeanArrayList.add(NoticeBean("标题巴拉巴拉${index}", "内容巴拉巴拉${index}"))
                }
                MiDialogList<NoticeBean>(this)
                    .builder()
                    .setData(mBeanArrayList)
                    .setTitle("标题(多选)")
                    .setGravity(MiDialogList.MILIST_DIALOG_CENTER)
                    .setReturnType(MiDialogList.MILIST_RETURN_MULTIPLE)
                    .setCallBack {
                        val size = it.size
                        for (i in 0..size - 1) {
                            LogUtils.loge(TAG, MiJsonUtil.getJson(mBeanArrayList.get(it.get(i))))
                        }
                        T.shortToast(this, "您选择了${size}个用户，去控制台看看吧")
                    }
                    .show()
            }
            R.id.mine_bottom_userlist -> {
                mBeanArrayList.clear()
                for (index in 0..20) {
                    mBeanArrayList.add(NoticeBean("标题巴拉巴拉${index}", "内容巴拉巴拉${index}"))
                }
                MiDialogList<NoticeBean>(this)
                    .builder()
                    .setData(mBeanArrayList)
                    .setTitle("标题(单选)")
                    .setGravity(MiDialogList.MILIST_DIALOG_BOTTOM)
                    .setReturnType(MiDialogList.MILIST_RETURN_SINGLE)
                    .setCallBack {
                        T.shortToast(this, MiJsonUtil.getJson(mBeanArrayList.get(it.get(0))))
                    }
                    .show()
            }
            R.id.mine_download -> {
                PermissionsUtil.getPermission(
                    this,
                    permissions,
                    "下载需要读写SD卡",
                    object : PermissionsUtil.PermissionCallBack {
                        override fun onSuccess() {
                            downLoad()
                        }

                        override fun onFail() {

                        }

                    })
            }
        }

    }


    fun downLoad() {
        DownloadUtil(
            "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk",
            "QQ.apk",
            object : DownloadListener {
                override fun downStart() {
                    LogUtils.loge("开始下载")
                    mMineDownload.text = "开始下载"
                }

                override fun downProgress(progress: Int, speed: Long) {
                    mMineDownload.text = "下载进度：${progress} speed:${speed}"
                    LogUtils.loge("下载进度：${progress} speed:${speed}")
                }

                override fun downSuccess() {
                    LogUtils.loge("下载成功")
                    mMineDownload.text = "下载成功"
                }

                override fun downFailed(failedDesc: String?) {
                    LogUtils.loge("下载失败")
                    mMineDownload.text = "下载失败"
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionsUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        PermissionsUtil.onActivityResult(this, requestCode, resultCode, data)
    }

    override fun startListenerData() {
    }

    override fun createLifeCycle(): UtilsActivityLifecycle {
        return UtilsActivityLifecycle(this)
    }

    override fun creatViewModel(): BaseViewModelImpl {
        return ViewModelProviders.of(this).get(BaseViewModelImpl::class.java)
    }

    override fun setStatusBarColor(): Int {
        return R.color.default_color
    }
}