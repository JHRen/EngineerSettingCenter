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
                            <form action="oiversion/${msg}.do" name="editForm" id="editForm" method="post">
                                <input type="hidden" name="ID" id="ID" value="${pd.ID}" /> 

                                <div id="zhongxin" style="padding-top: 13px;">
                                    <div id="hasCPData" class="alert alert-warning"
                                        style="display: none">
                                        <strong>警告！</strong>此数据存在重复值。
                                    </div>
                                    <table id="table_report" class="table table-striped table-bordered table-hover">
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">OIVersion:</td>
                                            <td><input type="text" name="OIVersion"id="OIVersion" value="${pd.OIVersion }"maxlength="60" title="OIVersion" style="width: 98%;" /></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">CustomerCode:</td>
                                            <td><input type="text" name="CustomerCode" id="CustomerCode" value="${pd.CustomerCode }" maxlength="60" title="CustomerCode"style="width: 98%;" /></td>
                                        </tr>
                                       <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">IGXL:</td>
                                            <td><input type="text" name="IGXL" id="IGXL" value="${pd.IGXL}"maxlength="60"  title="IGXL" style="width: 98%;" /></td>
                                        </tr> 
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">Process:</td>
                                            <td><input type="text" name="Process"id="Process" value="${pd.Process }"maxlength="60" title="Process" style="width: 98%;" /></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">DeviceCheck:</td>
                                            <td><input type="text" name="DeviceCheck" id="DeviceCheck" value="${pd.DeviceCheck }" maxlength="60" title="DeviceCheck"style="width: 98%;" /></td>
                                        </tr>
                                       <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">DiskSpaceCheck:</td>
                                            <td><input type="text" name="DiskSpaceCheck" id="DiskSpaceCheck" value="${pd.DiskSpaceCheck}"maxlength="60"  title="DiskSpaceCheck" style="width: 98%;" /></td>
                                        </tr> 
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">FlowidCheck:</td>
                                            <td><input type="text" name="FlowidCheck"id="FlowidCheck" value="${pd.FlowidCheck }"maxlength="60" title="FlowidCheck" style="width: 98%;" /></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">ProcessCheck:</td>
                                            <td><input type="text" name="ProcessCheck" id="ProcessCheck" value="${pd.ProcessCheck }" maxlength="60" title="ProcessCheck"style="width: 98%;" /></td>
                                        </tr>
                                       <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">TestcodeCheck:</td>
                                            <td><input type="text" name="TestcodeCheck" id="TestcodeCheck" value="${pd.TestcodeCheck}"maxlength="60" title="TestcodeCheck" style="width: 98%;" /></td>
                                        </tr> 
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">MD5Check:</td>
                                            <td><input type="text" name="MD5Check"id="MD5Check" value="${pd.MD5Check }"maxlength="60" title="MD5Check" style="width: 98%;" /></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">IGXLCheck:</td>
                                            <td><input type="text" name="IGXLCheck" id="IGXLCheck" value="${pd.IGXLCheck }" maxlength="60" title="IGXLCheck"style="width: 98%;" /></td>
                                        </tr>
                                       <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">OTProxyCheck:</td>
                                            <td><input type="text" name="OTProxyCheck" id="OTProxyCheck" value="${pd.OTProxyCheck}"maxlength="60"   title="OTProxyCheck" style="width: 98%;" /></td>
                                        </tr> 
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">WaferidCheck:</td>
                                            <td><input type="text" name="WaferidCheck"id="WaferidCheck" value="${pd.WaferidCheck }"maxlength="60" title="WaferidCheck" style="width: 98%;" /></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">PGMCheck:</td>
                                            <td><input type="text" name="PGMCheck" id="PGMCheck" value="${pd.PGMCheck }" maxlength="60" title="PGMCheck"style="width: 98%;" /></td>
                                        </tr>
                                       <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">LogRecord:</td>
                                            <td><input type="text" name="LogRecord" id="LogRecord" value="${pd.LogRecord}"maxlength="60"  title="LogRecord" style="width: 98%;" /></td>
                                        </tr> 
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">Enable:</td>
                                            <td><input type="text" name="Enable"id="Enable" value="${pd.Enable }"maxlength="60" title="Enable" style="width: 98%;" /></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">InputTime:</td>
                                            <td><input type="text" name="InputTime" id="InputTime" value="${pd.InputTime }" maxlength="60" title="InputTime"style="width: 98%;" /></td>
                                        </tr>
                                       <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">ModifyTime:</td>
                                            <td><input type="text" name="ModifyTime" id="ModifyTime" value="${pd.ModifyTime}"maxlength="60" title="ModifyTime" style="width: 98%;" /></td>
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
        	    if($("#OIVersion").val()==""){
                    $("#OIVersion").tips({
                        side:3,
                        msg:'Please input OIVersion!',
                        bg:'#AE81FF',
                        time:2
                    });
                    $("#OIVersion").focus();
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
        	    
        	 
            hasData();
        }else{
            $("#editForm").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show(); 
        }  
    }
    
    //判断数据是否存在
    function hasData(){
        var Hostname = $.trim($("#OIVersion").val());
        var CustomerCode = $.trim($("#CustomerCode").val());
        $.ajax({
            type: "POST",
            url: '<%=basePath%>oiversion/hasData.do',
            data : {
            	OIVersion : OIVersion,
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
                    $("#OIVersion").css("background-color", "#D16E6C");
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
    
     
    
</script>
</html>