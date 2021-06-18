//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
var id = getUrlParam('id');

function updateOnClick(){
    $.ajax({
        type: 'post',
        url: serviceIp + '/score/blogwork/details?id='+id,
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success: function(data){
            if (data.code == "200") {
                window.location.href="assignmentScore.html?id="+id;
            }
            else {
                alert('您没有该权限！');
            }
        }
    });
}

//加载表根据博客id
$('#myTable').bootstrapTable({
    method: 'post',
    url: serviceIp + "/score/blogwork/details?id="+id, // 请求路径
    striped: true, // 是否显示行间隔色
    pageNumber: 1, // 初始化加载第一页
    pagination: true, // 是否分页
    sidePagination: 'client', // server:服务器端分页|client：前端分页
    height:600,
    dataField: "detailsDataList",
    pageSize: 10, // 单页记录数
    onLoadSuccess: function(){ //加载成功时执行
        layer.msg("加载成功");
    },
    ajaxOptions:{
        headers: {"Token":getToken("token")}
    },
    onLoadError: function(){ //加载失败时执行
        layer.msg("加载数据失败", {time : 1500, icon : 2});
    },
    responseHandler:function(res){
        return res.data.score;
    },

    paginationLoop: true,
    paginationHAlign: 'left',
    paginationDetailHAlign: 'right',
    paginationPreText: '上一页',
    paginationNextText: '下一页',
    columns: [
        {
            title: '分数项',
            field: 'detailsName'
        },{
            title: '得分',
            field: 'score',
            formatter: function (value, row, index) {
                if (value == null || value == undefined) {
                    return "0";
                } else {
                    return value;
                }
            },
        }
    ]
});