package com.bsys.geartracker.ui.detailinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bsys.geartracker.ApplicationClass
import com.bsys.geartracker.databinding.FragmentDetailInfoBinding
import com.bsys.geartracker.databinding.FragmentEquipListBinding
import com.bsys.geartracker.utils.EQUIP_TOTAL_INFO

class DetailInfoFragment: Fragment() {

    private var _binding: FragmentDetailInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var serialNo: String

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
        binding.tvTitle.text = serialNo


        Toast.makeText(requireActivity(), "$serialNo + ${ApplicationClass.mainPref.getString("empNo", "없음")}", Toast.LENGTH_SHORT).show()
    }
}