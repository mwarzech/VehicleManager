package com.agh.wtm.vehiclemanager.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.agh.wtm.vehiclemanager.R

/**
 * A simple [Fragment] subclass.
 */
class RefuellingListFragment constructor(private val mCtx: Context): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_refuelling_list, container, false)
    }


}
