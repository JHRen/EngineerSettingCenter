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
								<form action="probecard/tsmc/${msg}.do" name="editForm" id="editForm" method="post">
									<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
									<input type="hidden" name="Cust_Tool_Name_check"  value="${pd.Cust_Tool_Name}"/>
									<input type="hidden" name="Tool_Name_check"  value="${pd.Tool_Name}"/>
									<input type="hidden" name="Customer_check" value="${pd.Customer}"/>
									<input type="hidden" name="Device_Name_check"  value="${pd.Device_Name}"/>
									<input type="hidden" name="Operation_check"  value="${pd.Operation}"/>
									<input type="hidden" name="Touchdown_Per_Wafer_check"  value="${pd.Touchdown_Per_Wafer}"/>
									<input type="hidden" name="Touchdown_Limit_check"  value="${pd.Touchdown_Limit}"/>
									<input type="hidden" name="Gross_Die_Per_Wafer_check"  value="${pd.Gross_Die_Per_Wafer}"/>
									
									<div id="zhongxin" style="padding-top: 13px;">
									<div id="hasCPData" class="alert alert-warning" style="display: none">									 
									   <strong>警告！</strong>此数据存在重复值。
									</div>
									<table id="table_report" class="table table-striped table-bordered table-hover">

										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Cust&nbsp;Tool&nbsp;Name:</td>
											<td><input type="text" name="Cust_Tool_Name" id="Cust_Tool_Name" value="${pd.Cust_Tool_Name }" maxlength="60"  title="Device" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Tool&nbsp;Name:</td>
											<td><input type="text" name="Tool_Name" id="Tool_Name" value="${pd.Tool_Name }" maxlength="60"  title="Operation" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Customer Code:</td>
											<td><input type="text" name="Customer" id="Customer" value="${pd.Customer }" maxlength="60"  title="Operation_Code" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Device&nbsp;Name:</td>
											<td><input type="text" name="Device_Name" id="Device_Name" value="${pd.Device_Name}" maxlength="60"  title="Cust_Code" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Operation:</td>
											<td><input type="text" name="Operation" id="Operation" value="${pd.Operation}" maxlength="60"  title="Operation" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Touchdown&nbsp;Per&nbsp;Wafer:</td>
											<td><input type="text" name="Touchdown_Per_Wafer" id="Touchdown_Per_Wafer" value="${pd.Touchdown_Per_Wafer}" maxlength="60"  title="Cust_Code" style="width:98%;"/></td>
										</tr>
											<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">Touchdown&nbsp;Limit:</td>
											<td><input type="text" name="Touchdown_Limit" id="Touchdown_Limit" value="${pd.Touchdown_Limit}" maxlength="60"  title="Cust_Code" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">GrossDiePer_Wafer:</td>
											<td><input type="text" name="Gross_Die_Per_Wafer" id="Gross_Die_Per_Wafer" value="${pd.Gross_Die_Per_Wafer}" maxlength="60"  title="Gross_Die_Per_Wafer" style="width:98%;"/></td>
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
			if($("#MCN_Name").val()!=$("#MCN_Name_check").val()){
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
		var Cust_Tool_Name = $.trim($("#Cust_Tool_Name").val());
		var Device_Name = $.trim($("#Device_Name").val());
		var Operation = $.trim($("#Operation").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>probecard/tsmc/hasData.do',
			data: {Cust_Tool_Name:Cust_Tool_Name,Device_Name:Device_Name,Operation:Operation},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#editForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
					bootbox.alert("操作成功，已邮件提醒管理员及时审批！");
				 }else{
					$("#Cust_Tool_Name").css("background-color","#D16E6C");
					$("#Device_Name").css("background-color","#D16E6C");
					$("#Operation").css("background-color","#D16E6C");
					//setTimeout("$('#idx').val('此数据存在重复记录!')",500);
					$("#hasCPData").show();
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
	});
	
	
</script>
</html>