<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
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
<!-- 检索  -->
<form action="sockettdlimit/list.do?type=2" method="post" name="Form" id="Form">
<table style="margin-top:5px;">
		<tr>
			<td>
				<div class="nav-search">
					<span class="input-icon"> <input
						name="keywords"
						class="nav-search-input" autocomplete="off"
						id="nav-search-input" type="text" name="keywords"
						value="${pd.keywords}" placeholder="这里输入SOCKET ID" /> <i
						class="ace-icon fa fa-search nav-search-icon"></i>
					</span>
				</div>
			</td>	
			<c:if test="${QX.cha == 1 }">
				<td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>检索</a></td>
			</c:if>						
	<!-- 检索  END-->
</tr>
</table>
	
	<!--列表 -->
	<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;" >序号</th>
									<th class="center" ><span data-toggle="tooltip" title="用户名">申请人</span></th>
									<th class="center" ><span data-toggle="tooltip" title="操作状态">操作状态</span></th>
									<th class="center" ><span data-toggle="tooltip" title="操作时间">申请时间</span></th>
									
									<th class="center" ><span data-toggle="tooltip" title="SOCKET_ID">SOCKET_ID</span></th>
									<th class="center" ><span data-toggle="tooltip" title="CUSTOMER_CODE">CUSTOMER_CODE</span></th>
									<th class="center" ><span data-toggle="tooltip" title="DEVICE_NAME">DEVICE_NAME</span></th>
									<th class="center"><span data-toggle="tooltip" title="OPERATION">OPERATION</span></th>
									<th class="center"><span data-toggle="tooltip" title="TD_LIMIT">TD_LIMIT</span></th>
									
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList_r}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList_r}" var="var" varStatus="vs">

										<tr>
											<td class='center' style="width: 30px;vertical-align: middle;">${vs.index+1}</td>
											<td class='center' style="vertical-align: middle;">${var.USERNAME }</td>
											<td class='center' style="vertical-align: middle;">${var.STATUS	}</td>
											<td class='center' style="vertical-align: middle;">${var.CZTIME  }</td>
											
											<td class='center' style="vertical-align: middle;">${mcnList[vs.count-1].SOCKET_ID}</td>
										    <td class='center' style="vertical-align: middle;">${mcnList[vs.count-1].CUSTOMER_CODE}</td>
										    <td class='center' style="vertical-align: middle;">${mcnList[vs.count-1].DEVICE_NAME}</td>
											<td class='center' style="vertical-align: middle;">${mcnList[vs.count-1].OPERATION}</td>	
											<td class='center' style="vertical-align: middle;">${mcnList[vs.count-1].TD_LIMIT}</td>	
											
											<!-- 操作按钮start -->
											<td class="center" style="width:86px"> 
												<c:if test="${QX.apr != 1 && QX.apr != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.apr == 1 }">
														<a class="btn btn-xs btn-success" title="审批通过" onclick="agree('${var.ID}');" >
															<i class="ace-icon fa fa-check bigger-120" title="审批通过"></i>
														</a>
													
														<a class="btn btn-xs btn-danger" onclick="deny('${var.ID}');">
														<i class="ace-icon fa fa-close bigger-120" title="审批不通过"></i>
														</a>
													</c:if>
												
												</div>
												
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.apr == 1 }">
																<li>
																	<a style="cursor:pointer;" onclick="agree('${var.ID}');" class="tooltip-success" data-rel="tooltip" title="审批通过">
																		<span class="green">
																			<i class="ace-icon fa fa-check bigger-120"></i>
																		</span>
																	</a>
																</li>
															
																
																<li>
																	<a style="cursor:pointer;" onclick="deny('${var.ID}');" class="tooltip-error" data-rel="tooltip" title="审批不通过">
																		<span class="red">
																			<i class="ace-icon fa fa-close bigger-120"></i>
																		</span>
																	</a>
																</li>
																
															</c:if>
															
														</ul>
													</div>
												</div>
												
											</td>
											<!-- 操作按钮end -->
										</tr>
										
									</c:forEach>				
										
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="100" class="center">您无权查看</td>
										</tr>
									</c:if> 
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						
				<div class="page-header position-relative">
					<table style="width:100%;">
						<tr>
							<td style="vertical-align:top;">
								<c:if test="${QX.apr == 1 }">
										<a class="btn btn-mini btn-warning" onclick="window.location.href='<%=basePath%>sockettdlimit/list.do';">切换回数据视图</a>
								</c:if>
							</td>
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
						</tr>
					</table>
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

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->
	
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>

	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	

	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		
		
		//查询
		function searchs(){
			top.jzts();
			$("#Form").submit();
		}
		//审批通过
		function agree(ID){
			bootbox.confirm("确定要审批通过吗?", function(result) {
				if(result) {
					top.jzts();   			
					var url = "<%=basePath%>sockettdlimit/agree.do?approvalID="+ID+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
				};
			});
		}
		
		//审批不通过
		function deny(ID){
			bootbox.confirm("确定不同意审批吗?", function(result) {
				if(result) {
					top.jzts();   			
					var url = "<%=basePath%>sockettdlimit/deny.do?approvalID="+ID+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
				};
			});
		}
	
		
		//悬浮
		$(function () { $("[data-toggle='tooltip']").tooltip(); });
		
		
	
			//查看详情
			function editMember(USER_PHONE){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="资料";
				 diag.URL = '<%=basePath%>member/showDetails.do?USER_PHONE='+USER_PHONE;
				 diag.Width = 469;
				 diag.Height = 510;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						nextPage('${page.currentPage}');
					}
					diag.close();
				 };
				 diag.show();
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
</body>
</html>