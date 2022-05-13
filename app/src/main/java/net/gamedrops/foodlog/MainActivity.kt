package net.gamedrops.foodlog

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
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
        val listView: ListView = findViewById(R.id.listView)
        var btnHomeAdd = findViewById<Button>(R.id.homeAddButton)
        var recycleviewaa = findViewById<RecyclerView>(R.id.recycleview)
        btnHomeAdd.setOnClickListener{

            var intent = Intent(this,Add_Entry::class.java)
            startActivity(intent)

        }
        var homeStatButton = findViewById<Button>(R.id.homeStatButton)
        var refreshButton = findViewById<ImageButton>(R.id.refreshButton)
        var resetButton = findViewById<Button>(R.id.buttonClear)
        homeStatButton.setOnClickListener{

            var intent = Intent(this,stats::class.java)
            startActivity(intent)

        }
        refreshButton.setOnClickListener{
            finish()
            startActivity(getIntent())
        }


        var ReViewData = mutableListOf<logListData>()
        val sharedPreferences = getSharedPreferences("Add_Entry", Context.MODE_PRIVATE)
        val savedPref = sharedPreferences.all
        val dateList = arrayListOf<String>()
        val dateObjectList = arrayListOf<String>()
        val dataList = arrayListOf<List<String>>()
        val regex = """(\{|\}|\[|\])""".toRegex()
        var testList = arrayListOf<String>()
        for ((key, value) in savedPref){
            testList.add("${value.toString().split(",")[0].toString()} - ${key.toString()} at ${value.toString().split(",")[1].toString()} with ${value.toString().split(",")[2].toString()}")
            println("the for - $key $value")
            dataList.add(value.toString().split(","))
            dateList.add(key)
            println("My DataBase: ${dataList[0][2]}")
        }
        resetButton.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you Sure!")
            builder.setMessage("Do you want to clear all data?")
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener{ dialog, id ->
                dialog.cancel()
                sharedPreferences.edit().clear().commit()
                startActivity(getIntent())
            })
            builder.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, id ->
                dialog.cancel()
            })
            var alet = builder.create()
            alet.show()
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


        listView.adapter = arrayAdapter
        val adapter = foodAdapter(ReViewData)
        findViewById<RecyclerView>(R.id.recycleview).adapter = adapter
        findViewById<RecyclerView>(R.id.recycleview).layoutManager = LinearLayoutManager(this)


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