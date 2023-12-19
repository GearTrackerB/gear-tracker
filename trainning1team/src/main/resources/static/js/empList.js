$(document).ready(function(){
    search(1);

    $("#comp_cd").change(function(){
        search(1);
    })

    $(".btn-search").click(function(){
        search(1);
    });

    $("#search_emp_no").keyup(function(event){
        if (event.keyCode === 13) {
            search(1);
        }
    });

    $("#search_emp_nm").keyup(function(event){
        if (event.keyCode === 13) {
            search(1);
        }
    });

    $(".btn-regist").click(function(){
        location.href = "/emp/regist";
    })

    $(".btn-download").click(function(){
        location.href = "/download?filePath=/upload/emp_upload_format.xlsx";
    })

    $(".btn-reg").click(function(){
        if ($.trim($("#file").val()).length <= 0) {
            alert("대상 파일을 선택하여야만 합니다.");
        } else {

            let formData = new FormData($("#addFromFileFrm")[0]);
            formData.append("uploadFile", $("#file")[0].files[0]);

            $.ajax({
                url: "/emp/addEmpInfoFromFile",
                type: "post",
                data: formData,
                dataType: "json",
                contentType: false,
                processData: false,
                success: function (data) {
                    console.log("response = " + JSON.stringify(data));

                    if (data.resultCode === "0000") {
                        alert("등록되었습니다.(성공:" + data.result.successCnt + ", 실패:" + data.result.failCnt + ")");
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

    $("#file").change(function(){
        let filePath = $(this).val();

        $("#fileList").html('<button type="button" class="btn btn-default pull-right">' + filePath + '</button>')
    });

    $(document).on("click", ".dataRow", function (){
        let dv =  $(this).attr("data-value");
        goView(dv);
    });
})

function goView(id) {
    location.href = "/emp/modify/" + id;
}

function search(page) {
    let params = {
        PAGE: parseInt(page),
        COMP_CD: $("#comp_cd").val(),
        EMP_NO: $("#search_emp_no").val(),
        EMP_NM: $("#search_emp_nm").val()
    }

    $.ajax({
        url: "/emp/getEmpList",
        type: "post",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            console.log("response = " + JSON.stringify(data));

            if (data.resultCode === "0000") {
                let result = data.result;
                let list = result.list;

                var strHtml = "";
                if (list.length > 0) {
                    var startNo = result.startNo;
                    for (var i = 0; i < list.length; i++) {
                        let item = list[i];

                        strHtml += '<tr class="dataRow tac cursorP" data-value="'+ item.ID +'">';
                        strHtml += '    <td class="m_table">' + (startNo--) + '</td>';
                        strHtml += '    <td>' + item.COMP_NM + '</td>';
                        strHtml += '    <td>' + item.AUTH_NM + '</td>';
                        strHtml += '    <td>' + item.EMP_NO + '</td>';
                        strHtml += '    <td>' + item.EMP_NM + '</td>';
                        strHtml += '    <td>' + item.DISP_LAST_LOGIN_DT + '</td>';
                        strHtml += '</tr>';
                    }
                } else {
                    strHtml += '<tr>';
                    strHtml += '    <td colspan="6">등록된 데이터가 없습니다.</td>';
                    strHtml += '</tr>';
                }
                $("#totalCount").html(result.totalCount);
                $("#listBody").html(strHtml);
                $("#paging").html(result.paging);


                /**
                 * paging 구현시 필수 추가.
                 */
                $(".page-item").click(function(){
                    let goPage = $(this).attr("page");

                    search(goPage);
                })
            } else {
                alert(data.resultMessage);
            }
        },
        error: function (request, status, error) {
            console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
        }
    })
}