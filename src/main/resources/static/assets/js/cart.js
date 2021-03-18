var memberCart = null;
var removeItemList = null;

$(function () {
    initCart();
    deleteItemEvent();
    updateItemEvent();
    deleteAllItemEvent();
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
                    /*商品价格变化数量*/
                    var cartItemPriceChangeNumber = item.priceChangeNumber;


                    let cartItemStr = '<div class="cart_item">\n' +
                        '                    <div class="goods_info">\n' +
                        '                        <a href="#20">\n' +
                        '                            <div class="img"><img id="cartItemThumbnail" src="' + cartItemThumbnail + '"></div>\n' +
                        '                            <div class="desc">\n' +
                        '                                <h3 class="cartItemTitle">' + cartItemTitle + '</h3>\n' +
                        '                            </div>\n' +
                        '                            <div class="spec">\n' +
                        '                                <span>16厘米</span>\n' +
                        '                                <span>|</span>\n' +
                        '                                <span>950克</span>\n' +
                        '                            </div>\n' +
                        '                            <div class="priceChangeBox" data-index="'+index+'">\n' +
                        '                                比加入时\n' +
                        '                                <span class="changeState" data-index="'+index+'"></span>\n' +
                        '                                <span class="changeNumber" data-index="'+index+'"></span>\n' +
                        '                                元\n' +
                        '                            </div>\n' +
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


// function priceChangeEvent(e) {
//
//     var cartItemSpecDiv = $(".spec");
//     var $cartItemPriceChangeBox = $(".priceChangeBox");
//     var cartItemChangeState = $(".changeState");
//     var cartItemChangeNumber = $(".changeNumber");
//     var index = e.target.dataset.index;
//
//     if (memberCart.cartItemDTOList[index].priceChangeFlag === 0) {
//         //$(".priceChangeBox").hide();
//         $cartItemPriceChangeBox[index].hide();
//     } else if (memberCart.cartItemDTOList[index].priceChangeFlag === 1) {
//         $(".changeState")[index].html("下降了");
//         $(".changeNumber")[index].html(memberCart.cartItemDTOList[index].number);
//     } else if (memberCart.cartItemDTOList[index].priceChangeFlag === 2) {
//         $(".changeState")[index].html("上涨了");
//         $(".changeNumber")[index].html(memberCart.cartItemDTOList[index].number);
//     }
//
// }

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
                    , yes: function () {
                        /*删除确定按钮回调*/
                        alert("删除前");
                        deleteItem(e);
                        alert("删除后");
                        layer.close();

                    }
                    , btn2: function () {
                        /*删除取消按钮回调*/
                        layer.close();
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
            console.log(response);

        },
        error: function () {

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
                    , yes: function (e) {
                        /*删除确定按钮回调*/
                        deleteAllItem();

                    }
                    , btn2: function () {
                        /*删除取消按钮回调*/
                        layer.close();
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
            console.log(response);
            count();
            sum();
        },
        error: function () {

        }
    })
}


function submitCart() {
}




