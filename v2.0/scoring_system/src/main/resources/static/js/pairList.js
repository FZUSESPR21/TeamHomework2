var classRoom = getToken("class");

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
    showPairList(classRoom);
});

document.getElementById('class_room').onchange =function () {
    var classId = $("#class_room").find("option:selected").val();
    if (classId == 0){
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        showPairList(classRoom);
    }else {
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        showPairList(classId);
    }
};


function showPairList(classroom){
    $('#myTable').bootstrapTable({
        method: 'post',
        url: serviceIp + '/team/selPairTeamByClassRoomId',
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
            var param = {
                pageNum: params.pageNumber,
                pageSize: params.pageSize,
                classRoomId: classroom,
            };
            return param;
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
            title: '结对ID',
            field: 'id',
            sort: true,
            sortable: true,
        }, {
            title: '队员',
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
                    // window.open('teamDetail.html?id='+row.id);
                    showPairDetail(row.id);
                },
                'click #delete': function (e, value, row, index) {
                    deleteInfo(row.id);
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

//查看结对信息
function showPairDetail(pairId) {
    $.ajax({
        type: "get",
        url: serviceIp + "/team/selAllPairTeamMemberByPairId?pairId="+pairId,
        dataType:"json",
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success: function (data) {
            if (data.code == 200) {
                if (data.data[1] != null){
                    console.log(1);
                    $('#student1_id').text(data.data[0].account);
                    $('#student2_id').text(data.data[1].account);
                    $('#student2_name').text(data.data[1].userName);
                    $('#student1_name').text(data.data[0].userName);
                    $("#std2").attr("style","");
                    box.style.display = 'flex';
                    hidden.style.display = 'block';
                }else if (data.data[0] != null){
                    $('#student1_id').text(data.data[0].account);
                    $('#student1_name').text(data.data[0].userName);
                    $("#std2").attr("style","display:none");
                    box.style.display = 'flex';
                    hidden.style.display = 'block';
                }else {
                    layer.msg("结对成员为空");
                }
            }
            else {
                alert('删除失败');
            }
        }
    });
}

$("#submit").click(function () {
    var box = document.getElementById('box');
    var hidden = document.getElementById('hidden');
    box.style.display = 'none';
    hidden.style.display = 'none';
    // 关闭后恢复box到原来的默认位置
    box.style.top = '200px';
    box.style.marginRight = '100%';
});

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
    });
}