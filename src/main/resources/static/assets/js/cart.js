var memberCart = null;
var removeItemList = null;

$(function () {
    initCart();
    deleteItemEvent();
    updateItemEvent();
    deleteAllItemEvent();
    submitCartEvent();
})

/*初始化购物车*/
function initCart() {
    $.ajax({
        url: '/cart/get/',
        type: 'GET',
        async: false,
        dataType: 'json',
        success: function (response) {

            if (response[successKey] == true && response[codeKey] == 200) {

                memberCart = response[dataKey];
                console.log(response);
                /*加入购物车商品总数量*/
                $("#cartItemTotal").html(response[dataKey].size);
                $("#cartItemTotal").val(response[dataKey].size);

                /*加入购物车商品总价格*/
                $("#cart_total_price").html(response[dataKey].totalPrice);
                $("#cart_total_price").val(response[dataKey].totalPrice);

                var cartItemList = response[dataKey].cartItemDTOList;

                $.each(cartItemList, function (index, item) {

                    /*商品项id*/
                    var cartItemId = item.productId;
                    /*商品项SKU id*/
                    var cartItemSkuId = item.productSkuId;
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
                    /*商品价格变化状态*/
                    var cartItemPriceChangeFlag = item.priceChangeFlag;
                    var cartItemPriceChangeStr = "";
                    /*商品价格变化数量*/
                    var cartItemPriceChangeNumber = item.priceChangeNumber;

                    var specObj = JSON.parse(cartItemSpec);

                    if (cartItemPriceChangeFlag === 1){
                        cartItemPriceChangeStr =
                            '<div class="priceChangeBox" data-index="'+index+'">\n' +
                            '比加入时\n' +
                            '<span class="changeState" data-index="'+index+'">'+'下降了'+'</span>\n' +
                            '<span class="changeNumber" data-index="'+index+'">'+cartItemPriceChangeNumber+'</span>\n' +
                            '元\n' +
                            '</div>\n';
                    }else if (cartItemPriceChangeFlag === 2){
                        cartItemPriceChangeStr =
                            '<div class="priceChangeBox" data-index="'+index+'">\n' +
                            '比加入时\n' +
                            '<span class="changeState" data-index="'+index+'">'+'上涨了'+'</span>\n' +
                            '<span class="changeNumber" data-index="'+index+'">'+cartItemPriceChangeNumber+'</span>\n' +
                            '元\n' +
                            '</div>\n';
                    }else if (cartItemPriceChangeFlag === 0) {
                        cartItemPriceChangeStr =
                            '<div class="priceChangeBox" data-index="'+index+'">\n' +
                            '</div>\n';
                    }

                    let cartItemStr = '<div class="cart_item">\n' +
                        '                    <div class="goods_info">\n' +
                        '                        <a href="#20">\n' +
                        '                            <div class="img"><img id="cartItemThumbnail" src="' + cartItemThumbnail + '"></div>\n' +
                        '                            <div class="desc">\n' +
                        '                                <h3 class="cartItemTitle">' + cartItemTitle + '</h3>\n' +
                        '                            </div>\n' +
                        '                            <div class="spec">\n' +
                        '                                <span>'+specObj.size+'</span>\n' +
                        '                                <span>|</span>\n' +
                        '                                <span>'+ specObj.weight+'</span>\n' +
                        '                            </div>\n' +
                                                    cartItemPriceChangeStr +
                        '                        </a>\n' +
                        '                    </div>\n' +
                        '                    <div class="goods_price clearfix">\n' +
                        '                        <span>￥</span>\n' +
                        '                        <span class="unit_price" >' + cartItemUnitPrice + '</span>\n' +
                        '                        <span>/</span>\n' +
                        '                        <span>件</span>\n' +
                        '                    </div>\n' +
                        '                    <div class="goods_count clearfix">\n' +
                        '                        <span><button class="del_btn"><i class="layui-icon" data-index="'+index+'">&#xe67e;</i></button></span>\n' +
                        '                        <span class="count_value" data-index="'+ index +'">' + cartItemCount + '</span>\n' +
                        '                        <span><button class="add_btn"><i class="layui-icon" data-index="'+index+'" >&#xe624;</i></button></span>\n' +
                        '                    </div>\n' +
                        '                    <div class="goods_total">\n' +
                        '                        <span class="item_price" >' + cartItemTotalPrice + '</span>\n' +
                        '                        <span>￥</span>\n' +
                        '                        <a class="remove_item" data-index="' + index + '" href="javascript:;">删除</a>\n' +
                        '                    </div>\n' +
                        '                </div>';

                    $("#cart_item_area").append(cartItemStr);

                })





            }
        },
        error: function () {

        }
    })
}

