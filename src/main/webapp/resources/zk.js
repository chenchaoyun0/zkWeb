var flag = false;
$(function() {
	initDataGrid();
	hideUserAuth();
	/* 判断select选择 */
	$('#schemelist').change(function() {
		var p1 = $(this).children('option:selected').val();// 这就是selected的值
		if (p1 == 'digest') {
			$("#userAuth").show();
		} else if (p1 == 'world') {
			$("#userAuth").hide();
		}
		/*
		 * var p2=$('#param2').val();//获取本页面其他标签的值
		 * window.location.href="xx.php?param1="+p1+"?m2="+p2+"";//页面跳转并传参
		 */
	});
	/* 判断select选择 */
	$('#schemelistACL').change(function() {
		var p1 = $(this).children('option:selected').val();// 这就是selected的值
		if (p1 == 'digest') {
			$("#userAuthACL").show();
		} else if (p1 == 'world') {
			$("#userAuthACL").hide();
		}
		/*
		 * var p2=$('#param2').val();//获取本页面其他标签的值
		 * window.location.href="xx.php?param1="+p1+"?m2="+p2+"";//页面跳转并传参
		 */
	});
	/* 验证表单 */
	$("#zkNodeName").blur(function() {
		var _nodeName = $("#zkNodeName").val();
		if (_nodeName != '') {
			$("#zkNodeNametip").html("SUCCESS");
			$("#zkNodeNametip").css({
				"color" : "#7ABD54"
			});
		} else {
			$("#zkNodeNametip").css({
				"color" : "red"
			});
			$("#zkNodeNametip").html("*节点名称不可为空");
			$('#zkNodeName').focus();
			return;
		}
		/* 验证子节点是否存在 */
		var _path = "/";
		var node = $('#zkTree').tree('getSelected');
		if (node) {
			// var s = node.text;
			if (node.attributes) {
				_path = node.attributes.path;
			}
		}
		/* 获取父节点 */
		/* 获取id */
		var _cfg = $('#zkweb_zkcfg').datagrid('getSelected');
		/* 发送请求验证 */
		$.post("zk/existChildPath", {
			path : _path,
			cacheId : _cfg.zkId,
			nodeName : _nodeName
		}, function(data) {
			if (data == 'true') {
				$("#zkNodeNametip").css({
					"color" : "red"
				});
				$("#zkNodeNametip").html("*该子节点已存在");
				$('#zkNodeName').focus();
				flag = false;
				return;
			} else {
				$("#zkNodeNametip").html("SUCCESS");
				$("#zkNodeNametip").css({
					"color" : "#7ABD54"
				});
				flag = true;
			}
		});
	});
	$("#zkNoteUser").blur(function() {
		var _nodeName = $("#zkNoteUser").val();
		if (_nodeName != '') {
			$("#zkNoteUsertip").html("SUCCESS");
			$("#zkNoteUsertip").css({
				"color" : "#7ABD54"
			});
		}
	});
	$("#zkNotePassword").blur(function() {
		var _nodeName = $("#zkNotePassword").val();
		if (_nodeName != '') {
			$("#zkNotePasswordtip").html("SUCCESS");
			$("#zkNotePasswordtip").css({
				"color" : "#7ABD54"
			});
		}
	});
	/**/
	$('#w').window({
		onBeforeClose : function() { // 当面板关闭之前触发的事件
			/*
			 * if (confirm('窗口正在关闭，请确认您当前的操作已保存。\n 是否继续关闭窗口？')) {
			 * $('#windowsMSG').window('close', true); //这里调用close 方法，true
			 * 表示面板被关闭的时候忽略onBeforeClose 回调函数。 } else return false;
			 */
			removeAllCheck();
		}

	});
	/* 勾选all时取消之前的,其他选项取消all */
	$('#permsAll').click(function() {
		$("input[id='permsRead']:checked").attr("checked", false);
		$("input[id='permsWrite']:checked").attr("checked", false);
		$("input[id='permsCreate']:checked").attr("checked", false);
		$("input[id='permsDelete']:checked").attr("checked", false);
		$("input[id='permsAdmin']:checked").attr("checked", false);
	});
	$('#permsRead').click(function() {
		$("input[id='permsAll']:checked").attr("checked", false);
	});
	$('#permsWrite').click(function() {
		$("input[id='permsAll']:checked").attr("checked", false);
	});
	$('#permsCreate').click(function() {
		$("input[id='permsAll']:checked").attr("checked", false);
	});
	$('#permsDelete').click(function() {
		$("input[id='permsAll']:checked").attr("checked", false);
	});
	$('#permsAdmin').click(function() {
		$("input[id='permsAll']:checked").attr("checked", false);
	});
	$('#permsACLAll').click(function() {
		$("input[id='permsACLRead']:checked").attr("checked", false);
		$("input[id='permsACLWrite']:checked").attr("checked", false);
		$("input[id='permsACLCreate']:checked").attr("checked", false);
		$("input[id='permsACLDelete']:checked").attr("checked", false);
		$("input[id='permsACLAdmin']:checked").attr("checked", false);
	});
	$('#permsACLRead').click(function() {
		$("input[id='permsACLAll']:checked").attr("checked", false);
	});
	$('#permsACLWrite').click(function() {
		$("input[id='permsACLAll']:checked").attr("checked", false);
	});
	$('#permsACLCreate').click(function() {
		$("input[id='permsACLAll']:checked").attr("checked", false);
	});
	$('#permsACLDelete').click(function() {
		$("input[id='permsACLAll']:checked").attr("checked", false);
	});
	$('#permsACLAdmin').click(function() {
		$("input[id='permsACLAll']:checked").attr("checked", false);
	});
});

