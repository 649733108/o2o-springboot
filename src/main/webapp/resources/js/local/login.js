$(function () {
    //绑定账号的controller url
    var loginUrl = '/local/logincheck';
    //从地址栏的URL里获取usertype
    //usertype=1则为前端展示页面,其余为店家管理系统
    var usertype = getQueryString("usertype");
    //登录次数 累计登录三次失败之后自动弹出验证码要求输入
    var loginCount = 0;
    $('#submit').click(function () {
        //获取输入的账号密码验证码
        var userName = $('#userName').val();
        var password = $('#password').val();
        var verifyCodeActual = $('#j_captcha').val();
        var needVerify = false;
        if (loginCount >= 3) {
            if (!verifyCodeActual) {
                $.toast('请输入验证码!');
                return;
            }else {
                needVerify=true;
            }
        }

        //访问后台 进行登录验证
        $.ajax({
            url:loginUrl,
            async:false,
            cache:false,
            type:"post",
            dataType:'json',
            data:{
                userName:userName,
                password:password,
                verifyCodeActual:verifyCodeActual,
                needVerify:needVerify
            },
            success:function (data) {
                if (data.success) {
                    $.toast('登录成功!');
                    if (usertype == 1) {
                        window.location.href='/frontend/index';
                    }else {
                        window.location.href='/shopadmin/shoplist';
                    }
                }else {
                    $.toast('登录失败!' + data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        $('#verifyPart').show();
                    }
                }
                changeVerifyCode($('#captcha_img'));
            }
        })
    })
})