package com.lira.cinetime.ui.myList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lira.cinetime.R
import com.lira.cinetime.presentation.myList.MyListViewModel

class MyListFragment : Fragment() {

    companion object {
        fun newInstance() = MyListFragment()
    }

    private lateinit var viewModel: MyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}