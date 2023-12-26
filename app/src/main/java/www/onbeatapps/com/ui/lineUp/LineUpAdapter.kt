package www.onbeatapps.com.ui.lineUp

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import www.onbeatapps.com.R
import www.onbeatapps.com.data.model.api.response.LineUpRESPONSE
import www.onbeatapps.com.databinding.AdapterLineUpBinding
import www.onbeatapps.com.databinding.SampleListItemBinding
import www.onbeatapps.com.utils.Utils.Companion.convetDateToTime
import www.onbeatapps.com.utils.urls.Companion.image
import java.util.*

/**
 * Created by PKB on 11-06-2021.
 * PKB@gmail.com
 */
class LineUpAdapter(private val context: Context, val clickFunction: (String) -> Unit) :
    RecyclerView.Adapter<LineUpAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dataList:List<LineUpRESPONSE.Data> = arrayListOf()

    //This method inflates view present in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterLineUpBinding.inflate(inflater, parent, false));
    }

    //Binding the data using get() method of POJO object
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItems = dataList[position]
        holder.bindView(listItems, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setListContent(listItems: List<LineUpRESPONSE.Data>) {
        dataList = listItems
        notifyDataSetChanged()
    }

    inner class MyViewHolder(b: AdapterLineUpBinding) : RecyclerView.ViewHolder(b.root) {
        fun bindView(listItems: LineUpRESPONSE.Data, position: Int) {
            binding.root.setOnClickListener { clickFunction(listItems.artistCode) }
            binding.apply {
               tvName.text = listItems.artistName
                tvStage.text = listItems.stage
                tvLive.isVisible = listItems.live
                tvTime.text = "${convetDateToTime(listItems.startDate)} - ${convetDateToTime(listItems.endDate)}"
//                imgArtist.loadUrl(listItems.image)
                Glide.with(context).load(listItems.image).placeholder(R.drawable.ic_user).into(imgArtist)
//                if (listItems.)
            }
        }

        var binding: AdapterLineUpBinding = b

    }
}