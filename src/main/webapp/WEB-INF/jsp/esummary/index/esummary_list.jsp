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

<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
    <style type="text/css">
    html, body {    width: 100%;}
/*Bootstrap-overlay*/

body {
 margin-top: -10px;  font-family: 'Open Sans', sans-serif; font-size:12px; color:#666;
}
a{color:#666;}
a:hover, a:focus {
 text-decoration: none; color:#28b779;
}
.dropdown-menu .divider{ margin:4px 0px;}
.dropdown-menu{ min-width:180px;}
.dropdown-menu > li > a{ padding:3px 10px; color:#666; font-size:12px;}
.dropdown-menu > li > a i{ padding-right:3px;}
/* select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input, .label, .dropdown-menu, .btn, .well, .progress, .table-bordered, .btn-group > .btn:first-child, .btn-group > .btn:last-child, .btn-group > .btn:last-child, .btn-group > .dropdown-toggle, .alert{ border-radius:0px;}
.btn, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{ box-shadow:none;}
.progress, .progress-success .bar, .progress .bar-success, .progress-warning .bar, .progress .bar-warning, .progress-danger .bar, .progress .bar-danger, .progress-info .bar, .progress .bar-info, .btn, .btn-primary{background-image:none;}
.accordion-heading h5{ width:70%; } 
hr{ border-top-color:#dadada;}
*/
.form-horizontal .form-actions{ padding-left:10px; }
#footer{ padding:10px; text-align:center;}

.carousel{ margin-bottom:0px;}
.fl { float:left}
.fr {float:right}
.label-important, .badge-important{ background:#f74d4d;}


body {padding: 0; margin-top:0px;margin: 0px;} 
.control-group{ padding:8px 0; margin-bottom:0px; height:50px;}
.main_select_box_testcode{float:left;}
.main_select_box_retestcode{float:left;vertical-align: middle;}
.main_select_box{display:inline-block;}
#ftlotno{display:inline-block; }
#testcode{display:inline-block; }
#retestcode{display:inline-block;}
#filename{display:inline-block;}
#simple-table{ padding: 0; margin-top:0px;margin: 0px; }
#tabs-exchange{table-responsive;}   
</style>

</head>
<body >
	<!-- 页面顶部¨ -->
	<%@ include file="head.jsp"%>
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="hr hr-18 dotted hr-double">
					</div>
					<div class="row" id="serachbox">
						<div class="col-xs-12">
							<div class="alert alert-block alert-success">
					  <form action="esummary/listAll" method="post" name="searchForm" id="searchForm"> 
						 <!--  信息框内容-->
                            <div class="control-group">
                                <div class="controls">
                                <div class="main_input_box">
                                    <span class="add-on bg_lg" "> <i> Enter the LotID:</i></span>
                                     <input type="text" style="width: 20%; height:34px;" name="custlotno" id="custlotno" value="${pd.custlotno }" /> 
                                    <input type="hidden" name="isExcel" id="isExcel" value="${pd.isExcel }"/>
                                    <span><a  onclick="search_lot()" class="btn btn-success">Serach</a></span>
                                     <td style="vertical-align:top;padding-right:20px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td>
                                    <td style="vertical-align:top;padding-right:20px;"></td> <td style="vertical-align:top;padding-right:20px;"></td>
                                     <td style="vertical-align:top;padding-right:20px;"><a class="btn btn-light btn-xs" onclick="exportAll();" title="批量导出"><i id="nav-search-icon" class="ace-icon fa fa-external-link bigger-110 nav-search-icon blue"></i></a></td>
                                 </div>
                                </div>
                            </div>
                            
                              
                              <div class="control-group-ft" id="control-group-ft" style="display:${ftCss}">
                                <div class="controls">
                                <div class="main_select_box">
                                    <span class="add-on bg_lg" "><i>FT&nbsp;&nbsp;LOTNO:</i></span>
                                     <select class="chosen-select form-control" name="ftlotno" id="ftlotno"  title="请选择ftlotno" style="width:170px;">
                                        <c:forEach items="${listFtlotno}" var="list">
                                             <option value="${list.FTLOTNO }" <c:if test="${pd.ftlotno==list.FTLOTNO}">selected</c:if>>${list.FTLOTNO }</option>
                                           </c:forEach>
                                    </select>
                                 </div>
                                </div>
                              </div>
                           
                            
                            <div class="control-group">
                               <div class="controls">
                                <div class="main_select_box_testcode">
                                    <span class="add-on bg_lg" "><i>TESTCODE:</i></span>
                                       <select  class="chosen-select form-control"  name="testcode" id="testcode"  title="请选择testcode" style="width:170px;">
										   <c:forEach items="${listTestcode}" var="list">
                                             <option value="${list.TESTCODE }" <c:if test="${pd.testcode==list.TESTCODE}">selected</c:if>>${list.TESTCODE }</option>
                                           </c:forEach>
                                       </select>
                                  </div>
                                   
                                    <div class="main_select_box_retestcode">       
                                        <span class="add-on bg_lg" "><i>&nbsp;RETESTCODE:</i></span>
                                        <select class="chosen-select form-control" name="retestcode" id="retestcode" data-placeholder="choose retestcode" style="width: 170px; ">
                                          <c:forEach items="${listReTestcode}" var="list">
                                                 <option value="${list.RETESTCODE }"<c:if test="${pd.retestcode==list.RETESTCODE}">selected</c:if>>${list.RETESTCODE }</option>
                                            </c:forEach> </select>
                                     </div>
                                 
                                </div>
                            </div>
                            
                           <div class="control-group">
                                <div class="controls">
                                 <div class="main_select_box">       
                                      <span class="add-on bg_lg" "><i>FILENAME:</i></span>
                                      <select class="chosen-select form-control" name="filename" id="filename" data-placeholder="choose filename" style="width: 1150px; ">
                                         <c:forEach items="${listFilename}" var="list">
                                              <option value="${list.FILENAME }"<c:if test="${pd.filename==list.FILENAME}">selected</c:if>>${list.FILENAME }</option>
                                          </c:forEach> </select>
                                      </select>
                                 </div>
                                </div>
                            </div>
                            
						 </form> 
							
							</div>

						</div>
					</div>
					<!-- /.row -->


			
					<!--列表 -->

					<div class="tabbable " id="tabs-exchange"  >
						<ul class="nav nav-tabs" >
							<li class="active"><a href="#panel-hbin" data-toggle="tab">HBIN</a>
							</li>
							<li><a href="#panel-sbin" data-toggle="tab">SBIN</a></li>
						</ul>
						<div class="tab-content"  id="zhongxin">
							<div class="tab-pane active" id="panel-hbin">
								<table id="simple-table" class="table table-striped table-bordered table-hover table-responsive " >
									<thead>
										<tr>
											<th class="center"><span data-toggle="tooltip">HBIN</span></th>
											<th class="center"><span data-toggle="tooltip">HB_TOTAL</span></th>
											<th class="center"><span data-toggle="tooltip">YIELD</span></th>
											<th class="center"><span data-toggle="tooltip">SITE0</span></th>
											<th class="center"><span data-toggle="tooltip">S0_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE1</span></th>
											<th class="center"><span data-toggle="tooltip">S1_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE2</span></th>
											<th class="center"><span data-toggle="tooltip">S2_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE3</span></th>
											<th class="center"><span data-toggle="tooltip">S3_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE4</span></th>
											<th class="center"><span data-toggle="tooltip">S4_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE5</span></th>
											<th class="center"><span data-toggle="tooltip">S5_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE6</span></th>
											<th class="center"><span data-toggle="tooltip">S6_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE7</span></th>
											<th class="center"><span data-toggle="tooltip">S7_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE8</span></th>
											<th class="center"><span data-toggle="tooltip">S8_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE9</span></th>
											<th class="center"><span data-toggle="tooltip">S9_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE10</span></th>
											<th class="center"><span data-toggle="tooltip">S10_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE11</span></th>
											<th class="center"><span data-toggle="tooltip">S11_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE12</span></th>
											<th class="center"><span data-toggle="tooltip">S12_Y</span></th>
										</tr>
									</thead>
									
									<tbody >
									   <!-- 开始循环 -->   
                                     <c:choose>
                                       <c:when test="${not empty listHbin}">
                                        <c:forEach items="${listHbin}" var="lsh" varStatus="vs">
                                         <tr>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.HBIN}">${lsh.HBIN}</c:if><c:if test="${empty lsh.HBIN }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.HB_TOTAL}">${lsh.HB_TOTAL}</c:if><c:if test="${empty lsh.HB_TOTAL }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.YIELD}">${lsh.YIELD}</c:if><c:if test="${empty lsh.YIELD }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE0}">${lsh.SITE0}</c:if><c:if test="${empty lsh.SITE0 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S0_Y}">${lsh.S0_Y}</c:if><c:if test="${empty lsh.S0_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE1}">${lsh.SITE1}</c:if><c:if test="${empty lsh.SITE1 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S1_Y}">${lsh.S1_Y}</c:if><c:if test="${empty lsh.S1_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE2}">${lsh.SITE2}</c:if><c:if test="${empty lsh.SITE2 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S2_Y}">${lsh.S2_Y}</c:if><c:if test="${empty lsh.S2_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE3}">${lsh.SITE3}</c:if><c:if test="${empty lsh.SITE3 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S3_Y}">${lsh.S3_Y}</c:if><c:if test="${empty lsh.S3_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE4}">${lsh.SITE4}</c:if><c:if test="${empty lsh.SITE4 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S4_Y}">${lsh.S4_Y}</c:if><c:if test="${empty lsh.S4_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE5}">${lsh.SITE5}</c:if><c:if test="${empty lsh.SITE5 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S5_Y}">${lsh.S5_Y}</c:if><c:if test="${empty lsh.S5_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE6}">${lsh.SITE6}</c:if><c:if test="${empty lsh.SITE6 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S6_Y}">${lsh.S6_Y}</c:if><c:if test="${empty lsh.S6_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE7}">${lsh.SITE7}</c:if><c:if test="${empty lsh.SITE7 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S7_Y}">${lsh.S7_Y}</c:if><c:if test="${empty lsh.S7_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE8}">${lsh.SITE8}</c:if><c:if test="${empty lsh.SITE8 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S8_Y}">${lsh.S8_Y}</c:if><c:if test="${empty lsh.S8_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE9}">${lsh.SITE9}</c:if><c:if test="${empty lsh.SITE9 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S9_Y}">${lsh.S9_Y}</c:if><c:if test="${empty lsh.S9_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE10}">${lsh.SITE10}</c:if><c:if test="${empty lsh.SITE10 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S10_Y}">${lsh.S10_Y}</c:if><c:if test="${empty lsh.S10_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE11}">${lsh.SITE11}</c:if><c:if test="${empty lsh.SITE11 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S11_Y}">${lsh.S11_Y}</c:if><c:if test="${empty lsh.S11_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.SITE12}">${lsh.SITE12}</c:if><c:if test="${empty lsh.SITE12 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lsh.S12_Y}">${lsh.S12_Y}</c:if><c:if test="${empty lsh.S12_Y }">0</c:if></td>
                                         </tr>
                                        </c:forEach>   
                                        </c:when>
                                        <c:otherwise>
								          <tr class="main_info">
                                          <td colspan="30" class="center">没有相关数据</td>
                                         </tr>
                                        </c:otherwise>
                                      </c:choose>    
                                      
									</tbody>
								</table>
							</div>
							<div class="tab-pane" id="panel-sbin">
								<table id="simple-table" class="table table-striped table-bordered table-hover table-nowrap" >
									<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
									<thead>
										<tr>
											<th class="center"><span data-toggle="tooltip">SBIN</span></th>
											<th class="center"><span data-toggle="tooltip">SB_TOTAL</span></th>
											<th class="center"><span data-toggle="tooltip">YIELD</span></th>
											<th class="center"><span data-toggle="tooltip">SITE0</span></th>
											<th class="center"><span data-toggle="tooltip">S0_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE1</span></th>
											<th class="center"><span data-toggle="tooltip">S1_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE2</span></th>
											<th class="center"><span data-toggle="tooltip">S2_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE3</span></th>
											<th class="center"><span data-toggle="tooltip">S3_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE4</span></th>
											<th class="center"><span data-toggle="tooltip">S4_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE5</span></th>
											<th class="center"><span data-toggle="tooltip">S5_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE6</span></th>
											<th class="center"><span data-toggle="tooltip">S6_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE7</span></th>
											<th class="center"><span data-toggle="tooltip">S7_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE8</span></th>
											<th class="center"><span data-toggle="tooltip">S8_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE9</span></th>
											<th class="center"><span data-toggle="tooltip">S9_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE10</span></th>
											<th class="center"><span data-toggle="tooltip">S10_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE11</span></th>
											<th class="center"><span data-toggle="tooltip">S11_Y</span></th>
											<th class="center"><span data-toggle="tooltip">SITE12</span></th>
											<th class="center"><span data-toggle="tooltip">S12_Y</span></th>
										</tr>
									</thead>
									</nav>
									<tbody>
									   <!-- 开始循环 -->   
                                     <c:choose>
                                       <c:when test="${not empty listSbin}">
                                        <c:forEach items="${listSbin}" var="lss" varStatus="vs">
                                         <tr>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SBIN}">${lss.SBIN}</c:if><c:if test="${empty lss.SBIN }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SB_TOTAL}">${lss.SB_TOTAL}</c:if><c:if test="${empty lss.SB_TOTAL }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.YIELD}">${lss.YIELD}</c:if><c:if test="${empty lss.YIELD }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE0}">${lss.SITE0}</c:if><c:if test="${empty lss.SITE0 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S0_Y}">${lss.S0_Y}</c:if><c:if test="${empty lss.S0_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE1}">${lss.SITE1}</c:if><c:if test="${empty lss.SITE1 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S1_Y}">${lss.S1_Y}</c:if><c:if test="${empty lss.S1_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE2}">${lss.SITE2}</c:if><c:if test="${empty lss.SITE2 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S2_Y}">${lss.S2_Y}</c:if><c:if test="${empty lss.S2_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE3}">${lss.SITE3}</c:if><c:if test="${empty lss.SITE3 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S3_Y}">${lss.S3_Y}</c:if><c:if test="${empty lss.S3_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE4}">${lss.SITE4}</c:if><c:if test="${empty lss.SITE4 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S4_Y}">${lss.S4_Y}</c:if><c:if test="${empty lss.S4_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE5}">${lss.SITE5}</c:if><c:if test="${empty lss.SITE5 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S5_Y}">${lss.S5_Y}</c:if><c:if test="${empty lss.S5_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE6}">${lss.SITE6}</c:if><c:if test="${empty lss.SITE6 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S6_Y}">${lss.S6_Y}</c:if><c:if test="${empty lss.S6_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE7}">${lss.SITE7}</c:if><c:if test="${empty lss.SITE7 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S7_Y}">${lss.S7_Y}</c:if><c:if test="${empty lss.S7_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE8}">${lss.SITE8}</c:if><c:if test="${empty lss.SITE8 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S8_Y}">${lss.S8_Y}</c:if><c:if test="${empty lss.S8_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE9}">${lss.SITE9}</c:if><c:if test="${empty lss.SITE9 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S9_Y}">${lss.S9_Y}</c:if><c:if test="${empty lss.S9_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE10}">${lss.SITE10}</c:if><c:if test="${empty lss.SITE10 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S10_Y}">${lss.S10_Y}</c:if><c:if test="${empty lss.S10_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE11}">${lss.SITE11}</c:if><c:if test="${empty lss.SITE11 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S11_Y}">${lss.S11_Y}</c:if><c:if test="${empty lss.S11_Y }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.SITE12}">${lss.SITE12}</c:if><c:if test="${empty lss.SITE12 }">0</c:if></td>
                                           <td class='center' style="vertical-align: middle;"><c:if test="${not empty lss.S12_Y}">${lss.S12_Y}</c:if><c:if test="${empty lss.S12_Y }">0</c:if></td>
                                         </tr>
                                        </c:forEach>   
                                        </c:when>
                                        <c:otherwise>
                                          <tr class="main_info">
                                          <td colspan="30" class="center">没有相关数据</td>
                                         </tr>
                                        </c:otherwise>
                                      </c:choose>   
									</tbody>
								</table>
							</div>
						</div>
					</div>

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
	<%@ include file="../index/foot.jsp"%>
	<!-- inline scripts related to this page -->
	<script type="text/javascript" src="static/ace/js/jquery.js"></script>
	<!--提示框-->
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
    <!-- 弹出窗口 -->
    <script src="static/ace/js/bootbox.js"></script>
    
	<script type="text/javascript">
	    $(top.hangge());//关闭加载状态
		//检索
		function search_lot(){
			top.jzts();
			 $("#isExcel").val('0');
			document.getElementById("zhongxin").style.display="none";
		    var custlotno = $("#custlotno").val();
		     $("#testcode").empty();
		     $("#retestcode").empty();
             $("#filename").empty();
		    if(custlotno!=""){
		    	 $.ajax({
		                type: "POST",
		                url: 'esummary_lotno',
		                data: {CUSTLOTNO: custlotno},
		                dataType:'json',
		                cache: false,
		                success: function(data){
		                    if(data.result=="ft_sucess"){
		                         document.getElementById("control-group-ft").style.display="";
		                          $("#ftlotno").html('<option value="" >请选择ftlotno</option>');
		                          $.each(data.ftlotnoList, function(i, dvar){
		                                  $("#ftlotno").append("<option value="+dvar.ftlotno+">"+dvar.ftlotno+"</option>");
		                          });
		                    }else if(data.result=="ft_null"){
		                         $("#testcode").html('<option value="" >请选择testcode</option>');
		                         $.each(data.testcodeList, function(i, dvar){
		                             
		                                 $("#testcode").append("<option value="+dvar.testcode+">"+dvar.testcode+"</option>");
		                         });
		                    }
		          
		                }
		            }); 
		    	 
		    }else{
		         $("#custlotno").tips({
                     side : 1,
                     msg : "请输入custlotno",
                     bg : '#FF5080',
                     time : 15
                 });
                 $("#custlotno").focus();
		    	
		    	$("#custlotno").css("background-color","#D16E6C");
		    }
		 
		}
		
        //TESTCODE
        $("#testcode").change(function(){
        	  //FT LotNo下拉
              var custlotno = $("#custlotno").val();
              var ftlotno =  $("#ftlotno").val();
              var testcode =  $("#testcode").val();
              var keywords = 'RETESTCODE';
              
               $("#filename").empty();
             $.ajax({
                   type: "POST",
                   url: 'esummary/getLevel',
                   data: {CUSTLOTNO: custlotno,FTLOTNO:ftlotno,TESTCODE:testcode,KEYWORDS:keywords},
                   dataType:'json',
                   cache: false,
                   success: function(data){
                       $("#retestcode").html('<option value="" >请选择retestcode</option>');
                        $.each(data.listLevel, function(i, dvar){
                           $("#retestcode").append("<option value="+dvar.RETESTCODE+">"+dvar.RETESTCODE+"</option>");
                            
                        });
                   }
               });
            
        });
        
        //RETESTCODE
        $("#retestcode").change(function(){
        	  top.jzts();
        	  $("#searchForm").submit();
        	  $("#isExcel").val('1');
        	// $.post("esummary/listAll.do")
        });
      
		
		  //FTLOTNO
        $("#ftlotno").change(function(){
              //FT LotNo下拉
              var custlotno = $("#custlotno").val();
              var ftlotno =  $("#ftlotno").val();
              var keywords = 'TESTCODE';
             $("#retestcode").empty();
              $("#filename").empty();
              
             $.ajax({
                   type: "POST",
                   url: 'esummary/getLevel',
                   data: {CUSTLOTNO: custlotno,FTLOTNO:ftlotno,KEYWORDS:keywords},
                   dataType:'json',
                   cache: false,
                   success: function(data){
                       $("#testcode").html('<option value="" >请选择testcode</option>');
                        $.each(data.listLevel, function(i, dvar){
                           $("#testcode").append("<option value="+dvar.TESTCODE+">"+dvar.TESTCODE+"</option>");
                            
                        });
                   }
               });
            
        });
		  
      //导出当前excel
        function toExcel(){
        	  var LOTID = $("#custlotno").val();
        	  var testcode = $("#testcode").val();
        	  var retestcode = $("#retestcode").val();
        	  var ftlotno = $("#ftlotno").val();
        	  
        	  
        	 if(retestcode!=""&& $("#isExcel").val()=="1"){
        		 
               window.location.href='<%=basePath%>esummary/excel.do?LOTID='+LOTID+'&testcode='+testcode+'&retestcode='+retestcode+'&ftlotno='+ftlotno;
        	 }else{
        		 alert("需要先查询出数据才能导出excel");
        	 }
        	  
        }   
      
      //根据条件导出excel
        function exportAll(){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="批量导出";
            diag.URL = '<%=basePath%>esummary/goExport.do';
            diag.Width = 600;
            diag.Height = 330;
            diag.Modal = true;             //有无遮罩窗口
            diag. ShowMaxButton = true;    //最大化按钮
            diag.ShowMinButton = true;     //最小化按钮 
            diag.CancelEvent = function(){ //关闭事件
            	 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
            		 alert("excel导出完成!");
            	 } 
               diag.close();
            };
            diag.show();
        }
      
        //清除加载进度
        function hangge(){
            $("#jzts").hide();
        }

        //显示加载进度
        function jzts(){
            $("#jzts").show();
        }
	</script>
</body>
</html>