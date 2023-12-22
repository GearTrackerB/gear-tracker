package com.bsys.geartracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bsys.geartracker.data.model.response.RentalStatusResponse
import com.bsys.geartracker.databinding.ItemTotalEquipListBinding

class TotalInfoAdapter: ListAdapter<RentalStatusResponse, TotalInfoAdapter.EquipItemViewHolder>(diffUtil){

    // Viewholder : 리사이클러 뷰의 미리 만들어져 있는, 뷰를 담을 수 있는 홀더
    inner class EquipItemViewHolder(private val binding: ItemTotalEquipListBinding): RecyclerView.ViewHolder(binding.root) {
        // 장비 데이터를 하나 가져와 넣는다.
        // onBindViewHolder로 부터 아이템을 하나 가져와 Layout과 연결
        fun bind(equip: RentalStatusResponse) {
            binding.apply {
                cItem.setOnClickListener {
                    equipClickListener.onClick(it, layoutPosition, equip)
                }
                tvEquipSerial.text = equip.serialNo
                tvEquipName.text = equip.equipmentName
                tvEquipEmploy.text = equip.employeeNo
                tvEquipStatus.text = equip.statusName
                if(equip.statusName == "출고예정" || equip.statusName == "N")
                        tvEquipStatus.setTextColor(Color.RED)
                else if(equip.statusName == "반납예정" )
                        tvEquipStatus.setTextColor(Color.BLUE)

            }
        }
    }

    // 만들어진 Viewholder가 없으면 새로 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipItemViewHolder {
        // ViewHolder 객체를 생성한다.
        // LayoutInflater는 context에서 가져오는데, View에 있는 Constext 즉 ViewGroup을 참조하자.
        // layoutinflater, 어디에 붙을지, 붙을지 말 지 정보를 itemTotalEquipListBinding의 inflate로 가져온다.
        // Item의 View Binding을 return 한다.
        return EquipItemViewHolder(ItemTotalEquipListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // 데이터가 그려질 때 ViewHolder와 데이터를 Binding
    override fun onBindViewHolder(holder: EquipItemViewHolder, position: Int) {
        //ViewHolder의 Bind 함수를 호출한다.
        //currentList는 ListAdapter의 DataList ... 이 경우 Equipment List
        //Equipment List [position]의 Equipmnet 하나를 ViewHolder의 bind에 전달
        holder.bind(currentList[position])
    }


    // Item 클릭 이벤트에 사용
    interface EquipClickListener {
        fun onClick(view: View, position: Int, item: RentalStatusResponse)
    }

    private lateinit var equipClickListener: EquipClickListener
    fun setEquipClickListener(itemClickListener: EquipClickListener) {
        this.equipClickListener = itemClickListener
    }



    //difutil로 다른 값만 갱신
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RentalStatusResponse>() {
            // 새로운, 이전의 아이템이 실제로 같은가?
            override fun areItemsTheSame(oldItem: RentalStatusResponse, newItem: RentalStatusResponse): Boolean {
                // 객체 비교
                return oldItem == newItem
            }

            // 아이템의 내부가 같은가?
            override fun areContentsTheSame(oldItem: RentalStatusResponse, newItem: RentalStatusResponse): Boolean {
                // 어떤 걸 기준으로?
                return oldItem.serialNo == newItem.serialNo
            }

        }
    }
}