function initDataGrid() {
	$('#zkweb_zkcfg').datagrid({
		onClickRow : function(rowIndex, rowData) {
			// alert(rowData.DES);
			initTree(rowData.zkId);
		},
		url : 'zkcfg/queryZkCfg'
	});
}
/** ************************************************************************************************************************* */

function initTree(cacheId) {

	$('#zkTree')
			.tree(
					{
						checkbox : false,
						url : "zk/queryZnode?cacheId=" + cacheId,
						animate : true,
						lines : true,
						onContextMenu : function(e, node) {
							e.preventDefault();
							$(this).tree('select', node.target);
							$('#mm').menu('show', {
								left : e.pageX,
								top : e.pageY
							});
						},
						onClick : function(node) {
							var tab = $('#zkTab').tabs('getSelected');
							/*
							 * var index = $('#zkTab').tabs('getTabIndex',tab);
							 * alert(index);
							 */
							if (tab != null) {
								tab.title = node.text;
								// tab.panel('refresh',
								// "zk/queryZnodeInfo?path="+node.attributes.path);
								$('#zkTab')
										.tabs(
												'update',
												{
													tab : tab,
													options : {
														title : node.text,
														href : "zk/queryZnodeInfo?path="
																+ encodeURI(encodeURI(node.attributes.path))
																+ "&cacheId="
																+ cacheId
													}
												});
							} else {
								$('#zkTab')
										.tabs(
												'add',
												{
													id : 0,
													title : node.text,
													href : "zk/queryZnodeInfo?path="
															+ encodeURI(encodeURI(node.attributes.path))
															+ "&cacheId="
															+ cacheId
												// closable:true
												});
							}

						},
						onBeforeExpand : function(node, param) {
							
							if (node.attributes != null) {
								
								$('#zkTree').tree('options').url = "zk/queryZnode?path="
										+ encodeURI(encodeURI(node.attributes.path))
										+ "&cacheId=" + cacheId;

							}
						}
					});

}
/* 删除节点 */
function valiataConfig() {
	var _cfg = $('#zkweb_zkcfg').datagrid('getSelected');

	if (_cfg) {
		$('#w').window('open');
	} else {
		$.messager.alert('提示', '请选择一个节点');
	}

}
/* 删除节点 */
function importdata() {
	$('#import').window('open');
}
function impInfo(){
        alert(getPath(document.getElementById('uploadfile')));
            alert("1===" + $('#uploadfile').val());
            if($('#uploadfile').val() == "")
            {
                alert("请选择Excel文件!");
                return;
            }
            $('#loading').ajaxStart(function(){
                $(this).show();
            })//开始上传文件时显示一个图片
            .ajaxComplete(function(){
                $(this).hide();
            });
             
            $.ajaxFileUpload({
                url:'servlet/ReadExcelServlet',//用于文件上传的服务器端请求地址
                secureuri:true,//是否启用安全提交，一般设置为false
                fileElementId:'uploadfile',//文件上传控件的id
                dataType:'text',//服务器返回的数据类型
                success: function (data,status){
                    if(data == 'success'){
                        $('#dd').window('close');
                        alert("导入成功!");
                    }else if(data == 'error'){
                        alert("文件上传过程中出错!请重试!");
                    }
                },
                error: function (data,status,e){
                    alert("服务中断或连接超时导致通信失败！");
                    alert(e);
                }
            }
            )
            return false;
        }
