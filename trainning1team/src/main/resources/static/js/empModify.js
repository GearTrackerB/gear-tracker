$(document).ready(function(){

    $("#btnRegist").click(function(){
        if (validate()) {
            let params = {
                EMP_ID: $("#emp_id").val(),
                AUTH_CD: $("#auth_cd").val(),
                EMP_NM: $.trim($("#emp_nm").val())
            }

            $.ajax({
                url: "/emp/modifyProc",
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                success: function(data) {
                    console.log("response = " + JSON.stringify(data));

                    if (data.resultCode === "0000") {
                        alert("수정되었습니다.");
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

    $("#btnDelete").click(function(){
        let params = {
            EMP_ID: $("#emp_id").val()
        }

        $.ajax({
            url: "/emp/delEmpInfo",
            type: "post",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success: function(data) {
                console.log("response = " + JSON.stringify(data));

                if (data.resultCode === "0000") {
                    alert("삭제 되었습니다.");
                    location.href = "/emp/list";
                } else {
                    alert(data.resultMessage);
                }
            },
            error: function (request, status, error) {
                console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
            }
        })
    })

    $("#btnList").click(function(){
        location.href = "/emp/list";
    })

    $(".btn-reset-pw").click(function(){
        let params = {
            EMP_ID: $("#emp_id").val()
        }

        $.ajax({
            url: "/emp/resetPw",
            type: "post",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success: function(data) {
                console.log("response = " + JSON.stringify(data));

                if (data.resultCode === "0000") {
                    alert("초기화 되었습니다.");
                } else {
                    alert(data.resultMessage);
                }
            },
            error: function (request, status, error) {
                console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
            }
        })
    })
})

function validate(){
    if ($.trim($("#auth_cd").val()).length <= 0) {
        alert("권한을 입력하여야만 합니다.");
        $("#auth_cd").focus();
        return false;
    }
    if ($.trim($("#emp_nm").val()).length <= 0) {
        alert("성명을 입력하여야만 합니다.");
        $("#emp_nm").focus();
        return false;
    }

    return true;
}