<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="head.jsp"></jsp:include>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/changePwd/ChangePwd.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/zk.js"></script>
<title>Zookeeper 管理系统</title>

</head>


<body class="easyui-layout" id="zkweb_body"> 
    <div id="LoginBox11">
        <div class="row1">
                                            修改密码
            <a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn"><img src="${pageContext.request.contextPath}/resources/easyui/themes/icons/no.png" alt="关闭"/></a>
        </div>
        
        <div class = "row2">
            <div id = "row21">
                <span id = "notice">当前密码:</span>
                <span id = "notice">新密码:</span>
            </div>
            <div id = "row22">
                <input id = "input_name" type = "password" value = "">
                <input id = "input_pwd" type = "password" value = "">
            </div>
            <div id = "row23">
                <input id = "login_btn" type = "button" value = "确认" onclick = "clickDecide()"><br>
                <label id = "input_notice"></label>
            </div>
        </div>
    </div> 
    <div data-options="region:'north',border:false" style="height:200px">
    
        <center>
        <table class="easyui-datagrid" title="Zookeeper管理系统" id="zkweb_zkcfg"
               data-options="pagination:true,singleSelect:true,fitColumns:true,rownumbers:true,pageSize:5" toolbar="#zkweb_tb" >  
            <thead>  
                <tr>  
                    <th data-options="field:'zkId'">ID</th>  
                    <th data-options="field:'zkDescription'">Description</th>  
                    <th data-options="field:'zkConnectStr'">Zookeeper Address</th>  
                    <th data-options="field:'zkSessionTimeOut'">Session TimeOut</th>  
                    <th data-options="field:'zkUserName'">用户</th>  
                </tr>  
            </thead>  
        </table> 
        
        <div id="zkweb_tb">    
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#zkweb_add_cfg').window('open');removeAllCheck();">添加</a>    
            <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="openUpdateWin()">更新</a>    
            <a href="#" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="openDelWin()">删除</a>      
            <!-- <a href="#" class="easyui-linkbutton" id = "changePwd" iconCls="icon-edit" plain="true">修改登录密码</a>      
            <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="exitLogin()">退出登陆</a> -->    
        </div>
        </center>
                
    </div> 
   
    <!--
    <div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>  
    
    <div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>  
    -->
    <div data-options="region:'west',split:true,title:'zookeeper tree'" style="width:150px;padding:10px;height:120px;">
        <ul id="zkTree" class="easyui-tree" >
        </ul> 
        <!-- right -->
        <div id="mm" class="easyui-menu" style="width:120px;">  
            <div onclick="javascript:$('#w').window('open');removeAllCheck();" data-options="iconCls:'icon-add'">添加子节点</div>  
        <div onclick="javascript:$('#wacl').window('open');removeAllCheckACL();" data-options="iconCls:'icon-add'">添加节点用户</div> 

            <div onclick="removePath()" data-options="iconCls:'icon-remove'">删除</div>  
            <div class="menu-sep"></div>  
            <div onclick="expand()">展开</div>  
            <div onclick="collapse()">收起</div>  
        </div>
    </div> 
    
    <div data-options="region:'center'" border="false" style="overflow: hidden;">  
        <div class="easyui-tabs" id="zkTab" data-options="tools:'#tab-tools',toolPosition:'left'">  
            <div title="Home" style="padding:10px;">  
                Welcome! 
            </div>  
        </div>  
        <div id="tab-tools">  
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="valiataConfig()"></a>  <!-- 添加 -->
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="removePath()"></a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="importdata()"></a>
        </div>
        
    </div>  

    <!-- import window -->
    <div id="import" class="easyui-window" title="批量导入数据" data-options="iconCls:'icon-add',modal:true,closed:true,maximizable:false" style="width:500px;padding:10px;">         
        

         <form method="post" action="zk/upload" enctype="multipart/form-data">
             <!--   <input type="text" name="name" />-->
             <p>请输入配置ID <input name="id" class="f1 easyui-textbox" data-options="required:true,tipPosition:'right'"></input></p>
             <p>选择上传的配置文件 <input type="file" name="file" /></p> 
             <p><input type="submit" value="Submit" data-options="tipPosition:'center'"/></p> 
         </form>
    </div>
    
    <!-- add -->
    <div id="w" class="easyui-window" title="添加节点" data-options="iconCls:'icon-add',modal:true,closed:true,maximizable:false" style="width:500px;padding:10px;">  
        
        <div style="text-align:left;padding:5px">
            输入节点名称:
            <input id="zkNodeName"  type="text" value="" data-options="required:true,tipPosition:'right'"></input>&nbsp;<span style="color: red" id="zkNodeNametip">*</span> 
        </div>
        <div style="text-align:left;padding:5px">
            输入节点数据:
            <input id="zkNoteData"  type="text" value="" data-options="required:false,tipPosition:'right'"></input>
        </div>
        <div style="text-align:left;padding:5px">
            请选择scheme:  
                 <select id="schemelist">
                    <option value="digest" id="digest" name="digest">digest</option>
                    <option value="world" id="world" name="world">world</option>
                </select>
        </div>
        <div id="userAuth">
            <div style="text-align:left;padding:5px">
                      设置节点用户:
                        <input id="zkNoteUser"  type="text" value="" data-options="required:true,tipPosition:'right'"></input>&nbsp;<span style="color: red" id="zkNoteUsertip">*</span>  
            </div>
            <div style="text-align:left;padding:5px">
                      设置用户密码:
                        <input id="zkNotePassword"  type="text" value="" data-options="required:true,tipPosition:'right'"></input>&nbsp;<span style="color: red" id="zkNotePasswordtip">*</span>
            </div>
        </div>
        <div style="text-align:left;padding:5px">
            设置用户权限:  
            <input type="checkbox" value="1" name="perms" id="permsRead"/>Read
            <input type="checkbox" value="2" name="perms" id="permsWrite"/>Write
            <input type="checkbox" value="4" name="perms" id="permsCreate"/>Create
            <input type="checkbox" value="8" name="perms" id="permsDelete"/>Delete
            <input type="checkbox" value="16" name="perms" id="permsAdmin"/>Admin
            <input type="checkbox" value="31" name="perms" id="permsAll"/>All
        </div>
        <div style="text-align:center;padding:5px">
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="addzkNode();">保存</a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="removeAllCheck()">清空</a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#w').window('close');removeAllCheck();" >取消</a>  
        </div>
        
    </div>
    <!-- acl w -->
    <div id="wacl" class="easyui-window" title="添加权限" data-options="iconCls:'icon-add',modal:true,closed:true,maximizable:false" style="width:500px;padding:10px;">  
        <div style="text-align:left;padding:5px">
            请选择scheme:  
                 <select id="schemelistACL">
                    <option value="digest" id="digestACL" name="digestACL">digest</option>
                    <option value="world" id="worldACL" name="worldACL">world</option>
                </select>
        </div>
        <div id="userAuthACL">
            <div style="text-align:left;padding:5px">
                      请输入ADMIN用户名:
                        <input id="zkAdminName"  type="text" value="" data-options="required:true,tipPosition:'right'"></input>&nbsp;<span style="color: red" id="zkAdminNametipACL">*</span>  
            </div>
            <div style="text-align:left;padding:5px">
                      请输入ADMIN密码:&nbsp;
                        <input id="zkAdminPwd"  type="text" value="" data-options="required:true,tipPosition:'right'"></input>&nbsp;<span style="color: red" id="zkAdminPwdACL">*</span>  
            </div>
            <div style="text-align:left;padding:5px">
                      添加ACL用户名:&nbsp;&nbsp;
                        <input id="zkNoteUserACL"  type="text" value="" data-options="required:true,tipPosition:'right'"></input>&nbsp;<span style="color: red" id="zkNoteUsertipACL">*</span>  
            </div>
            <div style="text-align:left;padding:5px">
                      添加ACL用户密码:&nbsp;
                        <input id="zkNotePasswordACL"  type="text" value="" data-options="required:true,tipPosition:'right'"></input>&nbsp;<span style="color: red" id="zkNotePasswordtipACL">*</span>
            </div>
        </div>
        <div style="text-align:left;padding:5px">
            设置用户权限:  
            <input type="checkbox" value="1" name="permsACL" id="permsACLRead"/>Read
            <input type="checkbox" value="2" name="permsACL" id="permsACLWrite"/>Write
            <input type="checkbox" value="4" name="permsACL" id="permsACLCreate"/>Create
            <input type="checkbox" value="8" name="permsACL" id="permsACLDelete"/>Delete
            <input type="checkbox" value="16" name="permsACL" id="permsACLAdmin"/>Admin
            <input type="checkbox" value="31" name="permsACL" id="permsACLAll"/>All
        </div>
        <div style="text-align:center;padding:5px">
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="addzkACL();">保存</a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="removeAllCheckACL()">清空</a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#wacl').window('close');removeAllCheckACL();" >取消</a>  
        </div>
    </div>
     <!-- acl w end-->
    <div id="zkweb_add_cfg" class="easyui-window" title="添加配置信息" data-options="iconCls:'icon-add',modal:true,closed:true,maximizable:false" style="width:330px;height:220px;padding:10px;">  

        
        <form id="zkweb_add_cfg_form" method="post" action="zkcfg/addZkCfg">  
            <table>    
                <tr>    
                    <td>Description:</td>    
                    <td><input name="zkDescription" id="zkDescription" type="text" value=""></input></td>    
                </tr>    
                <tr>    
                    <td>Zookeeper Address:</td>    
                    <td><input name="zkConnectStr" id="zkConnectStr"  type="text" value=""></input></td>    
                </tr>    
                <tr>    
                    <td>Session TimeOut:</td>    
                    <td><input name="zkSessionTimeOut" id="zkSessionTimeOut"  type="text" value=""></input></td>    
                </tr>    
                <tr>    
                    <td>用户:</td>    
                    <td><input name="zkUserName" id="zkUserName" type="text" value=""></input></td>    
                </tr>    
                <tr>    
                    <td>密码:</td>    
                    <td><input name="zkUserPwd" id="zkUserPwd" type="password" value=""></input></td>    
                </tr>    
                <tr>    
                    <td>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveCfg()">保存</a>
                    </td>    
                    <td>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#zkweb_add_cfg').window('close');removeAllCheck();" >取消</a> 
                    </td>    
                </tr>    
            </table>  
        </form>
        
    </div>  
    
    <div id="zkweb_up_cfg" class="easyui-window" title="更新配置信息" data-options="iconCls:'icon-update',modal:true,closed:true,maximizable:false" style="width:330px;height:220px;padding:10px;">  
        
        <form id="zkweb_up_cfg_form" method="post" action="zkcfg/updateZkCfg">  
            <input type="hidden" name="zkId"/>
            <table>    
                <tr>    
                    <td>Description:</td>    
                    <td><input name="zkDescription" id="zkDescription" type="text"></input></td>    
                </tr>    
                <tr>    
                    <td>Zookeeper Address:</td>    
                    <td><input name="zkConnectStr" id="zkConnectStr" type="text"></input></td>    
                </tr>    
                <tr>    
                    <td>Session TimeOut:</td>    
                    <td><input name="zkSessionTimeOut" id="zkSessionTimeOut" type="text"></input></td>    
                </tr>    
                <tr>    
                    <td>用户:</td>    
                    <td><input name="zkUserName" id="zkUserName" type="text"></input></td>    
                </tr>    
                <tr>    
                    <td>密码:</td>    
                    <td><input name="zkUserPwd" id="zkUserPwd"  type="password"></input></td>    
                </tr> 
                <tr>    
                    <td>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="updateCfg()">保存</a>
                    </td>    
                    <td>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="$('#zkweb_up_cfg').window('close');" >取消</a> 
                    </td>    
                </tr>    
            </table>  
        </form>
        
    </div>  

</body> 

</html>