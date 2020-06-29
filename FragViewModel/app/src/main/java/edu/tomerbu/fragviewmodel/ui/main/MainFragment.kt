package edu.tomerbu.fragviewmodel.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.tomerbu.fragviewmodel.R
import kotlinx.android.synthetic.main.main_fragment.*
import java.lang.StringBuilder

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
           val users = it.fold(StringBuilder()){
               acc, user ->  acc.append(user.name).append(System.lineSeparator())
           }.toString()

            message.text = users
        })
    }

}
