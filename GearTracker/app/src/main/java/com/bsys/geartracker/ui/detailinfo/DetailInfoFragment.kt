package com.bsys.geartracker.ui.detailinfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bsys.geartracker.ApplicationClass
import com.bsys.geartracker.data.model.response.EquipDetailResponse
import com.bsys.geartracker.databinding.FragmentDetailInfoBinding
import com.bsys.geartracker.databinding.FragmentEquipListBinding
import com.bsys.geartracker.ui.equiplist.EquipInfoViewModel
import com.bsys.geartracker.utils.EQUIP_TOTAL_INFO

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
            tvStatusNmInfo.text = equip.statusNM
        }
    }
}