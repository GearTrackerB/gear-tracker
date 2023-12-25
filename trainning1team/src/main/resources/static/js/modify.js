$(document).ready(function(){
    $("#file").change(function(){
        let filePath = $(this).val();

        $("#fileList").html('<button type="button" class="btn btn-default pull-right">' + filePath + '</button>')
    })

    // 수정 버튼 클릭 시
    $("#btnRegist").click(function(){
        if (validate()) {
            let params = {
                "serialNo": $("#serialNo").val(),
                "eqType" : $("#eqType").val(),
                "eqNm" : $("#eqNm").val(),
                "eqModel" : $("#eqModel").val(),
                "eqStatus" : $("#eqStatus").val(),
                "empNo" : $("#empNo").val(),
                "regAt" : $("#regAt").val()
            }

            // let formData = new FormData($(".info-form")[0]);
            // formData.append("serialNo", removeTag($("#serialNo").val()));
            // formData.append("eqType", removeTag($.trim($("#eqType").val())));
            // formData.append("eqNm", removeTag($.trim($("#eqNm").val())));
            // formData.append("eqModel", removeTag($.trim($("#eqModel").val())));
            // formData.append("eqStatus", removeTag($.trim($("#eqStatus").val())));
            // formData.append("empNo", removeTag($.trim($("#empNo").val())));
            // formData.append("regAt", removeTag($.trim($("#regAt").val())));

            let segments = window.location.pathname.split('/');
            let id = segments[segments.length - 1];

            $.ajax({
                url: "/admin/modify/" + id,
                type: "post",
                data: JSON.stringify(params),
                // data: formData,
                dataType: "json",
                // contentType: false,
                contentType: "application/json",
                // processData: false,
                success: function(data) {
                    console.log("response = " + JSON.stringify(data));

                    alert("수정되었습니다.");
                    location.reload();
                },
                error: function (request, status, error) {
                    console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
                }
            })
        }
    })

    // $("#btnDownload").click(function(){
    //
    //
    //     // const codeWriter = new ZXing.BrowserQRCodeSvgWriter();
    //     // let svgElement;
    //     //
    //     // const input = $("#serialNo").val()
    //     // svgElement = codeWriter.writeToDom('#result', input, 300, 300)
    //     //
    //     // const data = $('#result').get(0);
    //     // const svgData = (new XMLSerializer()).serializeToString(data)
    //     // const blob = new Blob([svgData])
    //     //
    //     // saveAs(blob, $("#serialNo").val()+'.svg')
    // })

    $("#btnDelete").click(function(){
        if (confirm("삭제된 데이터는 복구되지 않습니다.\n그래도 정말로 삭제하시겠습니까?")) {
            let segments = window.location.pathname.split('/');
            let id = segments[segments.length - 1];

            $.ajax({
                url: "/admin/equipment/" + id,
                type: "delete",
                dataType: "json",
                contentType: "application/json",
                success: function (data) {
                    console.log("response = " + data);
                    alert("삭제되었습니다.");
                    location.href = "/admin/main-page";
                },
                error: function (request, status, error) {
                    console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
                }
            })
        }
    })

    $("#btnList").click(function(){
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

    if ($.trim($("#eqStatus").val()).length <= 0) {
        alert("장비 상태를 입력해야합니다.");
        $("#eqStatus").focus();
        return false;
    }

    return true;
}