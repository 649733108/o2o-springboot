$(function () {
    var url = '/local/changelocalpwd';
    var usertype = getQueryString('usertype');
    $('#submit').click(function () {
        var userName = $('#userName').val();
        var password = $('#password').val();
        var newPassword = $('#newPassword').val();
        var confirmPassword = $('#confirmPassword').val();

        if (newPassword!==confirmPassword){
            $.toast('两次输入的密码不一致');
            return;
        }
        var formData = new FormData();
        formData.append("userName",userName);
        formData.append("password",password);
        formData.append("newPassword",newPassword);
        formData.append("confirmPassword",confirmPassword);

        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码!');
            return;
        }
        formData.append("verifyCodeActual",verifyCodeActual);

        $.ajax({
            url:url,
            type:'post',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if (data.success){
                    $.toast('提交成功!');
                    if (usertype==1){
                        window.location.href='/frontend/index';
                    } else {
                        window.location.href='/shopadmin/shoplist';
                    }
                } else {
                    $.toast('提交失败: '+ data.errMsg);
                }
                changeVerifyCode($('#captcha_img'));
            }
        })
    })

})