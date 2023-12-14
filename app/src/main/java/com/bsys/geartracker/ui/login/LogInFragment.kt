package com.bsys.geartracker.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.databinding.FragmentLogInBinding

class LogInFragment: Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LogInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 버튼 동작 등
        initButton()
        initObserver()
    }

    private fun initButton() {
        // 버튼 클릭 시 비즈니스 로직 호출
        viewModel.user_log_in(User("id", "pass"))
    }

    private fun initObserver() {
        // 데이터 통신 후 결과 반응
        viewModel.userSeq.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}