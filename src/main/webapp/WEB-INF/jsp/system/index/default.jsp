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
					<div class="hr hr-18 dotted hr-double"></div>
					<div class="row">
						<div class="col-xs-12">

							<div class="alert alert-block alert-success">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>
								<i class="ace-icon fa fa-check green"></i>
								欢迎使用 &nbsp;&nbsp;
							</div>
							
							
							<!-- <div id="main" style="width: 600px;height:300px;"></div> -->
							
							
						</div>
					</div>
					<!-- /.row -->
					
					
				 <div class="row">
						<div class="col-xs-12">
						    <table style="width: 100%;">
						       <thead><tr style="height: 40px;"><th style="border: 1px solid #d0d0d0;border-right: none;padding-left: 10px;background-color: #d0d0d0;"><span class="glyphicon glyphicon-user"></span>我的基本信息</th><th style="border: 1px solid #d0d0d0;border-left: none;background-color: #d0d0d0;"></th></tr></thead>
						       <tbody>
						         <tr style="height: 30px;"><td style="border: 1px solid #d0d0d0;width: 110px;text-align: right;background-color: skyblue;padding-right: 5px;">真实姓名:</td><td style="border: 1px solid #d0d0d0;padding-left: 5px;">${user.NAME}</td></tr>
						         <tr style="height: 30px;"><td style="border: 1px solid #d0d0d0;width: 110px;text-align: right;background-color: skyblue;padding-right: 5px;">拥有角色:</td><td style="border: 1px solid #d0d0d0;padding-left: 5px;">${userr.role.ROLE_NAME}</td></tr>
						         <tr style="height: 30px;"><td style="border: 1px solid #d0d0d0;width: 110px;text-align: right;background-color: skyblue;padding-right: 5px;">上次登录时间:</td><td style="border: 1px solid #d0d0d0;padding-left: 5px;">${user.LAST_LOGIN}</td></tr>
						         <tr style="height: 30px;"><td style="border: 1px solid #d0d0d0;width: 110px;text-align: right;background-color: skyblue;padding-right: 5px;">上次登录IP:</td><td style="border: 1px solid #d0d0d0;padding-left: 5px;">${user.IP}</td></tr>
						       </tbody>
						    </table>
						</div>
					</div> 
						
						
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->


		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
	</script>
<script type="text/javascript" src="static/ace/js/jquery.js"></script>
</body>
</html>