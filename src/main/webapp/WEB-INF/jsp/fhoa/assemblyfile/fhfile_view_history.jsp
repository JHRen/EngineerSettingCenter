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
                                <form action="file/history/${msg}.do" name="form" id="form" method="post">
                                
                                 <div id="zhongxin" style="padding-top: 13px;">
                                  <div><strong>指定Manager查看人数 :</strong>&nbsp; ${adminViewCount } / ${admintNum}&nbsp;<strong>人</strong>
                                  <button id="btn-view" class="btn btn-link btn-large" type="button" onclick="view()">(查看未看名单)</button></div>
                                 
                                    <table id="simple-table"
                                    class="table table-striped table-bordered table-hover"
                                    style="margin-top: 5px;">
                                    <thead>
                                        <tr>
                                            <th class="center" style="width: 50px;">序号</th>
                                            <th class="center">用户名</th>
                                             <th class="center">首次查看时间</th>
                                            <th class="center">上次查看时间</th>
                                        </tr>
                                    </thead>
                                    
                                   <tbody id="windows1">     
                                   <!-- 开始循环 -->
                                        <c:choose>
                                            <c:when test="${not empty varList}">
                                                    <c:forEach items="${varList}" var="var" varStatus="vs">
                                                        <tr>
                                                            <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                            <td class='center'>${var.USER_NAME}</td>
                                                            <td class='center'>${var.FIRST_VIEW_TIME}</td>
                                                            <td class='center'>${var.VIEW_TIME}</td>
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
                                      
                                    <tbody id="windows2" style="display: none;">
                                      <c:choose>
                                            <c:when test="${not empty adminList}">
                                                    <c:forEach items="${adminList}" var="list" varStatus="vs">
                                                        <tr >
                                                            <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                            <td class='center'>${list.NAME}</td>
                                                            <td class='center'>未查看</td>
                                                            <td class='center'>未查看</td>
                                                        </tr>
                                                       
                                                    </c:forEach>
                                            </c:when>
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
    <!-- 下拉框 -->
    <script src="static/ace/js/chosen.jquery.js"></script>
    <!--提示框-->
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
    <!-- 弹出窗口 -->
    <script src="static/ace/js/bootbox.js"></script>
</body>
<script type="text/javascript">
    $(top.hangge());
    
    $(document).ready(function() {
    	$("#windows2").hide();
        $("#windows1").show();
    });
   
    var id=1;
    function view(){
    	if(id==1){
    		$("#windows1").hide();
            $("#windows2").show();
            $("#btn-view").html("(查看已看名单)");
            id=2;
    	}else{
    		$("#windows2").hide();
            $("#windows1").show();
            $("#btn-view").html("(查看未看名单)");
            id=1;
    	}
    	
      
    }

    
</script>
</html>