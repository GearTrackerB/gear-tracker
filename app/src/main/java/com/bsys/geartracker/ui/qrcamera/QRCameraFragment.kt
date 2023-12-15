package com.bsys.geartracker.ui.qrcamera

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bsys.geartracker.databinding.FragmentQrCameraBinding
import com.bsys.geartracker.ui.MainActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class QRCameraFragment: Fragment() {
    private var _binding: FragmentQrCameraBinding? = null
    private val binding get() = _binding!!

    lateinit var code_scanner: CodeScanner

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
//            권한을 허용한 경우 QR 스캔
            scan()
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
            }
//                CoroutineScope(Dispatchers.Main) {
//                    Toast.makeText(activity, it.text, Toast.LENGTH_SHORT).show()
//                }
//            }

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

    //     액티비티 재실행 되면 실행됨
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
}
