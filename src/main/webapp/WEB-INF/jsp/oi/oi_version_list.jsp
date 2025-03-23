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
<form action="scopes/list.do" method="post" name="Form" id="Form">
<table style="margin-top:5px;">
		<tr>
			<td>
				<div class="nav-search">
					<span class="input-icon"> <input
						name="keywords"
						class="nav-search-input" autocomplete="off"
						id="nav-search-input" type="text" name="keywords"
						value="${pd.keywords}" placeholder="输入OI VERSION 或者 Customer Code" /> <i
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
	
	<!-- 数据列表 -->
	<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center"><span data-toggle="tooltip" title="OIVersion">OIVersion</span></th>
									<th class="center"><span data-toggle="tooltip" title="CustomerCode">CustomerCode</span></th>
									<th class="center"><span data-toggle="tooltip" title="IGXL VERSION">IGXL</span></th>
									<th class="center"><span data-toggle="tooltip" title="PROCESS">Process</span></th>
									<th class="center"><span data-toggle="tooltip" title="device Check:compare emes device with setupfile device">DeviceCheck</span></th>
									 <th class="center"><span data-toggle="tooltip" title="Check diskpack">DiskSpaceCheck</span></th>
									<th class="center"><span data-toggle="tooltip" title="Setupfile Flowid check ">FlowidCheck</span></th>
									<th class="center"><span data-toggle="tooltip" title="Setupfile Process Check ">ProcessCheck</span></th>
									<th class="center"><span data-toggle="tooltip" title="Setupfile Testcode Check">TestcodeCheck</span></th>
									<th class="center"><span data-toggle="tooltip" title="MD5 Check : Copmare machine MD5 file with server MD5 file">MD5Check</span></th>
									<th class="center"><span data-toggle="tooltip" title="IGXL Check:Compare seupfile IGXL line with machine IGXL ">IGXLCheck</span></th>
									<th class="center"><span data-toggle="tooltip" title="OT Check : Compare OT proxy , DLL file and process">OTProxyCheck</span></th>
									<th class="center"><span data-toggle="tooltip" title="Check :compare probe waferid with emes customerid">WaferidCheck</span></th>
									<th class="center"><span data-toggle="tooltip" title="PGM CHECK :COMPARE SETUPFILE PGM WITH AMS PGM">PGMCheck</span></th>
									<th class="center"><span data-toggle="tooltip" title="Log record STDF generate status">LogRecord</span></th>
									<th class="center"><span data-toggle="tooltip" title=" data status">Enable</span></th>
									<th class="center"><span data-toggle="tooltip" title="Input data Time">InputTime</span></th>
									<th class="center"><span data-toggle="tooltip" title="Modify data Time">ModifyTime</span></th>
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
                                            <td class='center' style="vertical-align: middle;">${var.OIVersion}</td>
                                            <td class='center' style="vertical-align: middle;">${var.CustomerCode}</td>
                                            <td class='center' style="vertical-align: middle;">${var.IGXL}</td>
                                            <td class='center' style="vertical-align: middle;">${var.Process}</td>
                                            <td class='center' style="vertical-align: middle;">${var.DeviceCheck}</td>
                                            <td class='center' style="vertical-align: middle;">${var.DiskSpaceCheck}</td>
                                            <td class='center' style="vertical-align: middle;">${var.FlowidCheck}</td>      
                                            <td class='center' style="vertical-align: middle;">${var.ProcessCheck}</td>   
                                            <td class='center' style="vertical-align: middle;">${var.TestcodeCheck}</td>     
                                            <td class='center' style="vertical-align: middle;">${var.MD5Check}</td>
                                            <td class='center' style="vertical-align: middle;">${var.IGXLCheck}</td>
                                            <td class='center' style="vertical-align: middle;">${var.OTProxyCheck}</td>
                                            <td class='center' style="vertical-align: middle;">${var.WaferidCheck}</td>
                                            <td class='center' style="vertical-align: middle;">${var.PGMCheck}</td>
                                            <td class='center' style="vertical-align: middle;">${var.LogRecord}</td>
                                            <td class='center' style="vertical-align: middle;">${var.Enable}</td>      
                                            <td class='center' style="vertical-align: middle;"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${var.InputTime}"></fmt:formatDate></td>   
                                            <td class='center' style="vertical-align: middle;"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${var.ModifyTime}"></fmt:formatDate></td>                                                                                  
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
			var url = "<%=basePath%>oiversion/delete.do?ID="+ID+"&tm="+new Date().getTime();
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
	 diag.URL = '<%=basePath%>oiversion/goAdd.do';
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
function edit(ID){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="修改";
	 diag.URL = '<%=basePath%>oiversion/goEdit.do?ID='+ID;
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
						url: '<%=basePath%>scopes/deleteAllU.do?tm='+new Date().getTime(),
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



		
</script>
</html>
