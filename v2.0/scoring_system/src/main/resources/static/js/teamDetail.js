//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
var id = getUrlParam('id');

//加载表
$('#myTable').bootstrapTable({
    method: 'get',
    url: serviceIp + "/score/teamUser/show?id="+id, // 请求路径
    striped: true, // 是否显示行间隔色
    pageNumber: 1, // 初始化加载第一页
    pagination: true, // 是否分页
    sidePagination: 'client', // server:服务器端分页|client：前端分页
    height:600,
    width:800,
    sortable: true,
    pageSize: 10, // 单页记录数
    pageList: [10, 15],
    paginationLoop: true,
    paginationHAlign: 'left',
    paginationDetailHAlign: 'right',
    paginationPreText: '上一页',
    paginationNextText: '下一页',
    ajaxOptions:{
        headers: {"Token":getToken("token")}
    },
    responseHandler:function(res){
        if (res.code == "200"){
            return res.data;
        }else {
            layer.msg(res.message);
            return false;
        }
    },
    columns: [{
        field: 'account',
        title: '成员学号',
        sortable: true,

    }, {
        title: '成员姓名',
        field: 'userName',
        sortable: true,
    }

    ]
});