function downLoadModel() {
    window.open('http://1.15.129.32:8888/student/export/formwork');
}

$(document).ready(function () {
    //加载下拉列表的内容
    $.ajax({
        type: 'post',
        url: 'http://1.15.129.32:8888/score/class/showlist',
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success: function(data){
            $.each(data.data,function (index,item) {
                $("#classId").append("<option value='"+data.data[index].id+"'>"+data.data[index].className+"</option>");
            });
        }
    });
});

function onClicked() {
    //判断作业名是否为空
    if ($("#classId").val() == ""){
        alert("作业名不能为空！ ");
        return false;
    }

    var $file1 = $("input[name='file_upload']").val();//用户文件内容(文件)
    // 判断文件是否为空
    if ($file1 == "") {
        alert("请选择上传的目标文件! ");
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
    var classId = $("#classId").val();
    form.append("excel",$("#file_upload")[0].files[0]);
    form.append("classId",classId);
    $.ajax({
        url:"http://1.15.129.32:8888/student/import",
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
                alert(data.message);
            }
        }
    })
}