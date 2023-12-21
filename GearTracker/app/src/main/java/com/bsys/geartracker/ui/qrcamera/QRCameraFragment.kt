package com.bsys.geartracker.ui.qrcamera

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bsys.geartracker.R
import com.bsys.geartracker.databinding.FragmentQrCameraBinding
import com.bsys.geartracker.ui.equiplist.EquipInfoViewModel
import com.bsys.geartracker.ui.login.LogInViewModel
import com.bsys.geartracker.utils.EQUIP_DETAIL
import com.bsys.geartracker.utils.EQUIP_INVENTORY
import com.bsys.geartracker.utils.EQUIP_INVETORY_INFO
import com.bsys.geartracker.utils.EQUIP_SEND
import com.bsys.geartracker.utils.EQUIP_TAKE
import com.bsys.geartracker.utils.EQUIP_TOTAL_INFO
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.ramotion.circlemenu.CircleMenuView


class QRCameraFragment: Fragment() {
    private var _binding: FragmentQrCameraBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QRViewModel by viewModels()
    private val loginViewModel: LogInViewModel by activityViewModels()
    private val equipViewModel: EquipInfoViewModel by viewModels()

    lateinit var code_scanner: CodeScanner

    private var qrType: Int = EQUIP_SEND

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQrCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 버튼 설정
        init_btn()

        // 원형 메뉴 설정
        init_circle_menu()

        // 옵저버 설정
        init_observer()

        // 카메라 권한 확인 & QR 스캔 실행
        check_permission()

        // 스테이터스바 설정
        setStatusNavigationBarColor(requireActivity(), R.color.black)


    }

    private fun init_btn() {
        binding.apply{
            tvEquipTotalInfo.setOnClickListener {
                // 장비 현황 조회에 어떤 모드로 들어갈 지 설정
                val bundle: Bundle = bundleOf("info_type" to EQUIP_TOTAL_INFO)
                // 장비 출납 현황 조회로 이동
                findNavController().navigate(R.id.action_QRCameraFragment_to_totalInfoFragment, bundle)
            }
            tvInventoryInfo.setOnClickListener {
                // 장비 현황 조회에 어떤 모드로 들어갈 지 설정
                val bundle: Bundle = bundleOf("info_type" to EQUIP_INVETORY_INFO)
                // 재물 조사 현황 조회로 이동
                findNavController().navigate(R.id.action_QRCameraFragment_to_totalInfoFragment, bundle)
            }
        }

    }

    private fun init_circle_menu() {
        binding.apply{
            circleMenu.setEventListener(object : CircleMenuView.EventListener() {
                override fun onButtonClickAnimationStart(view: CircleMenuView, buttonIndex: Int) {
                    super.onButtonClickAnimationStart(view, buttonIndex)

                    var txt: String = ""
                    when(buttonIndex) {
                        0 -> {qrType = EQUIP_SEND; txt = "출고 모드"; change_mode_text(txt)}
                        1 -> {qrType = EQUIP_TAKE; txt = "반납 모드"; change_mode_text(txt)}
                        2 -> {qrType = EQUIP_INVENTORY; txt = "재물 조사 모드"; change_mode_text(txt)}
                        3 -> {qrType = EQUIP_DETAIL; txt = "장비 정보 조회 모드"; change_mode_text(txt)}
                    }
                }
            })

        }
    }

    private fun change_mode_text(txt: String) {
        binding.tvMode.text = txt
    }

    private fun init_observer() {
        viewModel.qrResult.observe(viewLifecycleOwner) {
            lateinit var typeMsg: String
            when(qrType) {
                EQUIP_SEND -> typeMsg = "출고"
                EQUIP_TAKE -> typeMsg = "반납"
                EQUIP_INVENTORY -> typeMsg = "재물 조사"
                else -> typeMsg = "출고, 반납, 재물 조사 아님"
            }

            if(it == 200) {
                Toast.makeText(requireActivity(), "$typeMsg 요청 성공", Toast.LENGTH_SHORT).show()
            } else if(it == 400) {
                Toast.makeText(requireActivity(), "$typeMsg 요청 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //  액티비티 재실행 되면 실행됨
    override fun onResume() {
        super.onResume()
        //  초기화 확인하고 실행
        if (::code_scanner.isInitialized){
            code_scanner.startPreview()
        }
    }

    //    액티비티 정지되면 실행됨
    override fun onPause() {
        super.onPause()
        //    초기화 확인하고 실행
        if (::code_scanner.isInitialized){
            code_scanner.releaseResources()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // 카메라 권한 확인 & QR 스캔 실행 함수
    private fun check_permission() {
        //  권한 체크
        val cameraPermissionCheck =
            ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)
        if (cameraPermissionCheck == PackageManager.PERMISSION_DENIED) {
            //  권한이 없는 경우
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                1000
            )
        } else {
            //  권한을 허용한 경우 QR 스캔
            scan()
        }
    }

    //  권한요청 처리 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireActivity(), "카메라 권한 부여됨", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireActivity(), "카메라 권한 거부됨", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // QR 스캔 실행
    private fun scan() {
        //  초기화
        val scanner_view: CodeScannerView = binding.svScannerView
        //  스캐너 초기화
        code_scanner = CodeScanner(requireActivity(), scanner_view)
        //  스캐너 셋팅
        code_scanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS // 포맷

            autoFocusMode = AutoFocusMode.SAFE // 포커스
            isAutoFocusEnabled = true // 자동포커스 활성화
            isFlashEnabled = false // 플래쉬

            //  QR 코드 확인되면 실행
            decodeCallback = DecodeCallback {
                activity?.runOnUiThread {
                    Toast.makeText(requireActivity(), it.text, Toast.LENGTH_SHORT).show()
                }

                request_qr(qrType, loginViewModel.empNo.value ?: "사번 null", it.text)
            }

            code_scanner.errorCallback = ErrorCallback {
                activity?.runOnUiThread {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        //  스캔뷰 클릭 이벤트
        scanner_view.setOnClickListener {
            //  초기화 확인하고 실행
            if(::code_scanner.isInitialized){
                code_scanner.startPreview()
            }
        }
    }

    // QR 촬영 후 출고, 반납, 재물조사, 장비 확인 요청
    private fun request_qr(qrType: Int, empNo: String, serialNo: String) {
        when(qrType) {
            EQUIP_SEND -> viewModel.equip_send_request(serialNo, empNo)
            EQUIP_TAKE -> viewModel.equip_take_request(serialNo, empNo)
            EQUIP_INVENTORY -> viewModel.equip_inventory_check_request(serialNo, empNo)
            EQUIP_DETAIL -> move_to_detail_info_fragment(serialNo)
        }
    }

    private fun move_to_detail_info_fragment(serialNo: String) {
        val bundle: Bundle = bundleOf("serialNo" to serialNo)

        // 장비 출납 현황 조회로 이동
        activity?.runOnUiThread {
            findNavController().navigate(R.id.action_QRCameraFragment_to_detailInfoFragment, bundle)
        }
    }


    // 네비게이션과 상태바 검은색으로 변경
    private fun setStatusNavigationBarColor(activity: Activity, colorResId: Int) {
        val window: Window? = activity.window
        window?.statusBarColor = ContextCompat.getColor(activity, colorResId)
        window?.navigationBarColor = Color.parseColor("#000000")  // 변경하려는 색상
    }

}