function removePath() {
	$.extend($.messager.defaults, {
		ok : "确定",
		cancel : "取消"
	});

	var node = $('#zkTree').tree('getSelected');
	if (node) {
		if ('/' == node.attributes.path || '/zookeeper' == node.attributes.path
				|| '/zookeeper/quota' == node.attributes.path) {
			$.messager.alert('提示', '不能删除此节点！');
			return;
		}

		var _cfg = $('#zkweb_zkcfg').datagrid('getSelected');

		if (_cfg) {

			$.messager.confirm('提示', '删除' + node.attributes.path
					+ '下所有子节点！确认吗？', function(r) {
				if (r) {
					// var s = node.text;
					if (node.attributes) {
						_path = node.attributes.path;
						$.post("zk/deleteNode", {
							path : _path,
							cacheId : _cfg.zkId
						}, function(data) {
							// alert("Data Loaded: " + data);
							$.messager.alert('提示', data);
							// initTree(_cfg.zkId);
							// var tab = $('#zkTab').tabs('getTab',0);
							// alert(tab.title);
							$('#zkTab').tabs('close', 0);
							var node = $('#zkTree').tree('getSelected');
							var parent = $('#zkTree').tree('getParent', node.target);
	                        $('#zkTree').tree('reload', parent.target);
						});
					}
				}
			});
		}

	} else {
		$.messager.alert('提示', '请选择一个节点');
	}
	;
}

function collapse() {
	var node = $('#zkTree').tree('getSelected');
	$('#zkTree').tree('collapse', node.target);
}

