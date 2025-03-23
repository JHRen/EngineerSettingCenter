<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
 <meta charset="utf-8" />
 <title>${pd.cuString}</title>
 <meta name="description" content="" />
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
 <!-- Latest compiled and minified CSS -->
 <link rel="icon" href="static/login/favicon.ico" type="image/x-icon"/>
 <!-- bootstrap & fontawesome -->
 <link rel="stylesheet" href="static/ace/css/bootstrap.css" />
 <link rel="stylesheet" href="static/ace/css/font-awesome.css" />
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- datetime日期框 -->
<link rel='stylesheet'
    href="static/ace/css/bootstrap-datetimepicker.min.css">
<!-- 自定义页面css -->
<link rel="stylesheet" href="static/ace/css/filetransfer.css" />
<!-- jsp文件头和头部 -->
<%-- <%@ include file="../../../system/index/top.jsp"%> --%>
</head>
<body class="no-skin">
    <div class="main-container">
    <form action="filetransfer/ft-non/${msg }.do" name="Form" id="Form" method="post">
        <input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
    <div id="zhongxin" style="padding-top: 13px;">
        <!-- ----------------------------- General Info Module ----------------------------------->
      <%@ include file="../../filetransfer/generalInfo_list.jsp"%>
        <!-- -----------------------------FT-Non-Memory Module ----------------------------------->
        <fieldset id="ft-non-memory">
            <legend>FT-Non-Memory</legend>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <div class="radio">
                            <label ><input class="ace" name="form-field-radio" id="form-field-radio1" onclick="setType('1');" <c:if test="${pd.isReadProgram == '1' }">checked="checked"</c:if> type="radio" ><span class="lbl">Program new release</span></label>
                            <label ><input class="ace"  name="form-field-radio" id="form-field-radio2" onclick="setType('2');" <c:if test="${pd.isReadProgram == '2' }">checked="checked"</c:if> type="radio" ><span class="lbl">Program update</span></label>
                             <input type="hidden" name="isReadProgram" id="isReadProgram" value="${pd.isReadProgram }"/>                         
                        </div>
                    </div>
                </div>
            </div>
            
             <div class="row"> 
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Tester Type</label>
                         <input type="text" name="testerType" class="form-control" placeholder="Tester Type" />
                    </div>
                </div>
                 
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Program Name From</label> 
                        <input type="text" name="programNameFrom" class="form-control" placeholder="Program Name From" id="program-name-from" readonly />
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Program Name To</label>
                         <input type="text" name="programNameTo"  class="form-control" placeholder="Program Name To" id="ft-memory-program-to" />
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-4">
                    <div class="form-group">
                        <label class="required">Program source path</label> 
                        <input type="text" name="programSourcePath" class="form-control" placeholder="Program source path" id="program-source-path">
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label class="required">Program destination path</label>
                         <input type="text" name="programDestinationPath" class="form-control" placeholder="Program destination path"  id="program-destination-path"/>
                    </div>
                </div>
 
               <div class="col-md-4">
                    <div class="form-group">
                        <label>Old Version Program Path</label>
                         <input type="text" name="oldVersionProgramPath"  class="form-control" placeholder="Old Version Program Path" id="ft-memory-old-path" readonly />
                          
                    </div>
                </div>   

            </div>
            
            <div class="row">  
               <div class="col-md-4">
                    <div class="form-group">
                        <label>Setup file Source path</label>
                          <input type="text" name="setupFileSourcePath" class="form-control" placeholder="Setup file Source path" />
                    </div>
                </div>
                
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Setup file destination path</label>
                          <input type="text" name="setupFileDestinationPath" class="form-control" placeholder="Setup file destination path" />
                    </div>
                </div>             
            
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Auto Delete</label>
                         <select class="form-control" name="autoDelete" >
                            <option value="0">Disable</option>
                            <option value="1">Immediately</option>
                        </select>
                    </div>
                </div>
            </div>

        </fieldset>

        <hr class="hr-module">
        <!-- Submit Button -->
        <div class="submit-button">
            <a class="btn btn-lg btn-success" onclick="save();">提交</a>
        </div>
        <!-- 返回顶部 -->
        <a href="#" id="btn-scroll-up"  class="btn-scroll-up btn btn-sm btn-inverse"  ">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>
    </div>
     <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
    
