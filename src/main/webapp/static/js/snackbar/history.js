global.define("CurrentIndex", 1);

$(function () {
    var clipboard = new Clipboard('.copy');
    clipboard.on('success', function (e) {
        $.snackbar({ content: "复制成功!", timeout: 2000 });
        e.clearSelection();
    });

    var clipboardmk = new Clipboard('.copymk');
    clipboardmk.on('success', function (e) {
        $.snackbar({ content: "MarkDown格式复制成功!", timeout: 2000 });
        e.clearSelection();
    });

    $("#navHistory").addClass("active");
    getPage(1);
});

//分页
function getPage(pageIndex) {
    $.ajax({
        url: "/Home/HistoryList?pageIndex=" + pageIndex,
        type: "get",
        beforeSend: function () { AjaxStart(); },
        success: function (data) {
            $("#div_historyList").html(data);
            global.CurrentIndex = pageIndex; 
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.snackbar({ content: "网络问题，请稍后再试!", timeout: 1000 });
        },
        complete: function () {
            AjaxComplete();
        }
    });
}

//删除
function AjaxDelete(id, isSync) {
    if (confirm("你确定要删除该条数据吗？")) {
        $.ajax({
            type: "post",
            url: "/Home/DeleteSelect?ids=" + id + "&isSync=" + isSync,
            beforeSend: function () { AjaxComplete(); },
            success: function (data) {
                if (data.IsSuccess) {
                    $.snackbar({ content: "删除成功!", timeout: 2000 });
                    getPage(global.CurrentIndex);
                } else {
                    $.snackbar({ content: "删除失败!", timeout: 2000 });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.snackbar({ content: "网络问题，请稍后再试!", timeout: 1000 });
            }
        });
    }
}
