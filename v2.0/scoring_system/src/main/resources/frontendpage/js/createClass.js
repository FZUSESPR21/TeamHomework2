function onClicked() {
    //判断作业名是否为空
    if ($("#class_name").val() == ""){
        alert("作业名不能为空！ ");
        return false;
    }

    if ($("#grade").val() == ""){
        alert("年级不能为空");
        return false;
    }

    if ($("#grade").val() < 1900 || $("#grade").val() > 3000){
        alert("请输入1900~2099之间的年份");
        return false;
    }

    $.ajax({
        url:"http://1.15.129.32/:8888/class/insert",
        type:"POST",
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        data: {
            className: $("#class_name").val(),
            grade: $("#grade").val(),
            teacherId: getToken("id"),
        },
        success:function(data){
            layer.msg(data.message);
        }
    })
}