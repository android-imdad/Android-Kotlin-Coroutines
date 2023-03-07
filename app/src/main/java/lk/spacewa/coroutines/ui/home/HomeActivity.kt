package lk.spacewa.coroutines.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import lk.spacewa.coroutines.BR
import lk.spacewa.coroutines.R
import lk.spacewa.coroutines.ui.base.BaseActivity
import lk.spacewa.coroutines.databinding.ActivityHomeBinding

/**
 * Created by Imdad on 05/11/20.
 */
//Should use AndroidEntryPoint annotation to tell Hilt where you need to inject the modules, typically used in Activities and fragments
@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding?, HomeViewModel?>() {

    //when using the same instance of the HomeViewModel in a fragment you can initiate it by using the following code in the fragment
    //private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mHomeViewModel: HomeViewModel by viewModels()

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_home

    override val viewModel: HomeViewModel?
        get() {
            return mHomeViewModel
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        setListeners()
    }

    private fun setListeners(){
        viewDataBinding?.fabSortStarwars?.setOnClickListener {
            viewModel?.switchData()
        }
    }
    private fun initRecyclerView(){
        val homeRvAdapter = HomeRvAdapter()
        val layoutManager = LinearLayoutManager(this@HomeActivity)
        viewDataBinding?.rvStarwarsDetails?.layoutManager = layoutManager
        viewDataBinding?.rvStarwarsDetails?.adapter = homeRvAdapter
        subscribeUI(homeRvAdapter)
    }

    private fun subscribeUI(adapter: HomeRvAdapter){
        viewModel?.starwarsSortMediator!!.observe(this, Observer {
            adapter.submitList(it)
        })

    }


}