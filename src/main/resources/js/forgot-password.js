(function(jquery){

    let $title = $("#title");
    let $subTitle = $("#subTitle");
    let $emailElement = $("#email");
    let $confirmBtnElement = $("#confirmBtn");

    function fetchSendEmail(){
        let email = $emailElement.val();
        smbCom.createAction("/view/user/forgot-password",
            {email : email},
            function(data){
                $title.text("메일 발송 완료");
                $subTitle.text("임시 비밀번호를 가입하신 메일로 보내드렸습니다!");
                $confirmBtnElement.hide();
                $emailElement.hide();
            },
            function(data){
                alert(data.message);
            }
        )

    }

    $confirmBtnElement.on("click", fetchSendEmail);

})($);

