package com.elvis.alcoholdispensersense.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.elvis.alcoholdispensersense.R
import com.elvis.alcoholdispensersense.models.OriginData
import com.elvis.alcoholdispensersense.items.OriginDataItem
import com.google.gson.GsonBuilder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_origin_data.view.*
import okhttp3.*
import java.io.IOException

class OriginDataFragment : Fragment() {

    var dataList: Array<OriginData?>? = null

    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchJson()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_origin_data, container,false)

        v.origin_data_recyclerview.adapter = adapter
        v.origin_data_recyclerview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        v.origin_data_num_textview.text = dataList?.size.toString()

        return v
    }

    private fun fetchJson() {
        val url = "https://iot.martinintw.com/api/v1/data/12345614"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                Log.d("FetchJson", body)

                val gson = GsonBuilder().create()

                val DataList = arrayOfNulls<OriginData>(300)
                dataList = gson.fromJson(body, DataList::class.java)

                activity?.runOnUiThread {
                    for(i in dataList.orEmpty()) {
                        adapter.add(OriginDataItem(i))
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("FetchJson", "Failed")
            }
        })
    }
}
