package net.gamedrops.foodlog

import android.content.Context
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.gson.Gson
import java.util.*
import kotlin.math.sign

class Add_Entry : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0
    var selectedType = "Food"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        pickDate()

        var btnEntryHome = findViewById<Button>(R.id.entryHomeButton)
        var btnEntryAdd = findViewById<Button>(R.id.entryAddButton)
        var radioTypeGroup = findViewById<RadioGroup>(R.id.radioType)
        var valInput = findViewById<EditText>(R.id.textValueInput)
        var unitSelectSpinner = findViewById<Spinner>(R.id.unitSpinner)
        var itemName = findViewById<EditText>(R.id.textItemName)
        var btnTimePick = findViewById<Button>(R.id.dateTimeButton)

        btnEntryHome.setOnClickListener{

            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }

        btnEntryAdd.setOnClickListener{
            var formScore = 0
            if (valInput.text.isEmpty()){
                valInput.setHintTextColor(Color.RED)
                formScore += 1
                Toast.makeText(this, "ERROR: Please fill in missing items", Toast.LENGTH_SHORT).show()
            }
            if (savedYear == 0){
                formScore += 1
                btnTimePick.setBackgroundColor(Color.RED)
                Toast.makeText(this, "ERROR: Please fill in missing items", Toast.LENGTH_SHORT).show()
            }
            if (formScore == 0){
                var newItemData = arrayOf("Type", "Name", 0)
                newItemData[0] = selectedType
                newItemData[1] = itemName.text.toString()
//            newItemData[2] = "$savedYear-$savedMonth-$savedDay+$savedHour:$savedMinute"
                if (selectedType == "Food"){
                    newItemData[2] = valInput.getText().toString()/*.toInt()*/
                } else {
                    newItemData[2] = volumeConverter(valInput.getText().toString().toDouble(), unitSelectSpinner.selectedItem.toString())
                }
                println("${newItemData[0]} ${newItemData[1]} ${newItemData[2]}")

                val pref = getPreferences(Context.MODE_PRIVATE)
                val editor = pref.edit()
//                val dataJsonString = '"' + Gson().toJson(newItemData) + '"'
                val dataJsonString = "${newItemData[0]},${newItemData[1]},${newItemData[2]}"
                editor.putString(dateZeroAdder(savedYear, savedDay, savedMonth, savedHour, savedMinute), dataJsonString)
                editor.commit()
            }
        }

        radioTypeGroup.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.radioFood) {
                Toast.makeText(this, "food", Toast.LENGTH_SHORT).show()
                valInput.hint = "Calories"
                unitSelectSpinner.visibility = View.GONE
                selectedType = "Food"
            }
            if(checkedId == R.id.radioWater) {
                Toast.makeText(this, "water", Toast.LENGTH_SHORT).show()
                valInput.hint = "Volume"
                unitSelectSpinner.visibility = View.VISIBLE
                selectedType = "Water"
            }
        }

    }

    private fun volumeConverter(inputUnit: Double, type: String): String{
        if (type == "Ounces") {
            return inputUnit.toString()
        } else if (type == "Liters"){
            return (inputUnit * 33.814).toString()
        }
        return 0.0.toString()
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)

    }

    private fun pickDate() {
        var btnTimePick = findViewById<Button>(R.id.dateTimeButton)
        btnTimePick.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedYear = year
        savedMonth = month + 1

        getDateTimeCalendar()

        TimePickerDialog(this, this, hour, minute, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        var datetext = findViewById<TextView>(R.id.selectedDateText)
        datetext.text = "Selected Date and Time: $savedDay-${savedMonth}-$savedYear $savedHour:$savedMinute"
    }

    private fun dateZeroAdder(Year: Int, Day: Int, Month: Int, Hour: Int, Minute: Int): String {
        var rDay = Day.toString()
        var rMonth = Month.toString()
        var rHour = Hour.toString()
        var rMinute = Minute.toString()
        if (Day < 10) {
            rDay = "0" + Day
        }
        if (Month < 10) {
            rMonth = "0" + Month
        }
        if (Hour < 10) {
            rHour = "0" + Hour
        }
        if (Minute < 10) {
            rMinute = "0" + Minute
        }
        return "$rDay-$rMonth-$Year | $rHour:$rMinute"
    }


}