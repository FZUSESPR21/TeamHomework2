function loadList(url) {
    $('#myTable').bootstrapTable({
        method: 'post',
        url: url,
        striped: true, // 是否显示行间隔色
        pageNumber: 1, // 初始化加载第一页
        pagination: true, // 是否分页
        dataField: "list",
        height:600,
        sortable: true,
        //search: true,
        dataType: 'json',
        paginationShowPageGo: true,
        showJumpto: true,
        showColumns: true, //筛选要显示的列
        showSearchClearButton: true, //显示搜索清除按钮
        pageSize: 10, // 单页记录数
        pageList: [10, 15, 20],
        sidePagination: "server", //表示服务端请求
        contentType: "application/x-www-form-urlencoded",//必须要有！！！！
        queryParamsType : "undefined",
        ajaxOptions:{
            headers: {"Token":getToken("token")}
        },
        queryParams: function queryParams(params) { //设置查询参数
            var options = $("#work_item option:selected").val();
            var classId = $("#classes option:selected").val();
            if (classId == null || classId === ""){
                classId = getToken("class");
            }
            var param = {
                pageNum: params.pageNumber,
                pageSize: params.pageSize,
                id: options,
                classRoomId: classId,
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
            title: '博客ID',
            field: 'id',
            sort: true,
            align: 'center',
            sortable: true,
            searchable:true,
        },{
            title: '博客链接',
            field: 'blogUrl',
            formatter: function (value, row, index) {
                var result = "";
                result += '<a style="color: #0a53be" href="'+value+'">原文链接</a>';
                return result;
            },
            align: 'center',
        }, {
            title: '博客名',
            field: 'blogWorkName',
            sort: true,
            align: 'center',
            sortable: true,
            searchable:true,
        },  {
            title: '博客状态',
            field: 'isMark',
            align: 'center',
            formatter: function (value, row, index) {
                if (value == 0) {
                    var a = '<p style="color: #9c3328;font-size: 15px"> 未评分</p>';
                    return a;
                } else {
                    var a = '<p style="color: #0f5132;font-size: 15px"> 已评分</p>';
                    return a;
                }
            }
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            events: {
                'click #edit': function (e, value, row, index) {
                    //editInfo(row.id);
                    window.open('assignmentScore.html?id='+row.id);
                },
            },
            formatter: function (value, row, index) {
                var result = "";
                result += '<button id="edit" class="btn btn-info" data-toggle="modal" data-target="#editModal">查看博客</button>';
                return result;
            }
        }
        ]
    });
}

$(document).ready(function () {
    loadList(serviceIp + "/score/blogwork/all/showlist");
});

//下拉选项改变时
document.getElementById('classes').onchange=function(){
    var options = $("#work_item option:selected").val();
    var classId = $("#classes option:selected").val();
    if (options != "" && classId != ""){
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        loadList(serviceIp + "/score/blogwork/showlist");
    }else {
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        loadList(serviceIp + "/score/blogwork/all/showlist");
    }
};

//下拉选项改变时
document.getElementById('work_item').onchange=function(){
    var options = $("#work_item option:selected").val();
    var classId = $("#classes option:selected").val();
    if (options != "" && classId != ""){
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        loadList(serviceIp + "/score/blogwork/showlist");
    }else {
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        loadList(serviceIp + "/score/blogwork/all/showlist");
    }
};