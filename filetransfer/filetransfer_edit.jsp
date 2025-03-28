<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- datetime日期框 -->
<link rel='stylesheet'  href="static/ace/css/bootstrap-datetimepicker.min.css" >

<!-- jsp文件头和头部 -->
<%@ include file="../../../system/index/top.jsp"%>
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
                            <form action="equipment/${msg}.do" name="Form" id="Form"
                                method="post">
                                <input type="hidden" name="ID" id="ID" value="${pd.ID }" />
                                <input type="hidden" name="Status" id="status" value="${pd.Status }"/>
                                <input type="hidden" name="IsRemindByEmail" id="isRemindByEmail" value="${pd.IsRemindByEmail }"/>
                                
                                <div id="zhongxin" style="padding-top: 13px;">
                                    <div id="hasCPData" class="alert alert-warning"
                                        style="display: none">
                                        <strong>警告！</strong>此数据存在重复值。
                                    </div>
                                    <table id="table_report"
                                        class="table table-striped table-bordered table-hover">
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">源路径:</td>
                                            <td>
                                              <input type="text" name="LOCAL_PATH" id="LOCAL_PATH" value="${pd.LOCAL_PATH }" placeholder="请选输入源文件或文件夹路径"   title="LOCAL_PATH" style="width: 98%;" />
                                              <div class="switch switch-large">  <input type="checkbox" id="checkbox_md5check"   onclick="change()"/>
                                             <label class="form-check-label" for="flexSwitchCheckDefault">MD5验证</label></div>
                                             </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">文件操作:</td>
                                            <td>
                                                <label style="float:left;padding-left: 8px;padding-top:7px;">
                                                <input name="form-field-status" type="radio" class="ace" id="form-field-status0" <c:if test="${pd.TYPE == 0 }">checked="checked"</c:if> onclick="setStatus('0');"/>
                                                　　                                                            　<span class="lbl"> 剪切</span>
                                                </label>
                                                <label style="float:left;padding-left: 5px;padding-top:7px;">
                                                    <input name="form-field-status" type="radio" class="ace" id="form-field-status1" <c:if test="${pd.TYPE == 1 }">checked="checked"</c:if> onclick="setStatus('1');"/>
                                                    <span class="lbl"> 复制</span>
                                                </label>
       
                                            </td>   
                                        </tr>

                                        <tr>
                                            <td
                                                style="width: 79px; text-align: right; padding-top: 13px;">目标路径:</td>
                                            <td>
                                              <input type="text" name="TARGET_PATH"  id="TARGET_PATH"    
                                                placeholder="请选输入目标路径,多个请用(;)分号隔开" value="${pd.TARGET_PATH }" 
                                                title="TARGET_PATH" style="width: 98%;" />
                                             </td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="width: 79px; text-align: right; padding-top: 13px;">客户:</td>
                                            <td>
                                             <input type="text" name="CUSTOMER" id="CUSTOMER"  value="${pd.CUSTOMER }"  title="CUSTOMER"  style="width: 98%;" />
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            
                                            <td  style="width: 79px; text-align: right; padding-top: 13px;">Device:</td>
                                            <td>
                                             <input type="text" name="DEVICE" id="DEVICE"  value="${pd.DEVICE }"  title="DEVICE"  style="width: 98%;" />
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="text-align: center;" colspan="10"><a
                                                class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
                                                class="btn btn-mini btn-danger"
                                                onclick="top.Dialog.close();">取消</a></td>
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
    <%@ include file="../../../system/index/foot.jsp"%>
    <!-- ace scripts -->
    <script src="static/ace/js/ace/ace.js"></script>
    <!-- inline scripts related to this page -->
    <!-- 下拉框 -->
    <script src="static/ace/js/chosen.jquery.js"></script>
    <!--提示框-->
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
        <!-- 日期框 -->
    <script type="text/javascript" src="<%=basePath%>static/ace/js/date-time/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>static/ace/js/date-time/bootstrap-datetimepicker.zh-CN.js"></script>
    

    <script src="static/ace/js/bootbox.js"></script>
</body>
<script type="text/javascript">
    $(top.hangge());
    
    //保存
    function save(){
        var device=$("#device").val();
        var family_name=$("#family_name").val();
        var operation=$("#operation").val();
        var operation_code=$("#operation_code").val();
        var customer_code=$("#customer_code").val();
        var lod_file=$("#lod_file").val();
        
        if($("#device").val()==""){
            $("#device").tips({
                side:3,
                msg:'【device】 不能为空！',
                bg:'#AE81FF',
                time:2
            });
            $("#device").focus();
            return false;
        }
        if($("#family_name").val()==""){
            $("#family_name").tips({
                side:3,
                msg:'【family_name】 不能为空！',
                bg:'#AE81FF',
                time:2
            });
            $("#family_name").focus();
            return false;
        }
        
        if($("#operation").val()==""){
            $("#operation").tips({
                side:3,
                msg:'【operation】 不能为空！',
                bg:'#AE81FF',
                time:2
            });
            $("#operation").focus();
            return false;
        }

        if($("#operation_code").val()==""){
            $("#operation_code").tips({
                side:3,
                msg:'【operation_code】 不能为空！',
                bg:'#AE81FF',
                time:2
            });
            $("#operation").focus();
            return false;
        }
        
        if($("#customer_code").val()==""){
            $("#customer_code").tips({
                side:3,
                msg:'【customer_code】 不能为空！',
                bg:'#AE81FF',
                time:2
            });
            $("#customer_code").focus();
            return false;
        }

        if($("#lod_file").val()==""){
            $("#lod_file").tips({
                side:3,
                msg:'【lod_file】 不能为空！',
                bg:'#AE81FF',
                time:2
            });
            $("#lod_file").focus();
            return false;
        }

         if($("#id").val()==""){            
                hasCP();    
        }else{

            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show(); 

        } 
    }
    
    //判断数据是否存在
    function hasCP(){
        var device = $.trim($("#device").val());
        var operation = $.trim($("#operation").val());
        var customer_code = $.trim($("#customer_code").val());
        $.ajax({
            type: "POST",
            url: '<%=basePath%>equipment/hasCP.do',
            data : {
                device : device,
                operation : operation,
                customer_code : customer_code
            },
            dataType : 'json',
            cache : false,
            success : function(data) {
                if ("success" == data.result) {
                    $("#Form").submit();
                    $("#zhongxin").hide();
                    $("#zhongxin2").show();
                } else {
                    $("#device").css("background-color", "#D16E6C");
                    $("#operation").css("background-color", "#D16E6C");
                    $("#customer_code").css("background-color", "#D16E6C");
                    //setTimeout("$('#idx').val('此数据存在重复记录!')",500);
                    $("#hasCPData").show();
                }
            }
        });
    }
    
    //设置状态
    function setStatus(value){
            $("#status").val(value);    
    }
    
    //设置是否发送邮件
    function setEmail(value){
            $("#isRemindByEmail").val(value);   
    }

    $(function() {
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
        
        if ('${msg}' == "edit") {
            // document.Form.device.readOnly = true;               
        }
    });
</script>
</html>