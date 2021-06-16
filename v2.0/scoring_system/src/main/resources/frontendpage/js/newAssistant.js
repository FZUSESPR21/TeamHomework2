function submitOnClick() {
    //判断助教姓名是否为空
    if ($("#userName").val() == ""){
        alert("助教名称不能为空！ ");
        return false;
    }

    //判断助教账号是否为空
    if ($("#account").val() == ""){
        alert("助教账号不能为空！ ");
        return false;
    }

    var zhengze =  /^[0-9a-zA-Z]{6,16}$/;    //判断密码
    var repPass1 = /[0-9]{1,}/;       //判断数字
    var repPass2 = /[a-zA-Z]{1,}/;     //判断字母
    var va=$("#password").val();
    if(!zhengze.test(va)){
        alert("密码由6-16位数字和字母组成");
    }else if(!repPass1.test(va) || !repPass2.test(va)){
        alert("请输入符合规则的密码~!");
    }
    else if(va.length<6||va.length>16)
    {
        alert("登录密码长度不符合要求!");
    }
    var account= document.getElementById("account").value;
    var userName = document.getElementById("userName").value;
    var password = document.getElementById("password").value;
    var data1={
        account:account,
        password:password,
        userName:userName,
        perms:null,
        roles:null,
        class_id:"1"
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
            if(data.code == '204'){
                alert("注册成功！");
            }
            else if(data.code=='404'){
                alert("账户名已存在！");
            }
        }
    });
}