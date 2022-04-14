package net.gamedrops.foodlog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.gamedrops.foodlog.databinding.ActivityMainBinding
import org.w3c.dom.Text

//class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
//
//    private var name = arrayOf("brah", "ree", "two")
//    private var date = arrayOf("someday", "someday", "someday")
//    private var valuelist = arrayOf("34", "343", "234234")
//    private var type = arrayOf(R.drawable.foodicon, R.drawable.watericon, R.drawable.foodicon)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.logitemrow, parent, false)
//        return ViewHolder(v)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
//        holder.itemTitle.text = name[position]
//        holder.itemImage.setImageResource(type[position])
//        holder.itemDate.text = date[position]
//        holder.itemVal.text = valuelist[position]
//
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//
//    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        var itemImage: ImageView
//        var itemTitle: TextView
//        var itemDate: TextView
//        var itemVal: TextView
//        var itemRemove: ImageView
//
//        init {
//            itemImage = itemView.findViewById(R.id.typeImageRe)
//            itemTitle = itemView.findViewById(R.id.nameText)
//            itemDate = itemView.findViewById(R.id.dateText)
//            itemVal = itemView.findViewById(R.id.valueText)
//            itemRemove = itemView.findViewById(R.id.removeButton)
//        }
//
//    }
//
//}
//
//data class Test(val imageResource: Int, val title: String, val date: String, val valu: String)

class foodAdapter(
    var listData: List<logListData>
    ) : RecyclerView.Adapter<foodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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