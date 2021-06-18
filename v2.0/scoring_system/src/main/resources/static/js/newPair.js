function downLoadModel() {
    window.open( serviceIp + '/team/export/formwork');
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
    form.append("excel",$("#file_upload")[0].files[0]);
    form.append("classRoomId",user_class);
    $.ajax({
        url: serviceIp + '/pair/import',
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
    })
}