/**
 * main page에서 동작하는 script 입니다.
 */
(function(jquery){

    $("#alertDayInfo").text('2023년 2월 1일');
    setTimeout(function (){
        $("#alertsDropdown").trigger("click");
    },500);
})($);

