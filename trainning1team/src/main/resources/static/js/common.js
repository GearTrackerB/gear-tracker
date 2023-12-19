/*
 *
 * 같은 값이 있는 행을 병합함
 *
 * 사용법 : $('#테이블 ID').colspan (0);
 *
 */
$.fn.colspan = function(rowIdx) {
    return this.each(function(){
        var that;
        $('tr', this).filter(":eq("+rowIdx+")").each(function(row) {
            $(this).find('td').filter(':visible').each(function(col) {
                if ($(this).attr("bgColor") == $(that).attr("bgColor") && $(this).text() == $(that).text()) {
                    colspan = $(that).attr("colSpan") || 1;
                    colspan = Number(colspan)+1;

                    $(that).attr("colSpan",colspan);
                    $(this).remove();
                } else {
                    that = this;
                }

                // set the that if not already set
                that = (that == null) ? this : that;

            });
            $(this).find('th').filter(':visible').each(function(col) {
                if(col > 0){
                    //if ($(this).attr("bgColor") == $(that).attr("bgColor") && $(this).text() == $(that).text()) {
                    if ($(this).attr("class") == $(that).attr("class") && $(this).text() == $(that).text()) {
                        colspan = $(that).attr("colspan") || 1;
                        colspan = Number(colspan)+1;

                        $(that).attr("colspan",colspan);
                        $(this).remove();
                    } else {
                        that = this;
                    }

                    // set the that if not already set
                    that = (that == null) ? this : that;
                }

            });
        });
    });
}

$.datepicker.regional['ko'] = {
    closeText: '닫기',
    prevText: '이전달',
    nextText: '다음달',
    currentText: '오늘',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    buttonImageOnly: false,
    weekHeader: 'Wk',
    dateFormat: 'yy-mm-dd',
    firstDay: 0,
    isRTL: false,
    duration: 200,
    showAnim: 'show',
    showMonthAfterYear: false
};
$.datepicker.setDefaults($.datepicker.regional['ko']);

function isValidateAllTime(stm, etm)
{
    var sstm = parseInt(stm.replace(/:/gi, ""), 10);
    var eetm = parseInt(etm.replace(/:/gi, ""), 10);
    if (sstm >= eetm)
    {
        return false;
    }

    return true;
}

function dateAdd(sDate, v, t) {
    let yy = parseInt(sDate.substr(0, 4), 10);
    let mm = parseInt(sDate.substr(5, 2), 10);
    let dd = parseInt(sDate.substr(8), 10);
    if (t === 'd') {
        d = new Date(yy, mm - 1, dd + v);
    } else if (t === 'm') {
        d = new Date(yy, mm - 1 + v, dd);
    } else if (t === 'y') {
        d = new Date(yy + v, mm - 1, dd);
    } else {
        d = new Date(yy, mm - 1, dd + v);
    }
    yy = d.getFullYear();
    mm = d.getMonth() + 1; mm = (mm < 10) ? '0' + mm : mm;
    dd = d.getDate(); dd = (dd < 10) ? '0' + dd : dd;
    let resultDate = '' + yy + '.' + mm + '.' + dd;
    //resultDate = resultDate + " "+ getInputDayLabel(resultDate);
    return resultDate;
}

function getInputDayLabel(strDate) {
    let week = ['일', '월', '화', '수', '목', '금', '토'];
    let today = new Date(strDate).getDay();
    let todayLabel = week[today];
    return "("+ todayLabel +")";
}

function getStrToDateTime(dateStr,timeStr){	// 문자열을 날짜 Date로 변환
    dateStr = dateStr.replace(/-/gi,"").replace(/\//gi,"");
    timeStr = timeStr.replace(/:/gi,"");
    // Javascript에서 문자열 형변환을 위해 *1을 하였으며, Javascript의 Date에서 Month는 -1 해주어야 한다.
    return (new Date(dateStr.substr(0,4), (dateStr.substr(4,2) * 1 - 1), dateStr.substr(6,2), timeStr.substr(0,2), timeStr.substr(2,2)));
}

function compareDateTime(pramDate, pramTime){
    var now = new Date();
    var param = getStrToDateTime(pramDate, pramTime);
    console.log("param :" + param);
    console.log("param GET time :" + param.getTime());
    console.log("now GET time : " + now.getTime());
    //alert(Math.floor((param - now)%(1000*60*60)/(1000*60)));
    return (param.getTime() - now.getTime())/1000/60;	//분으로 바꿔서 체크
}

function removeTag(value){
    var rStr = value.replace(/(<([^>]+)>)/ig, "");
    return rStr;
}

$(document).ready(function(){
    $("form").submit(function(){
        return false;
    })
})