package com.elvis.alcoholdispensersense.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.elvis.alcoholdispensersense.R
import com.elvis.alcoholdispensersense.models.OriginData
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.google.gson.GsonBuilder
import com.superlht.htloading.view.HTLoading
import kotlinx.android.synthetic.main.fragment_bar_chart.view.*
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class BarChartFragment : Fragment() {

    var v: View? = null

    var date_from: String? = null
    var date_to: String? = null

    var dataArray: IntArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    var total: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_bar_chart, container, false)
        return v
    }

    override fun onResume() {
        super.onResume()

        v?.date_edittext?.setOnClickListener(DateListener)
        v?.enter_bar_chart_button?.setOnClickListener(ClickListener)
    }


    val calender = Calendar.getInstance()

    private val DateListener = View.OnClickListener {
        DatePickerDialog(activity,
            Date,
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)).show()
    }

    private val Date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        calender.set(year, month, day)
        format("yyyy-MM-dd", v?.date_edittext as View)
    }

    fun format(format: String, view: View) {
        val time = SimpleDateFormat(format, Locale.TAIWAN)
        (view as EditText).setText(time.format(calender.time))
    }

    private val ClickListener = View.OnClickListener {
        date_from = v?.date_edittext?.text.toString()
        date_to = getToDate(date_from)

        fetchJson()

        Log.d("DATE_DATA", date_from + "/////" +date_to)

        val handler = Handler()
        val runnable = Runnable {
            try {
                Thread.sleep(1800)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            v?.total_bar_chart_textview?.text = "全天使用總次數：$total 次"
            Log.d("TOTAL_DATE_DATA", "$total")

            updateBarChart()

            HTLoading(activity!!).setSuccessText("加载成功！").showSuccess()

        }

        handler.post(runnable)
    }


    private fun getToDate(date: String?): String {
        val str = date?.split("-")
        val str_temp: String

        if(str!![2].toInt() + 1 < 10) {
            str_temp = "0" + (str[2].toInt() + 1).toString()
        } else {
            str_temp = (str[2].toInt() + 1).toString()
        }
        return str[0] + "-" + str[1] + "-" +str_temp
    }


    private fun fetchJson() {
        val url = "https://iot.martinintw.com/api/v1/data/12345614?date_filter=$date_from+-+$date_to"
        Log.d("URLLLL", url)

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                Log.d("FetchJson", body)

                val gson = GsonBuilder().create()

                val DataList = arrayOfNulls<OriginData>(1000)
                val dataList = gson.fromJson(body, DataList::class.java)

                processData(dataList)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("FetchJson", "Failed")
            }
        })
    }

    private fun processData(dataList: Array<OriginData?>) {
        dataArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        total = 0

        for(i in dataList) {
            val list = i?.created_at?.split(' ', '-', ':')
            when(list!![3]) {
                "00" -> {
                    dataArray[0]++
                    total++
                }
                "01" -> {
                    dataArray[1]++
                    total++
                }
                "02" -> {
                    dataArray[2]++
                    total++
                }
                "03" -> {
                    dataArray[3]++
                    total++
                }
                "04" -> {
                    dataArray[4]++
                    total++
                }
                "05" -> {
                    dataArray[5]++
                    total++
                }
                "06" -> {
                    dataArray[6]++
                    total++
                }
                "07" -> {
                    dataArray[7]++
                    total++
                }
                "08" -> {
                    dataArray[8]++
                    total++
                }
                "09" -> {
                    dataArray[9]++
                    total++
                }
                "10" -> {
                    dataArray[10]++
                    total++
                }

                "11" -> {
                    dataArray[11]++
                    total++
                }
                "12" -> {
                    dataArray[12]++
                    total++
                }
                "13" -> {
                    dataArray[13]++
                    total++
                }
                "14" -> {
                    dataArray[14]++
                    total++
                }
                "15" -> {
                    dataArray[15]++
                    total++
                }
                "16" -> {
                    dataArray[16]++
                    total++
                }
                "17" -> {
                    dataArray[17]++
                    total++
                }
                "18" -> {
                    dataArray[18]++
                    total++
                }
                "19" -> {
                    dataArray[19]++
                    total++
                }
                "20" -> {
                    dataArray[20]++
                    total++
                }
                "21" -> {
                    dataArray[21]++
                    total++
                }
                "22" -> {
                    dataArray[22]++
                    total++
                }
                "23" -> {
                    dataArray[23]++
                    total++
                }

            }
        }
        Log.d("processDataDay", "$total" + "////" + dataArray[15].toString())
    }


    private fun updateBarChart() {
        // create BarEntry for Bar Group
        val bargroup = ArrayList<BarEntry>()

        for(ix in dataArray.indices) {
            Log.d("AAAAAAAAAAAAA", "dataArray[$ix] = " + dataArray[ix])
            bargroup.add(BarEntry(ix.toFloat(), dataArray[ix].toFloat(), ix.toString()))
        }

        // creating dataset for Bar Group
        val barDataSet = BarDataSet(bargroup, "每小時使用次數")

        barDataSet.color = ContextCompat.getColor(activity!!, R.color.material_blue_grey_800)

        val data = BarData(barDataSet)
        v?.bar_chart?.setData(data)
        v?.bar_chart?.xAxis?.position = XAxis.XAxisPosition.BOTTOM
        v?.bar_chart?.xAxis?.labelCount = 12
        v?.bar_chart?.xAxis?.enableGridDashedLine(5f, 5f, 0f)
        v?.bar_chart?.axisRight?.enableGridDashedLine(5f, 5f, 0f)
        v?.bar_chart?.axisLeft?.enableGridDashedLine(5f, 5f, 0f)
        v?.bar_chart?.description?.isEnabled = false
        v?.bar_chart?.animateY(1000)
        v?.bar_chart?.legend?.isEnabled = false
        v?.bar_chart?.setPinchZoom(true)
        v?.bar_chart?.data?.setDrawValues(false)

        //v?.bar_chart?.xAxis?.setCenterAxisLabels(true)

        val quarters = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23")

        val formatter = object : IAxisValueFormatter {

            // we don't draw numbers, so no decimal digits needed
            val decimalDigits: Int
                get() = 0

            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                return quarters[value.toInt()]
            }
        }
        v?.bar_chart?.xAxis?.granularity = 1f
        v?.bar_chart?.xAxis?.setValueFormatter(formatter)
    }

}
