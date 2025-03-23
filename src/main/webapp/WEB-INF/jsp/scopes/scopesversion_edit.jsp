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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
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
                            <form action="scopes/${msg}.do" name="editForm" id="editForm" method="post">
                                <input type="hidden" name="ID" id="ID" value="${pd.ID}" /> 
                                <input type="hidden" name="VersionNo" id="VersionNo" value="${pd.VersionNo}" /> 
                                <input type="hidden" name="versionAll" id="versionAll" value="${pd.versionAll}" /> 
                                <input type="hidden" name="VersionName" value="${pd.VersionName}" />
                        <%--         <input type="hidden" name="Hostname_check"value="${pd.Hostname}" /> 
                                <input type="hidden" name="CustomerCode_check" value="${pd.CustomerCode}" /> 
                                <input type="hidden" name="Factory_check" value="${pd.Factory}" /> 
                                <input type="hidden" name="Process_check" value="${pd.Process}" />
                                <input type="hidden"name="Platform_check" value="${pd.Platform}" /> 
                                <input type="hidden" name="VersionName_check" value="${pd.VersionName}" /> --%>

                                <div id="zhongxin" style="padding-top: 13px;">
                                    <div id="hasCPData" class="alert alert-warning"
                                        style="display: none">
                                        <strong>警告！</strong>此数据存在重复值。
                                    </div>
                                    <table id="table_report" class="table table-striped table-bordered table-hover">
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">*Hostname:</td>
                                            <td><input type="text" name="Hostname"id="Hostname" value="${pd.Hostname }"maxlength="60" title="Hostname" style="width: 98%;" /></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">*CustomerCode:</td>
                                            <td><input type="text" name="CustomerCode" id="CustomerCode" value="${pd.CustomerCode }" maxlength="60" title="CustomerCode"style="width: 98%;" /></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">*Factory:</td>
                                            <td style="vertical-align:top;padding-left:2px;"> 
								                <select class="chosen-select form-control" name="Factory" id="Factory" data-placeholder="" style="vertical-align:top;width: 79px;">
								                <option value=""></option>
								                <option value="ATC" <c:if test="${pd.Factory == 'ATC' }">selected</c:if>>ATC</option>
								                <option value="QTS" <c:if test="${pd.Factory == 'QTS' }">selected</c:if>>QTS</option>
								                </select>
								            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">*Process:</td>
                                            <td style="vertical-align:top;padding-left:2px;"> 
                                                <select class="chosen-select form-control" name="Process" id="Process" data-placeholder="" style="vertical-align:top;width: 79px;">
                                                <option value=""></option>
                                                <option value="FT" <c:if test="${pd.Process == 'FT' }">selected</c:if> >FT</option>
                                                <option value="CP" <c:if test="${pd.Process == 'CP' }">selected</c:if>>CP</option>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">*Platform:</td>
                                            <td style="vertical-align:top;padding-left:2px;"> 
                                                <select class="chosen-select form-control" name="Platform" id="Platform" data-placeholder="" style="vertical-align:top;width: 79px;">
                                                <option value=""></option>
                                                <option value="WIN"  <c:if test="${pd.Platform == 'WIN'  }">selected</c:if>>WIN</option>
                                                <option value="SMT7" <c:if test="${pd.Platform == 'SMT7' }">selected</c:if>>SMT7</option>
                                                <option value="SMT8" <c:if test="${pd.Platform == 'SMT8' }">selected</c:if>>SMT8</option>
                                                </select>
                                            </td>
                                        </tr>
                                    <%--     <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">Devicename:</td>
                                            <td><input type="text" name="Devicename" id="Devicename" value="${pd.Devicename}"maxlength="60" placeholder="选填项"  title="Devicename" style="width: 98%;" /></td>
                                        </tr> --%>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">*VersionName:</td>
                                            <td style="vertical-align:top;padding-left:2px">
                                           <select class="chosen-select form-control" name="Version" id="Version" data-placeholder="" style="vertical-align:top;width: 79px;">
                                            </select> 
                                            </td>
                                        </tr>
                                        
                                        <tr>
			                                <td style="width:70px;text-align: right;padding-top: 13px;">Mail To:</td>
			                                <td>
			                                    <textarea rows="3" cols="46" name="Mails" id="Mails" placeholder="这里输入Email, 以 ; 做分隔,为空则不发送" title="Mails"  style="width:98%;"></textarea>
			                                </td>
			                            </tr>
                                        
                                        <tr>
                                            <td style="text-align: center;" colspan="10">
                                                <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                                                <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div id="zhongxin2" class="center" style="display: none">
                                    <br />
                                    <br />
                                    <br />
                                    <br />
                                    <img src="static/images/jiazai.gif" /><br />
                                    <h4 class="lighter block green"></h4>
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
    </div>
    <!-- /.main-container -->
    <!-- basic scripts -->
    <!-- 页面底部js¨ -->
    <%@ include file="../system/index/foot.jsp"%>
    <!-- ace scripts -->
    <script src="static/ace/js/ace/ace.js"></script>
    <!-- inline scripts related to this page -->
    <!-- 下拉框 -->
    <script src="static/ace/js/chosen.jquery.js"></script>
    <!--提示框-->
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
    <!-- 弹出窗口 -->
    <script src="static/ace/js/bootbox.js"></script>
