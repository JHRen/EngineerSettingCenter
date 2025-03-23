<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
								<form action="user/${msg }.do" name="userForm" id="userForm" method="post">
									<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
									<input type="hidden" name="ADMIN" id="admin" value="${pd.ADMIN }"/>
									<textarea style="display: none;" name="ROLE_IDS" id="ROLE_IDS" >${pd.ROLE_IDS }</textarea>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<c:if test="${fx != 'head'}">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">主职角色:</td>
											<td id="juese">
											<select class="chosen-select form-control" name="ROLE_ID" id="role_id" data-placeholder="请选择角色" style="vertical-align:top;" style="width:98%;" >
											<option value=""></option>
											<c:forEach items="${roleList}" var="role">
												<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
											</c:forEach>
											</select>
											</td>
										</tr>
										</c:if>
										<c:if test="${fx == 'head'}">
											<input name="ROLE_ID" id="role_id" value="${pd.ROLE_ID }" type="hidden" />
										</c:if>
										<c:if test="${fx != 'head'}">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">副职角色:</td>
											<td>
											<div>
												<select multiple="" class="chosen-select form-control" id="form-field-select-4" data-placeholder="选择副职角色">
													<c:forEach items="${roleList}" var="role">
														<option onclick="setROLE_IDS('${role.ROLE_ID }')" value="${role.ROLE_ID }" <c:if test="${role.RIGHTS == '1' }">selected</c:if>>${role.ROLE_NAME }</option>
													</c:forEach>
												</select>
											</div>
											</td>
										</tr>
										</c:if>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">用户名:</td>
											<td><input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }" maxlength="32" placeholder="这里输入用户名" title="用户名" style="width:98%;"/></td>
										</tr>
										<input type="hidden" name="NUMBER" id="NUMBER" value="1" maxlength="32" placeholder="这里输入编号" title="编号" " style="width:98%;"/>
										<!-- 
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">编号:</td>
											<td><input type="text" name="NUMBER" id="NUMBER" value="${pd.NUMBER }" maxlength="32" placeholder="这里输入编号" title="编号" onblur="hasN('${pd.USERNAME }')" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">密码:</td>
											<td><input type="password" name="PASSWORD" id="password"  maxlength="32" placeholder="输入密码" title="密码" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">确认密码:</td>
											<td><input type="password" name="chkpwd" id="chkpwd"  maxlength="32" placeholder="确认密码" title="确认密码" style="width:98%;"/></td>
										</tr> -->
										<input type="hidden" name="PASSWORD" id="password"  maxlength="32" placeholder="输入密码" title="密码" value="1" style="width:98%;"/>
										<input type="hidden" name="chkpwd" id="chkpwd"  maxlength="32" placeholder="确认密码" title="确认密码" value="1" style="width:98%;"/>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">姓名:</td>
											<td><input type="text" name="NAME" id="name"  value="${pd.NAME }"  maxlength="32" placeholder="这里输入姓名" title="姓名" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">座机号:</td>
											<td><input type="number" name="PHONE" id="PHONE"  value="${pd.PHONE }"  maxlength="32" placeholder="这里输入手机号" title="手机号" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">邮箱:</td>
											<td><input type="email" name="EMAIL" id="EMAIL"  value="${pd.EMAIL }" maxlength="50" placeholder="这里输入邮箱" title="邮箱" onblur="hasE('${pd.USERNAME }')" style="width:98%;"/></td>
										</tr>
										<tr>
										    <td style="width:79px;text-align: right;padding-top: 13px;">部门:</td>
                                            <td > 
                                                <select name="DEPARTMENT_ID" id="DEPARTMENT_ID"  title="请选择部门" style="width:98%;"> </select>
                                             </td>
										</tr>
										
											<tr id="zt">
											<td style="width:79px;text-align: right;padding-top: 13px;">级别 : </td>
											<td>
											<%-- <label style="float:left;padding-left: 8px;padding-top:7px;">
											<input name="form-field-status" type="radio" class="ace" id="form-field-status0" <c:if test="${pd.ADMIN == 0 }">checked="checked"</c:if> onclick="setAdmin('0');"/>
											　　　<span class="lbl"> 否</span>
											</label>
											<label style="float:left;padding-left: 5px;padding-top:7px;">
												<input name="form-field-status" type="radio" class="ace" id="form-field-status1" <c:if test="${pd.ADMIN == 1 }">checked="checked"</c:if> onclick="setAdmin('1');"/>
												<span class="lbl"> 是</span>
											</label> --%>
											<select name="LEVEL" id="LEVEL"  title="请选择级别" style="width:98%;"> 
												  <option value="" >请选择级别</option>
	                                              <option value='8' <c:if test="${pd.LEVEL == 8}">selected</c:if>>Engineer</option>
	                                              <option value='10' <c:if test="${pd.LEVEL == 10}">selected</c:if>>Leader</option>
	                                              <option value='12' <c:if test="${pd.LEVEL == 12}">selected</c:if>>Manager</option>
                                             </select>
											</td>
										</tr>	
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
											<td><input type="text" name="BZ" id="BZ"value="${pd.BZ }" placeholder="这里输入备注" maxlength="64" title="备注" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
												<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
											</td>
										</tr>
									</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
								</form>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		$('#form-field-select-4').addClass('tag-input-style');
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	//保存
	function save(){
		if($("#role_id").val()==""){
			$("#juese").tips({
				side:3,
	            msg:'选择角色',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#role_id").focus();
			return false;
		}
		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			$("#loginname").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}
		
		if($("#NUMBER").val()==""){
			$("#NUMBER").tips({
				side:3,
	            msg:'输入编号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#NUMBER").focus();
			return false;
		}else{
			$("#NUMBER").val($.trim($("#NUMBER").val()));
		}
		if($("#user_id").val()=="" && $("#password").val()==""){
			$("#password").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#password").focus();
			return false;
		}
		if($("#password").val()!=$("#chkpwd").val()){
			
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#chkpwd").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#PHONE").val()==""){
			
			$("#PHONE").tips({
				side:3,
	            msg:'输入手机号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		}
		/*  else if($("#PHONE").val().length != 11 && !myreg.test($("#PHONE").val())){ 		
			$("#PHONE").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		} */
		if($("#EMAIL").val()==""){
			
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}
		if($("#user_id").val()==""){
			hasU();
		}else{
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
		}
	
	//判断用户名是否存在
	function hasU(){
		var USERNAME = $.trim($("#loginname").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#loginname").css("background-color","#D16E6C");
					setTimeout("$('#loginname').val('此用户名已存在!')",500);
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $.trim($("#EMAIL").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱 '+EMAIL+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#EMAIL").val('');
				 }
			}
		});
	}
	
	//判断编码是否存在
	function hasN(USERNAME){
		var NUMBER = $.trim($("#NUMBER").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasN.do',
	    	data: {NUMBER:NUMBER,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#NUMBER").tips({
							side:3,
				            msg:'编号 '+NUMBER+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#NUMBER").val('');
				 }
			}
		});
	}
	$(function() {
		//下拉框
		if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			});
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		}
		
		//复选框全选控制
	    var active_class = 'active';
	    $('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
	        var th_checked = this.checked;//checkbox inside "TH" table header
	        $(this).closest('table').find('tbody > tr').each(function(){
	            var row = this;
	            if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
	            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
	        });
	    });
	    
	      //下拉框
	    var DEPARTMENT_ID = "${pd.DEPARTMENT_ID}";
	    $.ajax({
	        type: "POST",
	        url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
	        data: {DICTIONARIES_ID:'1ee7032181b54a6094b196a8c75bc74b'},//DICTIONARIES_ID 为客户分类
	        dataType:'json',
	        cache: false,
	        success: function(data){
	            $("#DEPARTMENT_ID").html('<option value="" >请选择部门</option>');
	             $.each(data.list, function(i, dvar){
	                 if(DEPARTMENT_ID == dvar.BIANMA){
	                     $("#DEPARTMENT_ID").append("<option value="+dvar.BIANMA+" selected='selected'>"+dvar.NAME+"</option>");
	                 }else{
	                     $("#DEPARTMENT_ID").append("<option value="+dvar.BIANMA+">"+dvar.NAME+"</option>");
	                 }
	             });
	        }
	    });
		
	});
	
	//设置状态
	function setAdmin(value){
			$("#admin").val(value);
	}
	
	//移除副职角色
	function removeRoleId(ROLE_ID){
		var OROLE_IDS = $("#ROLE_IDS");
		var ROLE_IDS = OROLE_IDS.val();
		ROLE_IDS = ROLE_IDS.replace(ROLE_ID+",fh,","");
		OROLE_IDS.val(ROLE_IDS);
	}
	//添加副职角色
	function addRoleId(ROLE_ID){
		var OROLE_IDS = $("#ROLE_IDS");
		var ROLE_IDS = OROLE_IDS.val();
		if(!isContains(ROLE_IDS,ROLE_ID)){
			ROLE_IDS = ROLE_IDS + ROLE_ID + ",fh,";
			OROLE_IDS.val(ROLE_IDS);
		}
	}
	function isContains(str, substr) {
	     return str.indexOf(substr) >= 0;
	 }
</script>
</html>