</form>
    <!-- /.main-container -->
</div>
    <!-- basic scripts -->
    <!-- 页面底部js¨ -->
    <%@ include file="../../../system/index/foot.jsp"%>
    <!-- ace scripts -->
    <script src="static/ace/js/ace/ace.js"></script>
    <!-- inline scripts related to this page -->
    <!-- 下拉框 -->
    <script src="static/ace/js/chosen.jquery.js"></script>
    <!--提示框-->
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
    <!-- 日期框 -->
    <script type="text/javascript"
        src="<%=basePath%>static/ace/js/date-time/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript"
        src="<%=basePath%>static/ace/js/date-time/bootstrap-datetimepicker.zh-CN.js"></script>

    <script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="static/ace/js/bootbox.js"></script>
    <!--提示框-->
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
    
    <script type="text/javascript">
        $(top.hangge());//关闭加载状态
        //提交
        function save(){
        	if($("#customerCode").val()==""){
                $("#customerCode").tips({
                    side:3,
                    msg:'请输customerCode',
                    bg:'#AE81FF',
                    time:3
                });
                $("#customerCode").focus();
               return false;
            }
        	if($("#device").val()==""){
                $("#device").tips({
                    side:3,
                    msg:'请输device',
                    bg:'#AE81FF',
                    time:3
                });
                $("#device").focus();
               return false;
            }
            
            if($("#reason").val()==""){
                $("#reason").tips({
                    side:3,
                    msg:'请输reason',
                    bg:'#AE81FF',
                    time:3
                });
                $("#reason").focus();
               return false;
            }
            
            if($("#program-source-path").val()==""){
                $("#program-source-path").tips({
                    side:3,
                    msg:'请输program source path',
                    bg:'#AE81FF',
                    time:3
                });
                $("#program-source-path").focus();
               return false;
            }
            
            if($("#program-destination-path").val()==""){
                $("#program-destination-path").tips({
                    side:3,
                    msg:'请输program destination path',
                    bg:'#AE81FF',
                    time:3
                });
                $("#program-destination-path").focus();
               return false;
            }
        	
             if($("#ID").val()==""){
                checkData();
            }else{
                    $("#Form").submit();
                    $("#zhongxin").hide();
                    $("#zhongxin2").show(); 
                    bootbox.alert("操作成功，已邮件提醒管理员及时审批！");
            }  
        }
        
      //判断数据准确性
        function checkData(){
            var programSourcePath = $.trim($("#program-source-path").val());
            $.ajax({
                type: "POST",
                url: '<%=basePath%>filetransfer/checkData.do',
                data: {programSourcePath:programSourcePath},
                dataType:'json',
                cache: false,
                success: function(data){
                     if("success" == data.result){
                    	  $("#Form").submit();
                          $("#zhongxin").hide();
                        //$("#zhongxin2").show();
                        bootbox.alert("操作成功，已邮件提醒管理员及时审批！");
                     }else{
                        $("#program-source-path").css("background-color","#D16E6C");
                        $('#program-source-path').focus();
                     }
                }
            });
        }
        
        
        function setType(value){
                if (value == "1") {
                    // 禁用输入框
                    document.getElementById("program-name-from").readOnly = true;
                    document.getElementById("ft-memory-old-path").readOnly = true;
                } else {
                    // 恢复可输入状态
                    document.getElementById("program-name-from").readOnly = false;
                    document.getElementById("ft-memory-old-path").readOnly = false;
                }
            }
/* 
        function toggleDeleteOptions(selectElement) {
            const deleteOptions = document
                    .getElementsByClassName("delete-options");
            for (let i = 0; i < deleteOptions.length; i++) {
                if (selectElement.value === "enable") {
                    deleteOptions[i].style.display = "block";
                } else {
                    deleteOptions[i].style.display = "none";
                }
            }
        } */

/*         function toggleMD5Fields(selectElement) {
            const md5Fields = document.getElementsByClassName("md5-fields");
            if (selectElement.value === "yes") {
                for (let i = 0; i < md5Fields.length; i++) {
                    md5Fields[i].style.display = "block";
                }
            } else {
                for (let i = 0; i < md5Fields.length; i++) {
                    md5Fields[i].style.display = "none";
                }
            }
        } */
    </script>
</body>
</html>