function expand() {
	var node = $('#zkTree').tree('getSelected');
	$('#zkTree').tree('reload', node.target);
}
// 保存节点
function addzkNode() {
	var _path = "/";
	var node = $('#zkTree').tree('getSelected');
	if (node) {
		// var s = node.text;
		if (node.attributes) {
			_path = node.attributes.path;
		}
	}
	/* 获取input框值 */
	_scheme = $("#schemelist ").val();
	_nodeName = $('#zkNodeName').val();
	_nodeData = $('#zkNoteData').val();
	_noteUsername = $('#zkNoteUser').val();
	_noteUserPassword = $('#zkNotePassword').val();
	/* 验证 */
	if (_nodeName == '') {
		$("#zkNodeNametip").css({
			"color" : "red"
		});
		$("#zkNodeNametip").html("*节点名称不可为空");
		$('#zkNodeName').focus();
		return;
	} else if (flag == false) {
		$("#zkNodeNametip").css({
			"color" : "red"
		});
		$("#zkNodeNametip").html("*该子节点已存在");
		$('#zkNodeName').focus();
		return;
	} else {
		$("#zkNodeNametip").html("SUCCESS");
		$("#zkNodeNametip").css({
			"color" : "#7ABD54"
		});
	}
	if (_scheme == 'digest') {
		if (_noteUsername == '') {
			$("#zkNoteUsertip").css({
				"color" : "red"
			});
			$("#zkNoteUsertip").html("*节点用户不可为空");
			$('#zkNoteUser').focus();
			return;
		} else {
			$("#zkNoteUser").html("SUCCESS");
			$("#zkNoteUser").css({
				"color" : "#7ABD54"
			});
		}
		if (_noteUserPassword == '') {
			$("#zkNotePasswordtip").css({
				"color" : "red"
			});
			$("#zkNotePasswordtip").html("*节点密码不可为空");
			$('#zkNotePassword').focus();
			return;
		} else {
			$("#zkNotePassword").html("SUCCESS");
			$("#zkNotePassword").css({
				"color" : "#7ABD54"
			});
		}
	}
	/* _nodePerms = $("input[name='perms']:checked").serialize(); */
	var $check_boxes = $("input[name='perms']:checked");
	if ($check_boxes.length <= 0) {
		alert('您未设置权限，请勾选！');
		return;
	}
	var _dropIds = new Array();
	$check_boxes.each(function() {
		_dropIds.push($(this).val());
	});

	var _cfg = $('#zkweb_zkcfg').datagrid('getSelected');

	if (_cfg) {
		$.post("zk/createNodeOfDegist", {
			nodeName : _nodeName,
			nodeData : _nodeData,
			scheme : _scheme,
			nodeUser : _noteUsername,
			nodePassword : _noteUserPassword,
			nodePerms : _dropIds,
			path : _path,
			cacheId : _cfg.zkId
		}, function(data) {
			// alert("Data Loaded: " + data);
			$.messager.alert('提示', data);
			$('#w').window('close');
			alert(cacheId)
			initTree(_cfg.zkId);
			var node = $('#zkTree').tree('getSelected');
	        $('#zkTree').tree('reload', node.target);
		});
		/* 清除所有复选框 */
		removeAllCheck();
		var node = $('#zkTree').tree('getSelected');
        $('#zkTree').tree('reload', node.target);
	} else {
		$.messager.alert('提示', '你必须选择一个配置');
	}
}
// 新增权限
function addzkACL() {
	var _path = "/";
	var node = $('#zkTree').tree('getSelected');
	if (node) {
		// var s = node.text;
		if (node.attributes) {
			_path = node.attributes.path;
		}
	}
	/* 获取input框值 */
	_scheme = $("#schemelistACL ").val();
	_adminName = $('#zkAdminName').val();
	_adminPwd = $('#zkAdminPwd').val();
	_noteUsername = $('#zkNoteUserACL').val();
	_noteUserPassword = $('#zkNotePasswordACL').val();
	if (_scheme == 'digest') {
		if (_noteUsername == '') {
			$("#zkNoteUsertipACL").css({
				"color" : "red"
			});
			$("#zkNoteUsertipACL").html("*节点用户不可为空");
			$('#zkNoteUserACL').focus();
			return;
		} else {
			$("#zkNoteUsertipACL").html("SUCCESS");
			$("#zkNoteUsertipACL").css({
				"color" : "#7ABD54"
			});
		}
		if (_noteUserPassword == '') {
			$("#zkNotePasswordtipACL").css({
				"color" : "red"
			});
			$("#zkNotePasswordtipACL").html("*节点密码不可为空");
			$('#zkNotePasswordACL').focus();
			return;
		} else {
			$("#zkNotePasswordtipACL").html("SUCCESS");
			$("#zkNotePasswordtipACL").css({
				"color" : "#7ABD54"
			});
		}
	}
	/* _nodePerms = $("input[name='perms']:checked").serialize(); */
	var $check_boxes = $("input[name='permsACL']:checked");
	if ($check_boxes.length <= 0) {
		alert('您未设置权限，请勾选！');
		return;
	}
	var _dropIds = new Array();
	$check_boxes.each(function() {
		_dropIds.push($(this).val());
	});

	var _cfg = $('#zkweb_zkcfg').datagrid('getSelected');
	if (_cfg) {
		$.post("zk/addNodeAcl", {
			scheme : _scheme,
			adminName : _adminName,
			adminPwd : _adminPwd,
			nodeUser : _noteUsername,
			nodePassword : _noteUserPassword,
			nodePerms : _dropIds,
			path : _path,
			cacheId : _cfg.zkId
		}, function(data) {
			// alert("Data Loaded: " + data);
			$.messager.alert('提示', data);
			$('#wacl').window('close');
			initTree(_cfg.zkId);
		});
		/* 清除所有复选框 */
		removeAllCheck();
	} else {
		$.messager.alert('提示', '你必须选择一个配置');
	}
}

