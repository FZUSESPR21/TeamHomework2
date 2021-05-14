var url;
function downLoadExcel() {
    window.open(url);
}


$.ajax({
    // url:"/assistant/task/add",
    url: "test.json",
    type:"post",
    dataType:"JSON",
    success:function(data){
        $("#assignment_title").val(data.title);
        $("#checklist").val(data.checklist);
        $("#make_up_date").val(data.make_up_date);
        $("#closing_date").val(data.closing_date);
        $("#proportion").val(data.proportion);
        $("#work_type").val(data.work_type);
        url = data.url;
    }
});
