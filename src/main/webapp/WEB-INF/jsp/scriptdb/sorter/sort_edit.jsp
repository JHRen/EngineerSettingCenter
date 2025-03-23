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
								<form action="script/sortprogram/${msg}.do" name="editForm" id="editForm" method="post">
									<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
									
									<div id="zhongxin" style="padding-top: 13px;">
									<div id="hasCPData" class="alert alert-warning" style="display: none">									 
									   <strong>警告！</strong>TARGET DEVICE_18、AMKORWWOPERNAME存在重复值。
									</div>
									<table id="table_report" class="table table-striped table-bordered table-hover">

										<tr>
											<td style="width:100px;text-align: right;padding-top: 13px;">Group:</td>
											<td><input type="text" name="GROUP" id="GROUP" value="${pd.GROUP }" maxlength="60"  title="GROUP" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:100px;text-align: right;padding-top: 13px;">Device:</td>
											<td><input type="text" name="DEVICE" id="DEVICE" value="${pd.DEVICE }" maxlength="60"  title="DEVICE" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:100px;text-align: right;padding-top: 13px;">Target Device:</td>
											<td><input type="text" name="TARGET_DEVICE" id="TARGET_DEVICE" value="${pd.TARGET_DEVICE }" maxlength="60"  title="TARGET_DEVICE" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:100px;text-align: right;padding-top: 13px;">Target Device(18位):</td>
											<td><input type="text" name="TARGET_DEVICE_18" id="TARGET_DEVICE_18" value="${pd.TARGET_DEVICE_18}" maxlength="18"  title="TARGET_DEVICE_18" style="width:98%;"/></td>
										</tr>
										
                                        <tr>
                                            <td style="width:100px;text-align: right;padding-top: 13px;">AMKORWWOPERNAME:</td>
                                            <td><input type="text" name="AMKORWWOPERNAME" id="AMKORWWOPERNAME" value="${pd.AMKORWWOPERNAME}" maxlength="60"  title="AMKORWWOPERNAME" style="width:98%;"/></td>
                                        </tr>
                                          <tr>
                                            <td style="width:100px;text-align: right;padding-top: 13px;">PROCESS CODE:</td>
                                            <td><input type="text" name="PROCESS_CODE" id="PROCESS_CODE" value="${pd.PROCESS_CODE }" maxlength="60"  title="PROCESS_CODE" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:100px;text-align: right;padding-top: 13px;">PKG SORT Handler:</td>
                                            <td><input type="text" name="PKG_SORT_HANDLER" id="PKG_SORT_HANDLER" value="${pd.PKG_SORT_HANDLER}" maxlength="60"  title="PKG_SORT_HANDLER" style="width:98%;"/></td>
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
		  $("#editForm").submit();
	      $("#zhongxin").hide();
		  $("#zhongxin2").show();	
		}  
	}
	
	//判断数据是否存在
	function hasData(){
		var AMKORWWOPERNAME = $.trim($("#AMKORWWOPERNAME").val());
		var TARGET_DEVICE_18 = $.trim($("#TARGET_DEVICE_18").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>script/sortprogram/hasData.do',
	    	data: {AMKORWWOPERNAME:AMKORWWOPERNAME,TARGET_DEVICE_18:TARGET_DEVICE_18},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#editForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#AMKORWWOPERNAME").css("background-color","#D16E6C");
					$("#TARGET_DEVICE_18").css("background-color","#D16E6C");
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