/* 清空所有复选框 */
function hideUserAuth() {
	$("#userAuth").hide();
}

/* 清空所有复选框 */
function removeAllCheck() {
	$('#zkNodeName').val('');
	$('#zkNoteData').val('');
	/*$('#zkNoteUser').val('');
	$('#zkNotePassword').val('');*/
	$("input[name='perms']:checked").attr("checked", false);
	$("#zkNodeNametip").html("*");
	$("#zkNoteUsertip").html("*");
	$("#zkNotePasswordtip").html("*");
	$("#zkNodeNametip").css({
		"color" : "red"
	});
	$("#zkNoteUsertip").css({
		"color" : "red"
	});
	$("#zkNotePasswordtip").css({
		"color" : "red"
	});
	$("#des").val('');
	$("#connectstr").val('');
	$("#sessiontimeout").val('');
}
/* 清空所有复选框 */
function removeAllCheckACL() {
	$('#zkAdminName').val('');
	$('#zkAdminPwd').val('');
	$('#zkNodeNameACL').val('');
	$('#zkNoteUserACL').val('');
	$('#zkNotePasswordACL').val('');
	$("input[name='permsACL']:checked").attr("checked", false);
	$("#zkNodeNametipACL").html("*");
	$("#zkNoteUsertipACL").html("*");
	$("#zkNotePasswordtipACL").html("*");
	$("#zkNodeNametipACL").css({
		"color" : "red"
	});
	$("#zkNoteUsertipACL").css({
		"color" : "red"
	});
	$("#zkNotePasswordtipACL").css({
		"color" : "red"
	});
}

/** ************************************************************************************************************************* */

function saveCfg() {
	var _zkUserName=$("#zkUserName").val();
	var _zkUserPwd=$("#zkUserPwd").val();
	var _zkDescription=$("#zkDescription").val();
	var _zkConnectStr=$("#zkConnectStr").val();
	var _zkSessionTimeOut=$("#zkSessionTimeOut").val();
	if(_zkUserName==''){
		alert("请输入用户名");
		return;
	}
	if(_zkUserPwd==''){
		alert("请输入密码");
		return;
	}
	if(_zkDescription==''){
		alert("请输入配置描述信息");
		return;
	}
	if(_zkConnectStr==''){
		alert("请输入连接地址");
		return;
	}
	if(_zkSessionTimeOut==''){
		alert("请输入超时时间");
		return;
	}
	$.messager.progress();
	$('#zkweb_add_cfg_form').form('submit', {
		url : 'zkcfg/addZkCfg',
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid) {
				$.messager.progress('close'); // hide progress bar while the
												// form is invalid
			}
			return isValid; // return false will stop the form submission
		},
		success : function(data) {
			$.messager.alert('提示', data);
			$('#zkweb_zkcfg').datagrid("reload");
			$('#zkweb_add_cfg').window('close');
			$.messager.progress('close'); // hide progress bar while submit
			$("#zkUserName").val('');
			$("#zkUserPwd").val('');
			$("#zkDescription").val('');
			$("#zkConnectStr").val('');
			$("#zkSessionTimeOut").val('');
		}
	});
}

