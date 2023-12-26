package www.onbeatapps.com.ui.homeFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import www.onbeatapps.com.R
import www.onbeatapps.com.data.model.api.response.TransationListRESPONSE
import www.onbeatapps.com.databinding.AdapterHomeBinding
import www.onbeatapps.com.utils.Utils.Companion.NumberFormatForCurrencyCode
import www.onbeatapps.com.utils.Utils.Companion.dateConvert

/**
 * Created by PKB on 11-06-2021.
 * PKB@gmail.com
 */
class HomeAdapter(private val context: Context, val vieModel: HomeViewModel) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dataList = listOf<TransationListRESPONSE.Transation>()
    private var homeAdapter: HomeItemAdapter? = null
    private var selceted = -1

    //This method inflates view present in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterHomeBinding.inflate(inflater, parent, false));
    }

    //Binding the data using get() method of POJO object
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItems = dataList[position]
        holder.bindView(listItems, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setListContent(listItems: List<TransationListRESPONSE.Transation>) {
        dataList = listItems
        notifyDataSetChanged()
    }

    inner class MyViewHolder(b: AdapterHomeBinding) : RecyclerView.ViewHolder(b.root) {
        fun bindView(listItems: TransationListRESPONSE.Transation, position: Int) {
            binding.root.setOnClickListener {
//                clickFunction()
                selceted = if (selceted==position) -1
                else position
                notifyDataSetChanged()
            }
            binding.apply {
                if (selceted == position) {
                    imgArrow.setImageResource(R.drawable.ic_down_line_arrow)
                    recyPric.visibility = View.VISIBLE
                } else {
                    imgArrow.setImageResource(R.drawable.ic_left_arrow)
                    recyPric.visibility = View.GONE

                }

                txtDateTime.text = dateConvert(listItems.date)
                if (listItems.price!=null) {
                    if (listItems.price.toString().contains("-")) {
                        txtVenterName.text = "${listItems.vendorName} - refund"
                        var split = listItems.price.toString().split("-")
                        txtPrice.text = "-${vieModel.Currency()} ${NumberFormatForCurrencyCode(split[1])}"
                    } else {
                        txtVenterName.text = listItems.vendorName
                        txtPrice.text = "${vieModel.Currency()} ${NumberFormatForCurrencyCode(listItems.price.toString())}"
                    }
                }else{
                    txtVenterName.text = listItems.vendorName
                    txtPrice.text = "${vieModel.Currency()} ${NumberFormatForCurrencyCode(listItems.price.toString())}"
                }
            }

            homeAdapter = HomeItemAdapter(context,vieModel) {

            }
            binding.recyPric.apply {
                setHasFixedSize(true)
                adapter = homeAdapter
            }
            if (listItems.products!=null) {
                if (!listItems.products!!.isNullOrEmpty())
                    homeAdapter!!.setListContent(listItems.products!!)
            }
        }

        var binding: AdapterHomeBinding = b

    }
}