var add = document.getElementsByClassName("add_btn");
var del = document.getElementsByClassName("del_btn");
var val = document.getElementsByClassName("count_value");


/*增加商品数量事件*/
for (var i = 0, len = add.length; i < len; i++) {
    (function (i) {
        add[i].onclick = function () {
            var value = parseInt(val[i].innerHTML);
            ++value;
            val[i].innerHTML = value;
        }
    })(i)
}
/*减少商品数量事件*/
for (var i = 0, len = del.length; i < len; i++) {
    (function (i) {
        del[i].onclick = function () {
            var value = parseInt(val[i].innerHTML);
            if (value != 1) {
                --value;
                val[i].innerHTML = value;
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

