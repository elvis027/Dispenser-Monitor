package com.elvis.alcoholdispensersense.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.elvis.alcoholdispensersense.R
import com.elvis.alcoholdispensersense.models.OriginData
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_line_chart.view.*
import okhttp3.*
import java.io.IOException
import java.util.*
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter



class LineChartFragment : Fragment() {

    var v: View? = null

    val date_from: String? = "2019-01-07"
    val date_to: String? = "2019-01-15"

    var dataArray: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
    var total: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchJson()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_line_chart, container, false)

        val handler = Handler()
        val runnable = Runnable {
            try {
                Thread.sleep(1800)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            updateLineChart()
            updateProgressBar()

            v?.total_line_chart_textview?.text = "1/07~1/14 使用總次數：$total 次"
            v?.rest_percent?.text = "(${(2000 - total * 5) / 20}%)"
        }

        handler.post(runnable)

        return v
    }


    private fun fetchJson() {
        val url = "https://iot.martinintw.com/api/v1/data/12345614?date_filter=$date_from+-+$date_to"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                Log.d("FetchJson", body)

                val gson = GsonBuilder().create()

                val DataList = arrayOfNulls<OriginData>(300)
                val dataList = gson.fromJson(body, DataList::class.java)

                processData(dataList)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("FetchJson", "Failed")
            }
        })
    }

    private fun processData(dataList: Array<OriginData?>) {
        dataArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
        total = 0

        for(i in dataList) {
            val list = i?.created_at?.split(' ', '-', ':')
            when(list!![2]) {
                "07" -> {
                    dataArray[0]++
                    total++
                }
                "08" -> {
                    dataArray[1]++
                    total++
                }
                "09" -> {
                    dataArray[2]++
                    total++
                }
                "10" -> {
                    dataArray[3]++
                    total++
                }
                "11" -> {
                    dataArray[4]++
                    total++
                }
                "12" -> {
                    dataArray[5]++
                    total++
                }
                "13" -> {
                    dataArray[6]++
                    total++
                }
                "14" -> {
                    dataArray[7]++
                    total++
                }
            }
        }
        Log.d("processData", dataArray[0].toString())
    }


    private fun updateLineChart() {

        val yVals = ArrayList<Entry>()

        for(ix in dataArray.indices) {
            Log.d("AAAAAAAAAAAAA", "dataArray[$ix] = " + dataArray[ix])
            yVals.add(Entry(ix.toFloat(), dataArray[ix].toFloat(), ix.toString()))
        }


        val set1: LineDataSet
        set1 = LineDataSet(yVals, "每天使用次數")

        // set1.fillAlpha = 110
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(5f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.color = Color.BLUE
        set1.setCircleColor(Color.BLUE)
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 0f
        set1.setDrawFilled(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        val data = LineData(dataSets)

        // set data
        v?.line_chart?.setData(data)
        v?.line_chart?.description?.isEnabled = false
        v?.line_chart?.legend?.isEnabled = false
        v?.line_chart?.setPinchZoom(true)
        v?.line_chart?.xAxis?.enableGridDashedLine(5f, 5f, 0f)
        v?.line_chart?.axisRight?.enableGridDashedLine(5f, 5f, 0f)
        v?.line_chart?.axisLeft?.enableGridDashedLine(5f, 5f, 0f)
        //lineChart.setDrawGridBackground()
        v?.line_chart?.xAxis?.labelCount = 11
        v?.line_chart?.xAxis?.position = XAxis.XAxisPosition.BOTTOM

        // the labels that should be drawn on the XAxis
        val quarters = arrayOf("1/7", "1/8", "1/9", "1/10", "1/11", "1/12", "1/13", "1/14")

        val formatter = object : IAxisValueFormatter {

            // we don't draw numbers, so no decimal digits needed
            val decimalDigits: Int
                get() = 0

            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return quarters[value.toInt()]
            }
        }
        v?.line_chart?.xAxis?.granularity = 1f
        v?.line_chart?.xAxis?.setValueFormatter(formatter)

    }

    private fun updateProgressBar() {
        var nowProgress = 2000 - total * 5
        v?.rest_progressbar?.setProgress(nowProgress, true)
    }

}
