$(document).ready(function(){
    $("#file").change(function(){
        let filePath = $(this).val();

        $("#fileList").html('<button type="button" class="btn btn-default pull-right">' + filePath + '</button>')
    })

    //등록 버튼 시작
    $("#btnRegist").click(function(){
        if (validate()) {
            let params = {
                "serialNo": $("#serialNo").val(),
                "eqType" : $("#eqType").val(),
                "eqNm" : $("#eqNm").val(),
                "eqModel" : $("#eqModel").val(),
                "empNo" : $("#empNo").val(),
                "eqMaker" : $("#eqMaker").val()
            }
            $.ajax({
                url: "/admin/regist/equipment",
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                success: function(data) {
                    console.log("reponse = " + JSON.stringify(data));

                    alert("등록되었습니다.");
                    location.href = "/admin/main-page";

                },
                error: function (request, status, error) {
                    console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
                }
            })
        }
    })

    // 취소
    $("#btnCancel").click(function(){
        location.href = "/admin/main-page";
    })
})

function validate(){
    if ($.trim($("#serialNo").val()).length <= 0) {
        alert("식별 코드를 입력해야만 합니다.");
        $("#serialNo").focus();
        return false;
    }

    if ($.trim($("#eqType").val()).length <= 0) {
        alert("제품 종류를 선택해야합니다.");
        $("#eqType").focus();
        return false;
    }

    if ($.trim($("#eqNm").val()).length <= 0) {
        alert("제품명을 입력해야합니다.");
        $("#eqNm").focus();
        return false;
    }

    if ($.trim($("#empModel").val()).length <= 0) {
        alert("모델명을 입력해주세요.");
        $("#empNo").focus();
        return false;
    }

    if ($.trim($("#empNo").val()).length <= 0) {
        alert("배정자를 선택해주세요.");
        $("#empNo").focus();
        return false;
    }
    
    return true;
}