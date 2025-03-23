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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- datetime日期框 -->
<link rel='stylesheet'  href="static/ace/css/bootstrap-datetimepicker.min.css" >
<!-- jsp文件头和头部 -->
<%@ include file="../system/index/top.jsp"%>

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
<form action="equipment/list.do" method="post" name="Form" id="Form">
<table style="margin-top:5px;">
		<tr>
			<td>
				<div class="nav-search">
					<span class="input-icon"> <input
						name="keywords"
						class="nav-search-input" autocomplete="off"
						id="nav-search-input" type="text" name="keywords"
						value="${pd.keywords}" placeholder="这里输入用户名" /> <i
						class="ace-icon fa fa-search nav-search-icon"></i>
					</span>
				</div>
			</td>	
			
			<td style="vertical-align:top;padding-left:2px;"> 
			 	<select class="chosen-select form-control" name="Status" id="STATUS" data-placeholder="状态" style="vertical-align:top;width: 79px;">
				<option value=""></option>
				<option value="">全部</option>
				<option value="0" <c:if test="${pd.Status == '0' }">selected</c:if> >生效</option>
				<option value="1" <c:if test="${pd.Status == '1' }">selected</c:if> >失效</option>
				</select>
			</td>
			
			<td style="padding-left:3px;">账户有效时间：</td>
			<td style="padding-left:2px;"><input class="form_datetime" name="EffectiveDate" id="EffectiveDate"  value="${pd.EffectiveDate}" type="text"  style="width:150px;" placeholder="开始日期" title="开始日期" readonly="true"/></td>
			<td align="center">至</td>
			<td style="padding-left:2px;"><input class="form_datetime" name="ExpiredDate" id="ExpiredDate"  value="${pd.ExpiredDate}" type="text"  style="width:150px;" placeholder="结束日期" title="结束日期" readonly="true"/></td>											
			
			
			<c:if test="${QX.cha == 1 }">
				<td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>检索</a></td>
			</c:if>						
	<!-- 检索  END-->
</tr>
</table>
	
	<!-- 数据列表 -->
	<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center"><span data-toggle="tooltip" title="账户类型">账户类型</span></th>
									<th class="center"><span data-toggle="tooltip" title="机器类型">机器类型</span></th>
									<th class="center"><span data-toggle="tooltip" title="用户名">用户名</span></th>
									<th class="center"><span data-toggle="tooltip" title="密码">密码</span></th>
									<th class="center"><span data-toggle="tooltip" title="开始时间">生效时间</span></th>
									<th class="center"><span data-toggle="tooltip" title="结束时间">失效时间</span></th>
									<th class="center"><span data-toggle="tooltip" title="状态">状态</span></th>
									<th class="center" style="width:80px;"><span data-toggle="tooltip" title="是否提醒">邮件提醒</span></th>
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
											<td class='center' style="vertical-align: middle;">${var.AccountType}</td>
										    <td class='center' style="vertical-align: middle;">${var.MachineType}</td>
										    <td class='center' style="vertical-align: middle;">${var.UserID}</td>
											<td class='center' style="vertical-align: middle;">${var.Password}</td>												
											<td class='center' style="vertical-align: middle;">${var.EffectiveDate}</td>
											<td class='center' style="vertical-align: middle;">${var.ExpiredDate}</td>
											<td style="width: 60px;" class="center">
												<c:if test="${var.Status == '1' }"><span class="label label-important">失效</span></c:if>
												<c:if test="${var.Status == '0' }"><span class="label label-success">有效</span></c:if>
											</td>
										
											<td class='center' style="height: 20px;">			
												<label>
													<input name="switch-field-1" onclick="upRb('${var.ID}','${var.IsRemindByEmail}')" class="ace ace-switch ace-switch-3" type="checkbox" <c:if test="${var.IsRemindByEmail == 0 }">checked="checked"</c:if> >
													<span class="lbl"></span>
												</label>
											</td>																				
											
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
													<a class="btn btn-xs btn-danger" onclick="del('${var.ID}');">
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
																<a style="cursor:pointer;" onclick="del('${var.ID}');" class="tooltip-error" data-rel="tooltip" title="删除">
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
	<%@ include file="../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="<%=basePath%>static/ace/js/date-time/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/ace/js/date-time/bootstrap-datetimepicker.zh-CN.js"></script>
	</body>

