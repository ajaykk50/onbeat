package www.onbeatapps.com.ui.setSpendingLimit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.AdapterSpendingLimitBinding

/**
 * Created by PKB on 11-06-2021.
 * PKB@gmail.com
 */
class SpendingLimitAdapter(private val context: Context,private val vieModel:SpendingLimitViewModel, val clickFunction: (Int) -> Unit) :
    RecyclerView.Adapter<SpendingLimitAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dataList = arrayListOf("20", "50", "100", "200")
    private var selceted = -1
    private var clicked = true
    var types = 1

    //This method inflates view present in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterSpendingLimitBinding.inflate(inflater, parent, false));
    }

    //Binding the data using get() method of POJO object
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItems = dataList[position]
        holder.bindView(listItems, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setListContent(type: Int) {
        types = type
        dataList
        notifyDataSetChanged()
    }
    fun notifiy(){
        if (clicked) {
            selceted = -1
            notifyDataSetChanged()
        }else{
            clicked = true
        }
    }

    inner class MyViewHolder(b: AdapterSpendingLimitBinding) : RecyclerView.ViewHolder(b.root) {
        @SuppressLint("ResourceAsColor")
        fun bindView(listItems: String, position: Int) {

            binding.apply {
                btLimit.text = "${vieModel.Currency()} $listItems"
                if(position % 2 == 0) space.visibility = View.VISIBLE
                else space.visibility = View.GONE
                if (selceted == position) {
                    if (types==1) {
                        btLimit.setBackgroundDrawable(context.getDrawable(R.drawable.bg_uselect_button))
                        btLimit.setTextColor(Color.parseColor("#9F07C8"))
                    }else{
                        btLimit.setBackgroundDrawable(context.getDrawable(R.drawable.bg_white_blue_line))
                        btLimit.setTextColor(Color.parseColor("#51AADF"))
                    }
                } else {
                    if (types==1) {
                        btLimit.setBackgroundDrawable(context.getDrawable(R.drawable.bg_vilot_bt))
                        btLimit.setTextColor(Color.parseColor("#000000"))
                    }else{
                        btLimit.setBackgroundDrawable(context.getDrawable(R.drawable.ic_bg_gradient_small))
                        btLimit.setTextColor(Color.parseColor("#FFFFFF"))
                    }
                }
                btLimit.setOnClickListener {
                    clicked = false
                    if (selceted==position){
                        clickFunction(-1)
                        selceted = -1
                    }else{
                        selceted = position
                        clickFunction(listItems.toInt())
                    }
                    notifyDataSetChanged()
                }
            }
        }

        var binding: AdapterSpendingLimitBinding = b

    }
}