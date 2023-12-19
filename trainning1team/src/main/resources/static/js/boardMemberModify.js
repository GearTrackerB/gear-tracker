$(document).ready(function(){
    $("#file").change(function(){
        let filePath = $(this).val();

        $("#fileList").html('<button type="button" class="btn btn-default pull-right">' + filePath + '</button>')
    })

    $("#btnRegist").click(function(){
        if (validate()) {
            let formData = new FormData($(".info-form")[0]);
            formData.append("BRD_MEM_ID", removeTag($("#brd_mem_id").val()));
            formData.append("COMP_CD", removeTag($.trim($("#comp_cd").val())));
            formData.append("POSITION_CD", removeTag($.trim($("#position_cd").val())));
            formData.append("POSITION_LEVEL", removeTag($.trim($("#position_level").val())));
            formData.append("EMP_NO", removeTag($.trim($("#emp_no").val())));
            formData.append("EMP_NM", removeTag($.trim($("#emp_nm").val())));
            formData.append("uploadImage", $("#file")[0].files[0]);

            $.ajax({
                url: "/boardMember/modifyProc",
                type: "post",
                data: formData,
                dataType: "json",
                contentType: false,
                processData: false,
                success: function(data) {
                    console.log("response = " + JSON.stringify(data));

                    if (data.resultCode === "0000") {
                        alert("수정되었습니다.");
                        location.reload();
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
        if (confirm("삭제된 데이터는 복구되지 않습니다.\n그래도 정말로 삭제하시겠습니까?")) {
            let params = {
                BRD_MEM_ID: $("#brd_mem_id").val()
            }

            $.ajax({
                url: "/boardMember/removeProc",
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                success: function (data) {
                    console.log("response = " + data);
                    if (data.resultCode === "0000") {
                        alert("삭제되었습니다.");
                        location.href = '/boardMember/list';
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

    $("#btnList").click(function(){
        location.href = "/boardMember/list";
    })
})

function validate(){
    if ($.trim($("#comp_cd").val()).length <= 0) {
        alert("계열사를 선택하여야만 합니다.");
        $("#comp_cd").focus();
        return false;
    }

    if ($.trim($("#position_cd").val()).length <= 0) {
        alert("직급을 선택하여야만 합니다.");
        $("#position_cd").focus();
        return false;
    }

    if ($.trim($("#position_level").val()).length <= 0) {
        alert("트리 노출 레벨을 입력하여야만 합니다.");
        $("#position_level").focus();
        return false;
    }

    if (!$.isNumeric($.trim($("#position_level").val()))) {
        alert("트리 노출 레벨은 숫자만 입력 가능합니다.");
        $("#position_level").focus();
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