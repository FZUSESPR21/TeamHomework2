$(document).ready(function () {
    var classRoomId = getToken("class");
    $.ajax({
        type: 'post',
        //url: 'http://1.15.129.32:8080/score/task/show?classRoomId=1',
        url: 'http://1.15.129.32:8888/score/task/show?classRoomId='+classRoomId,
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        dataType: 'json',
        success: function (data) {
            $.each(data.data, function (index, item) {
                $("#work_item").append("<option value='" + data.data[index].id + "'>" + data.data[index].taskName + "</option>");
            });
        }
    });
    //加载下拉列表的内容
    $.ajax({
        type: 'post',
        url: 'http://1.15.129.32:8888/score/class/showlist',
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("Token", localStorage.token);
        },
        dataType: 'json',
        success: function(data){
            $.each(data.data,function (index,item) {
                $("#classes").append("<option value='"+data.data[index].id+"'>"+data.data[index].className+"</option>");
            });
        }
    });
});