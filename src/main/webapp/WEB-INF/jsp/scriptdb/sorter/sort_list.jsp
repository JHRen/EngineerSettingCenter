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
<form action="script/sortprogram/list.do" method="post" name="Form" id="Form">
	<table style="margin-top:5px;">
			<tr>
				<td>
					<div class="nav-search">
						<span class="input-icon"> 
						<input name="keywords"	class="nav-search-input" autocomplete="off"	id="nav-search-input" type="text" 
							value="${pd.keywords}" placeholder="这里输入DEVICE" /> <i
							class="ace-icon fa fa-search nav-search-icon"></i>
						</span>
					</div>
				</td>	
				
				<c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>检索</a></td>
			    	<td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="ace-icon fa fa-cloud-upload bigger-110 nav-search-icon blue"></i></a></td>					
				</c:if>	
		<!-- 检索  END-->
	</tr>
	</table>
	
	<!--列表 -->
	<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
		<thead>
			<tr>
				<th class="center" style="width:50px;" >序号</th>
				<th class="center" >Group</th>
				<th class="center" >Device</th>
				<th class="center" >Target Device</th>
				<th class="center">Target Device(18位)</th>
				<th class="center">AMKORWWOPERNAME</th>
				<th class="center">PROCESS CODE</th>
				<th class="center">PKG SORT Handler</th>
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
						<td class='center' style="vertical-align: middle;">${var.GROUP}</td>
					    <td class='center' style="vertical-align: middle;">${var.DEVICE}</td>
						<td class='center' style="vertical-align: middle;">${var.TARGET_DEVICE}</td>			
						<td class='center' style="vertical-align: middle;">${var.TARGET_DEVICE_18}</td>                                   
						<td class='center' style="vertical-align: middle;">${var.AMKORWWOPERNAME}</td>  
						<td class='center' style="vertical-align: middle;">${var.PROCESS_CODE}</td>                                             
						<td class='center' style="vertical-align: middle;">${var.PKG_SORT_HANDLER}</td>                                    
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
            bootbox.confirm("确定要删除?", function(result) {
                if(result) {
                    top.jzts();            
                    var url = "<%=basePath%>script/sortprogram/delete.do?ID="+ID+"&tm="+new Date().getTime();
                    $.get(url,function(data){
                        nextPage('${page.currentPage}');
                    });
                };
            });
        }
        
        
        //增加
    function add(){
     top.jzts();
     var diag = new top.Dialog();
     diag.Drag=true;
     diag.Title ="新增";
     diag.URL = '<%=basePath%>script/sortprogram/add.do';
     diag.Width = 480;
     diag.Height = 470;
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
         diag.URL = '<%=basePath%>script/sortprogram/goEdit.do?ID='+ID;
         diag.Width = 480;
         diag.Height = 470;
         diag.CancelEvent = function(){ //关闭事件
             if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                 nextPage('${page.currentPage}');
            }
            diag.close();
         };
         diag.show();
    }   
    
    
  //打开上传excel页面
    function fromExcel(){
         top.jzts();
         var diag = new top.Dialog();
         diag.Drag=true;
         diag.Title ="EXCEL 导入到数据库";
         diag.URL = '<%=basePath%>script/sortprogram/goUploadExcel.do';
         diag.Width = 300;
         diag.Height = 150;
         diag.CancelEvent = function(){ //关闭事件
             if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                 if('${page.currentPage}' == '0'){
                     top.jzts();
                     setTimeout("self.location.reload()",100);
                 }else{
                     nextPage(${page.currentPage});
                 }
            }
            diag.close();
         };
         diag.show();
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