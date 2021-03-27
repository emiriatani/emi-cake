var allShop = null;

$(function () {
    initShopData();
    orderProdData();
    submit();

})


layui.use(['form', 'element', 'laydate'], function () {
    var element = layui.element
        , form = layui.form
        , laydate = layui.laydate;

    laydate.render({
        elem: '#pick_date'
    });
    laydate.render({
        elem: '#pick_time'
        , type: 'time'
        , range: true
    });
})


function initShopData() {
    $.ajax({
        url: '/shop/all',
        type: 'GET',
        dataType: 'JSON',
        success: function (response) {
            if (response[successKey] == true && response[codeKey] == 200) {

                allShop = response[dataKey];

                console.log(allShop);

                $.each(allShop, function (index, item) {

                    var shopId = item.id;
                    var shopName = item.shopName;
                    var shopAddress = item.address;
                    /*店铺是否有效,0无效,1有效*/
                    var state = item.state;
                    /*店铺是否营业,0不营业,1营业*/
                    var operatingState = item.operatingState;
                    if (state === 1 && operatingState === 1) {
                        var shopItemstr =
                            '<div class="layui-form-item">\n' +
                            '<div class="layui-input-block">\n' +
                            '<input type="radio" name="store" value="' + shopId + '" title="' + shopName + ' - 地址：[ ' + shopAddress + ']">\n' +
                            '</div>\n' +
                            '</div>';
                        $(".store_add_box").append(shopItemstr);
                    }
                });

                $("input[type='radio']:first").attr("checked", "checked");

            }
        },
        error: function () {

        }
    })
}

function orderProdData() {
    $.ajax({
        url: '/cart/get',
        type: 'GET',
        dataType: 'JSON',
        success: function (response) {
            if (response[successKey] == true && response[codeKey] === 200) {

                console.log(response);
                memberCart = response[dataKey];

                /*订单商品数量*/
                $("#pick_goods_num").html(memberCart.size);
                $("#pick_goods_num").val(memberCart.size);
                $("#take_goods_num").html(memberCart.size);
                $("#take_goods_num").val(memberCart.size);

                /*订单总价格*/
                $("#pick_order_total_price").html(memberCart.totalPrice);
                $("#pick_order_total_price").val(memberCart.totalPrice);
                $("#take_order_total_price").html(memberCart.totalPrice);
                $("#take_order_total_price").val(memberCart.totalPrice);

                /*订单实付价格*/
                $("#pick_pay_total_price").html(memberCart.totalPrice);
                $("#pick_pay_total_price").val(memberCart.totalPrice);
                $("#take_pay_total_price").html(memberCart.totalPrice);
                $("#take_pay_total_price").val(memberCart.totalPrice);


                /*订单商品列表*/
                var cartItemList = memberCart.cartItemDTOList;

                $.each(cartItemList, function (index, item) {

                    /*商品id*/
                    var cartItemId = item.productId;
                    /*商品项标题*/
                    var cartItemTitle = item.title;
                    /*商品项缩略图*/
                    var cartItemThumbnail = item.thumbnail;
                    /*商品项目前单价*/
                    var cartItemUnitPrice = item.currentPrice;
                    /*商品项数量*/
                    var cartItemCount = item.number;
                    /*商品项总价*/
                    var cartItemTotalPrice = item.totalPrice;
                    /*商品规格*/
                    var cartItemSpec = item.spec;
                    var specObj = JSON.parse(cartItemSpec);

                    let cartItemStr =
                        '                            <div class="cart_item">\n' +
                        '                                <div class="goods_info">\n' +
                        '                                    <a href="#20">\n' +
                        '                                        <div class="img"><img src="' + cartItemThumbnail + '"></div>\n' +
                        '                                        <div class="desc">\n' +
                        '                                            <h3>' + cartItemTitle + '</h3>\n' +
                        '                                        </div>\n' +
                        '                                        <div class="spec">\n' +
                        '                                            <span>' + specObj.size + '</span>\n' +
                        '                                            <span>|</span>\n' +
                        '                                            <span>' + specObj.weight + '</span>\n' +
                        '                                        </div>\n' +
                        '                                    </a>\n' +
                        '                                </div>\n' +
                        '                                <div class="goods_price clearfix">\n' +
                        '                                    <span>￥</span>\n' +
                        '                                    <span class="unit_price">' + cartItemUnitPrice + '</span>\n' +
                        '                                    <span>/</span>\n' +
                        '                                    <span>件</span>\n' +
                        '                                </div>\n' +
                        '                                <div class="goods_count clearfix">\n' +
                        '                                    <span>×</span>\n' +
                        '                                    <span class="count_value">' + cartItemCount + '</span>\n' +
                        '                                </div>\n' +
                        '                                <div class="goods_total">\n' +
                        '                                    <span class="item_price">' + cartItemTotalPrice + '</span>\n' +
                        '                                    <span>￥</span>\n' +
                        '                                </div>\n' +
                        '                            </div>\n';

                    $(".pick_order_info").append(cartItemStr);
                    $(".take_order_info").append(cartItemStr);
                })
            }
        },
        error: function (response) {

        }

    })
}


function submit() {
    pickOrderSubmit();
    takeOrderSubmit();
    saveAddr();

}

function pickOrderSubmit() {
    $("#pick_order_submit_btn").click(function () {
        alert("自提下单");
        /*自提门店Id*/
        var shopId = $("input:checked").val();
        alert(shopId);
        /*提货人姓名*/
        var pickPersonName = $("#pick_person_name").val();
        alert(pickPersonName);
        /*提货人手机*/
        var pickPersonPhone = $("#pick_person_phone").val();
        alert(pickPersonPhone);
        /*自提日期*/
        var pickDate = $("#pick_date").val();
        alert(pickDate);
        /*自提时间段*/
        var pickTime = $("#pick_time").val();
        alert(pickTime);
        /*订单留言*/
        var pickOrderMsg = $("#pick_order_msg_input").val();
        alert(pickOrderMsg);
        /*支付方式*/
        var payment = $("input[type='radio']:checked").val();
        alert(payment);

        /*订单商品信息*/
        alert(JSON.stringify(memberCart));



    })
}

function takeOrderSubmit() {
    $("#take_order_submit_btn").click(function () {
        alert("外卖下单");

        /*订单留言*/
        var takeOrderMsg = $("#take_order_msg_input").val();
        alert(takeOrderMsg);
        /*支付方式*/
        var payment = $("input[type='radio']:checked").val();
        alert(payment);




    })
}


function pickOrderVerify() {

}

function takeOrderVerify() {

}

function saveAddr() {
    $("#order_saveAddr").click(function () {
        /*订货人姓名*/
        var ordererName = $("#orderer_name").val();
        alert(ordererName);
        /*订货人手机*/
        var ordererPhone = $("#orderer_phone").val();
        alert(ordererPhone);
        /*收货人姓名*/
        var receiverName = $("#receiver_name").val();
        alert(receiverName);
        /*收货人手机*/
        var receiverPhone = $("#receiver_phone").val();
        alert(receiverPhone);
        var provinceName = $("select[name='province'] option:selected").val();
        alert(provinceName);
        /*所在城市*/
        var cityName = $("select[name='city'] option:selected").val();
        alert(cityName);
        /*所在地区*/
        var regionName = $("select[name='region'] option:selected").val();
        alert(regionName);
        /*收货地址*/
        var orderAddr = $("#order_addr").val();
        alert(orderAddr);


    })
}