﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
    <%@ include file="top.jsp"%>
	
	<style type="text/css">
	#dialog-add,#dialog-message,#dialog-comment{width:100%; height:100%; position:fixed; top:0px; z-index:99999999; display:none;}
	.commitopacity{position:absolute; width:100%; height:10000px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.5; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
	.commitbox{width:100%; margin:0px auto; position:absolute; top:120px; z-index:99999;}
	.commitbox_inner{width:96%; height:255px;  margin:6px auto; background:#efefef; border-radius:5px;}
	.commitbox_top{width:100%; height:253px; margin-bottom:10px; padding-top:10px; background:#FFF; border-radius:5px; box-shadow:1px 1px 3px #e8e8e8;}
	.commitbox_top textarea{width:95%; height:195px; display:block; margin:0px auto; border:0px;}
	.commitbox_cen{width:95%; height:40px; padding-top:10px;}
	.commitbox_cen div.left{float:left;background-size:15px; background-position:0px 3px; padding-left:18px; color:#f77500; font-size:16px; line-height:27px;}
	.commitbox_cen div.left img{width:30px;}
	.commitbox_cen div.right{float:right; margin-top:7px;}
	.commitbox_cen div.right span{cursor:pointer;}
	.commitbox_cen div.right span.save{border:solid 1px #c7c7c7; background:#6FB3E0; border-radius:3px; color:#FFF; padding:5px 10px;}
	.commitbox_cen div.right span.quxiao{border:solid 1px #f77400; background:#f77400; border-radius:3px; color:#FFF; padding:4px 9px;}
	</style>
	
	</head> 
<body>

<!-- 编辑邮箱  -->
<div id="dialog-add">
	<div class="commitopacity"></div>
  	<div class="commitbox">
	  	<div class="commitbox_inner">
	        <div class="commitbox_top">
	        	<textarea name="FOLDERs" id="FOLDERs" placeholder="请选输入目标路径,多个请用(;)分号隔开" title="请选输入目标路径,多个请用(;)分号隔开"></textarea>
	            <div class="commitbox_cen">
	                <div class="left" id="cityname"></div>
	                <div class="right"><span class="save" onClick="saveFolder()">保存</span>&nbsp;&nbsp;<span class="quxiao" onClick="cancel_pl()">取消</span></div>
	            </div>
	        </div>
	  	</div>
  	</div>
</div>
	
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">		
				 	<div class="span12">
						<div class="widget-box">
							<div class="widget-header widget-header-blue widget-header-flat wi1dget-header-large">
								<h4 class="lighter">文件替换</h4>
							</div>
							<div class="widget-body">
							 <div class="widget-main">
							 <div class="step-content row-fluid position-relative">
								<div id="zhongxin">
							
								<input type="hidden" name="TYPE" id="TYPE" value="1"/>
								<input type="hidden" name="isAll" id="isAll" value="no"/>
								<table style="width:100%;" id="xtable">
								   <tr>
                                        <td>
                                             <input type="text" name="SOURCE_PATH" id="SOURCE_PATH" value="" placeholder="请选输入源文件或文件夹路径" style="width:88%"/>
                                             <a id="valid-btn" class="btn btn-mini btn-primary" onclick="checkFolder();">验证</a>
                                        </td>
                                    </tr>
                                  <!--   <tr>
                                        <div class="alert alert-danger" role="alert" id="error_info">
										  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
										  <span class="sr-only">Error:</span>
										    请输入正确的文件夹路径
										</div>
                                    </tr> -->
                                      <tr>
                                        <div class="ace-icon fa fa-check green" role="alert" id="error_info">
                                          <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                          <span class="sr-only">Error:</span>
                                                                                                                   文件替换申请已经发送!
                                        </div>
                                    </tr>
									<tr>
										<td style="margin-top:0px;padding-top: 10px;">
											 <div style="float: left;width:88%"><textarea name="TAR_PATH" id="TAR_PATH" rows="1" cols="50" style="width:100%;height:200px;" placeholder="请选输入目标路径,多个请用(;)分号隔开" title="请选输入目标路径,多个请用(;)分号隔开"></textarea></div>
											 <div style="float: right;width:10%;"><a class='btn btn-mini btn-info' title="编辑" onclick="dialog_open();"><i class='ace-icon fa fa-pencil-square-o bigger-120'></i></a></div>
										</td>
									</tr>
									<tr>
										<td style="text-align: center;padding-top: 5px;">
											<a style="float:left;padding-left: 15px;" class="btn btn-mini btn-primary" onclick="sendFolder();">替换</a>
										</td>
									</tr>
								</table>
								</div>
								<div id="zhongxin2" class="center" style="display:none"><br/><img src="static/images/jzx.gif" id='msg' /><br/><h4 class="lighter block green" id='msg'>正在发送...</h4><strong id="second_shows" class="red">6秒</strong>后关闭</div>
							 </div> 
							 </div><!--/widget-main-->
							</div><!--/widget-body-->
						</div>
					</div>
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
<%@ include file="foot.jsp"%>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 编辑框-->
<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
<!-- 编辑框-->

<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<!--引入属于此页面的js -->
<script type="text/javascript" src="static/js/myjs/toolFolder.js"></script>	
<script type="text/javascript">
	$(top.hangge());
</script>
</body>
</html>