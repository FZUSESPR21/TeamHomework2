function downLoadModel() {
    window.open(serviceIp + '/team/export/formwork');
}

function onClicked() {
    //判断团队名是否为空
    if ($("#teamId").val() == ""){
        alert("团队名称不能为空！ ");
        return false;
    }

    var $file1 = $("input[name='file_upload']").val();//用户文件内容(文件)
    // 判断文件是否为空
    if ($file1 == "") {
        alert("请选择上传的团队成员列表文件! （excel）");
        return false;
    }
    //判断文件类型,我这里根据业务需求判断的是Excel文件
    var fileName1 = $file1.substring($file1.lastIndexOf(".") + 1).toLowerCase();
    if(fileName1 != "xls" && fileName1 !="xlsx"){
        alert("请选择Execl文件!");
        return false;
    }
    //判断文件大小
    var size1 = $("input[name='file_upload']")[0].files[0].size;
    if (size1>104857600) {
        alert("上传文件不能大于100M!");
        return false;
    }
    var form=new FormData();
    var teamId = $("#teamId").val();
    form.append("excel",$("#file_upload")[0].files[0]);
    form.append("teamId",teamId);
    $.ajax({
        url: serviceIp + '/team/import',
        type:"POST",
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        data:form,
        contentType: false,
        processData: false,
        success:function(data){
            if(data.code == '200'){
                alert("上传成功！");
            }else {
                layer.msg(data.message, {time : 1500, icon : 2});
            }
        }
    });
}
function UpladFile() {
    //判断团队名是否为空
    if ($("#teamId").val() == ""){
        alert("团队名称不能为空！ ");
        return false;
    }

    var $file1 = $("input[name='file_upload']").val();//用户文件内容(文件)
    // 判断文件是否为空
    if ($file1 == "") {
        alert("请选择上传的团队成员列表文件! （excel）");
        return false;
    }
    //判断文件类型,我这里根据业务需求判断的是Excel文件
    var fileName1 = $file1.substring($file1.lastIndexOf(".") + 1).toLowerCase();
    if(fileName1 != "xls" && fileName1 !="xlsx"){
        alert("请选择Execl文件!");
        return false;
    }
    //判断文件大小
    var size1 = $("input[name='file_upload']")[0].files[0].size;
    if (size1>104857600) {
        alert("上传文件不能大于100M!");
        return false;
    }
    var fileObj = document.getElementById("file_upload").files[0]; // 获取文件对象
    var teamId = document.getElementById("teamId");
    var FileController = serviceIp + "/team/import"; // 接收上传文件的后台地址
    // FormData 对象
    var form = new FormData();
    form.append("teamId", teamId.Value); // 可以增加表单数据
    form.append("excel", fileObj);  // 文件对象
    // XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();
    xhr.open("post", FileController, true);
    xhr.setRequestHeader("Token",localStorage.token);
    xhr.onload = function () {
        alert("上传完成!");
    };
    xhr.send(form);
}