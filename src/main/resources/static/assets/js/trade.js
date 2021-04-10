var order = null;

$(function () {
    var orderId = getQueryVariable("orderId");
    if (orderId != "" && orderId != undefined){
        queryOrder(orderId);
    }

})

function queryOrder(id) {
    $.ajax({
        url: '/order/get',
        type: 'GET',
        data: {orderId: id},
        dataType: 'JSON',
        success: function (res) {
            if (res[successKey] == true && res[codeKey] === 200) {
                console.log(res);
                order = res[dataKey];
                $("#tips").html("您的订单已提交成功，请尽快付款");
                $("input[type='hidden']").val(order.id);
                $("#orderPrice").html("￥ " + order.orderTotalPrice);

            }
        },
        error: function () {

        }
    })
}