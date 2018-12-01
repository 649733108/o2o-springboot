$(function () {
    //绑定账号的controller url
    var bindUrl = '/local/bindlocalauth';
    //从地址栏的URL里获取usertype
    //usertype=1则为前端展示页面,其余为店家管理系统
    var usertype = getQueryString("usertype");
    $('#submit').click(function () {
        //获取输入的账号密码验证码
        var userName = $('#userName').val();
        var password = $('#password').val();
        var verifyCodeActual = $('#j_captcha').val();
        var needVerify = false;
        if (!verifyCodeActual) {
            $.toast('请输入验证码!');
            return;
        }
        //访问后台 绑定账号
        $.ajax({
            url:bindUrl,
            async:false,
            cache:false,
            type:"post",
            dataType:'json',
            data:{
                userName:userName,
                password:password,
                verifyCodeActual:verifyCodeActual
            },
            success:function (data) {
                if (data.success) {
                    $.toast('绑定成功!');
                    if (usertype == 1) {
                        window.location.href='/frontend/index';
                    }else {
                        window.location.href='/shopadmin/shoplist';
                    }
                }else {
                    $.toast('提交失败!' + data.errMsg);
                }
                changeVerifyCode($('#captcha_img'));
            }
        })
    })
})