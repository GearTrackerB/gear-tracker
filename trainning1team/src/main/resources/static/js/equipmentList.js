var currentPage = 1;
$(document).ready(function(){
    search(1);

    $("#comp_cd").change(function(){
        search(1);
    })

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

    $(".btn-search").click(function(){
        search(1);
    })

    $(".btn-regist").click(function(){
        location.href = "/boardMember/regist";
    })

    $(".btn-download").click(function(){
        location.href = "/download?filePath=/upload/boardmember_upload_format.xlsx";
    })

    $(".btn-reg").click(function(){
        if ($.trim($("#file").val()).length <= 0) {
            alert("대상 파일을 선택하여야만 합니다.");
        } else {

            let formData = new FormData($("#addFromFileFrm")[0]);
            formData.append("uploadFile", $("#file")[0].files[0]);

            $.ajax({
                url: "/boardMember/registProcFromExcel",
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
    location.href = "/boardMember/modify/" + id;
}

function search(page) {
    currentPage = parseInt(page);
    let params = {
        PAGE: parseInt(page),
    }

    $.ajax({
        url: "/admin/getEquipmentList",
        type: "post",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            console.log("response = " + JSON.stringify(data));

                let result = data;
                console.log("result = "+result);
                let list = result.data;
                console.log("list" + JSON.stringify(list));
                //let sessAuthCd = $("#sess_auth_cd").val();

                var strHtml = "";
                if (list.length > 0) {
                    var startNo = result.startNo;
                    for (var i = 0; i < list.length; i++) {
                        let item = list[i];

                        strHtml += '<tr class="tac cursorP">';
                        strHtml += '    <td class="m_table">' + item.serialNo + '</td>';
                        strHtml += '    <td class="dataRow" data-value="'+ item.serialNo +'">' + item.eqType + '</td>';
                        strHtml += '    <td class="dataRow" data-value="'+ item.serialNo +'">' + item.eqNm + '</td>';
                        strHtml += '    <td class="dataRow" data-value="'+ item.serialNo +'">' + item.eqModel + '</td>';
                        strHtml += '    <td class="dataRow" data-value="'+ item.serialNo +'">' + item.eqStatus + '</td>';
                        strHtml += '    <td class="dataRow" data-value="'+ item.serialNo +'">' + item.empNo + '</td>';
                        strHtml += '    <td class="dataRow" data-value="'+ item.serialNo +'">' + item.regAt + '</td>';
                        // if (sessAuthCd === "ROL001" || sessAuthCd === "ROL002") {
                        //     strHtml += '    <td><input type="text" id="order_no' + item.ID + '" value="' + item.ORDER_NO + '" class="form-control tac" maxlength="2" /></td>';
                        //     strHtml += '    <td><button type="button" class="btn btn-modify-col btn-modify btn-secondary" data-key="' + item.serialNo + '">수정</button></td>';
                        // }
                        strHtml += '</tr>';
                    }
                } else {
                    strHtml += '<tr>';
                    strHtml += '    <td colspan="8">등록된 데이터가 없습니다.</td>';
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

                $(".btn-modify").click(function(){
                    let id = $(this).attr("data-key");

                    if ($.trim($("#order_no" + id).val()).length <= 0) {
                        alert("순서 값을 입력하여야만 합니다.");
                    } else {
                        let params = {
                            "BRD_MEM_ID": id,
                            "ORDER_NO": $.trim($("#order_no" + id).val())
                        }

                        $.ajax({
                            url: "/boardMember/modifyOrderProc",
                            type: "post",
                            data: JSON.stringify(params),
                            dataType: "json",
                            contentType: "application/json",
                            success: function(data) {
                                console.log("response = " + JSON.stringify(data));

                                if (data.resultCode === "0000") {
                                    alert("수정되었습니다.");
                                    search(currentPage);
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
        },
        error: function (request, status, error) {
            console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
        }
    })
}