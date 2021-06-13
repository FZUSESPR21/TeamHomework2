function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
var id = getUrlParam('id');
var i = 1;

//加载评分项
$(function() {
    $.ajax({
        url: 'http://1.15.129.32:8888/score/blogwork/details?id='+id,
        type: 'post',
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success: function(data){
            // alert(data.data.id);
            // alert(data.data.blogWorkContent);
            $("#blog_id").val(data.data.id);
            $("#task_id").val(data.data.task.id);
            $("#blog_content").text(data.data.blogWorkContent);
            console.log(data.data.score.detailsDataList);
            var testView = editormd.markdownToHTML("test-markdown-view", {
            });
            $.each(data.data.score.detailsDataList,function (index,item) {
                //oninput="if(value>10)value=10" 最大值
                //placeholder 提示文字
                //value='"+data[index].score+"' 设置初始分数
                if (data.data.score.detailsDataList[index].id){
                    $("#scoring_item").append("<label class='item_label'>"+data.data.score.detailsDataList[index].detailsName+"</label>");
                    $("#scoring_item").append("</br>");
                    if(data.data.score.detailsDataList[index].score != null){
                        $("#scoring_item").append(" <input class='score_input' id='"+i+"' type=\"number\" value='"+data.data.score.detailsDataList[index].score+"' name='"+data.data.score.detailsDataList[index].id+"'" +
                            " placeholder='"+"总分"+100+"分"+"' " + "oninput=\"if(value>" +100 +")value="+100+"\""+
                            "onkeyup=\"if(isNaN(value))execCommand('undo')\" onafterpaste=\"if(isNaN(value))execCommand('undo')\">");
                    }else{
                        $("#scoring_item").append(" <input class='score_input' id='"+i+"' type=\"number\" value='' name='"+data.data.score.detailsDataList[index].id+"'" +
                            " placeholder='"+"总分"+100+"分"+"' " + "oninput=\"if(value>" + 100 +")value="+100+"\""+
                            "onkeyup=\"if(isNaN(value))execCommand('undo')\" onafterpaste=\"if(isNaN(value))execCommand('undo')\">");
                    }
                    $("#scoring_item").append("</br>");
                    i=i+1;
                }
            });
        }
    });
});

//提交按钮点击事件
function submitOnClick() {
    var t = 0;
    var itemsScore = new Array();
    for (var m=1; m<i; m++){
        var k = document.getElementById(m.toString()).value;
        var j = document.getElementById(m.toString()).name;
        var data2={
            score: k,
            id : j
        };
        itemsScore[t++] = data2;
    }
    var blog_id = $("#blog_id").val();

    console.log(blog_id);
    var data1={
        "blogWorkId": blog_id,
        "detailsDatas": itemsScore,
    };

    var data = JSON.stringify(data1);
    console.log(data);

    $.ajax({
        type: 'post',
        url: 'http://1.15.129.32:8888/score/blogwork/scoring',
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        contentType: "application/json",
        data: JSON.stringify(data1),
        success: function(data){
            layer.msg(data.message);
        }
    });
}

//下一篇博客点击
function nextOnClick() {
    var blog_id = $("#blog_id").val();
    var task_id = $("#task_id").val();
    var classRoom = getToken("class");
    $.ajax({
        type: 'post',
        url: "http://1.15.129.32:8888/score/blogwork/details/next?id="+task_id+"&classRoomId="+classRoom+"&blogWorkId="+blog_id,
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success: function(data){
            if (data.code == '200') {
                if (data.data != null){
                    window.location.href='assignmentScore.html?id='+ data.data.id;
                }else {
                    layer.msg("已经是最后一篇博客了~");
                }
            }else {
                layer.msg(data.message);
            }
        }
    });
}

//上一篇博客点击
function lastOnClick() {
    var blog_id = $("#blog_id").val();
    var task_id = $("#task_id").val();
    var classRoom = getToken("class");

    $.ajax({
        type: 'post',
        url: "http://1.15.129.32:8888/score/blogwork/details/previous?id="+task_id+"&classRoomId="+classRoom+"&blogWorkId="+blog_id,
        dataType: 'json',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        success: function(data){
            if (data.code == '200') {
                if (data.data != null){
                    window.location.href='assignmentScore.html?id='+ data.data.id;
                }else {
                    layer.msg("已经是第一篇博客了~");
                }
            }else {
                alert(data.message);
            }
        }
    });
}