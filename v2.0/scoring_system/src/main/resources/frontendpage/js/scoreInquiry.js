var classRoomId = getToken("class");

function loadWork(type){
    var classRoomId = getToken("class");
    //加载下拉列表的内容
    $.ajax({
        type: 'post',
        url: serviceIp + '/score/task/show?classRoomId='+classRoomId,
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success: function(data){
            document.getElementById("work_item").innerHTML="";
            $.each(data.data,function (index,item) {
                if (data.data[index].taskType == type){
                    $("#work_item").append("<option value='"+data.data[index].id+"'>"+data.data[index].taskName+"</option>");
                }
            });
        }
    });
}
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
    $("#work_item").attr("style","display: none");
    sum_of_score(classRoomId);

});

function setDropList() {
    $("#class_room").attr("style","display: none");
    $("#work_item").attr("style","");
    $("#work_item").append("<option value=\"\">请选择作业</option>");
}

//下拉选项改变时(作业类型)
document.getElementById('work_type').onchange=function(){
    var userId = getToken("id");

    var work_type_id = $("#work_type").find("option:selected").val();
    if (work_type_id == 1){
        setDropList();
        loadWork("个人作业");
    }else if (work_type_id == 2){
        setDropList();
        loadWork("结对作业");
    }else if (work_type_id == 3){
        setDropList();
        loadWork("团队作业");
    }else if (work_type_id == 4){
        var classId = $("#class_room").find("option:selected").val();
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        sum_of_score(classId);
        $("#work_item").attr("style","display: none");
        $("#class_room").attr("style","");
    }else {
        alert("请选择正确的作业类型!");
    }
};

//下拉选项改变时(作业项)
document.getElementById('work_item').onchange=function(){
    var work_type_id = $("#work_type").find("option:selected").val();
    if (work_type_id == 1){
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        personal_score();
    }else if (work_type_id == 2){
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        pair_score();
    }else {
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        team_score();
    }
};

document.getElementById('class_room').onchange =function () {
    var classId = $("#class_room").find("option:selected").val();
    if (classId == 0){
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        sum_of_score(classRoomId);
    }else {
        document.getElementById("table_show").innerHTML="";
        $("#table_show").append("<table id=\"myTable\" class=\"table table-hover text-nowrap\"></table>");
        sum_of_score(classId);
    }
};

