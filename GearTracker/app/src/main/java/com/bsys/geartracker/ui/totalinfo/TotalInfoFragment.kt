package com.bsys.geartracker.ui.totalinfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bsys.geartracker.adapter.TotalInfoAdapter
import com.bsys.geartracker.data.model.dto.Equipment
import com.bsys.geartracker.databinding.FragmentTotalInfoBinding
import com.bsys.geartracker.utils.EQUIP_TOTAL_INFO

class TotalInfoFragment: Fragment() {
    private var _binding: FragmentTotalInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TotalInfoViewModel by viewModels()

    private val totalInfoAdapter: TotalInfoAdapter by lazy {TotalInfoAdapter()}

    private var mode: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTotalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init_mode()
        init_button()
        init_recyclerView()
        init_observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 출납현황, 재물조사 중 모드에 따른 설정
    private fun init_mode() {
        // Mode Flag 변경
        mode = arguments?.getInt("info_type") ?: EQUIP_TOTAL_INFO
        // UI 변경
        set_ui_for_mode(mode)
    }

    private fun set_ui_for_mode(mode: Int) {
        if(mode == EQUIP_TOTAL_INFO) { // 출고현황조회 세팅
            make_ui_total()
        } else { // 재물조사조회 세팅
            make_ui_inventory()
        }
    }

    // 출고현황조회 세팅 - Title, 항목, 색상
    private fun make_ui_total() {
        binding.apply {
            tvTitle.text = "장비출납현황"
        }
    }

    // 재물조사조회 세팅 - Title, 항목, 색상
    private fun make_ui_inventory() {
        binding.apply {
            tvTitle.text = "재물조사현황"
        }
    }

    // 버튼 설정
    private fun init_button() {
        // 클릭 시 장비출납현황 리스트를 서버에 요청
        binding.tvTitle.setOnClickListener {
            viewModel.get_total_equip_list(1, 1)
        }
    }

    // 리스트 뷰 설정
    private fun init_recyclerView() {
        binding.rcTotalEquipList.apply {
            adapter = totalInfoAdapter.apply {
                // 리스트 각 아이템 클릭 시 이벤트 설정
                setEquipClickListener(object: TotalInfoAdapter.EquipClickListener {
                    override fun onClick(view: View, position: Int, equip: Equipment) {
                        Toast.makeText(activity, equip.name, Toast.LENGTH_SHORT).show()
                    }
                })
            }

            // 어떤 방식으로 화면 표시할 지 설정
            layoutManager = LinearLayoutManager(requireActivity())

            // 화면 마지막에 도달하면 다음 index 부터 정보 받아오게 설정
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // 마지막 스크롤된 항목 위치 - 화면에 보이는 마지막 아이템 position
                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    // 항목 전체 개수
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1
                    // 마지막으로 보이는 아이템 = 마지막 인덱스
                    if (lastVisibleItemPosition == itemTotalCount) {
                        Log.d("SCROLL", "last Position...");
                        viewModel.get_total_equip_list(20, totalInfoAdapter.itemCount+1)
                    }
                }
            })
        }
    }

    // 데이터 observer 세팅
    private fun init_observe() {
        // 서버로부터 장비출고현황리스트 받아오면 리스트 데이터 갱신
        viewModel.equipList.observe(viewLifecycleOwner) {
            // 기존 리스트 + 새로 얻은 리스트
            val newList = totalInfoAdapter.currentList.toMutableList().apply {
                addAll(it)
            }
            totalInfoAdapter.submitList(newList)
        }
    }
}