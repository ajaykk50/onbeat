package www.onbeatapps.com.ui.lineUp

import android.R.attr.country
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.data.model.api.response.LineUpRESPONSE
import www.onbeatapps.com.databinding.FragmentLineUpBinding
import www.onbeatapps.com.ui.base.BaseFragment
import www.onbeatapps.com.ui.dashBoardActivity.DashBoardActivity


@AndroidEntryPoint
class LineUpFragment : BaseFragment<LineUpViewModel>(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentLineUpBinding? = null
    private val binding get() = _binding!!
    private val lineUpViewModel: LineUpViewModel by viewModels()
    private var lineUpAdapter: LineUpAdapter? = null
    var datList:ArrayList<String> = arrayListOf()
    var artistData: List<LineUpRESPONSE.Data> = arrayListOf()

    override fun setup() {

    }

   fun setupSpinner(datList: ArrayList<String>) {

       val aa  = ArrayAdapter<String>(mContext, R.layout.spinner_text_artist, datList)
       aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
       binding.spinner.adapter = aa

   }


    private fun setUpObserver() {
        lineUpViewModel.lineUpList.observe(viewLifecycleOwner){
            it.forEach { day ->
                if (!datList.contains(day.day))
                    datList.add(day.day)
            }
            setupSpinner(datList)
            artistData = it

        }
    }

    private fun setUpClick() {
        binding.apply {
            btSideMenu.setOnClickListener {
                DashBoardActivity.clickEventSlide()
            }
            spinner.onItemSelectedListener = this@LineUpFragment
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        lineUpAdapter!!.setListContent(artistData.filter { filteDat ->
            filteDat.day == p0?.selectedItem
        })
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


    private fun setUpRV() {
        lineUpAdapter = LineUpAdapter(mContext){

//            artistDt = it.artistInfo
            findNavController().navigate(R.id.action_navigation_line_up_to_navigation_artist_info, bundleOf("artistId" to it))
        }
        binding.recyArtiest.apply {
            setHasFixedSize(true)
            adapter = lineUpAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding==null) {
            activity?.window?.statusBarColor = resources.getColor(R.color.white)
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            _binding = FragmentLineUpBinding.inflate(inflater, container, false)

            DashBoardActivity.naviId =  R.id.navigation_line_up
            setUpRV()
            setUpClick()
            setUpObserver()
            if (lineUpViewModel.artistList.value.isNullOrEmpty())
                lineUpViewModel.getLineUp()
        }
        return binding.root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    override fun getViewModel() = lineUpViewModel

}