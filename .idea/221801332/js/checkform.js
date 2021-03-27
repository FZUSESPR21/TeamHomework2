
function checkForm() {

    //获取用户名输入项
    var userNname = document.getElementById("username");
    var uName = userNname.value;
    if (uName.length < 1) {
        alert("账号不能为空");
        return false;
    }
    //密码长度大于6 和确认必须一致
    var password = document.getElementById("password");
    var userPass = password.value;
    if (userPass.length < 6) {
        alert("密码长度必须大于6位");
        return false;
    }
    return true;
}