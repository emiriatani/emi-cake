var allShop = null;
var allMemberAddressInfo = null;
var allOrdererInfo = null;
var allconsigneeInfo = null;
var defaultFlag = null;
var defaultAddrId = null;
var memberId = null;
var memberCart = null;

$(function () {
    initShopData();
    initAddrData();
    orderProdData();
    saveAddr();
    addAddress();
    selectOtherAddr();
    pickOrderSubmit();
    takeOrderSubmit();
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
    /*
    监听自提订单
    表单提交
    */
    form.on('submit(OrderSubmit)', function (data) {
        console.log(data.elem);//被执行事件的元素DOM对象，一般为button对象
        console.log(data.form);//被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field);//当前容器的全部表单字段，名值对形式：{name: value}
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
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

function initAddrData() {
    $.ajax({
        url: '/memberAddress/all',
        type: 'GET',
        dataType: 'JSON',
        success: function (response) {
            if (response[successKey] == true && response[codeKey] == 200) {
                console.log(response);
                /*用户所有地址信息*/
                allMemberAddressInfo = response[dataKey];
                /*该用户下所有订货人信息*/
                allOrdererInfo = allMemberAddressInfo.ordererInfo;
                /*该用户下所有收货人以及收货地址信息*/
                allconsigneeInfo = allMemberAddressInfo.ordererAddressInfo;

                for (let i = 0; i < allOrdererInfo.length; i++) {
                    if (allOrdererInfo[i].defaultAddress === 1) {
                        defaultFlag = true;
                        defaultAddrId = allOrdererInfo[i].id;
                        break;
                    } else {
                        defaultFlag = false;
                    }
                }

                if (defaultFlag) {

                    $("#noDefault_delivery_info").hide();
                    $("#order_addDefaultAddr").hide();
                    $("#default_delivery_info").show();
                    $("#order_saveDefaultAddr").show();
                    $("#select_addr").show();

                    $.each(allOrdererInfo, function (index, item) {
                        if (item.defaultAddress === 1) {
                            /*订货人姓名*/
                            var ordererName = item.ordererName;
                            $("#orderer_name").val(ordererName);
                            $("#orderer_name").html(ordererName);
                            /*订货人手机*/
                            var ordererPhone = item.ordererPhone;
                            $("#orderer_phone").val(ordererPhone);
                            $("#orderer_phone").html(ordererPhone);
                        }
                    });
                    $.each(allconsigneeInfo, function (index, item) {

                        if (item.defaultAddress === 1) {
                            /*收货人姓名*/
                            var consigneeName = item.consigneeName;
                            $("#receiver_name").val(consigneeName);
                            $("#receiver_name").html(consigneeName);
                            /*收货人手机*/
                            var consigneePhone = item.consigneePhone;
                            $("#receiver_phone").val(consigneePhone);
                            $("#receiver_phone").html(consigneePhone);
                            /*收货人所在省份*/
                            var consigneeProvinces = item.consigneeProvinces;
                            /*收货人所在城市*/
                            var consigneeCity = item.consigneeCity;
                            /*收货人所在地区*/
                            var consigneeRegion = item.consigneeRegion;

                            $("select[name='region'] option").each(function () {
                                var regionName = $(this).text();
                                if (consigneeRegion == regionName) {
                                    $(this).attr("selected", "selected");
                                }
                            })
                            /*收货人所在地址*/
                            var consigneeAddress = item.consigneeAddress;
                            $("#order_addr").val(consigneeAddress);
                            $("#order_addr").html(consigneeAddress);
                        }

                    })
                } else {
                    $("#noDefault_delivery_info").hide();
                    $("#default_delivery_info > div:not(:last-child)").hide();
                    $("#default_delivery_info > div:last-child > button:first-child").hide();
                }
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

                memberId = memberCart.memberId;

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
}

function pickOrderSubmit() {
    $("#pick_order_submit_btn").click(function () {

        /*商品总数量*/
        var productAmountTotal = memberCart.size;
        /*商品总价*/
        var productTotalPrice = memberCart.totalPrice;
        /*实际付款*/
        var orderTotalPrice = $("#pick_pay_total_price").html();
        /*配送方式 0 门店自提*/
        var deliveryType = 0;
        /*支付方式*/
        var payment = $("input[name='pick_payment']:checked").val();
        /*订单留言*/
        var pickOrderMsg = $("#pick_order_msg_input").val();

        var orderDetailDTOArray = [];

        $.each(memberCart.cartItemDTOList,function (index,item) {
                //封装OrderDetailDTO
                var productId = item.productId;

                var productName = item.title;

                var productSku = item.productSkuId;

                var productImage = item.thumbnail;

                var productPrice = item.currentPrice;

                var purchaseQuantity = item.number;

                var subtotalAmount = item.totalPrice;

                var orderDetailDTO = {
                    memberId:memberId,
                    productId:productId,
                    productName:productName,
                    productSku:productSku,
                    productImage:productImage,
                    productPrice:productPrice,
                    purchaseQuantity:purchaseQuantity,
                    subtotalAmount:subtotalAmount
                };

                orderDetailDTOArray[index] = orderDetailDTO;
        })

        /*自提门店Id*/
        var shopId = $("input[name='store']:checked").val();
        /*提货人姓名*/
        var pickPersonName = $("#pick_person_name").val();
        /*提货人手机*/
        var pickPersonPhone = $("#pick_person_phone").val();
        /*自提日期*/
        var pickDate = $("#pick_date").val();
        /*自提时间段*/
        var pickTime = $("#pick_time").val();
        var pickTimeStrArray = pickTime.split("-");

        var orderPickupDTO = {
            memberId:memberId,
            storeId:shopId,
            pickupName:pickPersonName,
            pickupPhone:pickPersonPhone,
            pickupDate:pickDate,
            pickupTimeStart:pickTimeStrArray[0],
            pickupTimeEnd:pickTimeStrArray[1],
            pickupTime:pickDate + " " + pickTimeStrArray[0] + "-" + pickTimeStrArray[1]
        };

        var orderDTO = {
            memberId:memberId,
            productAmountTotal:productAmountTotal,
            productTotalPrice:productTotalPrice,
            orderTotalPrice:orderTotalPrice,
            deliveryType:deliveryType,
            paymentType:payment,
            orderMessage:pickOrderMsg,
            orderDetail:orderDetailDTOArray,
            orderPickup:orderPickupDTO
        }
        alert(JSON.stringify(orderDTO));
        reqToGenerateOrder(orderDTO);
    })

}

// function takeOrderSubmit() {
//     $("#take_order_submit_btn").click(function () {
//
//         /*配送地址及订货收货人信息id*/
//         var addressId = defaultAddrId;
//         var orderAddressDTO = {
//             addressId:defaultAddrId
//         }
//
//         /*订单商品详情*/
//         var orderDetailDTOArray = [];
//         $.each(memberCart.cartItemDTOList,function (index,item) {
//             //封装OrderDetailDTO
//             var productId = item.productId;
//
//             var productName = item.title;
//
//             var productSku = item.productSkuId;
//
//             var productImage = item.thumbnail;
//
//             var productPrice = item.currentPrice;
//
//             var purchaseQuantity = item.number;
//
//             var subtotalAmount = item.totalPrice;
//
//             var orderDetailDTO = {
//                 memberId:memberId,
//                 productId:productId,
//                 productName:productName,
//                 productSku:productSku,
//                 productImage:productImage,
//                 productPrice:productPrice,
//                 purchaseQuantity:purchaseQuantity,
//                 subtotalAmount:subtotalAmount
//             };
//
//             orderDetailDTOArray[index] = orderDetailDTO;
//         })
//
//         /*商品总数量*/
//         var productAmountTotal = memberCart.size;
//         /*商品总价*/
//         var productTotalPrice = memberCart.totalPrice;
//         /*实际付款*/
//         var orderTotalPrice = $("#take_pay_total_price").html();
//         /*配送方式 1 外卖配送*/
//         var deliveryType = 1;
//         /*订单留言*/
//         var takeOrderMsg = $("#take_order_msg_input").val();
//         /*支付方式*/
//         var payment = $("input[name='take_payment']:checked").val();
//
//         var orderDTO = {
//             memberId:memberId,
//             productAmountTotal:productAmountTotal,
//             productTotalPrice:productTotalPrice,
//             orderTotalPrice:orderTotalPrice,
//             deliveryType:deliveryType,
//             paymentType:payment,
//             orderMessage:takeOrderMsg,
//             orderDetail:orderDetailDTOArray,
//             orderAddress:orderAddressDTO
//         };
//         alert(JSON.stringify(orderDTO));
//         reqToGenerateOrder(orderDTO);
//
//     })
// }

function reqToGenerateOrder(orderData) {
    $.ajax({
        url:'/order/add',
        type:'POST',
        data:JSON.stringify(orderData),
        dataType:'JSON',
        contentType: 'application/json',
        success: function (res) {
            if (res[successKey] == true && res[codeKey] === 200) {
                console.log(res);
                window.location.href = "trade.html?orderId=" + res[dataKey].id;
            }else {
                alertMsg(res[msgKey]);
            }
        },
        error: function (res) {

        }
    })
}


function pickOrderVerify() {
}

function takeOrderVerify() {
}

function saveAddr() {
    $("#order_saveDefaultAddr").click(function () {
        //修改默认地址
        /*需要修改的默认地址id*/
        defaultAddrId = $("#addrId").val();
        var addrId = defaultAddrId;
        /*会员id*/
        var id = memberId;
        /*订货人姓名*/
        var ordererName = $("#orderer_name").val();
        /*订货人手机*/
        var ordererPhone = $("#orderer_phone").val();
        /*收货人姓名*/
        var receiverName = $("#receiver_name").val();
        /*收货人手机*/
        var receiverPhone = $("#receiver_phone").val();
        var provinceName = $("select[name='province'] option:selected").html();
        /*所在城市*/
        var cityName = $("select[name='city'] option:selected").html();
        /*所在地区*/
        var regionName = $("select[name='region'] option:selected").html();
        /*收货地址*/
        var orderAddr = $("#order_addr").val();

        var memberFullAddressJsonData = {
            id: addrId,
            memberId: id,
            ordererName: ordererName,
            ordererPhone: ordererPhone,
            consigneeName: receiverName,
            consigneePhone: receiverPhone,
            consigneeProvinces: provinceName,
            consigneeCity: cityName,
            consigneeRegion: regionName,
            consigneeAddress: orderAddr,
            defaultAddress: 1
        };

        $.ajax({
            url: '/memberAddress/update',
            type: 'POST',
            data: JSON.stringify(memberFullAddressJsonData),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function (res) {
                console.log(res);
                if (res[successKey] == true && res[codeKey] === 200) {

                    alertMsg("默认配送地址修改成功")
                }
            },
            error: function (res) {
                if (res[successKey] == false && res[codeKey] === 400) {
                    alertMsg("默认配送地址修改失败，请重试")
                }
            }
        })


    })
}

function addAddress() {
    $("#order_addDefaultAddr").click(function () {
        layui.use(['layer'], function () {
            var layer = layui.layer;
            layer.open({
                type: 2,
                title: '添加默认地址',
                shadeClose: false,
                shade: 0.8,
                area: ['900px', '570px'],
                content: 'addAccountAddress.html', //iframe的url
                resize: false,
                success: function (dom) {
                    let $iframeDom = $(dom[0]).find("iframe").eq(0).contents();
                    $iframeDom.find("#memberIdBox").val(memberId);
                }
            });
        })
    })
}

function selectOtherAddr() {
    //选择其他地址
    $("#select_addr").click(function () {
        layui.use(['layer'], function () {
            var layer = layui.layer;
            layer.open({
                type: 2,
                title: '添加新地址',
                shadeClose: false,
                shade: 0.8,
                area: ['830px', '450px'],
                content: 'addAccountAddress.html', //iframe的url
                resize: false
            });
        })
    })
}

function addDefaultSuccessFallBack() {

    alertMsg("新增默认地址成功");

    var jsonData = {memberId:memberId};

    $.ajax({
        url: '/memberAddress/get',
        type: 'GET',
        data: jsonData,
        dataType: 'JSON',
        success: function (res) {
            if (res[successKey] == true && res[codeKey] === 200) {
                console.log(res[dataKey]);
                var defaultAddress = res[dataKey];

                $("#noDefault_delivery_info").hide();
                $("#order_addDefaultAddr").hide();

                $("#default_delivery_info > div").show();
                $("#order_saveDefaultAddr").show();

                $("#addrId").val(defaultAddress.id)

                $("#orderer_name").val(defaultAddress.ordererName);
                $("#orderer_name").html(defaultAddress.ordererName);

                $("#orderer_phone").val(defaultAddress.ordererPhone);
                $("#orderer_phone").html(defaultAddress.ordererPhone);

                $("#receiver_name").val(defaultAddress.consigneeName);
                $("#receiver_name").html(defaultAddress.consigneeName);

                $("#receiver_phone").val(defaultAddress.consigneePhone);
                $("#receiver_phone").html(defaultAddress.consigneePhone);

                var provinceItem =  $("#province  option");
                var cityItem =  $("#city  option")
                var regionItem =  $("#region  option")

                $.each(provinceItem,function (index,item) {
                    if ($(item).text() == defaultAddress.consigneeProvinces){
                        $(item).attr("selected","selected");
                    }
                });
                $.each(cityItem,function (index,item) {
                    if ($(item).text() == defaultAddress.consigneeCity){
                        $(item).attr("selected","selected");
                    }
                });
                $.each(regionItem,function (index,item) {
                    if ($(item).text() == defaultAddress.consigneeRegion){
                        $(item).attr("selected","selected");
                    }
                });

                $("#order_addr").val(defaultAddress.consigneeAddress);
                $("#order_addr").html(defaultAddress.consigneeAddress);

                layui.use(['form'], function () {
                    var form = layui.form;
                    form.render();
                })
            }
        },
        error: function (res) {
            if (res[successKey] == false && res[codeKey] === 400) {
                alertMsg("ajax request error,please try again");
            }
        }
    })

}