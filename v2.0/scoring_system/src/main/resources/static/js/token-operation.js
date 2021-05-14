function setToken(type, value) {
    localStorage.setItem(type, value);
}

function getToken(type) {
    return localStorage.getItem(type);
}

function clearToken(type) {
    localStorage.setItem(type, null);
}

// function setIdToken(value) {
//     setToken("id", value);
//     setToken("token", value);
//     setToken("class", value);
//     setToken("group", value);
// }

function clearAll() {
    setToken("id", null);
    setToken("token", null);
    setToken("class", null);
    setToken("group", null);
}
//添加请求头
// beforeSend: function (XMLHttpRequest) {
//     XMLHttpRequest.setRequestHeader("token", localStorage.token);
// },