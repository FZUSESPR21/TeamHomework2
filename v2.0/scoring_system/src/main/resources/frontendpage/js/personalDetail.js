personalWork();
document.getElementById('work_type').onchange=function(){
    var work_type_id = $("#work_type").find("option:selected").val();
    if (work_type_id == 1){
        console.log(1);
        document.getElementById("personal_table").innerHTML="";
        $("#personal_table").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        personalWork();
    }
    else {
        console.log(3);
        document.getElementById("personal_table").innerHTML="";
        $("#personal_table").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        teamWork();
    }
};
//加载表根据用户id
function personalWork() {
    $('#myTable').bootstrapTable({
        method: 'post',
        url: serviceIp + "/score/userblogwork/list?id="+user_id, // 请求路径
        striped: true, // 是否显示行间隔色
        pageNumber: 1, // 初始化加载第一页
        pagination: true, // 是否分页
        sidePagination: 'client', // server:服务器端分页|client：前端分页
        height:600,
        sortable: true,
        pageSize: 10, // 单页记录数
        pageList: [10, 15],
        dataField: "data",
        paginationLoop: true,
        paginationHAlign: 'left',
        paginationDetailHAlign: 'right',
        paginationPreText: '上一页',
        paginationNextText: '下一页',

        ajaxOptions:{
            headers: {"Token":getToken("token")}
        },

        columns: [{
            title: '博客ID',
            field: 'id',

            sortable: true,
        },{
            title: '作业名称',
            field: 'task',
            formatter: function (value, row, index) {
                if (value.taskName == null || value.taskName == undefined) {
                    return "0";
                } else {
                    return value.taskName;
                }
            },
        }, {
            field: 'score',
            title: '分数',
            sortable: true,
            formatter: function (value, row, index) {
                if (value == null || value == undefined){
                    return "0";
                }else if (value.score == null || value.score == undefined) {
                    return "0";
                } else {
                    return value.score;
                }
            },
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            events: {
                'click #edit': function (e, value, row, index) {
                    window.location.href="itemDetails.html?id="+row.id;
                },
            },

            formatter: function (value, row, index) {
                var result = "";
                result += '<button id="edit" class="btn btn-info" data-toggle="modal" data-target="#editModal">查看详情</button>';
                return result;
            }
        }
        ]
    });
}

function teamWork() {
    $('#myTable').bootstrapTable({
        method: 'post',
        url: serviceIp + "/score/teamblogwork/list?id="+user_id, // 请求路径
        striped: true, // 是否显示行间隔色
        pageNumber: 1, // 初始化加载第一页
        pagination: true, // 是否分页
        sidePagination: 'client', // server:服务器端分页|client：前端分页
        height:600,
        sortable: true,
        pageSize: 10, // 单页记录数
        pageList: [10, 15],
        dataField: "data",
        paginationLoop: true,
        paginationHAlign: 'left',
        paginationDetailHAlign: 'right',
        paginationPreText: '上一页',
        paginationNextText: '下一页',
        onLoadError: function(){ //加载失败时执行
            layer.msg("加载数据失败", {time : 1500, icon : 2});
        },
        responseHandler: function(res){
            if (res.code == "200"){
                return res;
            }else {
                layer.msg(res.message, {time : 1500, icon : 2});
            }
        },

        ajaxOptions:{
            headers: {"Token":getToken("token")}
        },

        columns: [{
            title: '博客ID',
            field: 'id',
            sortable: true,
        },{
            title: '作业名称',
            field: 'task',
            formatter: function (value, row, index) {
                if (value.taskName == null || value.taskName == undefined) {
                    return "0";
                } else {
                    return value.taskName;
                }
            },
        }, {
            field: 'score',
            title: '分数',
            sortable: true,
            formatter: function (value, row, index) {
                if (value.score == null || value.score == undefined) {
                    return "0";
                } else {
                    return value.score;
                }
            },
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            events: {
                'click #edit': function (e, value, row, index) {
                    window.location.href="itemDetails.html?id="+row.id;
                },
            },

            formatter: function (value, row, index) {
                var result = "";
                result += '<button id="edit" class="btn btn-info" data-toggle="modal" data-target="#editModal">查看详情</button>';
                return result;
            }
        }
        ]
    });
}