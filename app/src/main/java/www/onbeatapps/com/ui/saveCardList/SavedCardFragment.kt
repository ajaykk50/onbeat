package www.onbeatapps.com.ui.saveCardList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import www.onbeatapps.com.R
import www.onbeatapps.com.databinding.FragmentSaveCardListBinding
import www.onbeatapps.com.ui.base.BaseFragment

@AndroidEntryPoint
class SavedCardFragment : BaseFragment<SavedCardViewModel>() {
    private var _binding: FragmentSaveCardListBinding? = null
    private val binding get() = _binding!!
    private val SavedCardViewModel: SavedCardViewModel by viewModels()

    override fun setup() {
        setUpClick()
        setUpObserver()
        SavedCardViewModel.getUserDt()

    }

    private fun setUpObserver() {
        SavedCardViewModel.cardDt.observe(viewLifecycleOwner){
            if (it.card.isNotEmpty()){
                binding.apply {
                    txtCardNumber.text = "${it.card[0].cardType} **** **** **** ${it.card[0].lastFour}"
                }
            }
        }
    }

    private fun setUpClick() {
        binding.apply {
            btback.setOnClickListener {
                findNavController().popBackStack()
            }
            btAddCard.setOnClickListener {
                findNavController().navigate(
                    R.id.navigation_save_card)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveCardListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = SavedCardViewModel

}