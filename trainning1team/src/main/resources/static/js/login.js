$(document).ready(function(){
    $(".login-btn").click(function(){
        loginProc();
    })
})

function validate() {
    if ($.trim($("#loginId").val()).length <= 0) {
        alert("아이디를 입력하여야만 합니다서.");
        $("#loginId").focus();
        return false;
    }

    if ($.trim($("#loginPw").val()).length <= 0) {
        alert("비밀번호를 입력하여야만 합니다.");
        $("#loginPw").focus();
        return false;
    }

    return true;
}

function loginProc(){
    if (validate()) {
        let params = {
            "loginId": $("#loginId").val(),
            "loginPw": $("#loginPw").val()
        }

        $.ajax({
            url: "/admin/login",
            type: "post",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                console.log("데이터 전송 : " + JSON.stringify(data));

                location.href="main-page";
            },
            error: function (request, status, error) {
                console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
            }
        })
    }
}