var url;
function downLoadExcel() {
    // window.open(url);
    box.style.display = 'flex';
    hidden.style.display = 'block';
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
var id = getUrlParam('id');

$.ajax({
    url: serviceIp + "/score/task/details?id="+id,
    type:"post",
    dataType:"JSON",
    beforeSend: function (XMLHttpRequest) {
        XMLHttpRequest.setRequestHeader("Token", localStorage.token);
    },
    success:function(data){
        $("#assignment_title").val(data.data.taskName);
        $("#checklist").val(data.data.taskContent);
        $("#make_up_date").val(data.data.makeUpTime.substr(0,10));
        $("#closing_date").val(data.data.deadline.substr(0,10));
        $("#proportion").val(data.data.ratio*100);
        $("#work_type").val(data.data.taskType);
        console.log(data.data.taskType + ";" +data.data.makeUpTime +data.data.deadline);
        // url = data.url;
        $.each(data.data.detailsData,function (index,item) {
            $("#box").append("<span>"+data.data.detailsData[index].detailsName+"</span>");
        });
        if (data.data.detailsData.length <1){
            console.log(1);
            $("#down_load_excel").attr("class","layui-btn layui-btn-disabled");
            $("#down_load_excel").attr("onclick","");
        }

    }
});


var open = document.getElementById('open');
var box = document.getElementById('box');
var hidden = document.getElementById('hidden');
var close = document.getElementById('close');
var i = 1;

close.onclick = function () {
    box.style.display = 'none';
    hidden.style.display = 'none';
    // 关闭后恢复box到原来的默认位置
};

function getPage(e) {
    var pageX = e.pageX || e.clientX + getScroll().scrollLeft;
    var pageY = e.pageY || e.clientY + getScroll().scrollTop;
    return {
        pageX: pageX,
        pageY: pageY
    }
}

box.onmousedown = function (e) {
    e = e || window.event;
    // 盒子的位置
    var x = getPage(e).pageX - box.offsetLeft;
    var y = getPage(e).pageY - box.offsetTop;
    document.onmousemove = function (e) {
        e = e || window.event;
        box.style.left = getPage(e).pageX - x + 'px';
        box.style.top = getPage(e).pageY - y + 'px';
    }
};

document.onmouseup = function () {
    document.onmousemove = null;
};