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

class TotalInfoFragment: Fragment() {
    private var _binding: FragmentTotalInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TotalInfoViewModel by viewModels()

    private val totalInfoAdapter: TotalInfoAdapter by lazy {TotalInfoAdapter()}

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

        initButton()
        initRecyclerView()
        initObserve()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initButton() {
        binding.tvTitle.setOnClickListener {
            viewModel.get_total_equip_list(1, 1)
            Log.d("listview", "title 클릭")
        }
    }

    private fun initRecyclerView() {
        binding.rcTotalEquipList.apply {

            // adpater 클릭 이벤트 세팅
            adapter = totalInfoAdapter.apply {
                setEquipClickListener(object: TotalInfoAdapter.EquipClickListener {
                    override fun onClick(view: View, position: Int, equip: Equipment) {
                        Toast.makeText(activity, equip.name, Toast.LENGTH_SHORT).show()
                    }
                })
            }

            // 어떤 방식으로 화면 표시?
            layoutManager = LinearLayoutManager(requireActivity())

            // 스크롤 마지막 위치 확인 20개 12 13 17
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // 마지막 스크롤된 항목 위치 - 화면에 보이는 마지막 아이템 position
                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    // 항목 전체 개수
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                    // 마지막으로 보이는 아이템이 = 마지막 인덱스다
                    if (lastVisibleItemPosition == itemTotalCount) {
                        Log.d("SCROLL", "last Position...");
                        viewModel.get_total_equip_list(20, totalInfoAdapter.itemCount+1)
                    }
                }
            })
        }
    }

    private fun initObserve() {
        viewModel.equipList.observe(viewLifecycleOwner) {
            val newList = totalInfoAdapter.currentList.toMutableList().apply {
                addAll(it)
            }
            totalInfoAdapter.submitList(newList)
        }
    }
}