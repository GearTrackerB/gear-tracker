$(document).ready(function(){
    search(1);

    $("#searchUseYn").change(function(){
        search(1);
    });

    $("#searchCodeClsCd").keyup(function(event){
        if (event.keyCode === 13) {
            search(1);
        }
    });

    $("#searchCommCd").keyup(function(event){
        if (event.keyCode === 13) {
            search(1);
        }
    });

    $(".btn-search").click(function(){
        search(1);
    })

    $(".btn-reg").click(function(){
        if (validate('')) {
            let params = {
                CODE_CLS_CD: $.trim($("#code_cls_cd").val()),
                COMM_CD: $.trim($("#comm_cd").val()),
                CODE_NM: $.trim($("#code_nm").val()),
                CODE_DESC: $.trim($("#code_desc").val())
            }

            $.ajax({
                url: "/code/registProc",
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                success: function(data) {
                    console.log("response = " + JSON.stringify(data));

                    if (data.resultCode === "0000") {
                        alert("등록되었습니다.");
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
})

function validate(id) {
    if ($.trim($("#code_cls_cd" + id).val()).length <= 0) {
        alert("분류코드를 입력하여야만 합니다.");
        $("#code_cls_cd" + id).focus();
        return false;
    }

    if ($.trim($("#comm_cd" + id).val()).length <= 0) {
        alert("공통코드를 입력하여야만 합니다.");
        $("#comm_cd" + id).focus();
        return false;
    }

    if ($.trim($("#code_nm" + id).val()).length <= 0) {
        alert("코드명을 입력하여야만 합니다.");
        $("#code_nm" + id).focus();
        return false;
    }

    const regExp = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g;
    if(regExp.test($.trim($("#code_cls_cd" + id).val()))){
        alert("숫자와 영문자만 입력해 주세요");
        $("#code_cls_cd" + id).focus();
        return false;
    }

    if(regExp.test($.trim($("#comm_cd" + id).val()))){
        alert("숫자와 영문자만 입력해 주세요");
        $("#comm_cd" + id).focus();
        return false;
    }

    return true;
}

function search(page) {
    let params = {
        PAGE: parseInt(page),
        CODE_CLS_CD: $("#searchCodeClsCd").val(),
        COMM_NM: $("#searchCommNm").val(),
        USE_YN: $("#searchUseYn").val()
    }

    $.ajax({
        url: "/code/getList",
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

                        strHtml += '<tr class="tac">';
                        strHtml += '    <td class="m_table">' + (startNo--) + '</td>';
                        strHtml += '    <td><input type="text" class="form-control" id="code_cls_cd' + item.ID + '" value="' + item.CODE_CLS_CD + '" placeholder="분류코드" maxlength="20"  /></td>';
                        strHtml += '    <td><input type="text" class="form-control" id="comm_cd' + item.ID + '" value="' + item.COMM_CD + '" placeholder="공통코드" maxlength="7"/></td>';
                        strHtml += '    <td><input type="text" class="form-control" id="code_nm' + item.ID + '" value="' + item.CODE_NM + '" placeholder="코드명" maxlength="20" /></td>';
                        strHtml += '    <td><input type="text" class="form-control" id="code_desc' + item.ID + '" value="' + item.CODE_DESC + '" placeholder="코드설명" /></td>';
                        strHtml += '    <td><input type="checkbox" value="' + item.ID + '" class="chk_use_yn" ' + (item.USE_YN === "Y" ? "checked" : "") + '></td>';
                        strHtml += '    <td><button class="btn btn-submit ml-2 btn-secondary btn-modify w50" data-key="' + item.ID + '" >수정</button></td>';
                        strHtml += '</tr>';
                    }
                } else {
                    strHtml += '<tr>';
                    strHtml += '    <td colspan="7">등록된 데이터가 없습니다.</td>';
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

                $(".chk_use_yn").click(function(){
                    let thisObj = $(this);
                    let id = $(this).val();

                    let params = {
                        CODE_ID: id,
                        USE_YN: $(this).prop("checked") ? "Y" : "N"
                    }

                    $.ajax({
                        url: "/code/modifyProc",
                        type: "post",
                        data: JSON.stringify(params),
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data) {
                            console.log("response = " + JSON.stringify(data));

                            if (data.resultCode !== "0000") {
                                alert(data.resultMessage);
                                $(thisObj).prop("checked", !$(thisObj).prop("checked"));
                            }
                        },
                        error: function (request, status, error) {
                            console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
                        }
                    })
                })

                $(".btn-modify").click(function(){
                    let thisObj = $(this);
                    let id = $(this).attr("data-key");

                    if (validate(id)) {
                        let params = {
                            CODE_ID: id,
                            CODE_CLS_CD: $.trim($("#code_cls_cd" + id).val()),
                            COMM_CD: $.trim($("#comm_cd" + id).val()),
                            CODE_NM: $.trim($("#code_nm" + id).val()),
                            CODE_DESC: $.trim($("#code_desc" + id).val())
                        }

                        $.ajax({
                            url: "/code/modifyProc",
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
                    }
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