var open = document.getElementById('open');
var box = document.getElementById('box');
var hidden = document.getElementById('hidden');
var close = document.getElementById('close');
var i = 1;

$(function() {
    var editor = editormd("editor", {
        width: "100%",
        height: "500px",
        // markdown: "xxxx",     // dynamic set Markdown text
        path : "../editormd/lib/"  // Autoload modules mode, codemirror, marked... dependents libs path
    });
});

close.onclick = function () {
    box.style.display = 'none';
    hidden.style.display = 'none';
};

function getPage(e) {
    var pageX = e.pageX || e.clientX + getScroll().scrollLeft;
    var pageY = e.pageY || e.clientY + getScroll().scrollTop;
    return {
        pageX: pageX,
        pageY: pageY
    }
}

// box.onmousedown = function (e) {
//     e = e || window.event;
//     // 盒子的位置
//     var x = getPage(e).pageX - box.offsetLeft;
//     var y = getPage(e).pageY - box.offsetTop;
//     document.onmousemove = function (e) {
//         e = e || window.event;
//         box.style.left = getPage(e).pageX - x + 'px';
//         box.style.top = getPage(e).pageY - y + 'px';
//     }
// }
//
// document.onmouseup = function () {
//     document.onmousemove = null;
// }



function loadWork(type){
    var classRoomId = getToken("class");
    //加载下拉列表的内容
    $.ajax({
        type: 'post',
        //url: 'http://1.15.129.32:8080/score/task/show?classRoomId=1',
        url: 'http://1.15.129.32:8888/score/task/show?classRoomId='+classRoomId,
        dataType: 'json',
        success: function(data){
            $.each(data.data,function (index,item) {
                if (data.data[index].taskType == type){
                    $("#work").append("<option value='"+data.data[index].id+"'>"+data.data[index].taskName+"</option>");
                }
            });
        }
    });
}

$(document).ready(function () {
    loadWork("个人作业");
    var teamId = getToken("group");
    $.ajax({
        type: 'post',
        url: 'http://1.15.129.32:8888/score/teamUser/show?id='+teamId,
        //url: 'http://1.15.129.32:8080/score/teamUser/show?id=1',
        //url: 'test.json',
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success: function(data){
            //$("#work option").remove();

            $.each(data.data,function (index,item) {
                // if (data[index].work_id){
                //     $("#work").append("<option value='"+data[index].work_id+"'>"+data[index].work_name+"</option>");
                // }
                // if (data.data[index].id){
                //
                // }
                $("#box").append("<span>" +data.data[index].account+": "+ "<input id='"+i+"' name='"+data.data[index].account+"' class=\"input\" type=\"text\" placeholder=\"输入分数\"\n" +
                    "onkeyup=\"if(isNaN(value))execCommand('undo')\" onafterpaste=\"if(isNaN(value))execCommand('undo')\">\n" +
                    "</span>");
                i=i+1;
            });
            $("#box").append("<input type=\"button\" value=\"提交\" id=\"submit\" onclick=\"submitTeam()\">");
        }
    });
});

//下拉选项改变时
document.getElementById('work_type').onchange=function(){
    var userId = getToken("id");

    var work_type_id = $("#work_type").find("option:selected").val();
    if (work_type_id == 1){
        document.getElementById("work").innerHTML="";
        loadWork("个人作业");
        console.log(1);
        document.getElementById('team').style.display = 'none';
        document.getElementById('pair_member').style.display = 'none';
        document.getElementById('personal_id').style.display = 'block';
    }else if (work_type_id == 2){
        document.getElementById("work").innerHTML="";
        loadWork("结对作业");
        console.log(2);
        document.getElementById('team').style.display = 'none';
        document.getElementById('pair_member').style.display = 'block';
        document.getElementById('personal_id').style.display = 'none';
    }else {
        document.getElementById("work").innerHTML="";
        loadWork("团队作业");
        console.log(3);
        document.getElementById('pair_member').style.display = 'none';
        //document.getElementById('team').style.display = 'block';
        document.getElementById('personal_id').style.display = 'none';
        //$("#team").display = true;
    }
};




