package com.bsys.geartracker.ui.qrcamera

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bsys.geartracker.R
import com.bsys.geartracker.data.model.dto.QRRequest
import com.bsys.geartracker.databinding.FragmentQrCameraBinding
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
import com.google.gson.Gson
import com.ramotion.circlemenu.CircleMenuView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class QRCameraFragment: Fragment() {
    private var _binding: FragmentQrCameraBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QRViewModel by viewModels()
    private val loginViewModel: LogInViewModel by activityViewModels()

    lateinit var code_scanner: CodeScanner

    // 현재 요청 모드 / 버튼으로 선택
    private var qrType: Int = EQUIP_SEND
    // 장비 Serial
    private var serialNo: String = ""
    // 이미 다른 작업이 진행 중이면 카메라가 인식하지 않도록 하기 위한 Flag
    private var alreadyRequst: Boolean = false

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

        // 백버튼 시 종료
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

    }

    override fun onStart() {
        super.onStart()
        resetResponseCode()

        // QR 모드에 따른 설정
        qrType = loginViewModel.nowMode.value ?: EQUIP_SEND
        var txt = ""
        when(qrType-1) {
            0 -> {qrType = EQUIP_SEND; txt = "배정 모드"; change_mode_text(txt); loginViewModel.setNowMode(qrType)}
            1 -> {qrType = EQUIP_TAKE; txt = "회수 모드"; change_mode_text(txt); loginViewModel.setNowMode(qrType)}
            2 -> {qrType = EQUIP_INVENTORY; txt = "재물 조사 모드"; change_mode_text(txt); loginViewModel.setNowMode(qrType)}
            3 -> {qrType = EQUIP_DETAIL; txt = "장비 정보 조회 모드"; change_mode_text(txt); loginViewModel.setNowMode(qrType)}
        }
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
            // 원형 메뉴 선택 별 동작 설정
            circleMenu.setEventListener(object : CircleMenuView.EventListener() {
                override fun onButtonClickAnimationStart(view: CircleMenuView, buttonIndex: Int) {
                    super.onButtonClickAnimationStart(view, buttonIndex)

                    var txt: String = ""
                    when(buttonIndex) {
                        0 -> {qrType = EQUIP_SEND; txt = "배정 모드"; change_mode_text(txt)
                                loginViewModel.setNowMode(qrType)}
                        1 -> {qrType = EQUIP_TAKE; txt = "회수 모드"; change_mode_text(txt)
                            loginViewModel.setNowMode(qrType)}
                        2 -> {qrType = EQUIP_INVENTORY; txt = "재물 조사 모드"; change_mode_text(txt)
                            loginViewModel.setNowMode(qrType)}
                        3 -> {qrType = EQUIP_DETAIL; txt = "장비 정보 조회 모드"; change_mode_text(txt)
                            loginViewModel.setNowMode(qrType)}
                    }
                }
            })

        }
    }

    private fun change_mode_text(txt: String) {
        binding.tvMode.text = txt
    }

    private fun init_observer() {
        // QR 코드 요청 결과에 따른 처리
        viewModel.qrResult.observe(viewLifecycleOwner) {
            lateinit var typeMsg: String
            when(qrType) {
                EQUIP_SEND -> typeMsg = "배정"
                EQUIP_TAKE -> typeMsg = "회수"
                EQUIP_INVENTORY -> typeMsg = "재물 조사"
                else -> typeMsg = "배정, 회수, 재물 조사 아님"
            }
            // 통신 성공 시
            if(it == 200) {
                Toast.makeText(requireActivity(), "$serialNo $typeMsg 요청 성공", Toast.LENGTH_SHORT).show()
                binding.tvResult.text = "장비 $serialNo $typeMsg 요청 성공"
                resetResponseCode()
                code_scanner.startPreview()
                alreadyRequst = false
            } else if(it == 400) { // 실패 시
                Toast.makeText(requireActivity(), "$typeMsg 요청 실패", Toast.LENGTH_SHORT).show()
                binding.tvResult.text = "장비 $serialNo $typeMsg 요청 실패"
                resetResponseCode()
                code_scanner.startPreview()
                alreadyRequst = false
            }
        }
    }

    private fun resetResponseCode() {
        viewModel.resetQrResult()
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

                // 이미 다른 요청 작업 중이면 종료
                if(alreadyRequst) return@DecodeCallback

                serialNo = it.text

                // 정규표현식 패턴 // 해당 유형에 맞지 않는 QR이면 진행 안하도록
                val serialPattern = Regex("[A-Z]{3}-\\d+")

                // 진행 중이면 다시 진행하지 않게
                alreadyRequst = true

                // 시리얼 번호 검증
                if (serialNo.matches(serialPattern)) {
                    // 영어 대문자 3개와 하이픈 다음에 숫자가 있는 패턴과 일치하는 경우
                    // 여기서 원하는 작업 수행
                    if (qrType != EQUIP_DETAIL) {
                        take_picture()
                    } else {
                        // 다시 인식하게
                        alreadyRequst = false
                        move_to_detail_info_fragment(serialNo)
                    }
                }
            }

            code_scanner.errorCallback = ErrorCallback {
                activity?.runOnUiThread {
                    Toast.makeText(requireActivity(), "QR 인식 오류", Toast.LENGTH_SHORT).show()
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

    // QR 스캔을 일시적으로 중지하는 함수
    private fun pauseScanning() {
        if (::code_scanner.isInitialized) {
            code_scanner.releaseResources()
        }
    }

    // QR 스캔을 다시 시작하는 함수
    private fun resumeScanning() {
        if (::code_scanner.isInitialized) {
            code_scanner.startPreview()
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


    // 사진 및 요청 확인 다이얼로그
    private fun showConfirmationDialog(qrType: Int, empNo: String, serialNo: String, imageFile: File) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_image_preview, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.imageView)
        imageView.setImageURI(Uri.fromFile(imageFile))

        val requestType: String
        when (qrType) {
            EQUIP_SEND -> requestType = "배정 요청"
            EQUIP_TAKE -> requestType = "회수 요청"
            EQUIP_INVENTORY -> requestType = "재물 조사 요청"
            else -> requestType = "배정, 회수, 재물 조사 요청 아님"
        }


        val dialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
            .setTitle("QR 요청 확인")
            .setMessage("고유 번호 :   $serialNo\n요청 종류 :   $requestType")
            .setView(dialogView)
            .setPositiveButton("확인") { _, _ ->
                // 확인 버튼 클릭 시 서버 통신 요청
                request_qr(qrType, empNo, serialNo, imageFile)
            }
            .setNegativeButton("취소") { _, _ ->
                // 취소 버튼 클릭 시 아무 동작 없음
                code_scanner.startPreview()
                alreadyRequst = false
            }
            .create()

        // 취소, 확인 버튼 스타일 적용
        dialog.setOnShowListener {
            val positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEGATIVE)

            positiveButton.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            negativeButton.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        }

        // pauseScanning()와 dialog.show()를 분리
        pauseScanning()
        dialog.show()

    }

    // 카메라로 찍은 사진을 처리하기 위한 상수 및 변수 추가
    private val REQUEST_IMAGE_CAPTURE = 1
    private var currentPhotoPath: String = ""

    // 카메라 촬영 기능
    private fun take_picture() {

        // 카메라 촬영을 위한 Intent 생성
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Intent가 실행 가능한지 체크
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            // 사진을 저장할 임시 파일 생성
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }

            // 임시 파일이 생성되었으면 Intent에 파일 Uri 추가
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.bsys.geartracker.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                // 카메라 전면 모드로 설정
                takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 3)

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    // 임시 파일 생성
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }


        return imageFile
    }

    // 카메라 촬영 후 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // 이미지 파일을 가지고 원하는 작업 수행
            val imageFile = File(currentPhotoPath)
            // 여기서 imageFile을 사용해서 원하는 동작 수행
            showConfirmationDialog(qrType, loginViewModel.empNo.value ?: "사번 없음",
                serialNo, imageFile)
        } else {
            alreadyRequst = false
        }
    }

    // QR 촬영 후 출고, 반납, 재물조사, 장비 확인 요청
    private fun request_qr(qrType: Int, empNo: String, serialNo: String, imageFile: File) {
        val qrRequest: QRRequest = QRRequest(serialNo, empNo)
        val qrRequestJson = Gson().toJson(qrRequest)
        val qrRequestBody = RequestBody.create(MediaType.parse("application/json"), qrRequestJson)

        // 이미지 파일 Multipart로 변환
        val imageRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageFile)
        val imagePart = MultipartBody.Part.createFormData("eqImage", imageFile.name, imageRequestBody)

        when (qrType) {
            EQUIP_SEND -> viewModel.equip_send_request(qrRequestBody, imagePart)
            EQUIP_TAKE -> viewModel.equip_take_request(qrRequestBody, imagePart)
            EQUIP_INVENTORY -> viewModel.equip_inventory_check_request(qrRequestBody, imagePart)
        }
    }

}
