$(document).ready(function(){

    $("#btnRegist").click(function(){
        if (validate()) {
            let params = {
                COMP_CD: $("#comp_cd").val(),
                AUTH_CD: $("#auth_cd").val(),
                EMP_NO: $.trim($("#emp_no").val()),
                EMP_NM: $.trim($("#emp_nm").val())
            }

            $.ajax({
                url: "/emp/registProc",
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                success: function(data) {
                    console.log("reponse = " + JSON.stringify(data));

                    if (data.resultCode === "0000") {
                        alert("등록되었습니다.");
                        location.href = "/emp/list";
                    } else {
                        alert(data.resultMessage);
                    }
                },
                error: function (request, status, error) {
                    console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
                }
            })
        }
    })

    $("#btnCancel").click(function(){
        location.href = "/emp/list";
    })
})

function validate(){
    if ($.trim($("#comp_cd").val()).length <= 0) {
        alert("계열사를 선택하여야만 합니다.");
        $("#comp_cd").focus();
        return false;
    }

    if ($.trim($("#auth_cd").val()).length <= 0) {
        alert("권한을 선택하여야만 합니다.");
        $("#auth_cd").focus();
        return false;
    }

    if ($.trim($("#emp_no").val()).length <= 0) {
        alert("사번을 입력하여야만 합니다.");
        $("#emp_no").focus();
        return false;
    }

    if ($.trim($("#emp_nm").val()).length <= 0) {
        alert("성명을 입력하여야만 합니다.");
        $("#emp_nm").focus();
        return false;
    }

    return true;
}