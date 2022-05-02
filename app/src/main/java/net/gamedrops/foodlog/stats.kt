package net.gamedrops.foodlog

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.*
import java.util.*
import kotlin.collections.ArrayList
import java.time.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class stats : AppCompatActivity() {

    lateinit var barList: ArrayList<BarEntry>
    lateinit var lineDataSet: BarDataSet
    lateinit var barData: BarData

    private lateinit var barChart: BarChart
    private var scoreList = ArrayList<DayWeekData>()
//
    data class DayWeekData(
        val day:String,
        val data: Float
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val sharedPreferences = getSharedPreferences("Add_Entry", Context.MODE_PRIVATE)
        val savedPref = sharedPreferences.all
        val dateList = arrayListOf<String>()
        val dataList = arrayListOf<List<String>>()
        var dateObj = mutableListOf<Date>()
        for ((key, value) in savedPref) {
            dataList.add(value.toString().split(","))
            dateList.add(key)
        }
        val cmp = compareBy<String> { LocalDateTime.parse(it, DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm")) }
        var dateListSorted = dateList.sortedWith(cmp).reversed()
        var dateDataList = mutableListOf<List<String>>()
        for (x in dateListSorted) {
            dateObj.add(SimpleDateFormat("dd-MM-yyyy | HH:mm").parse(x))

        }
        for (br in dateListSorted) {
            dateDataList.add(sharedPreferences.getString(br, null).toString().split(","))
        }
        var weekAvg = WeekAverage(dateObj, dateListSorted, dateDataList)
        println(weekAvg)
        var toDay = LocalDateTime.now()
        var orderOfWeek = weekOrder(stringToWeekInt(toDay.dayOfWeek.toString()))
        println(orderOfWeek)
        var weektext = findViewById<TextView>(R.id.weekAv)
        var wt = "Week Averages\n"
        for (y in orderOfWeek){
            wt += "${IntToWeekString(y)}: ${weekAvg[y]}\n"
        }
        weektext.text = wt

//        var barChart = findViewById<BarChart>(R.id.barChart)
//        scoreList = getScoreList()
//        val entries: ArrayList<BarEntry> = ArrayList()
//        for (i in scoreList.indices) {
//            val score = scoreList[i]
//            entries.add(BarEntry(i.toFloat(), score.data.toFloat()))
//        }
//        val barDataSet = BarDataSet(entries, "")
//        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
//
//
//        val data = BarData(barDataSet)
//        barChart.data = data
//
//        barChart.invalidate()




//        var barChart = findViewById<BarChart>(R.id.barChart)
//
//        barList = ArrayList()
//        barList.add(BarEntry(1f,500f, "sdfdsf"))
//        barList.add(BarEntry(2f,700f, "sdfdsf"))
//        barList.add(BarEntry(3f,300f, "sdfdsf"))
//        barList.add(BarEntry(4f,200f, "sdfdsf"))
//        barList.add(BarEntry(5f,500f, "sdfdsf"))
//        lineDataSet = BarDataSet(barList, "Test")
//        barData = BarData(lineDataSet)
//        barChart.data = barData
//        lineDataSet.setColor(Color.CYAN, 250)
//        lineDataSet.valueTextColor = Color.BLACK
//        lineDataSet.valueTextSize = 15f
//        lineDataSet.setDraw
//    }
//
//    private fun getScoreList(): ArrayList<DayWeekData> {
//        scoreList.add(DayWeekData("John", 56f))
//        scoreList.add(DayWeekData("Rey", 75f))
//        scoreList.add(DayWeekData("Steve", 85f))
//        scoreList.add(DayWeekData("Kevin", 45f))
//        scoreList.add(DayWeekData("Jeff", 63f))
//
//        return scoreList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun WeekAverage(objDate: MutableList<Date>, dateList: List<String>, dateDataList: MutableList<List<String>>) : MutableList<Int> {
        var todayDate = LocalDateTime.now()
        var lastweek = todayDate.minusDays(6).withHour(0).withMinute(0).withSecond(0)
        var weekAvg= mutableListOf<Int>()
//        println(objDate[1].after(SimpleDateFormat("dd-MM-yyyy | HH:mm").parse("${lastweek.dayOfMonth}-${lastweek.monthValue}-${lastweek.year} | 00:00")) )
        println(objDate)
        println(lastweek)
        println(SimpleDateFormat("dd-MM-yyyy | HH:mm").parse("${lastweek.dayOfMonth}-${lastweek.monthValue}-${lastweek.year} | 00:00"))
        for (num in 0..6) {
            weekAvg.add(0)
        }
        for (x in dateList) {
            if (SimpleDateFormat("dd-MM-yyyy | HH:mm").parse("${lastweek.dayOfMonth}-${lastweek.monthValue}-${lastweek.year} | 00:00") <= objDate[dateList.indexOf(x)] && objDate[dateList.indexOf(x)] <= SimpleDateFormat("dd-MM-yyyy | HH:mm").parse("${todayDate.dayOfMonth}-${todayDate.monthValue}-${todayDate.year} | 23:59")){
                println(objDate[dateList.indexOf(x)].day)
                weekAvg[objDate[dateList.indexOf(x)].day] += dateDataList[dateList.indexOf(x)][2].toInt()
            }
        }
        return weekAvg
    }

    private fun weekOrder(day: Int) : MutableList<Int> {
        var array = mutableListOf<Int>()
        var dayin = day
        for (x in 0..6) {
            if (dayin <= 5){
                dayin += 1
                array.add(dayin)
            }else {
                dayin = 0
                array.add(dayin)
            }
        }
        return array
    }

    private fun stringToWeekInt(Day: String) : Int {
        when(Day) {
            "SUNDAY" -> return 0
            "MONDAY" -> return 1
            "TUESDAY" -> return 2
            "WEDNESDAY" -> return 3
            "THURSDAY" -> return 4
            "FRIDAY" -> return 5
            "SATURDAY" -> return 6
            }
        return 0
        }

    private fun IntToWeekString(Day: Int) : String {
        when(Day) {
            0 -> return "SUNDAY"
            1 -> return "MONDAY"
            2 -> return "TUESDAY"
            3 -> return "WEDNESDAY"
            4 -> return "THURSDAY"
            5 -> return "FRIDAY"
            6 -> return "SATURDAY"
        }
        return ""
    }
    }