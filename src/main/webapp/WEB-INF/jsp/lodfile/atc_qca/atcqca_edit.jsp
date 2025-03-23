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
								<form action="atc/qca/${msg}.do" name="atcForm" id="atcForm" method="post">
									<input type="hidden" name="idx" id="idx" value="${pd.idx }"/>
									<input type="hidden" name="device_check" id="device_check" value="${pd.device }"/>
									<input type="hidden" name="family_name_check" id="family_name_check" value="${pd.family_name }"/>
									<input type="hidden" name="operation_check" id="operation_check" value="${pd.operation }"/>
									<input type="hidden" name="operation_code_check" id="operation_code_check" value="${pd.operation_code }"/>						
									<input type="hidden" name="customer_code_check" id="customer_code_check" value="${pd.customer_code }"/>
									<input type="hidden" name="lod_file_check" id="lod_file_check" value="${pd.lod_file }"/>
								
									<div id="zhongxin" style="padding-top: 13px;">
									<div id="hasQCAData" class="alert alert-warning" style="display: none">									 
									   <strong>警告！</strong>此数据存在重复值。
									</div>
									<table id="table_report" class="table table-striped table-bordered table-hover">

										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Device:</td>
											<td><input type="text" name="device" id="device" value="${pd.device }" maxlength="32"  title="Device" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Family&nbsp;Name:</td>
											<td><input type="text" name="family_name" id="family_name" value="${pd.family_name }" maxlength="32"  title="Family_Name" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Operation:</td>
											<td><input type="text" name="operation" id="operation" value="${pd.operation }" maxlength="32"  title="Operation" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Operation&nbsp;Code:</td>
											<td><input type="text" name="operation_code" id="operation_code" value="${pd.operation_code }" maxlength="32"  title="Operation_Code" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Customer&nbsp;Code:</td>
											<td><input type="text" name="customer_code" id="customer_code" value="${pd.customer_code}" maxlength="32"  title="Cust_Code" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Lodfile:</td>
											<td><input type="text" name="lod_file" id="lod_file" value="${pd.lod_file}" maxlength="32"  title="Lod_File" style="width:98%;"/></td>
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
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script src="static/ace/js/bootbox.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	//保存
	function save(){
		if($("#device").val()==""){
			$("#device").tips({
				side:3,
	            msg:'【device】 不能为空！',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#device").focus();
			return false;
		}
		if($("#family_name").val()==""){
			$("#family_name").tips({
				side:3,
	            msg:'【family_name】 不能为空！',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#family_name").focus();
			return false;
		}
		
		if($("#operation").val()==""){
			$("#operation").tips({
				side:3,
	            msg:'【operation】 不能为空！',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operation").focus();
			return false;
		}

		if($("#operation_code").val()==""){
			$("#operation_code").tips({
				side:3,
	            msg:'【operation_code】 不能为空！',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operation").focus();
			return false;
		}
		
		if($("#customer_code").val()==""){
			$("#customer_code").tips({
				side:3,
	            msg:'【customer_code】 不能为空！',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#operation").focus();
			return false;
		}

		if($("#lod_file").val()==""){
			$("#lod_file").tips({
				side:3,
	            msg:'【lod_file】 不能为空！',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#lod_file").focus();
			return false;
		}
		 if($("#idx").val()==""){
				hasQCA();
			}else{
			/* 	if(($("#device")!=$("#device_check"))&&($("#operation")!=$("#operation_check"))&&($("#customer_code")!=$("#customer_code_check"))){
					hasQCA();
				}else{ */
					$("#atcForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
					bootbox.alert("操作成功，已邮件提醒管理员及时审批！");
			//	}
			} 
	}
	
	//判断数据是否存在
	function hasQCA(){
		var device = $.trim($("#device").val());
		var operation = $.trim($("#operation").val());
		var customer_code = $.trim($("#customer_code").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>atc/qca/hasQCA.do',
	    	data: {device:device,operation:operation,customer_code:customer_code},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#atcForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
					bootbox.alert("操作成功，已邮件提醒管理员及时审批！");
				 }else{
					$("#device").css("background-color","#D16E6C");
					$("#operation").css("background-color","#D16E6C");
					$("#customer_code").css("background-color","#D16E6C");
					//setTimeout("$('#idx').val('此数据存在重复记录!')",500);
					$("#hasQCAData").show();
				 }
			}
		});
	}

	$(function() {
		if('${msg}'=="edit"){
			 document.atcForm.device.readOnly = true; 
			   
		}
	});
	
	
	
	
	
	
	
	
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
	});
	
	
</script>
</html>