<script type="text/javascript">
$(top.hangge());

//查询
function searchs(){
	top.jzts();
	$("#Form").submit();
}

//删除
function del(ID){
	bootbox.confirm("确定要删除选中的"+ID+"数据吗?", function(result) {
		if(result) {
			top.jzts();
			var url = "<%=basePath%>equipment/delete.do?id="+ID;
			$.get(url,function(data){
				//nextPage('${page.currentPage}');
				 top.jzts();
				 setTimeout("self.location=self.location",100);

			});
		};
	});
}

//新增
function add(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="新增";
	 diag.URL = '<%=basePath%>equipment/goAdd.do';
	 diag.Width = 469;
	 diag.Height = 540;
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
function edit(cpid){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="修改";
	 diag.URL = '<%=basePath%>equipment/goEdit.do?id='+cpid;
	 diag.Width = 469;
	 diag.Height = 600;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 nextPage('${page.currentPage}');
		}
		diag.close();
	 };
	 diag.show();
}	

//处理按钮点击
function upRb(ID,emailStatus){
	if (emailStatus==1){
		emailStatus=0;
	}else{
		emailStatus=1;
	}
		
		
	$.ajax({
		type: "POST",
		url: "<%=basePath%>equipment/upRb.do?ID="+ID+"&IsRemindByEmail="+emailStatus,
    	data: encodeURI(""),
		dataType:'json',
		//beforeSend: validateData,
		cache: false,
		success: function(data){
		}
	});

}			

//批量操作
function makeAll(msg){
	bootbox.confirm(msg, function(result) {
		if(result) {
			var str = '';
			var emstr = '';
			var phones = '';
			var username = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				  if(document.getElementsByName('ids')[i].checked){
				  	if(str=='') str += document.getElementsByName('ids')[i].value;
				  	else str += ',' + document.getElementsByName('ids')[i].value;
				  	if(username=='') username += document.getElementsByName('ids')[i].title;
				  	else username += ';' + document.getElementsByName('ids')[i].title;
				  }
			}
			if(str==''){
				bootbox.dialog({
					message: "<span class='bigger-110'>您没有选择任何内容!</span>",
					buttons: 			
					{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
				});
				$("#zcheckbox").tips({
					side:3,
		            msg:'点这里全选',
		            bg:'#AE81FF',
		            time:8
		        });
				
				return;
			}else{
				if(msg == '确定要删除选中的数据吗?'){
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>user/deleteAllU.do?tm='+new Date().getTime(),
				    	data: {USER_IDS:str},
						dataType:'json',
						//beforeSend: validateData,
						cache: false,
						success: function(data){
							 $.each(data.list, function(i, list){
									nextPage('${page.currentPage}');
							 });
						}
					});
				}
			}
		}
	});
}



$(function() {
	//悬浮框
	$("[data-toggle='tooltip']").tooltip();
	
	//日期框		
	$('.form_datetime').datetimepicker({
		format: "yyyy-mm-dd hh:ii:ss",
		language:  'zh-CN',
		minView: 0,
		minuteStep:5,
		 clearBtn: true

	}).on('changeDate',function(ev){     //设定失效时间必须要大于生效时间
		var startTime = $('#EffectiveDate').val();
		$('#ExpiredDate').datetimepicker('setStartDate',startTime);
	});
	
	
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


//查看用户
function viewUser(USERNAME){
	if('admin' == USERNAME){
		bootbox.dialog({
			message: "<span class='bigger-110'>不能查看admin用户!</span>",
			buttons: 			
			{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
		});
		return;
	}
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="资料";
	 diag.URL = '<%=basePath%>user/view.do?USERNAME='+USERNAME;
	 diag.Width = 469;
	 diag.Height = 380;
	 diag.CancelEvent = function(){ //关闭事件
		diag.close();
	 };
	 diag.show();
}
		
</script>
</html>
