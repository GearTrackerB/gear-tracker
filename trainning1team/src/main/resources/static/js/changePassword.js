$(document).ready(function(){
    const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&^])[A-Za-z\d$@$!%*#?&^]{7,}$/;
    var msg = "";
    var classNm = "pw_success";

    $("#loginNewPwCheck").keyup(function(event) {
        if (regex.test($.trim($(this).val()))) {
            if ($.trim($("#loginNewPw").val()) === $.trim($(this).val())) {
                msg = "알맞은 비밀번호입니다.";

                $("#btnChange").prop("disabled", false);

            } else {
                classNm = "pw_error";
                msg = "신규 비밀번호가 일치하지 않습니다.";

                $("#btnChange").prop("disabled", true);
            }
        } else {
            classNm = "pw_error";
            msg = "비밀번호는 최소 8자리 영문자, 숫자, 특수문자 하나가 포함되어야 합니다.";

            $("#btnChange").prop("disabled", true);
        }
        $("#msg").html("");
        $("#msg").removeClass("pw_success").removeClass("pw_error");
        $("#msg").addClass(classNm);
        $("#msg").html(msg);
    })

    $("#btnChange").click(function(){
        let params = {
            ORIGIN_LOGIN_PW: $.trim($("#loginPw").val()),
            LOGIN_PW: $.trim($("#loginNewPw").val())
        }

        $.ajax({
            url: "/changePwProc",
            type: "post",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success: function(data) {
                console.log("response = " + JSON.stringify(data));

                if (data.resultCode === "0000") {
                    if ("${sessionScope.empInfo.IS_DEFAULT_PW}" === "Y") {
                        alert("비멀번호를 변경하였습니다. 다시 로그인 후 사용하시기 바랍니다.");
                        location.href = "/logout";
                    } else {
                        alert("비밀번호를 변경하였습니다.");
                        history.back();
                    }
                } else {
                    alert(data.resultMessage);
                }
            }
        })
    })

    $("#btnCancel").click(function(){
        if ("${sessionScope.empInfo.IS_DEFAULT_PW}" === "Y") {
            if (confirm("초기 비밀번호를 변경하지 않고는 사용이 불가합니다.그래도 그만두시겠습니까?")) {
                location.href = "/logout";
            }
        } else {
            history.back();
        }
    })
})