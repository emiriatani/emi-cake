var ajaxReqErrorStr = "ajax request error";
var successKey = "success";
var codeKey = "code";
var msgKey = "msg";
var dataKey = "data";




/*查看购物车*/
function toCart(){
    $("#cartBtn").click(function () {
        $.ajax({
            url:'/cart/toCartPage',
            type:'GET',
            async:false,
            dataType:'json',
            success: function (response) {
                if (response[codeKey] == 400){
                    console.log(response);
                    alertMsg(response[msgKey]);
                }else {
                    window.location.href = '/shop/cart.html';
                }
            },
            error: function () {
                console.log(response);
            }
        })
    })
}

/*弹窗*/
function alertMsg(str) {
    layui.use(['layer'], function () {
        layer.ready(function (e) {
            var layer = layui.layer;
            layer.msg(str, {
                time: 1500
            });
        })
    })
}

/*获取url中的参数*/
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

/**
 * 分隔数据库中查出来的图片url字符串
 * @param originObj 需要分割的对象
 * @param delimiter 分隔符
 * @returns {*|string[]}
 */
function splitParam(originObj, delimiter) {
    let str = originObj;
    let splitArray = str.split(delimiter);
    return splitArray;
}