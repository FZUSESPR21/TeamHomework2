function submitOnClick() {
    var zhengze =  /^[0-9a-zA-Z]{6,16}$/;    //判断密码
    var repPass1 = /[0-9]{1,}/;       //判断数字
    var repPass2 = /[a-zA-Z]{1,}/;     //判断字母
    var va=$("#password").val();
    if(!zhengze.test(va)){
        alert("密码由6-16位数字和字母组成");
    }else if(!repPass1.test(va) || !repPass2.test(va)){
        alert("密码由6-16位数字和字母组成!");
    }
    else if(va.length<6||va.length>16)
    {
        alert("登录密码长度不符合要求!");
    }
    var changePassword= document.getElementById("changePassword").value;
    var rePassword = document.getElementById("rePassword").value;
    var password = document.getElementById("password").value;

    if (password != changePassword){
        layer.msg("新密码与确认密码不符");
        return;
    }

    var data1={
        changePassword: changePassword,
        password:password,
        rePasswprd:rePassword,
        perms:null,
        roles:null,
        classId: classId,
    };

    $.ajax({
        url: serviceIp + "/teacher/assistant/add",
        type:'post',
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        contentType: "application/json",
        data: JSON.stringify(data1),
        success:function(data){
            
        }
    });
}