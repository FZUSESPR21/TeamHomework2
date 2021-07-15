var classRoom = getToken("class");
$('#myTable').bootstrapTable({
    method: 'post',
    url: serviceIp + '/teacher/assistant/show',
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
    contentType: "application/x-www-form-urlencoded",//必须要有！！！！
    queryParamsType : "undefined",
    queryParams: function queryParams(params) { //设置查询参数
        var param = {
            classRoomId: getToken("class"),
        };
        return param;
    },
    ajaxOptions:{
        headers: {"Token":getToken("token")}
    },
    onLoadError: function(){ //加载失败时执行
        layer.msg("加载数据失败", {time : 1500, icon : 2});
    },
    responseHandler:function(res){
        return res.data;
    },

    paginationLoop: true,
    paginationHAlign: 'left',
    paginationDetailHAlign: 'right',
    paginationPreText: '上一页',
    paginationNextText: '下一页',
    columns: [{
        checkbox: true
    }, {
        title: '账号',
        field: 'account',
        sort: true,
        sortable: true,
    }, {
        title: '姓名',
        field: 'userName',
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
                    editInfo(row.id);
                },
                'click #delete': function (e, value, row, index) {
                    deleteInfo(row.id);
                }
            },
            formatter: function (value, row, index) {
                var result = "";
                // result += '<button id="edit" class="btn btn-info" data-toggle="modal" data-target="#editModal">查看</button>';
                //result += '<button id="delete" class="btn btn-info" style="margin-left:1%;">删除</button>';
                return result;
            }
        }
    ]
});