function submitOnClicked() {

    var work_id = $("#work").find("option:selected").val();
    var work_type_id = $("#work_type").find("option:selected").val();
    var work_name = $("#blog_work_name").val();
    var blog_content = $("#blog_content").val();
    var blog_link = $("#blog_link").val();
    var student_id = $("#student").val();
    var student_id1 = $("#student1").val();
    var student_id2 = $("#student2").val();
    var userId = getToken("id");
    var userAccount = getToken("account");

    var reg=/^http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- ./?%&=]*)?$/;
    if(!reg.test(blog_link)){
        alert('输入正确的链接');
        return false;
    }
    // var team_id = $("#team").find("option:selected").val();
    if (work_type_id == 1){
        console.log(1);
        var data1={
            taskId: work_id,
            blogWorkContent: blog_content,
            blogurl: blog_link,
            userId: userId,
            blogWorkName: work_name,
            contributionList: [
                {
                    account: userAccount,
                    ratio: 100,
                }
            ]
        };
        var data =JSON.stringify(data1);
        $.ajax({
            type: 'post',
            url: 'http://1.15.129.32:8888/score/blogwork/submit',
            dataType: 'json',
            beforeSend: function (XMLHttpRequest) {
                XMLHttpRequest.setRequestHeader("Token", localStorage.token);
            },
            contentType: "application/json",
            data: data,
            success: function(data){
                layer.msg(data.message);
            }
        });
    }else if (work_type_id == 2){
        console.log(2);
        var data1={
            taskId: work_id,
            blogWorkContent: blog_content,
            blogurl: blog_link,
            userId: userId,
            blogWorkName: work_name,
            contributionList: [
                {
                    account: "s"+student_id1,
                    ratio: 100,
                },
                {
                    account: "s"+student_id2,
                    ratio: 100,
                }
            ]
        };
        var data=JSON.stringify(data1);
        $.ajax({
            type: 'post',
            url: 'http://1.15.129.32:8888/score/blogwork/submit',
            dataType: 'json',
            beforeSend: function (XMLHttpRequest) {
                XMLHttpRequest.setRequestHeader("Token", localStorage.token);
            },
            contentType: "application/json",
            data: data,
            success: function(data){
                layer.msg(data.message);
            }
        });
    }else {
        console.log(3);
        box.style.display = 'flex';
        // hidden.style.display = 'block';
    }
}
function submitTeam() {

    var work_id = $("#work").find("option:selected").val();
    var work_name = $("#blog_work_name").val();
    var blog_content = $("#blog_content").val();
    var blog_link = $("#blog_link").val();
    var userId = getToken("id");
    var t = 0;
    var sum = 0.0;
    var contributionList = new Array();
    for (var m=1; m<i; m++){
        var k = document.getElementById(m.toString()).value;
        var j = document.getElementById(m.toString()).name;
        sum = sum + parseFloat(k);
        if (k == ""){
            alert("评分项不能为空");
            return false;
        }
        var data2={
            "account": j,
            "ratio": k
        };
        contributionList[t++] = data2;
    }
    console.log("sum:" + sum);
    if (sum != 100.0){
        alert("评分和必须为100");
        return false;
    }
    console.log(contributionList);

    var data1={
        "userId": userId,
        "taskId": work_id,
        "blogWorkName": work_name,
        "blogWorkContent": blog_content,
        "blogurl": blog_link,
        "contributionList": contributionList,
    };

    var data = JSON.stringify(data1);
    console.log(data);
    $.ajax({
        type: 'post',
        contentType: "application/json",
        url: 'http://1.15.129.32:8888/score/blogwork/submit',
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        data: data,
        success: function(data){
            if(data.code == 200){
                layer.msg(data.message);
            }
        }
    });
}