<html>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/login/login.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-1.8.3.min.js"></script>

<title>Zookeeper</title>
</head>
<script type="text/javascript">
var msgdsq;

function show_loading()
{
    var str='<div class="msg_bg" style="background:#000;opacity:0.5;filter:alpha(opacity=50);z-index:99998;width:100%;position:absolute;left:0;top:0"></div>';
    str+='<div class="msg_bg" style="z-index:99999;width:100%;position:absolute;left:0;top:0;text-align:center;"><img src="style/images/loading.gif" alt="" class="loading"></div>'
    $('body').append(str);
    var scroll_height=$(document).scrollTop(); 
    $('.msg_bg').height($(document).height());
    $('.loading').css('margin-top',scroll_height+240);
}

function show_err_msg(msg){
    show_loading();
    $('.msg_bg').html('');
    clearTimeout(msgdsq);
    $('body').append('<div class="sub_err" style="position:absolute;top:60px;left:0;width:500px;z-index:999999;"></div>');
    var errhtml='<div style="padding:8px 0px;border:1px solid #95b8e7;width:100%;margin:0 auto;background-color:#F8F8FF;color:black;text-align:center;font-size:16px;font-family:微软雅黑;">';
    var errhtmlfoot='</div>';   
    $('.msg_bg').height($(document).height());
    $('.sub_err').html(errhtml+msg+errhtmlfoot);
    var left=($(document).width()-500)/2;
    $('.sub_err').css({'left':left+'px'});
    var scroll_height=$(document).scrollTop(); 
    $('.sub_err').animate({'top': scroll_height+120},300);
    msgdsq=setTimeout(function(){                   
        $('.sub_err').animate({'top': scroll_height+80},300);
        setTimeout(function(){
            $('.msg_bg').remove();
            $('.sub_err').remove();
        },300);
    }, "1000"); 
}

function login(){
	var name = $("#name").val();
	var password = $("#password").val();
	//alert(name);
	if(name == "")
		show_err_msg("请输入名字！");
	else if(password == "")
		show_err_msg("请输入密码！");
	else{
		$.ajax( {  
					url:'LoginSubmit',
                    type:'post',
                    dataType:'text',
					data:{
						   "name" : name,
						   "password" : password,
					},
					success:function(data){
						if(data==1){
							window.location.href = "${pageContext.request.contextPath}/operation/home";
						}
						else if(data == 0)
							show_err_msg("用户名或密码错误！");
						else
							show_err_msg("读取用户列表出错，请重试...");
					}
				});
	}
}

</script>
<body>
    <div class = "logo">
    </div>
    
    <div class = "formBackground">
		<form class = "header">
		   <div class = "labelNotice">
		       <label>登陆</label>
		   </div>
		   
		   <div class = "inputNotice">
		       <label id = "label1">用户名：</label><br>
		       <label id = "label2">密码：</label>
		   </div>
		   
		   <div class = "inputMessage">
	           <input id = "name" type = "text"><br>
	           <input id = "password" type = "password">
	       </div>
	       
	       <div class = "loginNotice">
	        <input id = "decide" type = "button" value = "登陆" onclick = "login()">
	       </div>
		</form>
	</div>
	
	<div class = "copyRight">©2016 www.ccy123.cn
	</div>
</body>
</html>