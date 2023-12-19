$(document).ready(function(){

    $("#sidebar ul li").removeClass();
    $("#sidebar ul li a").removeClass();

    const path = window.location.pathname;
    let pramStr = window.location.search;
    if(pramStr === ''){
        pramStr = "?sDT=";
    }
    const pathArr = path.split("/");
    let comCd = pathArr[2];

    //1층본점안내데스크 & 결재시간 통합본 보시는 분들
    if(comCd === "FRT" || comCd === "ALL"){
        $("#sidebar").hide();
        $("#ceoLamp").css("margin-left","0");
        $("#ceoLamp > ul:last-child").css("margin-left","130px");
        $("#ceoTable").css("margin-left","0");
    } else {
        $("#sidebar").show();
        $("#ceoLamp").css("margin-left","260px");
        $("#ceoLamp > ul:last-child").css("margin-left","0");
        $("#ceoTable").css("margin-left","260px");
    }

    $("#sidebar a").each(function (index, item){
        const linkStr = $(item).attr("href");
        if(pathArr[1] === linkStr.replace("/","")){
            $(item).closest("li").addClass("on");
            $(item).addClass("mArrow");
        }
        if(comCd === "ALL"){
            comCd = "CMP001";
        }
        if(linkStr.replace("/","") === 'timeTable'){
            $(item).attr("href", linkStr + "/" + pathArr[2]  + pramStr);
        } else {
            $(item).attr("href", linkStr + "/" + comCd);
        }
    });
})