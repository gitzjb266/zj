
layui.use(['form', 'layer', 'jquery'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
    $ = layui.jquery;

    //登录按钮
    form.on('submit(login)', function (result) {
        var btn = $(this);
        //设置登录按钮  为不可点击
        btn.text("登录中...").attr("disabled", "disabled").addClass("layui-disabled");
        $.ajax({
            url: '/sysUser/login',
            contentType: "application/json",
            dataType: 'json',
            type:"post",
            data: JSON.stringify(result.field),
            success: function (res) {
                //失败，返回错误信息
                debugger;
                if (res.code!=0) {
                    layer.msg(res.msg);
                    //设置登录按钮  为不可点击
                    btn.text("登录").attr("disabled", false).removeClass("layui-disabled");
                    $(function () {
                        changeImg();
                    })
                } else {
                    //成功，跳转到首页
                    layer.msg(res.msg);
                    location.href = "/";
                }
            }
        })
    });

    //表单输入效果
    $(".loginBody .input-item").click(function (e) {
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function () {
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function () {
        $(this).parent().removeClass("layui-input-focus");
        if ($(this).val() != '') {
            $(this).parent().addClass("layui-input-active");
        } else {
            $(this).parent().removeClass("layui-input-active");
        }
    })
})

/* 触发JS刷新  切换图片*/
function changeImg() {
    var img = document.getElementById("verify_code_img");
    img.src = "/verifyCode/getImg?date=" + new Date();

}