function updateCfg() {

	$.messager.progress();
	$('#zkweb_up_cfg_form').form('submit', {
		url : 'zkcfg/updateZkCfg',
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid) {
				$.messager.progress('close'); // hide progress bar while the
												// form is invalid
			}
			return isValid; // return false will stop the form submission
		},
		success : function(data) {
			$.messager.alert('提示', data);
			$('#zkweb_zkcfg').datagrid("reload");
			$('#zkweb_up_cfg').window('close');
			$.messager.progress('close'); // hide progress bar while submit
											// successfully
		}
	});
}

function openUpdateWin() {

	var _cfg = $('#zkweb_zkcfg').datagrid('getSelected');
	if (_cfg) {
		$('#zkweb_up_cfg').window('open');
		$('#zkweb_up_cfg_form').form("load",
				"zkcfg/queryZkCfgById?zkId=" + _cfg.zkId);
	} else {
		$.messager.alert('提示', '请选择一条记录');
	}

}

function openDelWin() {

	var _cfg = $('#zkweb_zkcfg').datagrid('getSelected');
	if (_cfg) {

		$.messager.confirm('提示', '确认删除这个配置吗?', function(r) {
			if (r) {
				/* alert(_cfg.zkId); */
				$.get('zkcfg/delZkCfg', {
					zkId : _cfg.zkId
				}, function(data) {
					$.messager.alert('提示', data);
				});
				$('#zkweb_zkcfg').datagrid("reload");
				initTree();
				$('#zkTab').tabs('close', 0);
				// $('#zkweb_up_cfg').window('open');
				// $('#zkweb_up_cfg_form').form("load","zkcfg/queryZkCfgById?id="+_cfg.zkId);
			}
		});
		// $('#zkweb_zkcfg').datagrid('selectRow',0);
	} else {
		$.messager.alert('提示', '请选择一条记录');
	}
}
window.onload = function() {
}

$(function($) {
	// 弹出登录
	$("#changePwd").hover(function() {
		$(this).stop().animate({
			opacity : '1'
		}, 600);
	}, function() {
		$(this).stop().animate({
			opacity : '0.6'
		}, 1000);
	}).on('click', function() {
		$("body").append("<div id='mask11'></div>");
		$("#mask11").addClass("mask11").fadeIn("slow");
		$("#LoginBox11").fadeIn("slow");
	});
	//
	// 按钮的透明度
	$("#loginbtn").hover(function() {
		$(this).stop().animate({
			opacity : '1'
		}, 600);
	}, function() {
		$(this).stop().animate({
			opacity : '0.8'
		}, 1000);
	});
	// 关闭
	$(".close_btn").hover(function() {
		$(this).css({
			color : 'black'
		})
	}, function() {
		$(this).css({
			color : '#999'
		})
	}).on('click', function() {
		$("#LoginBox11").fadeOut("fast");
		$("#mask11").css({
			display : 'none'
		});
	});
});

function clickDecide() {
	var oldPwd = $("#input_name").val();
	var newPwd = $("#input_pwd").val();
	if (oldPwd == "")
		$("#input_notice").html("请输入当前使用的密码！");
	else if (newPwd == "")
		$("#input_notice").html("请输入新密码！");
	else {
		$.post("zk/changePwdOperation", {
			oldPwd : oldPwd,
			newPwd : newPwd
		}, function(data) {
			if (data == 1) {
				alert("修改成功！");
				$("#LoginBox11").fadeOut("fast");
				$("#mask11").css({
					display : 'none'
				});
				window.location.href = "/zkWeb/";
			} else if (data == 0)
				$("#input_notice").html("输入的当前使用的密码错误，请重新输入！");
			else
				$("#input_notice").html("读取用户列表出错，请重试...");
		});
	}
}

function exitLogin() {
	$.post("zk/exitLogin", function(data) {
		window.location.href = "/zkWeb/";
	});
}