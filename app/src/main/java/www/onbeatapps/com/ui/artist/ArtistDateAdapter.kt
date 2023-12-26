package www.onbeatapps.com.ui.artist

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import www.onbeatapps.com.R
import www.onbeatapps.com.data.model.api.response.ArtistDetailsRESPONSE
import www.onbeatapps.com.data.model.api.response.ArtistListRESPONSE
import www.onbeatapps.com.databinding.AdapterArtiestDateBinding
import www.onbeatapps.com.databinding.AdapterArtistBinding
import www.onbeatapps.com.databinding.AdapterLineUpBinding
import www.onbeatapps.com.databinding.SampleListItemBinding
import www.onbeatapps.com.utils.Utils
import www.onbeatapps.com.utils.Utils.Companion.convetDateToTime
import java.util.*

/**
 * Created by PKB on 11-06-2021.
 * PKB@gmail.com
 */
class ArtistDateAdapter(private val context: Context, val clickFunction: () -> Unit) :
    RecyclerView.Adapter<ArtistDateAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dataList:List<ArtistDetailsRESPONSE.Data.Performance> = arrayListOf()

    //This method inflates view present in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterArtiestDateBinding.inflate(inflater, parent, false));
    }

    //Binding the data using get() method of POJO object
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItems = dataList[position]
        holder.bindView(listItems, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setListContent(listItems: List<ArtistDetailsRESPONSE.Data.Performance>) {
        dataList = listItems
        notifyDataSetChanged()
    }

    inner class MyViewHolder(b: AdapterArtiestDateBinding) : RecyclerView.ViewHolder(b.root) {
        fun bindView(listItems: ArtistDetailsRESPONSE.Data.Performance, position: Int) {
            binding.root.setOnClickListener { clickFunction() }
            binding.apply {
                tvDate.text = "${Utils.artisInfoConvert(listItems.startDate)}, ${convetDateToTime(listItems.startDate)} - ${convetDateToTime(listItems.endDate)}"
            }
        }

        var binding: AdapterArtiestDateBinding = b

    }
}