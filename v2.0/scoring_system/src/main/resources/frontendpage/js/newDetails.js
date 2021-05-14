
function downLoadModel() {
    window.open();
}

function judgeExcelFile() {
    //判断作业名是否为空
    if ($("#assignment_title").val() == ""){
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
}

function onClicked() {
    if (judgeExcelFile() == false){
        return false;
    }
    //alert("1");

    //返回服务器数据
    var date = new Date();
    var data2=new FormData();
    var title = $("#assignment_title").val();
    var checklist = $("#checklist").val();
    var make_up_date = $("#make_up_date").val();
    var closing_date = $("#closing_date").val();
    var proportion = $("#proportion").val()/100;
    var work_type = $("#work_type option:selected").val();
    var create_user = getToken("id");
    var class_id = getToken("class");
    //获取当前时间，格式为2021-05-05
    var create_year = date.getFullYear();
    var create_month = date.getMonth()+1;
    var create_day = date.getDate();
    var create_time = create_year + "-" + create_month + "-" + create_day;
    //alert(create_time);
    //console.log(create_year + create_month +create_day);

    if (make_up_date =="" || closing_date == ""){
        alert("时间不能为空！");
        return false;
    }

    if (proportion <0 || proportion>100){
        alert("请输入1~100的数!");
        return false;
    }
    //console.log(title+checklist+make_up_date+closing_date+proportion+work_type);
    data2.append("excel",$("#file_upload")[0].files[0]);
    data2.append("taskName",title);
    data2.append("taskContent",checklist);
    data2.append("makeUpTime",make_up_date);
    data2.append("deadline",closing_date);
    data2.append("ratio",proportion);
    data2.append("taskType",work_type);
    data2.append("creteUserId",create_user);
    data2.append("classRoomId",class_id);
    data2.append("begineTime",create_time);
    console.log(data2);
    $.ajax({
        url:"http://1.15.129.32:8888/details/import",
        type:"post",
        dataType:"JSON",
        data: data2,
        contentType: false,
        processData: false,
        success:function(data){

        }
    });
}