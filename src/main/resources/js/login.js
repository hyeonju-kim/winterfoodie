
(function(jquery){
    $("#confirmBtn").on("click", function(){
        fetchLogin();
    })

    function fetchLogin(){
        //data setting

        let email = $("#email").val();
        let password = $("#password").val();

        if(smbCom.isEmpty(email)){
            alert("이메일을 입력해주세요.");
            return;
        }

        if(smbCom.isEmpty(password)){
            alert("비밀번호를 입력해주세요.");
            return;
        }

        smbCom.createAction("/view/user/login", {
            email : email,
            password : password,
        }, function(data){
            //등록 완료 시
            alert(`환영합니다. ${data.name} 님`);
            window.location.href=data.redirect;

        }, function(data){
            //등록 불발 시
            alert(data.message);

        });
    }
})($);