</body>
<script type="text/javascript">
    $(top.hangge());
    //保存
    function save(){
         if($("#ID").val()==""){
        	 
        	    if($("#Version").val()==""){
                    $("#Version").tips({
                        side:3,
                        msg:'Please Select VersionName!',
                        bg:'#AE81FF',
                        time:2
                    });
                    $("#Version").focus();
                    return false;
                }  
        	    
        	    if($("#Hostname").val()==""){
                    $("#Hostname").tips({
                        side:3,
                        msg:'Please input Hostname!',
                        bg:'#AE81FF',
                        time:2
                    });
                    $("#Hostname").focus();
                    return false;
                }  
        	    
        	    if($("#CustomerCode").val()==""){
                    $("#CustomerCode").tips({
                        side:3,
                        msg:'Please input CustomerCode!',
                        bg:'#AE81FF',
                        time:2
                    });
                    $("#CustomerCode").focus();
                    return false;
                }  
        	    
        	    if($("#Factory").val()==""){
                    $("#Factory").tips({
                        side:3,
                        msg:'Please Select Factory!',
                        bg:'#AE81FF',
                        time:2
                    });
                    $("#Factory").focus();
                    return false;
                }  
        	    
        	    if($("#Process").val()==""){
                    $("#Process").tips({
                        side:3,
                        msg:'Please Select Process!',
                        bg:'#AE81FF',
                        time:2
                    });
                    $("#Process").focus();
                    return false;
                }  
        	    
        	    if($("#Platform").val()==""){
                    $("#Platform").tips({
                        side:3,
                        msg:'Please Select Platform!',
                        bg:'#AE81FF',
                        time:2
                    });
                    $("#Platform").focus();
                    return false;
                }  
        	 
            hasData();
        }else{
            $("#editForm").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show(); 
        }  
    }
    
    //判断数据是否存在
    function hasData(){
        var Hostname = $.trim($("#Hostname").val());
        var CustomerCode = $.trim($("#CustomerCode").val());
        $.ajax({
            type: "POST",
            url: '<%=basePath%>scopes/hasData.do',
            data : {
            	Hostname : Hostname,
            	CustomerCode : CustomerCode
            },
            dataType : 'json',
            cache : false,
            success : function(data) {
                if ("success" == data.result) {
                    $("#editForm").submit();
                    $("#zhongxin").hide();
                    $("#zhongxin2").show();
                } else {
                    $("#Hostname").css("background-color", "#D16E6C");
                    $("#CustomerCode").css("background-color", "#D16E6C");
                    //setTimeout("$('#idx').val('此数据存在重复记录!')",500);
                    $("#hasCPData").show();
                }
            }
        });
    }

    $(function() {
        //下拉框
        if (!ace.vars['touch']) {
            $('.chosen-select').chosen({
                allow_single_deselect : true
            });
            $(window).off('resize.chosen').on('resize.chosen', function() {
                $('.chosen-select').each(function() {
                    var $this = $(this);
                    $this.next().css({
                        'width' : $this.parent().width()
                    });
                });
            }).trigger('resize.chosen');
            $(document).on('settings.ace.chosen',
                    function(e, event_name, event_val) {
                        if (event_name != 'sidebar_collapsed')
                            return;
                        $('.chosen-select').each(function() {
                            var $this = $(this);
                            $this.next().css({
                                'width' : $this.parent().width()
                            });
                        });
                    });
            $('#chosen-multiple-style .btn').on('click', function(e) {
            	
                var target = $(this).find('input[type=radio]');
                var which = parseInt(target.val());
                if (which == 2)
                    $('#form-field-select-4').addClass('tag-input-style');
                else
                    $('#form-field-select-4').removeClass('tag-input-style');
            });
        }
        
        
        
    });
    
    $(function() {
    	
        $('#Process').on('change', function() {
        	   var Factory = $("#Factory").val();
               var Process = $("#Process").val();
            $.ajax({
                type: "POST",
                url: '<%=basePath%>scopes/getVersion.do?tm='+new Date().getTime(),
                data: {Factory: Factory,Process:Process},
                dataType:'json',
                cache: false,
                success: function(data){
                	 $("#Version").html('<option value="" >--choose version name--</option>');
                     $.each(data.list, function(i, dvar){
                         if(Version == dvar.Version){
                             $("#Version").append("<option value="+dvar.verNoAndName+" selected='selected'>"+dvar.VersionName+"</option>");
                         }else{
                             $("#Version").append("<option value="+dvar.verNoAndName+" >"+dvar.VersionName+"</option>");
                         }
                     });
                     $("#Version").trigger("chosen:updated");
                }
            });  
        });
    	
        $('#Factory').on('change', function() {
        	   var Factory = $("#Factory").val();
               var Process = $("#Process").val();
            $.ajax({
                type: "POST",
                url: '<%=basePath%>scopes/getVersion.do?tm='+new Date().getTime(),
                data: {Factory: Factory,Process:Process},
                dataType:'json',
                cache: false,
                success: function(data){
                	 $("#Version").html('<option value="" >--choose version name--</option>');
                     $.each(data.list, function(i, dvar){
                         if(Version == dvar.Version){
                             $("#Version").append("<option value="+dvar.verNoAndName+" selected='selected'>"+dvar.VersionName+"</option>");
                         }else{
                             $("#Version").append("<option value="+dvar.verNoAndName+" >"+dvar.VersionName+"</option>");
                         }
                     });
                     $("#Version").trigger("chosen:updated");
                }
            });  
        });
        
        var Factory = $("#Factory").val();
        var Process = $("#Process").val();
        
        if(Factory!=""&&Process!=""){
            var versionAll=$("#versionAll").val();
            
            $.ajax({
                type: "POST",
                url: '<%=basePath%>scopes/getVersion.do?tm='+new Date().getTime(),
                data: {Factory: Factory,Process:Process},
                dataType:'json',
                cache: false,
                success: function(data){
                     $("#Version").html('<option value="" >--choose version name--</option>');
                     $.each(data.list, function(i, dvar){
                       //  alert(versionAll+"| "+dvar.verNoAndName);
                         if(versionAll == dvar.verNoAndName){
                             $("#Version").append("<option value="+dvar.verNoAndName+" selected='selected'>"+dvar.VersionName+"</option>");
                         }else{
                             $("#Version").append("<option value="+dvar.verNoAndName+" >"+dvar.VersionName+"</option>");
                         }
                     });
                     $("#Version").trigger("chosen:updated");
                }
            });  
            
        } 
    });
    
</script>
</html>