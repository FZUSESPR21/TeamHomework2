function manageClicked() {
    document.getElementById("content-manage").style.display="block";
    document.getElementById("user-manage").style.display="none";
    document.getElementById("statistical-analysis").style.display="none";
}
function userClicked() {
    document.getElementById("content-manage").style.display="none";
    document.getElementById("user-manage").style.display="block";
    document.getElementById("statistical-analysis").style.display="none";
}
function analysisClicked() {
    document.getElementById("content-manage").style.display="none";
    document.getElementById("user-manage").style.display="none";
    document.getElementById("statistical-analysis").style.display="block";
}
function unLoadOnClick() {
    localStorage.setItem("class", null);
    localStorage.setItem("role", null);
    localStorage.setItem("token", null);
    localStorage.setItem("id", null);
    localStorage.setItem("account", null);
    localStorage.setItem("group", null);
    window.location.href="../login.html";
}