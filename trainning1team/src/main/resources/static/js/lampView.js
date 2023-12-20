$(document).ready(function(){
    search(1);

    $("#comp_cd").change(function(){
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

    $(".btn-search").click(function(){
        search(1);
    });
})

function search(page) {
    let params = {
        PAGE: parseInt(page),
        COMP_CD: $("#comp_cd").val(),
        EMP_NO: $("#search_emp_no").val(),
        EMP_NM: $("#search_emp_nm").val()
    }

    $.ajax({
        url: "/time/getMemberList",
        type: "post",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            console.log("response = " + JSON.stringify(data));

            if (data.resultCode === "0000") {
                let result = data.result;
                let list = result.list;
                let codes = result.statusList;

                var strHtml = "";
                if (list.length > 0) {
                    var startNo = result.startNo;
                    for (var i = 0; i < list.length; i++) {
                        let item = list[i];

                        strHtml += '<tr class="tac cursorP">';
                        strHtml += '    <td class="m_table">' + (startNo--) + '</td>';
                        strHtml += '    <td>' + item.COMP_NM + '</td>';
                        strHtml += '    <td>' + item.EMP_NO + '</td>';
                        strHtml += '    <td>' + item.EMP_NM + '</td>';
                        strHtml += '    <td>' + item.POSITION_NM + '</td>';
                        strHtml += '    <td>';
                        strHtml += '      <select id="status_cd' + item.ID + '" class="form-control form-select">';
                        for (var j = 0; j < codes.length; j++) {
                            var selectedStr = '';
                            if(codes[j].COMM_CD === item.STATUS_CD){
                                selectedStr = 'selected';
                            }
                            strHtml += '        <option value="'+ codes[j].COMM_CD +'" '+ selectedStr +'>' + codes[j].CODE_NM + '</option>';
                        }
                        strHtml +=  '     </select>';
                        strHtml += '    </td>';
                        strHtml += '    <td><input type="text" class="form-control" id="location_desc' + item.ID + '" value="' + item.LOCATION_DESC + '" placeholder="내용(위치,행선지,회의명 등)" maxlength="12" /></td>';
                        strHtml += '    <td><button type="button" class="btn btn-submit ml-2 btn-secondary btn-modify w50" data-key="' + item.ID + '" data-value="'+ item.STATUS_CD +'" >수정</button></td>';
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

                /**
                 * 데어터 수정
                 */
                $(".btn-modify").click(function(){
                    let thisObj = $(this);
                    let id = $(this).attr("data-key");
                    let preStatusCd = $(this).attr("data-value");
                    let newStatusCd = $.trim($("#status_cd" + id).val());
                    let checkChg = 'N';
                    if(preStatusCd !== newStatusCd) {
                        checkChg = 'Y';
                    }

                    let params = {
                        BRD_MEM_ID: id,
                        PRE_STATUS_CD : preStatusCd,
                        STATUS_CD: newStatusCd,
                        STATUS_DESC : '',
                        LOCATION_DESC : $.trim($("#location_desc" + id).val()),
                        REG_ID : $.trim($("#regId").val()),
                        CHANGE_CHECK : checkChg
                    }

                    $.ajax({
                        url: "/time/lampModifyProc",
                        type: "post",
                        data: JSON.stringify(params),
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data) {
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