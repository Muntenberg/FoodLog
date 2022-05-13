package net.gamedrops.foodlog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.gamedrops.foodlog.databinding.ActivityMainBinding
import org.w3c.dom.Text

class foodAdapter(
    var listData: List<logListData>
    ) : RecyclerView.Adapter<foodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var dButton:ImageView
        init {
            dButton = itemView.findViewById(R.id.imageView2)
            val sharedPreferences = itemView.getContext().getSharedPreferences("Add_Entry", Context.MODE_PRIVATE)
            val mEditor = sharedPreferences.edit()
            dButton.setOnClickListener{ println("Button Clicked " + listData[position].date) }
            dButton.setOnClickListener{
                println("Button Clicked " + listData[position].date)
                mEditor.remove(listData[position].date).apply()
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.logitemrow, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.nameText).text = listData[position].name
            findViewById<TextView>(R.id.dateText).text = listData[position].date
            findViewById<TextView>(R.id.valueText).text = listData[position].value
            findViewById<ImageView>(R.id.foodType).setImageResource(listData[position].type)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}