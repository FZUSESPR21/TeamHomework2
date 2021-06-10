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
        url: 'http://1.15.129.32:8888/score/blogwork/details?id='+id,
        //url: 'http://1.15.129.32:8080/score/blogwork/details?id=8',
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

// $(document).ready(function () {
//     //加载下拉列表的内容
//     $.ajax({
//         type: 'get',
//         url: 'test1.json',
//         dataType: 'json',
//         success: function(data){
//             $.each(data,function (index,item) {
//                 if (data[index].id){
//                     $("#student").append("<option value='"+data[index].id+"'>"+data[index].id+data[index].name+"</option>");
//                 }
//                 if (data[index].work_id){
//                     $("#work").append("<option value='"+data[index].work_id+"'>"+data[index].work_name+"</option>");
//                 }
//             });
//         }
//     });
// });
//
// //下拉选项改变时
// document.getElementById('student').onchange=function(){
//     var student_id = $("#student").find("option:selected").val();
//     var work_id = $("#work").find("option:selected").val();
//     $.ajax({
//         type: 'post',
//         url: 'test2.json',
//         dataType: 'json',
//         data: {
//             student_id: student_id,
//             work_id: work_id,
//         },
//         success: function(data){
//             if (data == 'Yes') {
//                 $('#myTable').bootstrapTable('refresh');
//             }
//             else {
//                 alert('查询不到该学生！');
//             }
//         }
//     });
// };
//
// document.getElementById('work').onchange=function(){
//     var student_id = $("#student").find("option:selected").val();
//     var work_id = $("#work").find("option:selected").val();
//     $.ajax({
//         type: 'post',
//         url: 'test2.json',
//         dataType: 'json',
//         data: {
//             student_id: student_id,
//             work_id: work_id,
//         },
//         success: function(data){
//             if (data == 'Yes') {
//                 $('#myTable').bootstrapTable('refresh');
//             }
//             else {
//                 alert('查询不到该学生！');
//             }
//         }
//     });
// };

//加载表根据博客id
$('#myTable').bootstrapTable({
    method: 'post',
    url: "http://1.15.129.32:8888/score/blogwork/details?id="+id, // 请求路径
    //url: "http://1.15.129.32:8080//score/blogwork/details?id=12",
    //url: "test.json",
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
    beforeSend: function (XMLHttpRequest) {
        XMLHttpRequest.setRequestHeader("Token", localStorage.token);
    },
    onLoadError: function(){ //加载失败时执行
        layer.msg("加载数据失败", {time : 1500, icon : 2});
    },
    responseHandler:function(res){
        //在ajax获取到数据，渲染表格之前，修改数据源
        //该项返回的为数据源内的二级列表data
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