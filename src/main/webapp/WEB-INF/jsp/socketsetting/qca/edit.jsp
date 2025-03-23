<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
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
							<form action="sockettdlimit/${msg}.do" name="editForm" id="editForm" method="post">
								<input type="hidden" name="ID" id="ID" value="${pd.ID}" /> 
								<input type="hidden" name="SOCKET_ID_check"value="${pd.SOCKET_ID}" /> 
								<input type="hidden" name="CUSTOMER_CODE_check" value="${pd.CUSTOMER_CODE}" /> 
								<input type="hidden" name="DEVICE_NAME_check" value="${pd.DEVICE_NAME}" /> 
								<input type="hidden" name="OPERATION_check" value="${pd.OPERATION}" />
								<input type="hidden"name="TD_LIMIT_check" value="${pd.TD_LIMIT}" /> 

								<div id="zhongxin" style="padding-top: 13px;">
									<div id="hasCPData" class="alert alert-warning"
										style="display: none">
										<strong>警告！</strong>此数据存在重复值。
									</div>
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">SOCKET_ID:</td>
											<td><input type="text" name="SOCKET_ID"id="SOCKET_ID" value="${pd.SOCKET_ID }"maxlength="60" title="SOCKET_ID" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">CUSTOMER_CODE:</td>
											<td><input type="text" name="CUSTOMER_CODE" id="CUSTOMER_CODE" value="${pd.CUSTOMER_CODE }" maxlength="60" title="CUSTOMER_CODE"style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">DEVICE_NAME:</td>
											<td><input type="text" name="DEVICE_NAME" id="DEVICE_NAME"value="${pd.DEVICE_NAME }" maxlength="60"title="DEVICE_NAME" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">OPERATION:</td>
											<td><input type="text" name="OPERATION" id="OPERATION" value="${pd.OPERATION}" maxlength="60"title="OPERATION" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td style="width: 79px; text-align: right; padding-top: 13px;">TD_LIMIT:</td>
											<td><input type="text" name="TD_LIMIT" id="TD_LIMIT" value="${pd.TD_LIMIT}" maxlength="60" title="Operation"style="width: 98%;" /></td>
										</tr>

										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
												<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
											</td>
										</tr>
									</table>
								</div>
								<div id="zhongxin2" class="center" style="display: none">
									<br />
									<br />
									<br />
									<br />
									<img src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green"></h4>
								</div>
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
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 弹出窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	//保存
	function save(){
		 if($("#ID").val()==""){
			hasData();
		}else{
			if($("#SOCKET_ID").val()!=$("#SOCKET_ID_check").val()){
				hasData();
			}else{
				$("#editForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();	
				bootbox.alert("操作成功，已邮件提醒管理员及时审批！");
			}
		}  
	}
	
	//判断数据是否存在
	function hasData(){
		var SOCKET_ID = $.trim($("#SOCKET_ID").val());
		var CUSTOMER_CODE = $.trim($("#CUSTOMER_CODE").val());
		var DEVICE_NAME = $.trim($("#DEVICE_NAME").val());
		var OPERATION = $.trim($("#OPERATION").val());
		
		$.ajax({
			type: "POST",
			url: '<%=basePath%>sockettdlimit/hasData.do',
			data : {
				SOCKET_ID : SOCKET_ID,
				CUSTOMER_CODE : CUSTOMER_CODE,
				DEVICE_NAME : DEVICE_NAME,
				OPERATION : OPERATION
			},
			dataType : 'json',
			cache : false,
			success : function(data) {
				if ("success" == data.result) {
					$("#editForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
					bootbox.alert("操作成功，已邮件提醒管理员及时审批！");
				} else {
					$("#SOCKET_ID").css("background-color", "#D16E6C");
					$("#CUSTOMER_CODE").css("background-color", "#D16E6C");
					$("#DEVICE_NAME").css("background-color", "#D16E6C");
					$("#OPERATION").css("background-color", "#D16E6C");
					//setTimeout("$('#idx').val('此数据存在重复记录!')",500);
					$("#hasCPData").show();
				}
			}
		});
	}

	$(function() {
		//下拉框
		if (!ace.vars['touch']) {
			$('.chosen-select').chosen({
				allow_single_deselect : true
			});
			$(window).off('resize.chosen').on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({
						'width' : $this.parent().width()
					});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen',
					function(e, event_name, event_val) {
						if (event_name != 'sidebar_collapsed')
							return;
						$('.chosen-select').each(function() {
							var $this = $(this);
							$this.next().css({
								'width' : $this.parent().width()
							});
						});
					});
			$('#chosen-multiple-style .btn').on('click', function(e) {
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if (which == 2)
					$('#form-field-select-4').addClass('tag-input-style');
				else
					$('#form-field-select-4').removeClass('tag-input-style');
			});
		}
	});
</script>
</html>