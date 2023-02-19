var smbCom = {};
smbCom.createAction = function(url, json, successCB, errorCB, type, contentType){
    $.ajax({
        type : (type == undefined) ? "POST" : type,
        url : url, //요청 할 URL
        data : JSON.stringify(json), //넘길 파라미터
        contentType: (contentType) ? contentType : "application/json; charset=utf-8",
        success : function(data) {
            successCB(data);
        },
        error : function(data,status,error) {
            errorCB(data.responseJSON);
        }
    });
}

smbCom.isEmpty = function(str){
    return (str == '' || str == undefined || str == null || str == 'null' );
}

smbCom.readURL = function(input){
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('preview').src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        document.getElementById('preview').src = "";
    }
}

