package com.bsys.geartracker.ui.detailinfo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bsys.geartracker.R
import com.bsys.geartracker.data.model.response.EquipDetailResponse
import com.bsys.geartracker.databinding.FragmentDetailInfoBinding
import com.bsys.geartracker.ui.equiplist.EquipInfoViewModel
import com.bsys.geartracker.utils.BASE_URL
import com.bumptech.glide.Glide


class DetailInfoFragment: Fragment() {

    private var _binding: FragmentDetailInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var serialNo: String

    private val viewModel: EquipInfoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serialNo = arguments?.getString("serialNo") ?: "none"
        Log.d("arguments", "arguments ${arguments.toString()}")

        viewModel.get_equip_detail(serialNo)

        init_observer()
    }


    private fun init_observer() {
        viewModel.equipInfo.observe(viewLifecycleOwner) {
            if(it != null) {
                setUI()
            }
        }
    }

    private fun setUI() {
        binding.apply {
            val equip : EquipDetailResponse = viewModel.equipInfo.value ?: return
            Log.d("equipdetail", "equipdetail success ${equip.toString()}")
            tvSerialInfo.text = equip.serialNO
            tvEqNmInfo.text = equip.eqNM
            tvEqModelInfo.text = equip.eqModel
            tvEqMakerInfo.text = equip.eqMaker
            tvStatusNmInfo.apply {
                text = equip.statusNM
                if(equip.statusNM == "출고예정") setTextColor(Color.RED)
                else if(equip.statusNM == "반납예정") setTextColor(Color.BLUE)

                when(equip.statusNM) {
                    "출고예정" -> {tvStatusNmInfo.text = "배정예정"}
                    "반납예정" -> {tvStatusNmInfo.text = "회수예정"}
                    "출고" -> {tvStatusNmInfo.text = "배정완료"}
                    "반납" -> {tvStatusNmInfo.text = "회수완료"}
                }
            }
            Log.d("equipdetail", "null과 일치? ${equip.manualImgUrl == null}")

            if(equip.manualImgUrl != null && equip.manualImgUrl != "null") {
                val url = BASE_URL + equip.manualImgUrl
                Log.d("equipdetail", "equipdetail 서버 이미지 ${url}")
                Glide.with(requireActivity()).load(url).into(ivEvidence)
            } else {
                Log.d("equipdetail", "equipdetail 기본 이미지 ${equip.manualImgUrl}")
                ivEvidence.setImageResource(R.drawable.img_state_changed)
            }
        }
    }
}