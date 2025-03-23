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
								<form action="atc/cp/${msg}.do" name="atcForm" id="atcForm" method="post">
									<input type="hidden" name="idx" id="idx" value="${pd.idx }"/>
									<input type="hidden" name="device_check" id="device_check" value="${pd.device }"/>
									<input type="hidden" name="family_name_check" id="family_name_check" value="${pd.family_name }"/>
									<input type="hidden" name="operation_check" id="operation_check" value="${pd.operation }"/>
									<input type="hidden" name="operation_code_check" id="operation_code_check" value="${pd.operation_code }"/>						
									<input type="hidden" name="customer_code_check" id="customer_code_check" value="${pd.customer_code }"/>
									<input type="hidden" name="lod_file_check" id="lod_file_check" value="${pd.lod_file }"/>
									<input type="hidden" name="MultiJobname" id="MultiJobname_Status" value="${pd.MultiJobname }"/>
								
									<div id="zhongxin" style="padding-top: 13px;">
									<div id="hasCPData" class="alert alert-warning" style="display: none">									 
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
										
										<tr id="MultiJobname_radio">
											<td	style="width: 79px; text-align: right; padding-top: 13px;">MultiJobname :</td>
											<td>
												<label style="float:left;padding-left: 8px;padding-top:7px;">
												<input name="form-field-status" type="radio" class="ace" id="form-field-status0" <c:if test="${pd.MultiJobname == 'M' }">checked="checked"</c:if> onclick="setStatus('M');"/>
												　　　<span class="lbl"> M</span>
												</label>
												<label style="float:left;padding-left: 5px;padding-top:7px;">
													<input name="form-field-status" type="radio" class="ace" id="form-field-status1" <c:if test="${pd.MultiJobname == 'S' }">checked="checked"</c:if> onclick="setStatus('S');"/>
													<span class="lbl"> S</span>
												</label>
       
											</td>	
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
		var device=$("#device").val();
		var family_name=$("#family_name").val();
		var operation=$("#operation").val();
		var operation_code=$("#operation_code").val();
		var customer_code=$("#customer_code").val();
		var lod_file=$("#lod_file").val();
		var MultiJobname_Status = $("#MultiJobname_Status").val();
		
		if($("#MultiJobname_Status").val()==""){
			$("#MultiJobname_radio").tips({
				side:3,
	            msg:'【MultiJobname】 选项不能为空！',
	            bg:'#AE81FF',
	            time:2
	        });
			//$("#device").focus();
			return false;
		}
		
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
			$("#customer_code").focus();
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
				hasCP();	
		}else{
		/* 	if(($("#device")!=$("#device_check"))&&($("#operation")!=$("#operation_check"))&&($("#customer_code")!=$("#customer_code_check"))){
				hasCP();
			}else{ */
/* 	 		bootbox.confirm("确认修改 device:["+device+"] family name:["+family_name+"] operation:["+operation+"] operation_code:["+operation_code+"] customer_code:["+customer_code+"] lod_file:["+lod_file+"]", function(result) {
				if(result) {
				$("#atcForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();					
				}
			});  */
			if($("#MultiJobname_Status").val()!=""){
				bootbox.confirm("确认MultiJobname为 "+MultiJobname_Status+"", function(result) {
					if(result) {
						$("#atcForm").submit();
						$("#zhongxin").hide();
						$("#zhongxin2").show();	
						bootbox.alert("操作成功，已邮件提醒管理员及时审批！");
					}
				})
			
			}
			
		
		//	}
		} 
	}
	
	//判断数据是否存在
	function hasCP(){
		var device = $.trim($("#device").val());
		var operation = $.trim($("#operation").val());
		var customer_code = $.trim($("#customer_code").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>atc/cp/hasCP.do',
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
					$("#hasCPData").show();
				 }
			}
		});
	}

	
	$(function() {
		if('${msg}'=="edit"){
			 document.atcForm.device.readOnly = true; 			   
		}
	});
	
	//设置状态
	function setStatus(value){
			$("#MultiJobname_Status").val(value);	
	}
	
	
</script>
</html>