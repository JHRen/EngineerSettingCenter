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
<!-- datetime日期框 -->
<link rel='stylesheet'  href="static/ace/css/bootstrap-datetimepicker.min.css" >

<!-- jsp文件头和头部 -->
<%@ include file="../../jsp/system/index/top.jsp"%>
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
							<form action="otversion/${msg}.do" name="Form" id="Form"
								method="post">
								<input type="hidden" name="ID" id="ID" value="${pd.ID }" />
								
								<div id="zhongxin" style="padding-top: 13px;">
									<div id="hasCPData" class="alert alert-warning"
										style="display: none">
										<strong>警告！</strong>此数据存在重复值。
									</div>
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">FactoryType:</td>
											<td><input type="text" name="FactoryType"
												id="FactoryType" value="${pd.FactoryType }" 
												title="FactoryType" style="width: 98%;" /></td>
										</tr>

										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">MachineType:</td>
											<td><input type="text" name="MachineType"
												id="MachineType" value="${pd.MachineType }" 
												title="MachineType" style="width: 98%;" /></td>
										</tr>
										
										 <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">FactoryLocation:</td>
                                            <td>
                                            <input type="text" name="FactoryLocation"
                                                id="FactoryLocation" value="${pd.FactoryLocation }" title="FactoryLocation" style="width: 98%;" />
                                             </td>
                                         </tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">Path:</td>
											<td><input type="text" name="Path" id="Path"
												value="${pd.Path }" maxlength="32" title="Path"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">Version:</td>
											<td><input type="text" name="Version" id="Version"
												value="${pd.Version }" maxlength="32" title="Version"
												style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 79px; text-align: right; padding-top: 13px;">UpdateTime:</td>
											<td>
											 <input type="text" readonly="readonly" class="form_datetime" name="UpdateTime" id="UpdateTime"  value="${pd.UpdateTime}"  style="width: 98%;"/>
										</tr>
									  <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">Result:</td>
                                            <td>
                                            <input type="text" name="Result"
                                                id="Result" value="${pd.Result }" title="Result" style="width: 98%;" />
                                             </td>
                                         </tr>
										

										<tr>
											<td style="text-align: center;" colspan="10"><a
												class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
												class="btn btn-mini btn-danger"
												onclick="top.Dialog.close();">取消</a></td>
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
	<%@ include file="../../jsp/system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!-- 日期框 -->
	<script type="text/javascript" src="<%=basePath%>static/ace/js/date-time/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/ace/js/date-time/bootstrap-datetimepicker.zh-CN.js"></script>
	

	<script src="static/ace/js/bootbox.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	
	//保存
	function save(){
		var FactoryType=$("#FactoryType").val();
		var MachineType=$("#MachineType").val();
		
		if($("#FactoryType").val()==""){
			$("#FactoryType").tips({
				side:3,
	            msg:'【FactoryType】 不能为空！',
	            bg:'#AE81FF',
	            time:2
	        });
			$("##FactoryType").focus();
			return false;
		}
		if($("#MachineType").val()==""){
			$("#MachineType").tips({
				side:3,
	            msg:'【MachineType】 不能为空！',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MachineType").focus();
			return false;
		}
		 if($("#ID").val()==""){			
				hasCP();	
		}else{
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();	

		} 
	}
	
	//判断数据是否存在
	function hasCP(){
		var FactoryType = $.trim($("#FactoryType").val());
		var MachineType = $.trim($("#MachineType").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>otversion/hasCP.do',
			data : {
				FactoryType : FactoryType,
				MachineType : MachineType,
			},
			dataType : 'json',
			cache : false,
			success : function(data) {
				if ("success" == data.result) {
					$("#Form").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				} else {
					$("#FactoryType").css("background-color", "#D16E6C");
					$("#MachineType").css("background-color", "#D16E6C");
					//setTimeout("$('#idx').val('此数据存在重复记录!')",500);
					$("#hasCPData").show();
				}
			}
		});
	}
	

	

	$(function() {
		//日期框		
		$('.form_datetime').datetimepicker({
			format: "yyyy-mm-dd hh:ii:ss",
			language:  'zh-CN',
			minView: 0,
			minuteStep:5,
			 clearBtn: true
		}).on('changeDate',function(ev){     //设定失效时间必须要大于生效时间
			var startTime = $('#UpdateTime').val();
			$('#UpdateTime').datetimepicker('setStartDate',startTime);
		});
		
		if ('${msg}' == "edit") {
			// document.Form.device.readOnly = true; 			   
		}
	});
</script>
</html>