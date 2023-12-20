$(document).ready(function(){

    search(1);

    $("#search_date").datepicker({
        showOn: "both",
        buttonImageOnly: false,
        buttonText: "",
        dateFormat : "yy.mm.dd"
    });
    $(".sBox > button").remove();
    $(".sBox").append('<button type="button" class="btn btn-submit btn-search ml-2 btn-secondary">검색</button>');

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
    })

    $(".btn-regist").click(function(){
        location.href = "/time/scheduleRegist";
    })

    $(document).on("click", ".dataRow", function (){
      let dv =  $(this).attr("data-value");
      goView(dv);
    });
})

function goView(id) {
    location.href = "/time/scheduleModify/" + id;
}

function search(page) {
    let params = {
        PAGE: parseInt(page),
        COMP_CD: $("#comp_cd").val(),
        EMP_NO: $("#search_emp_no").val(),
        EMP_NM: $("#search_emp_nm").val(),
        BASE_DT : $("#search_date").val().replace(/\./gi,"").substring(0,8)
    }

    console.log(params);

    $.ajax({
        url: "/time/getMemberScheduleList",
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
                        let bDate = item.BASE_DT.replace(/-/gi,"");

                        strHtml += '<tr class="dataRow tac cursorP" data-value="'+ item.ID +'">';
                        strHtml += '    <td class="m_table">' + (startNo--) + '</td>';
                        strHtml += '    <td>' + item.COMP_NM + '</td>';
                        strHtml += '    <td>' + item.EMP_NO + '</td>';
                        strHtml += '    <td>' + item.EMP_NM + '</td>';
                        strHtml += '    <td>' + item.POSITION_NM + '</td>';
                        strHtml += '    <td>' + bDate.substring(0,4) + '.' + bDate.substring(4,6) + '.' + bDate.substring(6,8)  + '</td>';
                        strHtml += '    <td>' + item.ST_TIME_CD + ' ~ ' + item.ED_TIME_CD +'</td>';
                        strHtml += '    <td>' + item.APPRV_STATUS_NM + '</td>';
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
            } else {
                alert(data.resultMessage);
            }
        },
        error: function (request, status, error) {
            console.log("code : " + request.status + ", message : " + request.responseText + ", error : " + error);
        }
    })
}