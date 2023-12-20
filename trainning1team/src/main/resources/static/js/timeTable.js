$(document).ready(function(){
    const rowCnt = $('#tBoard tr').length;

    for(let i = 0; i < rowCnt; i++){
        $('#tBoard').colspan(i); //날짜 행
    }

    $(".btn_excel").click(function(){
        location.href = "/timeTable/excel?sDT=" + $("#searchDt").val().substring(0, 10);
    })

    //날짜검색
    $('#searchDt').datepicker({
        showOn: "both",
        buttonImageOnly: false,
        buttonText: "",
        dateFormat : "yy.mm.dd"
    });
    $(".sDay > button").remove();

    $(".ceoCom").each(function (){
        let comRows = $(".ceoCom:contains('"+$(this).text()+"')");
        if(comRows.length > 1){
            comRows.eq(0).attr("rowspan", comRows.length);
            comRows.not(":eq(0)").remove();
        }
    });

    $('#searchDt').change(function () {
        fn_changeDate();
    });

    $(".prevDate").click(function (){
        var searchStartDt = $("#searchDt").val();
        var prevDt = dateAdd(searchStartDt, -1, "d");
        $("#searchDt").val(prevDt);
        fn_changeDate();
    });

    $(".nextDate").click(function (){
        var searchStartDt = $("#searchDt").val();
        var nextDt = dateAdd(searchStartDt, 1, "d");
        $("#searchDt").val(nextDt);
        fn_changeDate();
    });

    //출력 관련 설정
    $(".btn_print").click(function(e) {

        let initBody = document.body;
        e.preventDefault();

        //출력전 hidden
        window.onbeforeprint = function () {
            $("#gnb").hide();
            $("#sidebar").hide();
            $("#ceoTable > h3").hide();
            $(".btn_excel").hide();
            $(".btn_print").hide();
            $("#ceoTable").removeClass();
            $("#colCompNm").removeClass();
            $("#colCeoNm").removeClass();
            $("#colCompNm").addClass("w9");
            $("#colCeoNm").addClass("w12");
        }

        //출력후 원복
        window.onafterprint = function () {
            $("#gnb").show();
            $("#sidebar").show();
            $("#ceoTable > h3").show();
            $(".btn_excel").show();
            $(".btn_print").show();
            $("#ceoTable").addClass("ceoTable");
            $("#colCompNm").removeClass();
            $("#colCeoNm").removeClass();
            $("#colCompNm").addClass("w7");
            $("#colCeoNm").addClass("w10");
        }
        window.print();

    });
})
function fn_changeDate(){
    const path = window.location.pathname;
    location.href = path + "?sDT=" + $('#searchDt').val();
}