/*购物车商品数量*/
function count() {
    var cartItem = document.getElementsByClassName("cart_item");
    /*购物车中商品总数*/
    $("#cartItemTotal").html(cartItem.length);
    $("#cartItemTotal").val(cartItem.length);
}
/*购物车总金额价格计算*/
function sum() {

    var itemPrice = document.getElementsByClassName("item_price");
    var totalPrice = document.getElementById("cart_total_price");

    var sum = 0;
    for (var i = 0; i < itemPrice.length; i++) {
        sum += parseInt(itemPrice[i].innerText)
    }
    /*购物车总价*/
    totalPrice.innerHTML = sum;
    //totalPrice.val(sum);
}
/*删除商品点击事件*/
function deleteItemEvent() {
    /*删除商品*/
    $(".remove_item").click(function (e) {
        layui.use(['layer'], function () {
            var layer = layui.layer;
            var msg = "确定要删除吗？";
            layer.ready(function () {
                layer.msg(msg, {
                    btn: ['确定', '取消']
                    , time: 10 * 60 * 1000
                    , yes: function (index,layero) {
                        /*删除确定按钮回调*/
                        layer.close(index);
                        deleteItem(e);
                    }
                    , btn2: function (index,layero) {
                        /*删除取消按钮回调*/
                        layer.close(index);
                    }
                });
            });
        });
    })
}
/*删除商品请求*/
function deleteItem(e) {

    /*获取点击的商品项*/
    var cartItemDTOListElement = memberCart.cartItemDTOList[e.target.dataset.index];

    var jsonCartItemData = JSON.stringify(cartItemDTOListElement);

    $.ajax({
        url: '/cart/delete/',
        type: 'POST',
        data: jsonCartItemData,
        contentType: 'application/json',
        dataType: 'json',
        success: function (response) {
            if (response[successKey] == true && response[codeKey] == 200){
                console.log(response);
                $(".remove_item")[e.target.dataset.index].parentElement.parentElement.remove();
                count();
                sum();
            }
        },
        error: function () {
            if (response[successKey] == true && response[codeKey] == 200){
                alertMsg("删除商品失败，请重试！");

            }
        }
    })


}
/*更新商品数量点击事件*/
function updateItemEvent() {
    /*增加商品数量事件*/

    var add = document.getElementsByClassName("add_btn");
    var del = document.getElementsByClassName("del_btn");
    var val = document.getElementsByClassName("count_value");
    var unitPrice = document.getElementsByClassName("unit_price");
    var itemPrice = document.getElementsByClassName("item_price");
    var totalPrice = document.getElementById("cart_total_price");

    for (var i = 0, len = add.length; i < len; i++) {
        (function (i) {
            add[i].onclick = function (e) {
                var value = parseInt(val[i].innerHTML);
                ++value;
                val[i].innerHTML = value;
                var totalPrice = parseInt(unitPrice[i].innerText) * value;
                itemPrice[i].innerHTML = totalPrice;
                //alert(value);
                //alert(e.target.dataset.index);
                //alert(memberCart.cartItemDTOList[e.target.dataset.index].number);
                memberCart.cartItemDTOList[e.target.dataset.index].number = value;
                memberCart.cartItemDTOList[e.target.dataset.index].totalPrice = totalPrice;
                //alert(memberCart.cartItemDTOList[e.target.dataset.index].number);
                //alert(memberCart.cartItemDTOList[e.target.dataset.index].totalPrice);
                updateItem(e);
                //initCart();
                sum();
            }
        })(i);
    }
    /*减少商品数量事件*/
    for (var i = 0, len = del.length; i < len; i++) {
        (function (i) {
            del[i].onclick = function (e) {
                var value = parseInt(val[i].innerHTML);
                if (value != 1) {
                    --value;
                    val[i].innerHTML = value;
                    var totalPrice = parseInt(unitPrice[i].innerText) * value
                    itemPrice[i].innerHTML = totalPrice;

                    //alert(value);
                    //alert(e.target.dataset.index);
                    //alert(memberCart.cartItemDTOList[e.target.dataset.index].number);
                    memberCart.cartItemDTOList[e.target.dataset.index].number = value;
                    memberCart.cartItemDTOList[e.target.dataset.index].totalPrice = totalPrice;
                    //alert(memberCart.cartItemDTOList[e.target.dataset.index].number);
                    //alert(memberCart.cartItemDTOList[e.target.dataset.index].totalPrice);
                    updateItem(e);
                    //initCart();
                    sum();

                } else {
                    val[i].innerHTML = 1;
                    layui.use(['layer'], function () {
                        var layer = layui.layer;
                        var msg = "不能再少啦";
                        layer.ready(function () {
                            layer.msg(msg, {
                                time: 2000
                            });
                        });
                    });
                }
            }
        })(i)
    }
}
/*更新商品请求*/
function updateItem(e) {
    /*获取点击的商品项*/
    var cartItemDTOListElement = memberCart.cartItemDTOList[e.target.dataset.index];
    var jsonCartItemData = JSON.stringify(cartItemDTOListElement);

    $.ajax({
        url: '/cart/update/',
        type: 'POST',
        data: jsonCartItemData,
        contentType: 'application/json',
        dataType: 'json',
        success: function (response) {
            if (response[successKey] == true && response[codeKey] == 200){
                console.log(response);
            }
        },
        error: function () {
            if (response[successKey] == false && response[codeKey] == 400){
                alertMsg("更新购物车失败，请重试");
            }
        }
    })
}
/*清空购物车点击事件*/
function deleteAllItemEvent() {
    /*清空购物车*/
    $("#deleteAll").click(function () {
        layui.use(['layer'], function (e) {
            var layer = layui.layer;
            var msg = "确定要清空购物车吗？";
            layer.ready(function (e) {
                layer.msg(msg, {
                    btn: ['确定', '取消']
                    , time: 10 * 60 * 1000
                    , yes: function (index,layeror) {
                        /*删除确定按钮回调*/
                        layer.close(index);
                        deleteAllItem();
                    }
                    , btn2: function (index,layeror) {
                        /*删除取消按钮回调*/
                        layer.close(index);
                    }
                });
            });
        });
    })

}
/*清空购物车请求*/
function deleteAllItem() {
    $.ajax({
        url: '/cart/deleteAll/',
        type: 'POST',
        dataType: 'json',
        success: function (response) {
            if (response[successKey] == true && response[codeKey] == 200){
                console.log(response);
                $(".cart_item").remove();
                count();
                sum();
            }
        },
        error: function () {
            if (response[successKey] == false && response[codeKey] == 400){
                alertMsg("清空购物车失败,请重试");
            }
        }
    })
}

function submitCartEvent() {

    $("#settlementBtn").click(function () {
        submitCart();
    })

}

function submitCart() {
    $.ajax({
        url:'/order/toDeal',
        type:'POST',
        contentType:'application/json',
        data:JSON.stringify(memberCart),
        dataType:'JSON',
        success: function (response) {
            if (response[successKey] == true && response[codeKey] == 200){
                window.location.href = '/order/deal.html';
            }
        },
        error: function () {
            if (response[successKey] == false && response[codeKey] == 400){
                alertMsg("请求失败,请重试");
            }
        }
    })
}




