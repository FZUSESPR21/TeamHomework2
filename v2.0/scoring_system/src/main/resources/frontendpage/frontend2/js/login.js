var timestamp = Date.parse(new Date());
$(document).ready(function () {
    document.getElementById("log_image").src="http://1.15.129.32:8888/captcha?rnd="+timestamp;
});
function changeCode(){
    document.getElementById("log_image").src="http://1.15.129.32:8888/captcha?rnd="+timestamp;
}
function setToken(type, value) {
    localStorage.setItem(type, value);
}
function loging() {
    var id = document.getElementById("user_id").value;
    var password = document.getElementById("password").value;
    var verify_code = document.getElementById("verify_code").value;

    console.log(id +":" + password +":" +verify_code +":" + timestamp);
    $.ajax({
        url: 'http://1.15.129.32:8888/login',
        type: 'post',
        dataType: 'json',
        data: {
            account: id,
            password: password,
            verifyCode: verify_code,
            rnd: timestamp,
        },
        success: function(data){
            if (data.code == 200){
                setToken("id", data.data.id);
                setToken("token", data.data.tokenSalt);
                setToken("class", data.data.classId);
                setToken("group", data.data.teamId);
                setToken("account",data.data.account);
                setToken("role",data.data.roles[0].roleName);
                //跳转到页面
                if (data.data.roles[0].roleName =="student"){
                    window.location.href="student/scoreinquiry.html";
                }else if (data.data.roles[0].roleName =="teacher") {
                    window.location.href="teacher/scoreinquiry.html";
                }else if (data.data.roles[0].roleName =="admin"){
                    window.location.href="assigement/scoreinquiry.html";
                }else {
                    alert("身份错误");
                }
            }else {
                alert(data.message);
            }

        }
    });

}
