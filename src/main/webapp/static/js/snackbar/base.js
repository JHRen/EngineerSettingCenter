
$("a[href='#").parent().addClass("active");

function AjaxStart() {
    var loading = window.top.document.getElementById("frmloading"); //进度条
    loading.style.left = ((window.screen.width - 140) / 2) + "px";
    loading.style.top = ((window.screen.height - 38) / 2) + "px";
    loading.style.display = "block";
}

function AjaxComplete() {
    var loading0 = window.top.document.getElementById("frmloading");
    loading0.style.display = "none";
}