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
<form action="intel/list.do" method="post" name="Form" id="Form">
	<table style="margin-top:5px;">
			<tr>
				<td>
					<div class="nav-search">
						<span class="input-icon"> 
						<input name="keywords"	class="nav-search-input" autocomplete="off"	id="nav-search-input" type="text" 
							value="${pd.keywords}" placeholder="这里输入LotNo" /> <i
							class="ace-icon fa fa-search nav-search-icon"></i>
						</span>
					</div>
				</td>	
				<td style="padding-left:5px;">
				    <span class="input-icon"> 
				       <input name="operationCode"  type="text" value="${pd.operationCode}"  placeholder="op code" style="width: 100px;">
				    </span>
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
				<th class="center" >LotNo</th>
				<th class="center" >OperationCode</th>
				<th class="center" >Unit</th>
				<th class="center">Pass</th>
				<th class="center">Bin1</th>
				<th class="center">InsertTime</th>
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
						<td class='center' style="vertical-align: middle;">${var.LOTNO}</td>
					    <td class='center' style="vertical-align: middle;">${var.OPERATION_CODE}</td>
						<td class='center' style="vertical-align: middle;">${var.UNIT}</td>			
						<td class='center' style="vertical-align: middle;">${var.PASS}</td>                                   
						<td class='center' style="vertical-align: middle;">${var.BIN1}</td>                                   
						<td class='center' style="vertical-align: middle;"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${var.INSERTTIME}"></fmt:formatDate></td>                                   
					       <!-- 操作按钮start -->
                        <td class="center" style="width:86px"> 
                            <c:if test="${QX.edit != 1 && QX.del != 1 }">
                            <span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
                            </c:if>
                            <div class="hidden-sm hidden-xs btn-group">
                                <c:if test="${QX.del == 1 }">
                                <a class="btn btn-xs btn-danger" onclick="del('${var.LOTNO}','${var.OPERATION_CODE} ');">
                                    <i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
                                </a>
                                </c:if>
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
        function del(LOTNO,OPERATION_CODE){
            bootbox.confirm("确定要删除LOTNO:"+LOTNO+",OPERATION_CODE:"+OPERATION_CODE+"的信息吗?", function(result) {
                if(result) {
                    top.jzts();            
                    var url = "<%=basePath%>intel/delete.do?LOTNO="+LOTNO+"&OPERATION_CODE="+OPERATION_CODE;
                    $.get(url,function(data){
                        nextPage('${page.currentPage}');
                    });
                   
                };
            });
        }
		
		//悬浮
		$(function () { $("[data-toggle='tooltip']").tooltip(); });
		
								
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