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
	
	   <style type="text/css">
        #dialog-add,#dialog-message,#dialog-comment{width:100%; height:100%; position:fixed; top:0px; z-index:99999999; display:none;}
        .commitopacity{position:absolute; width:100%; height:700px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.5; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
        .commitbox{width:100%; margin:0px auto; position:absolute; top:0px; z-index:99999;}
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
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<script type="text/javascript" src="static/ace/js/jquery.js"></script>
	
	<!-- 上传插件 uploadifive-->
	<link href="plugins/uploadFive/uploadifive.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="plugins/uploadFive/jquery.uploadifive.min.js"></script>
		
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
					
					<form action="assemblyfile/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" value="no" id="hasTp1" />
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;">文件名:</td>
								<td><input type="text" name="NAME" id="NAME" value=""  placeholder="这里输入文件名" title="文件名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:100px;text-align: right;padding-top: 13px;" id="FILEPATHn">文件:</td>
								<td>
									<!-- <input type="file" name="File_name" id="uploadify1" keepDefaultStyle = "true"/> -->
									<input type="file" name="File_name" id="uploadifive1" keepDefaultStyle = "true"/>
									 <div id="fileQueue" class="fileQueue"></div>
									<input type="hidden" name="FILEPATH" id="FILEPATH" value=""/>
								</td>
							</tr>
							
							<tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">客户分类:</td>
                               
                                <td style="vertical-align:top;padding-left:2px">
                                    <select name="CUSTOMER" id="CUSTOMER"  title="请选择客户" style="width:155px;">
                                    </select>
                                </td>
                            </tr>
                            
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">级别分类:</td>
                               
                               <td style="vertical-align:top;padding-left:2px">
                                    <select name="LEVEL" id="LEVEL"  title="请选择级别" style="width:155px;">
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 18px;">Defect Mode:</td>
                               
                               <td style="vertical-align:top;padding-left:2px">
                                    <select name="DEFECT_MODE" id="DEFECT_MODE"  title="请选择Defect Mode" style="width:155px;">
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td style="width:100px;text-align: right;padding-top: 13px;">Process:</td>
                               
                               <td style="vertical-align:top;padding-left:2px">
                                    <select name="PROCESS" id="PROCESS"  title="请选择Process" style="width:155px;">
                                    </select>
                                </td>
                            </tr>
                           
                            <tr>
                              <!-- 邮件添加弹出框 -->
                             <div id="dialog-add">
                                    <div class="commitopacity"></div>
                                    <div class="commitbox">
                                        <div class="commitbox_inner">
                                            <div class="commitbox_top">
                                                <textarea name="EMAILs" id="EMAILs" placeholder="请输入对方邮箱,多个请用(;)分号隔开" title="请输入对方邮箱,多个请用(;)分号隔开"></textarea>
                                                <div class="commitbox_cen">
                                                    <div class="left" id="cityname"></div>
                                                    <div class="right"><span class="save" onClick="saveEmail()">保存</span>&nbsp;&nbsp;<span class="quxiao" onClick="cancel_pl()">取消</span></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                              </div>
                              <!-- 邮件添加弹出框 end -->
                             <td style="width:100px;text-align: right;padding-top: 13px;">邮箱:</td>
				                <td style="margin-top:0px;">
								       <!-- 编辑邮箱  -->
				                    <div class="main-content">
								
								       <table style="width:98%;margin-top: 10px;margin-left: 9px;" >
								           <tr>
								               <td style="margin-top:0px;">
								                    <div style="float: left;" style="width:81%"><textarea name="EMAILS" id="EMAIL" rows="1" cols="50" style="width:230px;height:45px;" placeholder="请输入邮箱,多个请用(;)分号隔开" title="请输入对方邮箱,多个请用(;)分号隔开">${pd.EMAILS}</textarea></div>
								                    <div style="float: right;margin-right: 12px;" style="width:19%"><a class='btn btn-mini btn-info' title="编辑邮箱" onclick="dialog_open();"><i class='ace-icon fa fa-pencil-square-o bigger-120'></i></a></div>
								               </td>
								           </tr>
								       </table>
								       </div>
				                      <!-- 编辑邮箱   end-->
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
    <!-- 编辑框-->
    <script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>
    <!-- 编辑框-->
    <!--引入属于此页面的js -->
    <script type="text/javascript" src="static/js/myjs/headEmail.js"></script>
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
			}
			$('#uploadify1').uploadifyUpload();
			*/
		     $('#uploadifive1').uploadifive('upload');
		  
		}
		
  
		//====================上传=================
			 $("#uploadifive1").uploadifive({
                'buttonText': '选择文件',                        //按钮文本
                'queueID': 'fileQueue',                        //队列的ID
                'queueSizeLimit': 1,                          //队列最多可上传文件数量，默认为999
                'auto': false,                                 //如果设置为true，则文件将在添加到队列时自动上传。
                'multi': true,                                 //是否为多选，默认为true
                'removeCompleted': true,                       //是否完成后移除序列，默认为true
                'fileSizeLimit': '0',                       //单个文件大小，0为无限制，可接受KB,MB,GB等单位的字符串值
                'fileType': "*.ppt,*.pptx",                      //上传的文件筛选后缀过滤器 如果限制图片类型可以用 image/*)
                'uploadScript': "<%=basePath%>plugins/uploadFive/uploadFile2.jsp",  //文件上传的地址
                'formData': {}, //传递的参数
                'method'       : 'post',
                //开始上传
                "onUploadComplete": function (file, data) {
                    str = data.trim();
                    },
                "onQueueComplete": function (uploads) {
                    //alert(str);   //全部上传完毕执行
                    $("#FILEPATH").val(str);
                    $("#Form").submit();
                    $("#zhongxin").hide();
                    $("#zhongxin2").show();
                },
                "onSelect": function (queue) {
                    $("#hasTp1").val("ok");
                }
            });
                    
       
			<%--  $("#uploadify1").uploadify({
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
					
		}); --%>
		//====================上传=================
			//清除空格
		String.prototype.trim=function(){
		     return this.replace(/(^\s*)|(\s*$)/g,'');
		};
		
		$(function() {
		    
		    //下拉框
		    var CUSTOMER = "${pd.CUSTOMER}";
		    var zidan = "${zidian.customer}";
            $.ajax({
                type: "POST",
                url: '<%=basePath%>dictionaries/getLevels.do?tm='+new Date().getTime(),
                data: {DICTIONARIES_ID:zidan},//DICTIONARIES_ID 为客户分类
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
            

		});
		


		</script>
</body>
</html>