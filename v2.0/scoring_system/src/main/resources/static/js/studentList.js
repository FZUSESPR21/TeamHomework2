var open = document.getElementById('open');
var box = document.getElementById('box');
var hidden = document.getElementById('hidden');
var close = document.getElementById('close');
var classRoom = getToken("class");

$('#myTable').bootstrapTable({
    method: 'post',
    url: serviceIp + "/student/selByPage",
    striped: true, // 是否显示行间隔色
    pageNumber: 1, // 初始化加载第一页
    pagination: true, // 是否分页
    dataField: "list",
    //sidePagination: 'client', // server:服务器端分页|client：前端分页
    height:600,
    sortable: true,
    search: true,
    showColumns: true, //筛选要显示的列
    showSearchClearButton: true, //显示搜索清除按钮
    pageSize: 10, // 单页记录数
    pageList: [10, 15],
    sidePagination: "server", //表示服务端请求
    contentType: "application/x-www-form-urlencoded",//必须要有！！！！
    queryParamsType : "undefined",

    ajaxOptions:{
        headers: {"Token":getToken("token")}
    },
    queryParams: function queryParams(params) { //设置查询参数
        var param = {
            pageNum: params.pageNumber,
            pageSize: params.pageSize,
            classRoomId: getToken("class"),
        };
        return param;
    },
    onLoadError: function(){ //加载失败时执行
        layer.msg("加载数据失败", {time : 1500, icon : 2});
    },
    responseHandler:function(res){
        if (res.code == "200"){
            return res.data;
        }else {
            layer.msg(res.message, {time : 1500, icon : 2});
            return false;
        }
    },

    paginationLoop: true,
    paginationHAlign: 'left',
    paginationDetailHAlign: 'right',
    paginationPreText: '上一页',
    paginationNextText: '下一页',
    columns: [{
        checkbox: true
    }, {
        title: '学号',
        field: 'account',
        sort: true,
        sortable: true,
    }, {
        title: '姓名',
        field: 'userName',
        sort: true,
        sortable: true,
        searchable:true,
    },
        {
            title: '班级',
            field: 'classId',
            sortable: true,
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            events: {
                'click #edit': function (e, value, row, index) {
                    window.location.href="personalDetail.html?id="+row.id;
                },
                'click #update': function (e, value, row, index) {
                    updateInfo(row.id,row.account,row.userName,row.perms,row.classId,row.totalScore);

                },
                'click #delete': function (e, value, row, index) {
                    // deleteInfo(row.id);
                    $('#student_id').text(row.account);
                    $('#delete_id').text(row.id);
                    console.log(row.account);
                    box.style.display = 'flex';
                    hidden.style.display = 'block';
                }
            },
            formatter: function (value, row, index) {
                var result = "";
                result += '<button id="edit" class="btn btn-info" data-toggle="modal" data-target="#editModal">查看</button>';
                result += '<button id="delete" class="btn btn-info" style="margin-left:1%;">删除</button>';
                result += '<button id="update"  class="btn btn-info" style="margin-left:1%;">重置密码</button>';
                return result;
            }
        }
    ]
});

//查看信息
function editInfo(id) {

    $.ajax({
        type: 'post',
        url: serviceIp + '/student/selSingleStudent/id',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        data: id,
        success: function (data) {
            if (data.code == '200') {
                window.location.href="itemDetails.html?id="+id;
            }
            else if(data.code="1002") {
                alert('没有这个权限');
            }
        }
    });
}


// 删除信息
function deleteInfo(id) {
    $.ajax({
        type: 'post',
        url: serviceIp + '/student/delStuById/'+id,
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success: function (data) {
            if (data.code == "200") {
                $('#myTable').bootstrapTable('refresh');
                layer.msg(data.message);
            }
            else if(data.code="1002") {
                alert('没有这个权限');
            }
        }
    })
}

//文件上传
function onClicked() {
    //判断作业名是否为空
    var $file1 = $("input[name='file_upload']").val();//用户文件内容(文件)
    // 判断文件是否为空
    if ($file1 == "") {
        alert("请选择上传的学生列表文件! （excel）");
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
    var classId = document.getElementById("classId");
    var FileController = serviceIp + "/student/import"; // 接收上传文件的后台地址
    // FormData 对象
    var form = new FormData();

    form.append("classId", classId); // 可以增加表单数据
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

//重置密码
function updateInfo(id,account,userName,perms,classId,totalScore){
    var data1={
        id:id,
        account:account,
        userName:userName,
        perms: perms,
        classId: classId,
        totalScore: totalScore,
    };
    var data = JSON.stringify(data1);
    $.ajax({
        url: serviceIp + "/student/updStudent1",
        type:'post',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        data: data,
        success:function(data){
            if(data.code == '200'){
                alert("重置成功！");
            }
            else if(data.code=='1002'){
                alert("没有这个学生用户");
            }
        }
    });
}

//关闭
close.onclick = function () {
    box.style.display = 'none';
    hidden.style.display = 'none';
    // 关闭后恢复box到原来的默认位置
    box.style.top = '200px';
    box.style.marginRight = '100%';
};

$("#submit").click(function () {
    deleteInfo($("#delete_id").text());
    close.click();
});
