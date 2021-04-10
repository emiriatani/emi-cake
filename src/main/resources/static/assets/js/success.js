$(function () {
    var queryVariable = getQueryVariable("out_trade_no");
    $("#orderId").html(queryVariable);
});