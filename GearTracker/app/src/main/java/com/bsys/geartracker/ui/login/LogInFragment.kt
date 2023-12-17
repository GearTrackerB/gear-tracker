package com.bsys.geartracker.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bsys.geartracker.R
import com.bsys.geartracker.data.model.dto.User
import com.bsys.geartracker.databinding.FragmentLogInBinding
import com.bsys.geartracker.ui.qrcamera.QRCameraFragment

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
        // 로그인 버튼 클릭 설정
        binding.btnTest.setOnClickListener {
            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()

            if(check_id_pw_input(id, pw)) { // id, pw가 정상 입력 되었으면
                viewModel.user_log_in(User("id", "pass"))  // 서버에 ID, PW 검증 요청
            } else {
                show_toast("ID와 PW를 입력하세요.")
            }
        }
    }

    // ID, PW 입력 확인
    private fun  check_id_pw_input(id: String, pw: String): Boolean {
        // 빈 값인지만 확인
        return !(id == null || pw == null || id == "" || pw == "")
    }

    // ID, PW가 비었으면 표시
    private fun show_toast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun initObserver() {
        // 데이터 통신 후 결과 반응
        viewModel.userSeq.observe(viewLifecycleOwner) {
            if(it == -1L) { // 로그인 실패
                show_toast("로그인 실패")
            } else if(it != 0L) { // 초기값 아니면 로그인 성공 후 이동
                show_toast("로그인 성공, 사번 ${viewModel.userSeq.value}")
                findNavController().navigate(R.id.action_logInFragment_to_QRCameraFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}