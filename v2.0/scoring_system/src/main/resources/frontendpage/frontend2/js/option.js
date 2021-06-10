var row = 0 ; //定义全局行数用于修改

//----获取行号-----
function getRow(r){
    var i=r.parentNode.parentNode.rowIndex;
    return i ;
}

//----删除某一行-----
function delRow(r){
    document.getElementById('myTable').deleteRow(getRow(r));
}

//----清除添加信息框的内容-----
function cleanAddInput(){
    document.getElementById('paperId').value='';
    document.getElementById('paperTitle').value='';
    document.getElementById('paperLink').value='';
    document.getElementById('paperKey').value='';
    document.getElementById('paperSummer').value='';
}

//----显示添加信息框-----
function showAddInput(){
    document.getElementById('addinfo').style="display:block-inline" ;
    document.getElementById('btn_add').style="display:block-inline" ;
    document.getElementById('btn_update').style="display:none" ;
    cleanAddInput();
}

//----隐藏添加信息框-----
function hideAddInput(){
    document.getElementById('addinfo').style="display:none" ;
}

//----新增信息的插入方法-----
function insertInfo(){
    //根据id获取表单信息
    var arr = new Array();
    arr[0] = document.getElementById('paperId').value;
    arr[1] = document.getElementById('paperTitle').value;
    arr[2] = document.getElementById('paperLink').value;
    arr[3] = document.getElementById('paperKey').value;
    arr[4] = document.getElementById('paperSummer').value;
    arr[5] ="<a style='text-align:center;color:blue;cursor:pointer;' onclick='updateRow(this);'>修改</a>" +
        "&nbsp<a style='text-align:center;color:blue;cursor:pointer;' onclick='delRow(this);'>删除</a>";
    var x = document.getElementById('myTable').insertRow(1); //获取第一行对象

    for(var i=0;i<arr.length;i++){
        x.insertCell(i).innerHTML = arr[i] ; //用循环把每个数据插入第一行的每一列
    }

}

//----新增信息-----
function addInfo(){
        insertInfo(); //执行插入
        hideAddInput(); //隐藏添加信息框
}

//----根据行号修改信息-----
function updateRow(r){
    row = getRow(r); //把该行号赋值给全局变量
    showAddInput(); //显示修改表单
    //提交按钮替换
    document.getElementById('btn_add').style="display:none" ;
    document.getElementById('btn_update').style="display:block-inline" ;
    insertInputFromQuery(queryInfoByRow(row));
}

//----根据行号查信息----
function queryInfoByRow(r){
    var arr = new Array();
    for(var m = 0 ; m<5; m ++){
        arr[m] = document.getElementById('myTable').rows[row].cells[m].innerText;
    }
    return arr ; //返回该行数据
}

//----把查询到的信息放入修改的表单里----
function insertInputFromQuery(arr){
    document.getElementById('paperId').value = arr[0];
    document.getElementById('paperTitle').value = arr[1];
    document.getElementById('paperLink').value = arr[2];
    document.getElementById('paperKey').value = arr[3];
    document.getElementById('paperSummer').value = arr[4];
}

//----更新信息----
function updateInfo(){
        document.getElementById('myTable').deleteRow(row);//删除原来那行
        insertInfo(); //插入修改后的值
        hideAddInput(); //隐藏添加模块
}