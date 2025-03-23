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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<style type="text/css">
table {
	table-layout: fixed;
}

.yulantu {
	z-index: 9999999999999999;
	position: absolute;
	border: 3px solid #438EB9;
	display: none;
}
</style>
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
							<form action="fhfile/list.do" method="post" name="Form" id="Form">
								<table style="margin-top: 5px;">
									<tr>
										<td>
											<div class="nav-search">
												<span class="input-icon"> <input type="text"
													placeholder="这里输入关键词" class="nav-search-input"
													id="nav-search-input" autocomplete="off" name="keywords"
													value="${pd.keywords }" placeholder="这里输入关键词" /> <i
													class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
											</div>
										</td>
										<td style="vertical-align: top; padding-left: 2px"><select
											name="CUSTOMER" id="CUSTOMER" title="请选择客户"
											style="width: 130px;">
										</select></td>
										<td style="vertical-align: top; padding-left: 2px"><select
											name="LEVEL" id="LEVEL" title="请选择级别" style="width: 130px;">
										</select></td>
										<td style="vertical-align: top; padding-left: 2px"><select
											name="DEFECT_MODE" id="DEFECT_MODE" title="请选择defect mode"
											style="width: 155px;">
										</select></td>
										<td style="vertical-align: top; padding-left: 2px"><select
											name="PROCESS" id="PROCESS" title="请选择process"
											style="width: 125px;">
										</select></td>
										<c:if test="${QX.cha == 1 }">
											<td style="vertical-align: top; padding-left: 2px"><a
												class="btn btn-light btn-xs" onclick="tosearch();"
												title="检索"><i id="nav-search-icon"
													class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
										</c:if>
										<c:if test="${filePd.fileOutDate == '1' }">
											<td style="text-align: center; padding-left: 15px;"><a
												onclick="window.location.href='<%=basePath%>fhfile/listExpiredFiles.do';";><label
													style="color: red;">您有文件已经过期,请更新截至日期(点击显示过期文件)</label></a></td>
										</c:if>
									</tr>
								</table>
								<!-- 检索  end-->


								<table id="simple-table"
									class="table table-striped table-bordered table-hover"
									style="margin-top: 5px;">
									<thead>
										<tr>
											<th class="center" style="width: 35px;"><label
												class="pos-rel"><input type="checkbox" class="ace"
													id="zcheckbox" /><span class="lbl"></span></label></th>
											<th class="center" style="width: 50px;">序号</th>
											<th class="center" style="width: 250px;">文件名</th>
											<th class="center" style="width: 70px;">上传者</th>
											<th class="center" style="width: 70px;">客户</th>
											<th class="center" style="width: 90px;">Process</th>
											<th class="center" style="width: 200px;">Defect Mode</th>
											<th class="center" style="width: 120px;">级别</th>
											<!-- <th class="center">文件说明</th> -->
											<th class="center" style="width: 120px;">上传时间</th>
											<th class="center" style="width: 70px;">文件状态</th>
											<th class="center" style="width: 50px;">操作</th>
										</tr>
									</thead>

									<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
												<c:if test="${QX.cha == 1 }">
													<c:forEach items="${varList}" var="var" varStatus="vs">
														<tr>
															<td class='center'><label class="pos-rel"><input
																	type='checkbox' name='ids' value="${var.FHFILE_ID}"
																	class="ace" /><span class="lbl"></span></label></td>
															<td class='center' style="width: 50px;">${vs.index+1}</td>
															<td class='center'
																style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden; width: 250px;"
																data-formatter="FirstFormatter"><img
																style="margin-top: -3px;" alt="${var.NAME}"
																src="static/images/extension/${var.fileType}.png">
																<%-- 
                                                <c:if test="${var.fileType =='ppt' }"><a title="${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}" style="cursor:pointer;" onclick="goViewPpt('${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}','${var.FHFILE_ID}');">${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}</a></c:if> 
                                                <c:if test="${var.fileType == 'pdf'}"><a title="${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}" style="cursor:pointer;" onclick="goViewPdf('${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}','${var.FHFILE_ID}');">${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}</a></c:if>
                                                <c:if test="${var.fileType == 'wenben' }"><a title="${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}" style="cursor:pointer;" onclick="goViewTxt('${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}','${var.FHFILE_ID}','gbk');">${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}</a></c:if>
                                                --%> <a
																title="${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}"
																style="cursor: pointer;"
																onclick="goViewOffice('${var.FHFILE_ID}','${var.FILEPATH}')">${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}</a>
																<div class="yulantu" id="yulantu${vs.index+1}"></div></td>
															<td class='center'>${var.USERNAME}</td>
															<td class='center' style="width: 70px;">${var.CUSTOMER}</td>
															<td class='center'>${var.PROCESS}</td>
															<td class='center'>${var.DEFECT_MODE}</td>
															<td class='center'>${var.LEVEL}</td>
															<%-- <td class='center'>${var.BZ}</td> --%>
															<td class='center' style="width: 150px;"><c:if
																	test="${not empty var.UPDATE_TIME }">
																	<a title="查看历史版本" style="cursor: pointer;"
																		onclick="goViewOther('${var.FHFILE_ID}')">(新)</a>
															${var.UPDATE_TIME}
															</c:if> <c:if test="${empty var.UPDATE_TIME }">
															${var.CTIME}
															</c:if></td>
															<td style="width: 100px;" class="center">
															    <c:if test="${var.FILE_STATUS =='3' }">
																	<span class="label label-warning">update</span>
																</c:if> 
																<c:if test="${var.FILE_STATUS =='2' }">
																	<span class="label label-important">expired</span>
																</c:if> <c:if test="${var.FILE_STATUS =='1' }">
																	<span class="label label-success">close</span>
																</c:if> <c:if test="${var.FILE_STATUS =='0' }">
																	<span class="label label-warning">loop</span>
																</c:if></td>

															<!-- <td class="center" style="width:150px;"> -->
															<td class="center" style="width: 50px;"><c:if
																	test="${QX.edit != 1 && QX.del != 1 }">
																	<span
																		class="label label-large label-grey arrowed-in-right arrowed-in"><i
																		class="ace-icon fa fa-lock" title="无权限"></i></span>
																</c:if>
																<div class="hidden-sm hidden-xs btn-group">
																	<div class="inline pos-rel">
																		<button
																			class="btn btn-minier btn-primary dropdown-toggle"
																			data-toggle="dropdown" data-position="auto">
																			<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																		</button>

																		<ul
																			class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																			<c:if test="${QX.edit == 1 }">
																			
																	<%-- 		 <c:if test="${var.STATUS == '2' }">
																			     <li><a style="cursor: pointer;"
                                                                                    onclick="endFile('${var.FHFILE_ID}');"
                                                                                    class="tooltip-success" data-rel="tooltip"
                                                                                    title="end"> <span class="red"> <i
                                                                                            class="ace-icon fa fa-check-square bigger-120" ></i>
                                                                                    </span>
                                                                                </a></li>
                                                                              </c:if>   --%>
                                                                                
																				<li><a style="cursor: pointer;"
																					onclick="window.location.href='<%=basePath%>/fhfile/download.do?FHFILE_ID=${var.FHFILE_ID}'"
																					class="tooltip-success" data-rel="tooltip"
																					title="下载"> <span class="green"> <i
																							class="ace-icon fa fa-cloud-download bigger-120"></i>
																					</span>
																				</a></li>
																				<li><a style="cursor: pointer;"
																					onclick="edit('${var.FHFILE_ID}');"
																					class="tooltip-success" data-rel="tooltip"
																					title="修改"> <span class="green"> <i
																							class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																					</span>
																				</a></li>
																			</c:if>

																			<c:if test="${QX.apr == 1 }">
																				<li><a style="cursor: pointer;"
																					onclick="view('${var.FHFILE_ID}');"
																					class="tooltip-success" data-rel="tooltip"
																					title="查看浏览记录"> <span class="bule"> <i
																							class="ace-icon fa fa-eye bigger-120"></i>
																					</span>
																				</a></li>
																			</c:if>

																			<c:if test="${QX.del == 1 }">
																				<li><a style="cursor: pointer;"
																					onclick="del('${var.FHFILE_ID}');"
																					class="tooltip-error" data-rel="tooltip" title="删除">
																						<span class="red"> <i
																							class="ace-icon fa fa-trash-o bigger-120"></i>
																					</span>
																				</a></li>
																			</c:if>
																		</ul>
																	</div>
																	<%-- 	<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="下载" onclick="window.location.href='<%=basePath%>/fhfile/download.do?FHFILE_ID=${var.FHFILE_ID}'">
														<i class="ace-icon fa fa-cloud-download bigger-120" title="下载"></i>
													</a>
													</c:if>
												    <c:if test="${QX.apr == 1 }">
												       <a class="btn btn-xs btn-info" title="查看浏览记录" onclick="view('${var.FHFILE_ID}');">
                                                         <i class="ace-icon fa fa-eye bigger-120" title="查看浏览记录"></i>
                                                       </a>
                                                    </c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" onclick="del('${var.FHFILE_ID}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
													</c:if> --%>
																</div>
																<div class="hidden-md hidden-lg">
																	<div class="inline pos-rel">
																		<button
																			class="btn btn-minier btn-primary dropdown-toggle"
																			data-toggle="dropdown" data-position="auto">
																			<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
																		</button>

																		<ul
																			class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																			<c:if test="${QX.edit == 1 }">
																				<li><a style="cursor: pointer;"
																					onclick="window.location.href='<%=basePath%>/fhfile/onlineDownload.do?FHFILE_ID=${var.FHFILE_ID}'"
																					class="tooltip-success" data-rel="tooltip"
																					title="下载"> <span class="green"> <i
																							class="ace-icon fa fa-cloud-download bigger-120"></i>
																					</span>
																				</a></li>
																			</c:if>

																			<c:if test="${QX.apr == 1 }">
																				<li><a style="cursor: pointer;"
																					onclick="view('${var.FHFILE_ID}');"
																					class="tooltip-success" data-rel="tooltip"
																					title="查看"> <span class="bule"> <i
																							class="ace-icon fa fa-eye bigger-120"></i>
																					</span>
																				</a></li>
																			</c:if>

																			<c:if test="${QX.del == 1 }">
																				<li><a style="cursor: pointer;"
																					onclick="del('${var.FHFILE_ID}');"
																					class="tooltip-error" data-rel="tooltip" title="删除">
																						<span class="red"> <i
																							class="ace-icon fa fa-trash-o bigger-120"></i>
																					</span>
																				</a></li>
																			</c:if>
																		</ul>
																	</div>
																</div></td>
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
													<td colspan="100" class="center">没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
								<div class="page-header position-relative">
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: top;"><c:if
													test="${QX.add == 1 }">
													<a class="btn btn-mini btn-success" onclick="add();">新增</a>
												</c:if> <c:if test="${QX.del == 1 }">
													<a class="btn btn-mini btn-danger"
														onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
														class='ace-icon fa fa-trash-o bigger-120'></i></a>
												</c:if></td>
											<td style="vertical-align: top;"><div class="pagination"
													style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
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
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
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
	<!-- 预览文档 -->
	<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="static/js/jquery.media.js"></script>

	<script type="text/javascript" src="static/js/myjs/base64.min.js"></script>

	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//悬浮
       /*  $(function () { $("[data-toggle='tooltip']").tooltip(); }); */
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
       
       
		$(function() {
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
			
			  //下拉框
            var CUSTOMER = "${pd.CUSTOMER}";
            var zidan = "${zidian.customer}";
            $.ajax({
                type: "POST",
                url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
                data: {DICTIONARIES_ID: zidan},//DICTIONARIES_ID 为客户分类
                dataType:'json',
                cache: false,
                success: function(data){
                    $("#CUSTOMER").html('<option value="" >请选择客户</option>');
                     $.each(data.list, function(i, dvar){
                         if(CUSTOMER == dvar.BIANMA){
                             $("#CUSTOMER").append("<option value="+dvar.BIANMA+" selected='selected'>"+dvar.NAME+"</option>");
                         }else{
                             $("#CUSTOMER").append("<option value="+dvar.BIANMA+">"+dvar.NAME+"</option>");
                         }
                     });
                }
            });
            
            var LEVEL = "${pd.LEVEL}";
            var zidan = "${zidian.level}";
            $.ajax({
                type: "POST",
                url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
                data: {DICTIONARIES_ID:zidan},//DICTIONARIES_ID 为级别分类
                dataType:'json',
                cache: false,
                success: function(data){
                    $("#LEVEL").html('<option value="" >请选择级别</option>');
                     $.each(data.list, function(i, dvar){
                         if(LEVEL == dvar.BIANMA){
                             $("#LEVEL").append("<option value="+dvar.BIANMA+" selected='selected'>"+dvar.NAME+"</option>");
                         }else{
                             $("#LEVEL").append("<option value="+dvar.BIANMA+">"+dvar.NAME+"</option>");
                         }
                     });
                }
            });
            
            var LEVEL = "${pd.DEFECT_MODE}";
            var zidan = "${zidian.defect}";
            $.ajax({
                type: "POST",
                url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
                data: {DICTIONARIES_ID:zidan},//DICTIONARIES_ID 为级别分类
                dataType:'json',
                cache: false,
                success: function(data){
                    $("#DEFECT_MODE").html('<option value="" >请选择 Defect Mode</option>');
                     $.each(data.list, function(i, dvar){
                         if(LEVEL == dvar.BIANMA){
                             $("#DEFECT_MODE").append("<option value="+dvar.BIANMA+" selected='selected'>"+dvar.NAME+"</option>");
                         }else{
                             $("#DEFECT_MODE").append("<option value="+dvar.BIANMA+">"+dvar.NAME+"</option>");
                         }
                     });
                }
            });
            
            var LEVEL = "${pd.PROCESS}";
            var zidan = "${zidian.process}";
            $.ajax({
                type: "POST",
                url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
                data: {DICTIONARIES_ID:zidan},//DICTIONARIES_ID 为级别分类
                dataType:'json',
                cache: false,
                success: function(data){
                    $("#PROCESS").html('<option value="" >请选择 Process</option>');
                     $.each(data.list, function(i, dvar){
                         if(LEVEL == dvar.BIANMA){
                             $("#PROCESS").append("<option value="+dvar.BIANMA+" selected='selected'>"+dvar.NAME+"</option>");
                         }else{
                             $("#PROCESS").append("<option value="+dvar.BIANMA+">"+dvar.NAME+"</option>");
                         }
                     });
                }
            });
            
            
            //提示过期文件
           if('1'== '${filePd.fileOutDate}'&&"true"=='${filePd.fileFlag}'){
        	    bootbox.confirm("您有过期文件需要处理,是否显示过期文件", function(result) {
                    if(result) {
                        top.jzts(); 
                        window.location.href='<%=basePath%>fhfile/listExpiredFiles.do';
                    };
                });
           } 
            
            //下拉 end //
		});
		
		
		//查看历史记录
		function view(ID){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title = '查询浏览记录';
			diag.URL = '<%=basePath%>file/viewHistory.do?FHFILE_ID='+ID;
			diag.Width = 800;
			diag.Height = 600;
			diag.Modal = true ;
		    diag. ShowMaxButton = true; //最大化按钮
            diag.ShowMinButton = true;     //最小化按钮
            diag.CancelEvent = function(){   //关闭事件
            	 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                     nextPage('${page.currentPage}');
                }
                diag.close();
             };
             diag.show();
		}
		
		//查看历史记版本
        function goViewOther(ID){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title = '查询历史版本';
            diag.URL = '<%=basePath%>fhfile/goOtherwise.do?FHFILE_ID='+ID;
            diag.Width = 800;
            diag.Height = 600;
            diag.Modal = true ;
            diag. ShowMaxButton = true; //最大化按钮
            diag.ShowMinButton = true;     //最小化按钮
            diag.CancelEvent = function(){   //关闭事件
                 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                     nextPage('${page.currentPage}');
                }
                diag.close();
             };
             diag.show();
        }
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>fhfile/goAdd.do';
			 diag.Width = 460;
			 diag.Height = 420;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
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
	         diag.URL =  '<%=basePath%>fhfile/goEdit.do?FHFILE_ID='+ID;
	         diag.Width = 460;
             diag.Height = 420;
             diag.Modal = true;                //有无遮罩窗口
             diag. ShowMaxButton = true;    //最大化按钮
             diag.ShowMinButton = true;     //最小化按钮
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
		
	       function endFile(Id){
	            bootbox.confirm("确定要END吗?", function(result) {
	                if(result) {
	                    top.jzts();
	                    var url = "<%=basePath%>fhfile/end.do?FHFILE_ID="+Id+"&tm="+new Date().getTime();
	                    $.get(url,function(data){
	                        nextPage('${page.currentPage}');
	                    });
	                }
	            });
	        }
	    
		
		
		//预览文件
		 function goViewOffice(fileID,fileName){
			 var originUrl  = '<%=basePath%>fhfile/download?FHFILE_ID='+fileID;  //要预览文件的访问地址
             var previewUrl = originUrl + '&fullfilename='+fileName;
			
			 var url = '<%=basePath%>fhfile/goViewOffice?FHFILE_ID='+fileID;
             $.get(url,function(data){
            	 window.open('http://10.86.140.200:8012/onlinePreview?url='+encodeURIComponent(Base64.encode(previewUrl)));
             }); 
		  
		 }
		
		  //预览pdf（ 过期方法）
	        function goViewPdf(fileName,Id){
	            var diag = new top.Dialog();
	            diag.Drag=true;
	            diag.Title =fileName;
	            diag.URL = '<%=basePath%>fhfile/goViewPdf.do?FHFILE_ID='+Id;
	            diag.Width = 1000;
	            diag.Height = 600;
	            diag.Modal = false;             //有无遮罩窗口
	            diag. ShowMaxButton = true;     //最大化按钮
	            diag.ShowMinButton = true;      //最小化按钮
	            diag.CancelEvent = function(){  //关闭事件
	            diag.close();
	            };
	            diag.show();
	        }
		
		//预览ppt（ 过期方法）
		  function goViewPpt2(fileName,Id){
              var diag = new top.Dialog();
              diag.Drag=true;
              diag.Title =fileName;
              diag.URL = '<%=basePath%>fhfile/goViewPpt.do?FHFILE_ID='+Id;
              diag.Modal = false;             //有无遮罩窗口
              diag.Width = screen.availWidth;
              diag.Height = screen.availHeight;
              diag.ShowMessageRow=false;
              diag. ShowMaxButton = true;     //最大化按钮
              diag.ShowMinButton = false;      //最小化按钮
              diag.CancelEvent = function(){  //关闭事件
                   diag.close();
              };
              diag.show();
          }
	
		//预览txt,java,php,等文本文件页面（ 过期方法）
		function goViewTxt(fileName,Id,encoding){
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title =fileName;
			diag.URL = '<%=basePath%>fhfile/goViewTxt.do?FHFILE_ID='+Id+'&encoding='+encoding;
			diag.Width = 1000;
			diag.Height = 608;
			diag.Modal = false;				//有无遮罩窗口
			diag.ShowMinButton = true;		//最小化按钮
			diag.CancelEvent = function(){ 	//关闭事件
			diag.close();
			};
			diag.show();
		}
		
		//显示图片
		function showTU(path,TPID){
			 $("#"+TPID).html('<img width="300" src="'+path+'">');
			 $("#"+TPID).show();
		}
		
		//隐藏图片
		function hideTU(TPID){
			 $("#"+TPID).hide();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>fhfile/delete.do?FHFILE_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage('${page.currentPage}');
					});
				}
			});
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  };
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
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
								url: '<%=basePath%>fhfile/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
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
					};
				};
			});
		};
		
		
		
	</script>


</body>
</html>