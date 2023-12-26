package www.onbeatapps.com.ui.homeFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import www.onbeatapps.com.data.model.api.response.TransationListRESPONSE
import www.onbeatapps.com.databinding.AdapterItemHomeBinding
import www.onbeatapps.com.utils.Utils.Companion.NumberFormatForCurrencyCode

/**
 * Created by PKB on 11-06-2021.
 * PKB@gmail.com
 */
class HomeItemAdapter(private val context: Context,private val viewModel:HomeViewModel, val clickFunction: () -> Unit) :
    RecyclerView.Adapter<HomeItemAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
//    private var dataList = arrayListOf("Item one", "Item two","Item three")
    private var dataList = listOf<TransationListRESPONSE.Transation.Products>()

    //This method inflates view present in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterItemHomeBinding.inflate(inflater, parent, false));
    }

    //Binding the data using get() method of POJO object
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItems = dataList[position]
        holder.bindView(listItems, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setListContent(listItems: List<TransationListRESPONSE.Transation.Products>) {
        dataList = listItems
        notifyDataSetChanged()
    }

    inner class MyViewHolder(b: AdapterItemHomeBinding) : RecyclerView.ViewHolder(b.root) {
        fun bindView(listItems: TransationListRESPONSE.Transation.Products, position: Int) {
            binding.root.setOnClickListener { clickFunction() }
            binding.apply {

                if (listItems.productName.isNullOrBlank())
                    txtName.text = "item ${position+1}"
                else txtName.text = listItems.productName
                txtPrice.text = "${viewModel.Currency()} ${NumberFormatForCurrencyCode(listItems.price)}"
            }
        }

        var binding: AdapterItemHomeBinding = b

    }
}