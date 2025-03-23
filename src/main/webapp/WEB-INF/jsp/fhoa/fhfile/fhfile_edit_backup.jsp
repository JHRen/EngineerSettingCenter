<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<script type="text/javascript" src="static/ace/js/jquery.js"></script>
	<!-- 上传插件 -->
	<link href="plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="plugins/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="plugins/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<!-- 上传插件 -->
	<script type="text/javascript">
	var jsessionid = "<%=session.getId()%>";  //勿删，uploadify兼容火狐用到
	</script>
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
					
					<form action="fhfile/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" value="no" id="hasTp1" />
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">文件名:</td>
								<td><input type="text" name="NAME" id="NAME" value="" maxlength="30" placeholder="这里输入文件名" title="文件名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;" id="FILEPATHn">文件:</td>
								<td>
									<input type="file" name="File_name" id="uploadify1" keepDefaultStyle = "true"/>
									<input type="hidden" name="FILEPATH" id="FILEPATH" value=""/>
								</td>
							</tr>
							
							<tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">客户分类:</td>
                               
                                <td style="vertical-align:top;padding-left:2px">
                                    <select name="CUSTOMER" id="CUSTOMER"  title="请选择客户" style="width:130px;">
                                    </select>
                                </td>
                            </tr>
                            
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">级别分类:</td>
                               
                               <td style="vertical-align:top;padding-left:2px">
                                    <select name="LEVEL" id="LEVEL"  title="请选择级别" style="width:130px;">
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 18px;">Defect Mode:</td>
                               
                               <td style="vertical-align:top;padding-left:2px">
                                    <select name="DEFECT_MODE" id="DEFECT_MODE"  title="请选择Defect Mode" style="width:130px;">
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">Process:</td>
                               
                               <td style="vertical-align:top;padding-left:2px">
                                    <select name="PROCESS" id="PROCESS"  title="请选择Process" style="width:130px;">
                                    </select>
                                </td>
                            </tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">文件说明:</td>
								<td><input type="text" name="BZ" id="BZ" value="" maxlength="100" placeholder="这里输入信息" title="备注" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入文件名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME").focus();
			return false;
			}
			if($("#hasTp1").val()=="no"){
				$("#FILEPATHn").tips({
					side:2,
			        msg:'请选择文件',
			        bg:'#AE81FF',
			        time:2
			    });
			return false;
			}
			if($("#CUSTOMER").val()==""){
                $("#CUSTOMER").tips({
                    side:2,
                    msg:'请选择客户分类',
                    bg:'#AE81FF',
                    time:2
                });
            return false;
            }
			if($("#LEVEL").val()==""){
                $("#LEVEL").tips({
                    side:2,
                    msg:'请选择级别分类',
                    bg:'#AE81FF',
                    time:2
                });
            return false;
            }
		     if($("#DEFECT_MODE").val()==""){
	                $("#DEFECT_MODE").tips({
	                    side:2,
	                    msg:'请选择Defect Mode分类',
	                    bg:'#AE81FF',
	                    time:2
	                });
	            return false;
	            }
		     if($("#PROCESS").val()==""){
	                $("#PROCESS").tips({
	                    side:2,
	                    msg:'请选择Process分类',
	                    bg:'#AE81FF',
	                    time:2
	                });
	            return false;
	            }
			/* if($("#BZ").val()==""){
				$("#BZ").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BZ").focus();
			return false;
			} */
			$('#uploadify1').uploadifyUpload();
		}
		
		//====================上传=================
		$(document).ready(function(){
			var str='';
			$("#uploadify1").uploadify({
				'buttonImg'	: 	"<%=basePath%>static/images/fileup.png",
				'uploader'	:	"<%=basePath%>plugins/uploadify/uploadify.swf",
				'script'    :	"<%=basePath%>plugins/uploadify/uploadFile.jsp;jsessionid="+jsessionid,
				'cancelImg' :	"<%=basePath%>plugins/uploadify/cancel.png",
				'folder'	:	"\\\\C3WJAVAP01\\EngCenterFile",//上传文件存放的路径,请保持与uploadFile.jsp中PATH的值相同
				'queueId'	:	"fileQueue",
				'queueSizeLimit'	:	1,//限制上传文件的数量
				//'fileExt'	:	"*.rar,*.zip",
				//'fileDesc'	:	"RAR *.rar",//限制文件类型
				'fileExt'     : '*.*;*.*;*.*',
				'fileDesc'    : 'Please choose(.*, .*, .*)',
				'auto'		:	false,
				'multi'		:	true,//是否允许多文件上传
				'simUploadLimit':	2,//同时运行上传的进程数量
				'buttonText':	"files",
				'scriptData':	{'uploadPath':'/uploadFiles/uploadFile/'},//这个参数用于传递用户自己的参数，此时'method' 必须设置为GET, 后台可以用request.getParameter('name')获取名字的值
				'method'	:	"GET",
				'onComplete':function(event,queueId,fileObj,response,data){
					str = response.trim();//单个上传完毕执行
				},
				'onAllComplete' : function(event,data) {
					//alert(str);	//全部上传完毕执行
					$("#FILEPATH").val(str);
					$("#Form").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
		    	},
		    	'onSelect' : function(event, queueId, fileObj){
		    		$("#hasTp1").val("ok");
		    	}
			});
					
		});
		//====================上传=================
			//清除空格
		String.prototype.trim=function(){
		     return this.replace(/(^\s*)|(\s*$)/g,'');
		};
		
		$(function() {
		    
		    //下拉框
		    var CUSTOMER = "${pd.CUSTOMER}";
            $.ajax({
                type: "POST",
                url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
                data: {DICTIONARIES_ID:'dca45fe9c6804f4d89ba603a445975ff'},//DICTIONARIES_ID 为客户分类
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
            $.ajax({
                type: "POST",
                url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
                data: {DICTIONARIES_ID:'80d37d77c5144c559356d7ab663d1f2d'},//DICTIONARIES_ID 为级别分类
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
            $.ajax({
                type: "POST",
                url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
                data: {DICTIONARIES_ID:'ce174640544549f1b31c8f66e01c111b'},//DICTIONARIES_ID 为级别分类
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
            $.ajax({
                type: "POST",
                url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
                data: {DICTIONARIES_ID:'d21b296c7c8844fc8cebe925f5b37180'},//DICTIONARIES_ID 为级别分类
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
            

		});
		


		</script>
</body>
</html>