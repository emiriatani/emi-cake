var memberCart = null;

$(function () {
    initCart();

    submitCart();
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
                        '                            <div class="priceChangeBox">\n' +
                        '                                比加入时\n' +
                        '                                <span class="changeState"></span>\n' +
                        '                                <span class="changeNumber"></span>\n' +
                        '                                元\n' +
                        '                            </div>\n' +
                        '                        </a>\n' +
                        '                    </div>\n' +
                        '                    <div class="goods_price clearfix">\n' +
                        '                        <span>￥</span>\n' +
                        '                        <span class="unit_price" >' +cartItemUnitPrice + '</span>\n' +
                        '                        <span>/</span>\n' +
                        '                        <span>件</span>\n' +
                        '                    </div>\n' +
                        '                    <div class="goods_count clearfix">\n' +
                        '                        <span><button class="del_btn"><i class="layui-icon">&#xe67e;</i></button></span>\n' +
                        '                        <span class="count_value">' + cartItemCount + '</span>\n' +
                        '                        <span><button class="add_btn"><i class="layui-icon">&#xe624;</i></button></span>\n' +
                        '                    </div>\n' +
                        '                    <div class="goods_total">\n' +
                        '                        <span class="item_price" >' + cartItemTotalPrice + '</span>\n' +
                        '                        <span>￥</span>\n' +
                        '                        <a class="remove_item" href="javascript:;">删除</a>\n' +
                        '                    </div>\n' +
                        '                </div>';

                    $("#cart_item_area").append(cartItemStr);
                    countEvent();
                })

                var cartItemSpecDiv = $(".spec");
                var cartItemPriceChangeBox = $(".priceChangeBox");
                var cartItemChangeState = $(".changeState");
                var cartItemChangeNumber = $(".changeNumber");


                for (let i = 0; i <cartItemChangeState.length; i++) {
                    if (memberCart.cartItemDTOList[i].priceChangeFlag == 0){
                        cartItemChangeState[i].hide();
                    }else if (memberCart.cartItemDTOList[i].priceChangeFlag == 1){
                        cartItemChangeState[i].html("下降了");
                        cartItemChangeNumber[i].html(memberCart.cartItemDTOList[i].number);
                    }
                    alert(memberCart.cartItemDTOList[i].productSkuId);

                }
            }





            /*判断每个商品目前价格和加购时的商品变化*/
            // if (cartItemPriceChangeFlag == 0){
            //     cartItemPriceChangeBox.hide();
            //     alert(cartItemPriceChangeBox);
            // } else if (cartItemPriceChangeFlag == 1){
            //     //cartItemChangeState.text("下降了");
            //     //cartItemChangeNumber.text(cartItemPriceChangeNumber);
            // }else if (cartItemPriceChangeFlag == 2){
            //     //cartItemChangeState.text("上涨了");
            //     //cartItemChangeNumber.html(cartItemPriceChangeNumber);
            // }

        },
        error: function () {

        }
    })
}

function countEvent() {

    var add = document.getElementsByClassName("add_btn");
    var del = document.getElementsByClassName("del_btn");
    var val = document.getElementsByClassName("count_value");
    var unitPrice = document.getElementsByClassName("unit_price");
    var itemPrice = document.getElementsByClassName("item_price");
    var totalPrice = document.getElementById("cart_total_price");
    var cartItem = document.getElementsByClassName("cart_item");
    var removeItem = document.getElementsByClassName("remove_item");

    /*增加商品数量事件*/
    for (var i = 0, len = add.length; i < len; i++) {
        (function (i) {
            add[i].onclick = function () {
                var value = parseInt(val[i].innerHTML);
                ++value;
                val[i].innerHTML = value;
                itemPrice[i].innerHTML = parseInt(unitPrice[i].innerText) * value;
                sum();
            }
        })(i);
    }
    /*减少商品数量事件*/
    for (var i = 0, len = del.length; i < len; i++) {
        (function (i) {
            del[i].onclick = function () {
                var value = parseInt(val[i].innerHTML);
                if (value != 1) {
                    --value;
                    val[i].innerHTML = value;
                    itemPrice[i].innerHTML = parseInt(unitPrice[i].innerText) * value;
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

    /*购物车总金额价格计算*/
    function sum() {
        var sum = 0;
        for (var i = 0; i < itemPrice.length; i++) {
            sum += parseInt(itemPrice[i].innerText)
        }
        totalPrice.innerHTML = sum;
    }

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
                    }
                    , btn2: function () {
                        /*删除取消按钮回调*/
                        layer.close();
                    }
                });
            });
        });
    })

    /*删除商品*/
    $(".remove_item").click(function (e) {
        layui.use(['layer'], function (e) {
            var layer = layui.layer;
            var msg = "确定要删除吗？";
            layer.ready(function (e) {
                layer.msg(msg, {
                    btn: ['确定', '取消']
                    , time: 10 * 60 * 1000
                    , yes: function (e) {
                        /*删除确定按钮回调*/
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


function submitCart() {

}




