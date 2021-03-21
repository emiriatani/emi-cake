var allShop = null;

$(function () {
    initShopData();
})


layui.use(['form', 'element', 'laydate'], function () {
    var element = layui.element
        , form = layui.form
        , laydate = layui.laydate;

    laydate.render({
        elem: '#date'
    });
    laydate.render({
        elem: '#test9'
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
                            '<input type="radio" name="store" value="' + shopId + '" title="' + shopName + ' - 地址：[ '+shopAddress+']">\n' +
                            '</div>\n' +
                            '</div>';
                        $(".store_add_box").append(shopItemstr);
                    }

                });

                $(":input:first").attr("checked","checked");


            }
        },
        error: function () {

        }
    })
}

