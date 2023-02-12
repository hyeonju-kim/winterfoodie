/**
 * main page에서 동작하는 script 입니다.
 */
(function(jquery){
    if($("#mainMessage").length > 0) {
        setTimeout(function () {
            $("#alertsDropdown").trigger("click");
        }, 500);
    }
})($);