//个人作业成绩
function personal_score() {
    $('#myTable').bootstrapTable({
        method: 'post',
        url: serviceIp + "/score/task_class_blogwork_score/list",
        striped: true, // 是否显示行间隔色
        pageNumber: 1, // 初始化加载第一页
        pagination: true, // 是否分页
        height:600,
        sortable: true,
        search: true,
        showColumns: true, //筛选要显示的列
        showSearchClearButton: true, //显示搜索清除按钮
        pageSize: 10, // 单页记录数
        pageList: [10, 15, 20],
        sidePagination: "client",
        contentType: "application/x-www-form-urlencoded",//必须要有！！！！
        queryParamsType : "undefined",

        ajaxOptions:{
            headers: {"Token":getToken("token")}
        },

        queryParams: function queryParams(params) { //设置查询参数
            var id = $("#work_item").find("option:selected").val();
            var param = {
                pageNum: params.pageNumber,
                pageSize: params.pageSize,
                id: id,
                classRoomId: classRoomId,
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
                var userInfo = res.data;
                var NewData = [];
                if (userInfo.length){
                    for (var i = 0; i < userInfo.length; i++){
                        var dataNewObj = {
                            "id": '',
                            "username": '',
                            "classId": '',
                            "score": '',
                        };
                        dataNewObj.id = userInfo[i].user.account;
                        dataNewObj.username = userInfo[i].user.userName;
                        dataNewObj.classId = userInfo[i].user.classRoom.className;
                        dataNewObj.score = userInfo[i].score.score;
                        NewData.push(dataNewObj);
                    }
                    console.log(NewData);
                }
                var data = {
                    rows:NewData,
                };
                return data;
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
            field: 'id',
            sort: true,
            sortable: true,
            searchable:true,
        }, {
            title: '姓名',
            field: 'username',
            sortable: true,
            searchable:true,
        }, {
            title: '班级',
            field: 'classId',
            sortable: true,
        }, {
            title: '分数',
            sortable: true,
            field: 'score',
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            events: {
                'click #edit': function (e, value, row, index) {

                    window.open('itemDetails.html?id='+row.id);
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

//结对作业成绩
function pair_score() {
    $('#myTable').bootstrapTable({
        method: 'post',
        url: serviceIp + "/score/task_class_blogwork_score/list",
        striped: true, // 是否显示行间隔色
        pageNumber: 1, // 初始化加载第一页
        pagination: true, // 是否分页
        height:600,
        sortable: true,
        search: true,
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
            var id = $("#work_item").find("option:selected").val();
            var param = {
                pageNum: params.pageNumber,
                pageSize: params.pageSize,
                id: id,
                classRoomId: classRoomId,
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
            title: '组号',
            field: 'team',
            sort: true,
            sortable: true,
            searchable:true,
            formatter: function (value, row, index) {
                if (value.id == null || value.id == undefined) {
                    return "";
                } else {
                    return value.id;
                }
            },
        }, {
            title: '组名',
            field: 'team',
            sortable: true,
            searchable:true,
            formatter: function (value, row, index) {
                if (value.sysTeamName == null || value.sysTeamName == undefined) {
                    return "";
                } else {
                    return value.sysTeamName;
                }
            },
        }, {
            title: '班级',
            field: 'team',
            sortable: true,
            formatter: function (value, row, index) {
                if (value.classRoomId == null || value.classRoomId == undefined) {
                    return "";
                } else {
                    return value.classRoomId;
                }
            },
        }, {
            title: '分数',
            sortable: true,
            field: 'score',
            formatter: function (value, row, index) {
                if (value.score == null || value.score == undefined) {
                    return "";
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

                    window.open('itemDetails.html?id='+row.id);
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

//团队作业成绩
function team_score() {
    $('#myTable').bootstrapTable({
        method: 'post',
        url: serviceIp + "/score/task_class_blogwork_score/list",
        striped: true, // 是否显示行间隔色
        pageNumber: 1, // 初始化加载第一页
        pagination: true, // 是否分页
        height:600,
        sortable: true,
        search: true,
        showColumns: true, //筛选要显示的列
        showSearchClearButton: true, //显示搜索清除按钮
        pageSize: 10, // 单页记录数
        pageList: [10, 15, 20],
        sidePagination: "client",
        contentType: "application/x-www-form-urlencoded",//必须要有！！！！
        queryParamsType : "undefined",
        ajaxOptions:{
            headers: {"Token":getToken("token")}
        },
        queryParams: function queryParams(params) { //设置查询参数
            var id = $("#work_item").find("option:selected").val();
            var param = {
                pageNum: params.pageNumber,
                pageSize: params.pageSize,
                id: id,
                classRoomId: classRoomId,
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
                var userInfo = res.data;
                var NewData = [];
                if (userInfo.length){
                    for (var i = 0; i < userInfo.length; i++){
                        var dataNewObj = {
                            "id": '',
                            "teamName": '',
                            "className": '',
                            "score": '',
                        };
                        dataNewObj.id = userInfo[i].team.id;
                        dataNewObj.teamName = userInfo[i].team.sysTeamName;
                        dataNewObj.className = userInfo[i].team.classRoom.className;
                        dataNewObj.score = userInfo[i].score.score;
                        NewData.push(dataNewObj);
                    }
                    console.log(NewData);
                }
                var data = {
                    rows:NewData,
                };
                return data;
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
            title: '组号',
            field: 'id',
            sort: true,
            sortable: true,
            searchable:true,
        }, {
            title: '组名',
            field: 'teamName',
            sortable: true,
            searchable:true,
        }, {
            title: '班级',
            field: 'className',
            sortable: true,
        }, {
            title: '分数',
            sortable: true,
            field: 'score',
            formatter: function (value, row, index) {
                if (value == null || value == undefined) {
                    return "";
                } else {
                    return value;
                }
            },
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            events: {
                'click #edit': function (e, value, row, index) {

                    window.open('itemDetails.html?id='+row.id);
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

//总成绩
function sum_of_score(classRoomId) {
    $('#myTable').bootstrapTable({
        method: 'post',
        url: serviceIp + "/user/show/class?classId="+classRoomId,
        striped: true, // 是否显示行间隔色
        pageNumber: 1, // 初始化加载第一页
        pagination: true, // 是否分页
        height:600,
        sortable: true,
        search: true,
        showColumns: true, //筛选要显示的列
        showSearchClearButton: true, //显示搜索清除按钮
        pageSize: 10, // 单页记录数
        pageList: [10, 15, 20],
        sidePagination: "client",
        contentType: "application/x-www-form-urlencoded",//必须要有！！！！
        queryParamsType : "undefined",
        ajaxOptions:{
            headers: {"Token":getToken("token")}
        },
        queryParams: function queryParams(params) { //设置查询参数
            var id = $("#work_item").find("option:selected").val();
            var param = {
                pageNum: params.pageNumber,
                pageSize: params.pageSize,
                id: id,
                classRoomId: classRoomId,
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
            searchable:true,
        }, {
            title: '姓名',
            field: 'userName',
            sortable: true,
            searchable:true,
        }, {
            title: '班级',
            field: 'classId',
        }, {
            title: '总分数',
            sortable: true,
            field: 'totalScore',
        }
        ]
    });
}
