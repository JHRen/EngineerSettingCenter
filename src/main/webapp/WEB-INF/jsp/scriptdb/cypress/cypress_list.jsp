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
<form action="cypress/list.do" method="post" name="Form" id="Form">
	<table style="margin-top:5px;">
			<tr>
				<td>
					<div class="nav-search">
						<span class="input-icon"> <input
							name="keywords"
							class="nav-search-input" autocomplete="off"
							id="nav-search-input" type="text" name="keywords"
							value="${pd.keywords}" placeholder="这里输入CustLotNo" /> <i
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
				<th class="center" >CustLotNo</th>
				<th class="center" >Device Name</th>
				<th class="center" >Process</th>
				<th class="center">Qty In</th>
				<th class="center">Bin1</th>
				<th class="center"><span data-toggle="tooltip" title="Soft Bin21">Bin5</span></th>
				<th class="center"><span data-toggle="tooltip" title="Soft Bin38">Bin6</span></th>
				<th class="center"><span data-toggle="tooltip" title="Bin7-CAT23-CAT38">Bin7</span></th>
				<th class="center"><span data-toggle="tooltip" title="Bin7">Bin5+Bin6+Bin7</span></th>
				<th class="center">Bin8</th>
				<th class="center">操作</th>
			</tr>
		</thead>
								
		<tbody>
		<!-- 开始循环 -->	
		<c:choose>
			<c:when test="${not empty varList}">
				<c:if test="${QX.cha == 1 }">
				<c:forEach items="${varList}" var="var" varStatus="vs">
					<tr>
						<td class='center' style="width: 30px;vertical-align: middle;">${vs.index+1}</td>
						<td class='center' style="vertical-align: middle;">${var.CUSTOMER_LOTNO}</td>
					    <td class='center' style="vertical-align: middle;">${var.DEVICE_NAME}</td>
					    <td class='center' style="vertical-align: middle;">${var.OPERATION_CODE}</td>
						<td class='center' style="vertical-align: middle;">${var.QTY_IN}</td>			
						<td class='center' style="vertical-align: middle;">${var.BIN1}</td>                                   
						<td class='center' style="vertical-align: middle;">${var.BIN5}</td>                                   
						<td class='center' style="vertical-align: middle;">${var.BIN6}</td>                                   
                       <td class='center' style="vertical-align: middle;">${var.BIN5_BIN6_BIN7}</td>                                   
						<td class='center' style="vertical-align: middle;">${var.BIN7}</td>                                   
						<td class='center' style="vertical-align: middle;">${var.BIN8}</td>                                   									
						<!-- 操作按钮start -->
						<td class="center" style="width:86px"> 
							<c:if test="${QX.edit != 1 && QX.del != 1 }">
							<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
							</c:if>
							<div class="hidden-sm hidden-xs btn-group">
								<c:if test="${QX.edit == 1 }">
								<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.ID}');">
									<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
								</a>
								</c:if>
								<c:if test="${QX.del == 1 }">
								<a class="btn btn-xs btn-danger" onclick="del('${var.ID}','${var.MCN_Name} ');">
									<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
								</a>
								</c:if>
							</div>
							<div class="hidden-md hidden-lg">
								<div class="inline pos-rel">
									<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
										<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
									</button>
									<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
										<c:if test="${QX.edit == 1 }">
										<li>
											<a style="cursor:pointer;" onclick="edit('${var.ID}');" class="tooltip-success" data-rel="tooltip" title="修改">
												<span class="green">
													<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
												</span>
											</a>
										</li>
										</c:if>
										<c:if test="${QX.del == 1 }">
										<li>
											<a style="cursor:pointer;" onclick="del('${var.ID}','${var.MCN_Name} }');" class="tooltip-error" data-rel="tooltip" title="删除">
												<span class="red">
													<i class="ace-icon fa fa-trash-o bigger-120"></i>
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
								<c:if test="${QX.add == 1 }">
								<a class="btn btn-mini btn-success" onclick="add();">新增</a>
								</c:if>
								<%-- <c:if test="${QX.del == 1 }">
								<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
								</c:if> --%>
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
		
		//删除
		function del(ID,MCN_Name){
			bootbox.confirm("确定要删除MCN_Name为["+MCN_Name+"]的信息吗?", function(result) {
				if(result) {
					top.jzts();            
					var url = "<%=basePath%>mcncheck/gf/delete.do?ID="+ID+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
					bootbox.alert("操作成功，已邮件提醒管理员及时审批！");
				};
			});
		}
		
		//悬浮
		$(function () { $("[data-toggle='tooltip']").tooltip(); });
		
		//增加
	function add(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="新增";
	 diag.URL = '<%=basePath%>mcncheck/gf/add.do';
	 diag.Width = 469;
	 diag.Height = 252;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 if('${page.currentPage}' == '0'){
				 top.jzts();
				 setTimeout("self.location=self.location",100);
			 }else{
				 nextPage('${page.currentPage}');
			 }
		}
		diag.close();
	 };
	 diag.show();
}
		
	//修改
	function edit(ID){
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="修改";
		 diag.URL = '<%=basePath%>mcncheck/gf/goEdit.do?ID='+ID;
		 diag.Width = 469;
		 diag.Height = 252;
		 diag.CancelEvent = function(){ //关闭事件
			 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				 nextPage('${page.currentPage}');
			}
			diag.close();
		 };
		 diag.show();
	}	
	
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