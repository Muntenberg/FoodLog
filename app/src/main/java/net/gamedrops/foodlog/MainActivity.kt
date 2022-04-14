package net.gamedrops.foodlog

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.net.Proxy
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var arrayofsup = arrayOf("ketchup","mustard","Relish","bun","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog","hotdog")
        val listView: ListView = findViewById(R.id.listView)
//        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
//            this, android.R.layout.simple_list_item_1, arrayofsup
//        )
//
//        listView.adapter = arrayAdapter
        var btnHomeAdd = findViewById<Button>(R.id.homeAddButton)
        var recycleviewaa = findViewById<RecyclerView>(R.id.recycleview)
        btnHomeAdd.setOnClickListener{

            var intent = Intent(this,Add_Entry::class.java)
            startActivity(intent)

        }

        var ReViewData = mutableListOf<logListData>()
//        var datetext = findViewById<TextView>(R.id.listTest)
        val sharedPreferences = getSharedPreferences("Add_Entry", Context.MODE_PRIVATE)
        val savedPref = sharedPreferences.all
        val dateList = arrayListOf<String>()
        val dateObjectList = arrayListOf<String>()
//        val dataList = arrayListOf<kotlin.collections.ArrayList<String>>()
        val dataList = arrayListOf<List<String>>()
        val regex = """(\{|\}|\[|\])""".toRegex()
        var testList = arrayListOf<String>()
        for ((key, value) in savedPref){
            testList.add("${value.toString().split(",")[0].toString()} - ${key.toString()} at ${value.toString().split(",")[1].toString()} with ${value.toString().split(",")[2].toString()}")
            println("the for - $key $value")
//            println(value.split(","))
//            val data = value
//            val test = regex.replace(data, "").split(",")
            dataList.add(value.toString().split(","))
            dateList.add(key)
//            dateObjectList.add()
            println("My DataBase: ${dataList[0][2]}")
//            println(dataList)
//            dateList.add()
//            dataList.add(gson.fromJson(value, arrayListOf<String>()::class.java))
//            dataList.add(gson.fromJson(key, Type))
        }

        val cmp = compareBy<String> { LocalDateTime.parse(it, DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm")) }
        var dateListSorted = dateList.sortedWith(cmp).reversed()
        println("The Sorted List $dateListSorted")
        for (br in dateListSorted) {
            var thedatarow = sharedPreferences.getString(br, null).toString().split(",")
            println("The For Loop $br - ${sharedPreferences.getString(br, null)}")
            ReViewData.add(logListData(typetoImg(thedatarow[0]), thedatarow[1], br, valueTextAdd(thedatarow[0], thedatarow[2])))
        }
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, testList
        )


        var todoList = mutableListOf(
            logListData(R.drawable.watericon, "123123", "123123", "brah1"),
            logListData(R.drawable.foodicon, "123123", "123123", "brah2"),
            logListData(R.drawable.watericon, "123123", "123123", "brah3"),
            logListData(R.drawable.watericon, "123123", "123123", "brah4"),
            logListData(R.drawable.watericon, "123123", "123123", "brah5"),
            logListData(R.drawable.watericon, "123123", "123123", "brah6"),
            logListData(R.drawable.watericon, "123123", "123123", "brah7"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah"),
            logListData(R.drawable.watericon, "123123", "123123", "brah")
        )

        listView.adapter = arrayAdapter
        val adapter = foodAdapter(ReViewData)
        findViewById<RecyclerView>(R.id.recycleview).adapter = adapter
        findViewById<RecyclerView>(R.id.recycleview).layoutManager = LinearLayoutManager(this)





//        val pref = getPreferences(Context.MODE_PRIVATE)
//        val editor = pref.edit()
//        val charges: Button = findViewById(R.id.buttonsub)
//        val savedString = pref.getString("test", null)
//        val result: TextView = findViewById(R.id.idhello)
//        result.text = savedString
//        println("my print - ${pref.all}")
//        val list = pref.all
//        println("selected thing ${list["test"]}")
//        for ((key, value) in list) {
//            println("$key and $value")
//        }
//
//
//        fun onSave() {
//            val brah: EditText = findViewById(R.id.nametest)
//            editor.putString("test", brah.text.toString())
//            editor.commit()
//        }
//
//        charges.setOnClickListener{
//            onSave()
//        }

    }

    private fun typetoImg(input: String) : Int{
        if (input == "Food"){
            return R.drawable.foodicon
        }
        if (input == "Water"){
            return R.drawable.watericon
        }
        return 0
    }

    private fun valueTextAdd(type: String, value: String) : String {
        if (type == "Food"){
            return "$value Calories"
        }
        if (type == "Water"){
            var formatDec = DecimalFormat(".00")
            return "${(formatDec.format(value.toDouble())).toString()} Ounces"
        }
        return ""
    }

}