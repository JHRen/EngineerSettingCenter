<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
                                <form action="fhfile/version.do" name="form" id="form" method="post">
                                
                                 <div id="zhongxin" style="padding-top: 13px;">
                                    <table id="simple-table"
                                    class="table table-striped table-bordered table-hover"
                                    style="margin-top: 5px;">
                                    <thead>
                                        <tr>
                                            <th class="center" style="width: 50px;">序号</th>
                                            <th class="center">文件名</th>
                                             <th class="center">上传时间</th>
                                             <th class="center">截至时间</th>
                                        </tr>
                                    </thead>
                                    
                                   <tbody >     
                                   <!-- 开始循环 -->
                                        <c:choose>
                                            <c:when test="${not empty varList}">
                                                    <c:forEach items="${varList}" var="var" varStatus="vs">
                                                        <tr>
                                                            <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                            <td class='center'  style=" text-overflow: ellipsis; white-space:nowrap; overflow:hidden; width:250px;" data-formatter="FirstFormatter">
                                                            <img style="margin-top: -3px;" alt="${var.NAME}" src="static/images/extension/${var.fileType}.png">
                                                                <a title="${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}" style="cursor:pointer;" onclick="goViewOffice('${var.FHFILE_ID}','${var.FILEPATH}')">${var.NAME}${fn:substring(var.FILEPATH ,19,fn:length(var.FILEPATH))}</a>
                                                           </td>
                                                            <td class='center'>${var.CTIME}</td>
                                                            <td class='center'>${var.DUE_DATE}</td>
                                                        </tr>
                                                    </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr class="main_info">
                                                    <td colspan="100" class="center">没有相关数据</td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                        
                                    </tbody>
                                      
                                   
                                </table>
                                    </div>
                                    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>          
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
    <%@ include file="../../system/index/foot.jsp"%>
    <!-- ace scripts -->
    <script src="static/ace/js/ace/ace.js"></script>
    <!-- inline scripts related to this page -->
   
    <!--提示框-->
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
    <!-- 弹出窗口 -->
    <script src="static/ace/js/bootbox.js"></script>
</body>
<script type="text/javascript">
    $(top.hangge());
    
    //预览文件
    function goViewOffice(fileID,fileName){
        var originUrl  = '<%=basePath%>fhfile/download?FHFILE_ID='+fileID;  //要预览文件的访问地址
        var previewUrl = originUrl + '&fullfilename='+fileName;
       
        var url = '<%=basePath%>fhfile/goViewOffice?FHFILE_ID='+fileID;
        $.get(url,function(data){
            window.open('http://10.86.140.200:8012/onlinePreview?url='+encodeURIComponent(Base64.encode(previewUrl)));
        }); 
     
    }
   

    
</script>
</html>