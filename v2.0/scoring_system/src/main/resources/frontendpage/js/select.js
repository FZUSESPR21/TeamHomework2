

// function myFunction() { //如果有搜索按钮，定义一个onclick即可
//                         // 声明变量
//     var input, filter, table, tr, td, i;
//     input = document.getElementById("myInput");
//     filter = input.value.toUpperCase(); //toUpperCase()是不区分大小写
//     table = document.getElementById("myTable");
//     tr = table.getElementsByTagName("tr");
//
//     // 循环表格每一行，查找匹配项
//     for (i = 0; i < tr.length; i++) {
//         td = tr[i].getElementsByTagName("td")[0]; //这个0时要搜索第1列的内容，以此类推
//         if (td) {
//             if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {  //如果要区分大小写就去掉这个toUpperCase()
//                 tr[i].style.display = "";
//             } else {
//                 tr[i].style.display = "none";
//             }
//         }
//     }
// }

function myFunction() { //如果有搜索按钮，定义一个onclick即可
                        // 声明变量
    var input, filter, table, tr, td, td1, td3, td4, i;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase(); //toUpperCase()是不区分大小写
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");

    // 循环表格每一行，查找匹配项
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0]; //这个0时要搜索第1列的内容，以此类推
        td1 = tr[i].getElementsByTagName("td")[1];
        td3 = tr[i].getElementsByTagName("td")[3];
        td4 = tr[i].getElementsByTagName("td")[4];

        if (td || td1 || td3 || td4) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1 || td1.innerHTML.toUpperCase().indexOf(filter) > -1
                || td3.innerHTML.toUpperCase().indexOf(filter) > -1 || td4.innerHTML.toUpperCase().indexOf(filter) > -1) {  //如果要区分大小写就去掉这个toUpperCase()
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}





