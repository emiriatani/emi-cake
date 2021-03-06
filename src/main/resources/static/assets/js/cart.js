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
    alert("111");
    (function (i) {
        alert("222");
        add[i].onclick = function () {
            alert(add[i]);
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




