var classRoom = getToken("class");

$('#myTable').bootstrapTable({
    method: 'post',
    url: "http://1.15.129.32:8888/score/task/show?classRoomId=" + classRoom,
    //url: "http://1.15.129.32:8080/score/task/show?classRoomId=1",
    //url: "test.json",
    striped: true, // 是否显示行间隔色
    pageNumber: 1, // 初始化加载第一页
    pagination: true, // 是否分页
    dataField: "data",
    sidePagination: 'client', // server:服务器端分页|client：前端分页
    height:600,
    sortable: true,
    search: true,
    showColumns: true, //筛选要显示的列
    showSearchClearButton: true, //显示搜索清除按钮
    pageSize: 10, // 单页记录数
    pageList: [10, 15],
    onLoadSuccess: function(){ //加载成功时执行
        layer.msg("加载成功");
    },
    beforeSend: function (XMLHttpRequest) {
        XMLHttpRequest.setRequestHeader("Token", localStorage.token);
    },
    onLoadError: function(){ //加载失败时执行
        layer.msg("加载数据失败", {time : 1500, icon : 2});
    },
    responseHandler:function(res){
        return res;
    },

    paginationLoop: true,
    paginationHAlign: 'left',
    paginationDetailHAlign: 'right',
    paginationPreText: '上一页',
    paginationNextText: '下一页',
    columns: [{
        checkbox: true
    }, {
        title: '作业ID',
        field: 'id',
        sort: true,
        sortable: true,
    }, {
        title: '作业名称',
        field: 'taskName',
        sort: true,
        sortable: true,
        searchable:true,
    }, {
        title: '创建时间',
        field: 'createTime',
        sortable: true,
    }, {
        title: '开始时间',
        field: 'begineTime',
        sortable: true,
    }, {
        title: '结束时间',
        field: 'deadline',
        sortable: true,
    } ,
        {
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            events: {
                'click #edit': function (e, value, row, index) {
                    editInfo(row.id);
                },
            },
            formatter: function (value, row, index) {
                var result = "";
                result += '<button id="edit" class="btn btn-info" data-toggle="modal" data-target="#editModal">查看</button>';
                return result;
            }
        }
    ]
});

$('#assistantTable').bootstrapTable({
    method: 'post',
    url: "http://1.15.129.32:8888/score/task/show?classRoomId=" + classRoom,
    striped: true, // 是否显示行间隔色
    pageNumber: 1, // 初始化加载第一页
    pagination: true, // 是否分页
    dataField: "data",
    sidePagination: 'client', // server:服务器端分页|client：前端分页
    height:600,
    sortable: true,
    search: true,
    showColumns: true, //筛选要显示的列
    showSearchClearButton: true, //显示搜索清除按钮
    pageSize: 10, // 单页记录数
    pageList: [10, 15],
    onLoadSuccess: function(){ //加载成功时执行
        layer.msg("加载成功");
    },
    beforeSend: function (XMLHttpRequest) {
        XMLHttpRequest.setRequestHeader("Token", localStorage.token);
    },
    onLoadError: function(){ //加载失败时执行
        layer.msg("加载数据失败", {time : 1500, icon : 2});
    },
    responseHandler:function(res){
        return res;
    },

    paginationLoop: true,
    paginationHAlign: 'left',
    paginationDetailHAlign: 'right',
    paginationPreText: '上一页',
    paginationNextText: '下一页',
    columns: [{
        checkbox: true
    }, {
        title: '作业ID',
        field: 'id',
        sort: true,
        sortable: true,
    }, {
        title: '作业名称',
        field: 'taskName',
        sort: true,
        sortable: true,
        searchable:true,
    }, {
        title: '创建时间',
        field: 'createTime',
        sortable: true,
    }, {
        title: '开始时间',
        field: 'begineTime',
        sortable: true,
    }, {
        title: '结束时间',
        field: 'deadline',
        sortable: true,
    } ,{
        field: 'operate',
        title: '操作',
        align: 'center',
        valign: 'middle',
        events: {
            'click #edit': function (e, value, row, index) {
                editInfo(row.id);
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

//查看信息
function editInfo(id) {
    $.ajax({
        url: "http://1.15.129.32:8888/score/task/details?id="+id,
        type:"post",
        dataType:"JSON",
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success:function(data){
            if (data.code == "200"){
                window.location.href="showdetails.html?id="+id;
            }else {
                alert(data.message);
            }
        }
    });
}

// 删除信息
function deleteInfo(id) {
    // $.ajax({
    //     type: 'post',
    //     url: 'test2.json',
    //     dataType: 'json',
    //     data: {
    //         id: id
    //     },
    //     success: function (data) {
    //         if (data == 'Yes') {
    //             $('#table').bootstrapTable('refresh');
    //         }
    //         else {
    //             alert('删除失败');
    //         }
    //     }
    // });
}