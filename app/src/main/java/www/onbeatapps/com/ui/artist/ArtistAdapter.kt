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
import www.onbeatapps.com.data.model.api.response.ArtistListRESPONSE
import www.onbeatapps.com.databinding.AdapterArtistBinding
import www.onbeatapps.com.databinding.AdapterLineUpBinding
import www.onbeatapps.com.databinding.SampleListItemBinding
import java.util.*

/**
 * Created by PKB on 11-06-2021.
 * PKB@gmail.com
 */
class ArtistAdapter(private val context: Context, val clickFunction: (ArtistListRESPONSE.Data) -> Unit) :
    RecyclerView.Adapter<ArtistAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dataList:List<ArtistListRESPONSE.Data> = arrayListOf()
    var image = "https://m.media-amazon.com/images/M/MV5BYmUyNWVjYmUtY2Y1NS00NDMwLWI1MmMtOWZmMzQ5Yzc0Y2QwXkEyXkFqcGdeQXVyMjQwMDg0Ng@@._V1_.jpg"

    //This method inflates view present in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterArtistBinding.inflate(inflater, parent, false));
    }

    //Binding the data using get() method of POJO object
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItems = dataList[position]
        holder.bindView(listItems, position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setListContent(listItems: List<ArtistListRESPONSE.Data>) {
        dataList = listItems
        notifyDataSetChanged()
    }

    inner class MyViewHolder(b: AdapterArtistBinding) : RecyclerView.ViewHolder(b.root) {
        fun bindView(listItems: ArtistListRESPONSE.Data, position: Int) {
            binding.root.setOnClickListener { clickFunction(listItems) }
            binding.apply {
                tvName.text = listItems.artistName
//                tvLive.isVisible = listItems.live
                Glide.with(context).load(listItems.image).placeholder(R.drawable.ic_user).into(imgArtist)
            }
        }

        var binding: AdapterArtistBinding = b

    }
}