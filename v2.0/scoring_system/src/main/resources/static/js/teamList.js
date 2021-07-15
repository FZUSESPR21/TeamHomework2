var classRoom = getToken("class");


//加载班级列表（应该另一个文件，使用较多
$(document).ready(function () {
    $.ajax({
        type: 'post',
        url: serviceIp + '/score/class/showlist',
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success: function(data){
            $.each(data.data,function (index,item) {
                $("#class_room").append("<option value='"+data.data[index].id+"'>"+data.data[index].className+"</option>");
            });
        }
    });
    showTeamList(classRoom);
});

//班级列表切换时事件，重新加载mytable的内容，采用的是先把表内容删除再加载，而不是refresh
document.getElementById('class_room').onchange =function () {
    var classId = $("#class_room").find("option:selected").val();
    if (classId == 0){
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        showTeamList(classRoom);
    }else {
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        showTeamList(classId);
    }
};

function showTeamList(classroom){
    $('#myTable').bootstrapTable({
        method: 'post',
        url: serviceIp + '/team/selTeamByClassRoomId',
        //toolbar: "#class_room",
        striped: true, // 是否显示行间隔色
        pageNumber: 1, // 初始化加载第一页
        pagination: true, // 是否分页
        dataField: "list",
        sidePagination: 'client', // server:服务器端分页|client：前端分页
        height:600,
        sortable: true,
        search: true,
        showColumns: true, //筛选要显示的列
        showSearchClearButton: true, //显示搜索清除按钮
        pageSize: 10, // 单页记录数
        pageList: [10, 15],
        contentType: "application/x-www-form-urlencoded",
        queryParamsType : "undefined",
        ajaxOptions:{
            headers: {"Token":getToken("token")}
        },

        queryParams: function queryParams(params) { //设置查询参数
            return {
                pageNum: params.pageNumber,
                pageSize: params.pageSize,
                classRoomId: classroom,
            };
        },
        onLoadSuccess: function(){ //加载成功时执行
            layer.msg("加载成功");
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
            title: '团队id',
            field: 'id',
            sort: true,
            sortable: true,
        }, {
            title: '团队名称',
            field: 'sysTeamName',
            sort: true,
            sortable: true,
            searchable:true,
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            events: {
                'click #edit': function (e, value, row, index) {
                    window.open('teamDetail.html?id='+row.id);
                },
                'click #delete': function (e, value, row, index) {
                    deleteInfo(row.Id);
                }
            },
            formatter: function (value, row, index) {
                var result = "";
                result += '<button id="edit" class="btn btn-info" data-toggle="modal" data-target="#editModal">查看</button>';
                result += '<button id="delete" class="btn btn-info" style="margin-left:1%;">删除</button>';
                return result;
            }
        }
        ]
    });
}




// 删除信息
function deleteInfo(id) {
    $.ajax({
        type: 'post',
        url: 'test2.json',
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        data: {
            id: id
        },
        success: function (data) {
            if (data == 'Yes') {
                $('#table').bootstrapTable('refresh');
            }
            else {
                alert('删除失败');
            }
        }
    })
}