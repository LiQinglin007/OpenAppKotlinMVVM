package com.lixiaomi.openappkotlinmvvm.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lixiaomi.baselib.utils.T
import com.lixiaomi.mvvmbaselib.base.BaseFragment
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.adapter.SystemFragmentAdapter
import com.lixiaomi.openappkotlinmvvm.bean.MyTreeBean
import com.lixiaomi.openappkotlinmvvm.databinding.FragmentSystemBinding
import com.lixiaomi.openappkotlinmvvm.mv.SystemFragmentViewMoldeImpl
import com.lixiaomi.openappkotlinmvvm.ui.activity.SystemListActivity
import com.lixiaomi.openappkotlinmvvm.ui.activity.SystemListActivityLifecycle
import com.lixiaomi.openappkotlinmvvm.utils.SYSTEM_TYPE_ID
import com.lixiaomi.openappkotlinmvvm.utils.SYSTEM_TYPE_TITLE
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_TITLE
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_URL

/**
 * @describe：体系<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/21<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class SystemFragment : BaseFragment<SystemFragmentLifecycle, FragmentSystemBinding, SystemFragmentViewMoldeImpl>() {


    companion object {
        fun getInstance(): SystemFragment {
            return SystemFragmentHodel.mSystemFragment
        }
    }

    object SystemFragmentHodel {
        val mSystemFragment = SystemFragment()
    }

    var mSystemRecy: RecyclerView? = null
    var mSystemFragmentAdapter: SystemFragmentAdapter? = null
    var mListData = ArrayList<MultiItemEntity>()


    var mTopLeftLy: LinearLayoutCompat? = null
    var mTopRightLy: LinearLayoutCompat? = null
    var mTopTitleTv: AppCompatTextView? = null


    override fun initData() {

    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        mTopLeftLy = rootView?.findViewById(R.id.top_back_ly)
        mTopRightLy = rootView?.findViewById(R.id.top_right_ly)
        mTopTitleTv = rootView?.findViewById(R.id.top_title_tv)

        mTopTitleTv?.text = "体系"
        mTopLeftLy?.visibility = View.INVISIBLE
        mTopRightLy?.visibility = View.INVISIBLE

        mSystemRecy = rootView?.findViewById(R.id.system_recy)
        mSystemRecy?.layoutManager = LinearLayoutManager(activity)
        mSystemFragmentAdapter = SystemFragmentAdapter(mListData)
        mSystemRecy?.adapter = mSystemFragmentAdapter

        mSystemFragmentAdapter?.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                if (adapter?.getItem(position) is MyTreeBean.ChildrenBean) {
                    startActivity(
                        Intent(activity, SystemListActivity::class.java)
                            .putExtra(SYSTEM_TYPE_ID, (adapter.getItem(position) as MyTreeBean.ChildrenBean).id)
                            .putExtra(SYSTEM_TYPE_TITLE, (adapter.getItem(position) as MyTreeBean.ChildrenBean).name)
                    )
                }
            }
        })
//        mSystemFragmentAdapter!!.setOnItemChildClickListener { adapter, view, position ->
//            if (adapter.getItem(position) is MyTreeBean.ChildrenBean) {
//                startActivity(
//                    Intent(activity, SystemListActivity::class.java)
//                        .putExtra(SYSTEM_TYPE_ID, (adapter.getItem(position) as MyTreeBean.ChildrenBean).id)
//                        .putExtra(SYSTEM_TYPE_TITLE, (adapter.getItem(position) as MyTreeBean.ChildrenBean).name)
//                )
//            }
//        }

        mViewModel.getTreeTypeList()
    }


    override fun startListenerData() {
        mViewModel.getmShowLoading().observe(this, Observer {
            it?.let { it1 -> setLoading(it1) }
        })

        mViewModel.getmToastMessage().observe(this, Observer {
            T.shortToast(context, it)
        })

        mViewModel.mTreeData.observe(this, Observer {
            mListData.clear()
            it?.let { it1 -> mListData.addAll(it1) }
            mSystemFragmentAdapter?.replaceData(mListData)
        })
    }

    override fun createLifeCycle(): SystemFragmentLifecycle {
        return SystemFragmentLifecycle(getContext())
    }

    override fun setLayout(): Int {
        return R.layout.fragment_system
    }


    override fun creatViewModel(): SystemFragmentViewMoldeImpl {
        return ViewModelProviders.of(getInstance()).get(SystemFragmentViewMoldeImpl::class.java)
    }

}