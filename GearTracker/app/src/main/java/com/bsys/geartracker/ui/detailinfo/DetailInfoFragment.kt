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

        // 장비 상세 정보 요청을 위한 시리얼 넘버
        serialNo = arguments?.getString("serialNo") ?: "none"

        // 서버에서 시리얼 넘버 가져오기
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
            tvSerialInfo.text = equip.serialNO
            tvEqNmInfo.text = equip.eqNM
            tvEqModelInfo.text = equip.eqModel
            tvEqMakerInfo.text = equip.eqMaker
            tvStatusNmInfo.apply {
                text = equip.statusNM
                if(equip.statusNM == "배정예정") setTextColor(Color.RED)
                else if(equip.statusNM == "회수예정") setTextColor(Color.BLUE)

                when(equip.statusNM) {
                    "배정예정" -> {tvStatusNmInfo.text = "배정예정"}
                    "회수예정" -> {tvStatusNmInfo.text = "회수예정"}
                    "배정" -> {tvStatusNmInfo.text = "배정완료"}
                    "회수" -> {tvStatusNmInfo.text = "회수완료"}
                }
            }

            // 이미지 없으면 기본 이미지 사용
            if(equip.manualImgUrl != null && equip.manualImgUrl != "null") {
                // 서버주소 + 이미지 URL로 실제 주소 생성
                val url = BASE_URL + equip.manualImgUrl
                Glide.with(requireActivity()).load(url).into(ivEvidence)
            } else {
                ivEvidence.setImageResource(R.drawable.img_state_changed)
            }
        }
    }
}