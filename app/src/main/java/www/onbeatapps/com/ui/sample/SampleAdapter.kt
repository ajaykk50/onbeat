package www.onbeatapps.com.ui.sample

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import www.onbeatapps.com.databinding.SampleListItemBinding
import java.util.*

/**
 * Created by PKB on 11-06-2021.
 * PKB@gmail.com
 */
class SampleAdapter(private val context: Context, val clickFunction: () -> Unit) :
    RecyclerView.Adapter<SampleAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dataList = arrayListOf("", "")

    //This method inflates view present in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(SampleListItemBinding.inflate(inflater, parent, false));
    }

    //Binding the data using get() method of POJO object
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItems = dataList[position]
        holder.bindView(listItems, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setListContent(listItems: ArrayList<String>) {
        dataList = listItems
        notifyDataSetChanged()
    }

    inner class MyViewHolder(b: SampleListItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun bindView(listItems: String, position: Int) {
            binding.root.setOnClickListener { clickFunction() }
        }

        var binding: SampleListItemBinding = b

    }
}