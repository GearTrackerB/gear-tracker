var scheduleCount = 0;
var arrFailData = Array();

$(document).ready(function(){
    $("#base_dt").datepicker({
        showOn: "both",
        buttonImageOnly: false,
        buttonText: "",
        dateFormat : "yy.mm.dd",
        minDate : 0
    });
    $(".bDate > button").remove();

    searchList(1);

    $("#btnRegistPreData").click(function() {
        if (confirm("전일자 결재시간데이터와 동일하게 등록하시겠습니까?")) {
            if ($.trim($("#brd_mem_id").val()).length <= 0) {
                alert("경영진을 선택하여야만 합니다.");
                $("#brd_mem_id").focus();
                return false;
            }

            if ($.trim($("#base_dt").val()).length <= 0) {
                alert("일자를 입력해 주세요");
                $("#base_dt").focus();
                return false;
            }

            let params = {
                BRD_MEM_ID: $.trim($("#brd_mem_id").val()),
                BASE_DT: $.trim($("#base_dt").val())
            }

            $.ajax({
                url: "/time/copyFromYesterday",
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                success: function(data) {
                    console.log("response = " + JSON.stringify(data));

                    if (data.resultCode === "0000") {
                        alert("등록되었습니다.");
                        location.href = "/time/scheduleList";
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

    $("#btnRegist").click(function(){
        let totalRows = $("select[id=startTimeHour]").length;

        // alert(totalRows);

        if (totalRows <= 0) {
            alert("한 건 이상의 데이터가 등록되어야만 합니다.");
        } else {
            var successCnt = 0;
            var failCnt = 0;
            arrFailData = Array();

            for (var i = 0; i < $("select[id=startTimeHour]").length; i++) {
                let tblId = $($("button[id=btnDelRow]")[i]).attr("data-id");

                // console.log("tblId = " + tblId);

                let startTime = $.trim($($("select[id=startTimeHour]")[i]).val()) + ":" + $.trim($($("select[id=startTimeMinute]")[i]).val());
                let endTime = $.trim($($("select[id=endTimeHour]")[i]).val()) + ":" + $.trim($($("select[id=endTimeMinute]")[i]).val());

                let valResult = validate(i);

                let el = $("#regDiv > .form-row")[i];
                let elHtml = '<div class="form-row">' + $(el).html() + '</div>';

                console.log("elHtml = " + elHtml);

                if (valResult.success) {
                    var params = {};
                    var goUrl = "";
                    if (tblId === undefined || tblId === "") {
                        params = {
                            BRD_MEM_ID: $.trim($("#brd_mem_id").val()),
                            BASE_DT: $.trim($("#base_dt").val().replace(/\./gi, "").substring(0, 8)),
                            ST_TIME_CD: startTime,
                            ED_TIME_CD: endTime,
                            APPRV_STATUS_CD: $.trim($($("select[id=apprv_status_cd]")[i]).val()),
                            APPRV_STATUS_DESC: ($.trim($($("input:text[id=apprv_status_desc]")[i]).val()) === "" ? " " : $.trim($($("input:text[id=apprv_status_desc]")[i]).val()))
                        };

                        goUrl = "/time/scheduleRegistProc";
                    } else {
                        params = {
                            TBL_ID: Number(tblId),
                            BRD_MEM_ID: $.trim($("#brd_mem_id").val()),
                            BASE_DT: $.trim($("#base_dt").val().replace(/\./gi, "").substring(0, 8)),
                            ST_TIME_CD: startTime,
                            ED_TIME_CD: endTime,
                            APPRV_STATUS_CD: $.trim($($("select[id=apprv_status_cd]")[i]).val()),
                            APPRV_STATUS_DESC: ($.trim($($("input:text[id=apprv_status_desc]")[i]).val()) === "" ? " " : $.trim($($("input:text[id=apprv_status_desc]")[i]).val()))
                        };

                        goUrl = "/time/scheduleModifyProc";
                    }

                    $.ajax({
                        url: goUrl,
                        type: "post",
                        data: JSON.stringify(params),
                        dataType: "json",
                        contentType: "application/json",
                        async: false,
                        success: function (data) {
                            console.log("response = " + data);

                            if (data.resultCode === "0000") {
                                // alert("등록되었습니다.");
                                // location.href = "/time/scheduleList";
                                successCnt++;

                            } else {
                                // alert(data.resultMessage);
                                failCnt++;
                                arrFailData.push(setFailData(i, data.resultMessage, elHtml));
                            }
                        },
                        error: function (request, status, error) {
                            console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
                            failCnt++;

                            arrFailData.push(setFailData(i, "데이터 처리 실패[" + request.status + "]", elHtml));
                        }
                    })
                } else {
                    failCnt++;
                    arrFailData.push(setFailData(i, valResult.message, elHtml));
                }
            }

            // alert(successCnt + ", " + totalRows);

            if (successCnt === totalRows) {
                alert("정상적으로 처리되었습니다.")
                location.href = "/time/scheduleList";
            } else {
                var dtMessage = "";
                for (var i = 0; i < failCnt; i++) {
                    let item = arrFailData[i];

                    if (dtMessage.length > 0) {
                        dtMessage += "\n";
                    }
                    dtMessage += "[" + (item.idx + 1) + "]번째 사유 : " + item.message;
                }

                if (successCnt > 0) {
                    alert("일부 데이터 처리시 오류가 발생하였습니다.\n[실패내역]\n" + dtMessage);

                    searchList(1);
                } else {
                    alert("데이터 처리시 오류가 발생하였습니다.\n[실패내역]\n" + dtMessage);
                }
            }
        }
    })

    $("#btnModify").click(function(){

        let startTime = $.trim($("#startTimeHour").val()) + ":" + $.trim($("#startTimeMinute").val());
        let endTime = $.trim($("#endTimeHour").val()) + ":" + $.trim($("#endTimeMinute").val());

        let valResult = validate(0);

        if (valResult.success) {
            let params = {
                TBL_ID: $.trim($("#tbl_id").val()),
                BRD_MEM_ID: $.trim($("#brd_mem_id").val()),
                BASE_DT : $.trim($("#base_dt").val().replace(/\./gi,"").substring(0,8)),
                ST_TIME_CD: startTime,
                ED_TIME_CD : endTime,
                APPRV_STATUS_CD : $.trim($("#apprv_status_cd").val()),
                APPRV_STATUS_DESC : ($.trim($("#apprv_status_desc").val())==""? " " : $.trim($("#apprv_status_desc").val()))
            }

            $.ajax({
                url: "/time/scheduleModifyProc",
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                success: function(data) {
                    console.log("reponse = " + JSON.stringify(data));

                    if (data.resultCode === "0000") {
                        alert("수정되었습니다.");
                        location.href = "/time/scheduleList";
                    } else {
                        alert(data.resultMessage);
                    }
                },
                error: function (request, status, error) {
                    console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
                }
            })
        } else {
            alert(valResult.message);
            $(valResult.obj).focus();
        }
    })

    $("#btnDelete").click(function(){
        if (confirm("삭제된 데이터는 복구되지 않습니다.\n그래도 정말로 삭제하시겠습니까?")) {
            let params = {
                TBL_ID: $("#tbl_id").val()
            }

            $.ajax({
                url: "/time/scheduleRemoveProc",
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                success: function (data) {
                    console.log("response = " + data);
                    if (data.resultCode === "0000") {
                        alert("삭제되었습니다.");
                        location.href = '/time/scheduleList';
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
        location.href = "/time/scheduleList";
    })

    $("#btnList").click(function(){
        location.href = "/time/scheduleList";
    })

    $("#btnAddRow").click(function() {
        var strHtml = '';

        let params = {
            CODE_CLS_CD: "status_cd"
        }

        $.ajax({
            url: "/code/getCodeList",
            type: "post",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                console.log("response = " + data);

                strHtml += '<div class="form-row">' +
                    '                        <div class="form-group col-md">' +
                    '                            <label for="brd_mem_id" class="essential">결재시간</label>' +
                    '                            <div class="input-group mb-3">' +
                    '                                <select id="startTimeHour" name="startTimeHour" class="form-control form-select">';
                for (var i=0;i<24;i++) {
                    strHtml += '<option value="' + (i > 9 ? i : "0" + i) + '">' + (i > 9 ? i : "0" + i) + '</option>';
                }
                    strHtml += '                                </select>' +
                    '                                <label class="ptx5">&nbsp;:&nbsp;</label>' +
                    '                                <select id="startTimeMinute" name="startTimeMinute" class="form-control form-select">' +
                    '                                    <option value="00">00</option>' +
                    '                                    <option value="30">30</option>' +
                    '                                </select>' +
                    '                                <label class="ptx5">&nbsp;~</label>' +
                    '                            </div>' +
                    '                        </div>' +
                    '                        <div class="form-group col-md">' +
                    '                            <label>&nbsp;</label>' +
                    '                            <div class="input-group mb-3">' +
                    '                                <select id="endTimeHour" name="endTimeHour" class="form-control form-select">';
                for (var i=0;i<24;i++) {
                    strHtml += '<option value="' + (i > 9 ? i : "0" + i) + '">' + (i > 9 ? i : "0" + i) + '</option>';
                }
                strHtml += '                                </select>' +
                    '                                <label class="ptx5">&nbsp;:&nbsp;</label>' +
                    '                                <select id="endTimeMinute" name="endTimeMinute" class="form-control form-select">' +
                    '                                    <option value="00">00</option>' +
                    '                                    <option value="30">30</option>' +
                    '                                </select>' +
                    '                            </div>' +
                    '                        </div>' +
                    '                        <div class="form-group col-md-2">' +
                    '                            <label for="apprv_status_cd" class="essential">업무내용</label>' +
                    '                            <div class="input-group mb-2">' +
                    '                                <select id="apprv_status_cd" class="form-control form-select">' +
                    '                                    <option value="">업무 선택</option>';
                    for (var i=0;i<data.result.length;i++) {
                        let item = data.result[i];

                        strHtml += '<option value="' + item.COMM_CD + '">' + item.CODE_NM + '</option>';
                    }
                    strHtml += '                                </select>' +
                    '                            </div>' +
                    '                        </div>' +
                    '                        <div class="form-group col-md">' +
                    '                            <label>&nbsp;</label>' +
                    '                            <div class="input-group mb-auto">' +
                    '                                <input ref="apprv_status_desc" type="text" class="form-control" id="apprv_status_desc"  placeholder="상세내용" maxlength="10" />' +
                    '                            </div>' +
                    '                        </div>' +
                    '                        <div class="form-group col-md-1">' +
                    '                            <label>&nbsp;</label>' +
                    '                            <div>' +
                    '                                <button type="button" class="btn btn-submit btn-secondary btnDeleteRow" id="btnDelRow" data-id="">행삭제</button>' +
                    '                            </div>' +
                    '                        </div>' +
                    '                    </div>';

                    $("#regDiv").append(strHtml);

                    $(".btnDeleteRow").click(function() {
                        removeRow(this);
                    })
            },
            error: function (request, status, error) {
                console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
            }
        })
    })

    $(".btnDeleteRow").click(function() {
        removeRow(this);
    })

    $("#brd_mem_id").select(function() {
        searchList(1);
    })

    $("#base_dt").change(function() {
        searchList(1);
    })
})

function searchList(page) {
    $("#regDiv").val("");

    let params = {
        PAGE: page,
        BRD_MEM_ID: $.trim($("#brd_mem_id").val()),
        BASE_DT: $.trim($("#base_dt").val().replace(/\./gi, "").substring(0, 8))
    }

    $.ajax({
        url: "/time/getMemberScheduleListAll",
        type: "post",
        data: JSON.stringify(params),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            console.log("response = " + data);
            if (data.resultCode === "0000") {
                let list = data.result.list;
                let codeList = data.result.statusList;

                scheduleCount = list.length;

                var strHtml = "";
                for (var k=0;k<list.length;k++) {
                    let item = list[k];
                    let arrStDt = item.ST_TIME_CD.split(":");
                    let arrEdDt = item.ED_TIME_CD.split(":");

                    strHtml += '<div class="form-row">' +
                        '                        <div class="form-group col-md">' +
                        '                            <label for="brd_mem_id" class="essential">결재시간</label>' +
                        '                            <div class="input-group mb-3">' +
                        '                                <select id="startTimeHour" name="startTimeHour" class="form-control form-select">';
                    for (var i=0;i<24;i++) {
                        strHtml += '<option value="' + (i > 9 ? i : "0" + i) + '" ' + ((i > 9 ? "" + i : "0" + i) === arrStDt[0] ? "selected" : "") +'>' + (i > 9 ? i : "0" + i) + '</option>';
                    }
                    strHtml += '                                </select>' +
                        '                                <label class="ptx5">&nbsp;:&nbsp;</label>' +
                        '                                <select id="startTimeMinute" name="startTimeMinute" class="form-control form-select">' +
                        '                                    <option value="00" ' + ("00" === arrStDt[1] ? "selected" : "") +'>00</option>' +
                        '                                    <option value="30" ' + ("30" === arrStDt[1] ? "selected" : "") +'>30</option>' +
                        '                                </select>' +
                        '                                <label class="ptx5">&nbsp;~</label>' +
                        '                            </div>' +
                        '                        </div>' +
                        '                        <div class="form-group col-md">' +
                        '                            <label>&nbsp;</label>' +
                        '                            <div class="input-group mb-3">' +
                        '                                <select id="endTimeHour" name="endTimeHour" class="form-control form-select">';
                    for (var i=0;i<24;i++) {
                        strHtml += '<option value="' + (i > 9 ? i : "0" + i) + '" ' + ((i > 9 ? "" + i : "0" + i) === arrEdDt[0] ? "selected" : "") +'>' + (i > 9 ? i : "0" + i) + '</option>';
                    }
                    strHtml += '                                </select>' +
                        '                                <label class="ptx5">&nbsp;:&nbsp;</label>' +
                        '                                <select id="endTimeMinute" name="endTimeMinute" class="form-control form-select">' +
                        '                                    <option value="00" ' + ("00" === arrEdDt[1] ? "selected" : "") +'>00</option>' +
                        '                                    <option value="30" ' + ("30" === arrEdDt[1] ? "selected" : "") +'>30</option>' +
                        '                                </select>' +
                        '                            </div>' +
                        '                        </div>' +
                        '                        <div class="form-group col-md-2">' +
                        '                            <label for="apprv_status_cd" class="essential">업무내용</label>' +
                        '                            <div class="input-group mb-2">' +
                        '                                <select id="apprv_status_cd" class="form-control form-select">' +
                        '                                    <option value="">업무 선택</option>';
                    for (var i=0;i<codeList.length;i++) {
                        let code = codeList[i];

                        strHtml += '<option value="' + code.COMM_CD + '" ' + (code.COMM_CD === item.APPRV_STATUS_CD ? "selected" : "") +'>' + code.CODE_NM + '</option>';
                    }
                    strHtml += '                                </select>' +
                        '                            </div>' +
                        '                        </div>' +
                        '                        <div class="form-group col-md">' +
                        '                            <label>&nbsp;</label>' +
                        '                            <div class="input-group mb-auto">' +
                        '                                <input ref="apprv_status_desc" type="text" class="form-control" id="apprv_status_desc" value="' + item.APPRV_STATUS_DESC + '"  placeholder="상세내용" maxlength="10" />' +
                        '                            </div>' +
                        '                        </div>' +
                        '                        <div class="form-group col-md-1">' +
                        '                            <label>&nbsp;</label>' +
                        '                            <div>' +
                        '                                <button type="button" class="btn btn-submit btn-secondary btnDeleteRow" id="btnDelRow" data-id="' + item.ID + '">행삭제</button>' +
                        '                            </div>' +
                        '                        </div>' +
                        '                    </div>';

                }

                $("#regDiv").html(strHtml);
                if (arrFailData.length > 0) {
                    for (var i=0;i<arrFailData.length;i++) {
                        let fail = arrFailData[i];
                        $("#regDiv").append(fail.html);
                    }
                }
                // $("#paging").html(data.result.paging);

                isEmptyList();

                /**
                 * paging 구현시 필수 추가.
                 */
                $(".page-item").click(function(){
                    let goPage = $(this).attr("page");

                    searchList(goPage);
                })

                $(".btnDeleteRow").click(function() {
                    removeRow(this);
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

function isEmptyList() {
    if (scheduleCount > 0) {
        $("#btnRegistPreData").hide();
    } else {
        $("#btnAddRow").click();
        $("#btnRegistPreData").show();
    }
}

function removeRow(obj) {
    let id = $(obj).attr("data-id");

    if (id === undefined || $.trim(id).length === 0) {
        $(obj).parent().parent().parent().remove();
        scheduleCount--;
        isEmptyList();
    } else {
        if (confirm("저장된 데이터가 존재합니다. 삭제하시는 경우 복구되지 않습니다.\n그래도 삭제하시겠습니까?")) {
            let params = {
                TBL_ID: id
            }

            $.ajax({
                url: "/time/scheduleRemoveProc",
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                success: function (data) {
                    console.log("response = " + data);
                    if (data.resultCode === "0000") {
                        alert("데이터가 정상적으로 삭제 되었습니다.");
                        $(obj).parent().parent().parent().remove();
                        scheduleCount--;
                        isEmptyList();
                    } else {
                        alert(data.resultMessage);
                    }
                },
                error: function (request, status, error) {
                    console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
                }
            })
        }
    }
}

function validate(idx){
    let startTime = $.trim($($("select[id=startTimeHour]")[idx]).val()) + ":" + $.trim($($("select[id=startTimeMinute]")[idx]).val());
    let endTime = $.trim($($("select[id=endTimeHour]")[idx]).val()) + ":" + $.trim($($("select[id=endTimeMinute]")[idx]).val());

    if ($.trim($("#brd_mem_id").val()).length <= 0) {
        // alert("경영진을 선택하여야만 합니다.");
        // $("input:text[id=brd_mem_id]")[idx].focus();
        return {
            success: false,
            message: "경영진을 선택하여야만 합니다.",
            obj: $("#brd_mem_id")
        };
    }

    if ($.trim($("#base_dt").val()).length <= 0) {
        // alert("일자를 입력해 주세요");
        // $("input:text[id=base_dt]")[idx].focus();
        return {
            success: false,
            message: "일자를 입력해 주세요.",
            obj: $("#base_dt")
        };
    }

    if ($.trim($($("select[id=startTimeHour]")[idx]).val()).length <= 0) {
        // alert("시작일자를 입력해 주세요");
        // $("select[id=startTimeHour]")[idx].focus();
        return {
            success: false,
            message: "시작일자를 입력해 주세요.",
            obj: $($("select[id=startTimeHour]")[idx])
        };
    }

    if ($.trim($($("select[id=endTimeHour]")[idx]).val()).length <= 0) {
        // alert("종료일자를 입력해 주세요");
        // $("#end_time").focus();
        return {
            success: false,
            message: "종료일자를 입력해 주세요.",
            obj: $($("select[id=endTimeHour]")[idx])
        };
    }
    if (!isValidateAllTime(startTime, endTime)) {
        // alert("결재시작시간이 종료시간보다 크거나 같습니다.");
        return {
            success: false,
            message: "결재시작시간이 종료시간보다 크거나 같습니다."
        };
    }

    if ($.trim($($("select[id=apprv_status_cd]")[idx]).val()).length <= 0) {
        // alert("업무내용을 선택해 주세요");
        // $("select[id=apprv_status_cd]")[idx].focus();
        return {
            success: false,
            message: "업무내용을 선택해 주세요",
            obj: $($("select[id=apprv_status_cd]")[idx])
        };
    }

    //오늘날짜보다 이전일 경우 등록 안되도록 수정
    let compareTime = compareDateTime($.trim($("#base_dt").val().replace(/\./gi,"-")), "23:59");
    if (compareTime <= 0) {
        // alert("이전 일자는 등록할 수 없습니다.");
        // $("input:text[id=base_dt]")[idx].focus();
        return {
            success: false,
            message: "이전 일자는 등록할 수 없습니다.",
            obj: $("#base_dt")
        };
    }
    return {
        success: true,
        message: "",
        obj: undefined
    };
}

function setFailData(idx, message, html) {
    return {
        idx: idx,
        message: message,
        html: html
    };
}