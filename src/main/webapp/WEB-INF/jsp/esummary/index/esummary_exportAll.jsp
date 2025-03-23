<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 (带小时分钟)-->
	<link rel="stylesheet" href="static/ace/css/bootstrap-datetimepicker.css" />
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
					
					<form action="esummary/exportAll.do" name="Form" id="Form" method="post" target="_blank">
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开始时间:</td>
								<td>
									<div class="input-group bootstrap-timepicker">
									<input readonly="readonly" class="form-control" type="text" name="STARTTIME" id="STARTTIME" value="${pd.STARTTIME}" data-date-format="YYYY-MM-DD HH:mm:ss" maxlength="100" placeholder="这里输入开始时间" title="开始时间" style="width:100%;"/>
									<span class="input-group-addon"><i class="fa fa-clock-o bigger-110"></i></span>
									</div>
								</td>
								<td style="width:75px;text-align: right;padding-top: 13px;">结束时间:</td>
								<td>
									<div class="input-group bootstrap-timepicker">
									<input readonly="readonly" class="form-control" type="text" name="ENDTIME" id="ENDTIME" value="${pd.ENDTIME}" data-date-format="YYYY-MM-DD HH:mm:ss" maxlength="100" placeholder="这里输入结束时间" title="结束时间" style="width:100%;"/>
									<span class="input-group-addon"><i class="fa fa-clock-o bigger-110"></i></span>
									</div>
								</td>
							</tr>
					
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">lotno:</td>
								<td colspan="10">
									<textarea  name="LOTNO" id="LOTNO" maxlength="-1" placeholder="这里输入所有lotno" title="lotno" style="width:100%;height:160px;" >${pd.LOTNO}</textarea>
								</td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">导出</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 日期框(带小时分钟) -->
	<script src="static/ace/js/date-time/moment.js"></script>
	<script src="static/ace/js/date-time/locales.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
		$(function() {
			//日期框(带时间)
			$('.form-control').datetimepicker().next().on(ace.click_event, function(){
				$(this).prev().focus();
			});
		});
		
		
		//保存
		function save(){
			if($("#STARTTIME").val()==""){
				$("#STARTTIME").tips({
					side:3,
		            msg:'请输入开始时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STARTTIME").focus();
			return false;
			}
			if($("#ENDTIME").val()==""){
				$("#ENDTIME").tips({
					side:3,
		            msg:'请输入结束时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ENDTIME").focus();
			return false;
			}
			if($("#LOTNO").val()==""){
				$("#LOTNO").tips({
					side:3,
		            msg:'请输入要导出的LOTNO',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#LOTNO").focus();
			return false;
			}
			 var STARTTIME = $("#STARTTIME").val();
             var ENDTIME = $("#ENDTIME").val();
             var LOTNO = $("#LOTNO").val();
			
          // 提交表单
           $("#Form").submit();
           $("#zhongxin").hide();
           $("#zhongxin2").show();
           top.Dialog.close();
		}
		
	
		</script>
</body>
</html>