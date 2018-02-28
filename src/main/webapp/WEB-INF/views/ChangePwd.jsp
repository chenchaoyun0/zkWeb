<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="resources/easyui/themes/changePwd/ChangePwd.css">
<script type="text/javascript" src="resources/jquery-1.8.3.min.js"></script>
<title>Insert title here</title>
</head>
<script type="text/javascript">
$(function ($) {
    //弹出登录
    $("#login").hover(function () {
        $(this).stop().animate({
            opacity: '1'
        }, 600);
    }, function () {
        $(this).stop().animate({
            opacity: '0.6'
        }, 1000);
    }).on('click', function () {
        $("body").append("<div id='mask'></div>");
        $("#mask").addClass("mask").fadeIn("slow");
        $("#LoginBox").fadeIn("slow");
    });
    //
    //按钮的透明度
    $("#loginbtn").hover(function () {
        $(this).stop().animate({
            opacity: '1'
        }, 600);
    }, function () {
        $(this).stop().animate({
            opacity: '0.8'
        }, 1000);
    });
    //关闭
    $(".close_btn").hover(function () { $(this).css({ color: 'black' }) }, function () { $(this).css({ color: '#999' }) }).on('click', function () {
        $("#LoginBox").fadeOut("fast");
        $("#mask").css({ display: 'none' });
    });
});
</script>
<body>
    <input id = "login" type = "button" value = "修改密码">
    <div id="LoginBox">
        <div class="row1">
                                            登陆
            <a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>
        </div>
        
        <div class = "row2">
            <div id = "row21">
                <span id = "notice">用户名:</span>
                <span id = "notice">密&nbsp;码:</span>
            </div>
            <div id = "row22">
                <input id = "input_name" type = "text" value = "">
                <input id = "input_pwd" type = "password" value = "">
            </div>
            <div id = "row23">
                <input id = "login_btn" type = "button" value = "确认" onclick = "clickDecide()"><br>
                <label id = "input_notice"></label>
            </div>
        </div>
    </div>
</body>
</html>