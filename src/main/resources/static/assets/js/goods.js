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

layui.use(['carousel', 'rate', 'form'], function () {
    var carousel = layui.carousel;
    var rate = layui.rate;
    var form = layui.form;

    carousel.render({
        elem: '#goods_carousel'
        , width: '1519px'
        , height: '650px'
        , autoplay: false
        , arrow: 'always'
        , indicator: 'none'
        , anim: 'fade'

    })
    rate.render({
        elem: '#score'
        , value: 4
        , readonly: true
    });
})