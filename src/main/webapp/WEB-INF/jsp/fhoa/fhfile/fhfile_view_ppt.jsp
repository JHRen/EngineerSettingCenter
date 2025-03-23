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
<script type="text/javascript" src="plugins/gdocsviewer/jquery.gdocsviewer.js"></script>
<script type="text/javascript" src="plugins/jquery.gdocsviewer.min.js"></script>


<link href="plugins/officetohtml/layout/styles/layout.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" href="plugins/officetohtml/include/jquery_ui/themes/start/jquery-ui.min.css">
  <script src="plugins/officetohtml/include/jquery/jquery-1.12.4.min.js"></script>
  <script src="plugins/officetohtml/include/jquery_ui/jquery-ui.min.js"></script>
   <!--PDF-->
  <link rel="stylesheet" href="plugins/officetohtml/include/pdf/pdf.viewer.css">
  <script src="plugins/officetohtml/include/pdf/pdf.js"></script>
  <!--Docs-->
  <script src="plugins/officetohtml/include/docx/jszip-utils.js"></script>
  <script src="plugins/officetohtml/include/docx/mammoth.browser.min.js"></script>
  <!--All Spreadsheet -->
  <link rel="stylesheet" href="plugins/officetohtml/include/SheetJS/handsontable.full.min.css">
  <script type="text/javascript" src="plugins/officetohtml/include/SheetJS/handsontable.full.min.js"></script>
  <script type="text/javascript" src="plugins/officetohtml/include/SheetJS/xlsx.full.min.js"></script>
  <!--Image viewer-->
  <link rel="stylesheet" href="plugins/officetohtml/include/verySimpleImageViewer/css/jquery.verySimpleImageViewer.css">
  <script type="text/javascript" src="plugins/officetohtml/include/verySimpleImageViewer/js/jquery.verySimpleImageViewer.js"></script>
<!--PPTX-->
<link rel="stylesheet" href="plugins/officetohtml/include/PPTXjs/css/pptxjs.css">
<link rel="stylesheet" href="plugins/officetohtml/include/PPTXjs/css/nv.d3.min.css">
<script type="text/javascript" src="plugins/officetohtml/include/PPTXjs/js/filereader.js"></script>
<script type="text/javascript" src="plugins/officetohtml/include/PPTXjs/js/d3.min.js"></script>
<script type="text/javascript" src="plugins/officetohtml/include/PPTXjs/js/nv.d3.min.js"></script>
<script type="text/javascript" src="plugins/officetohtml/include/PPTXjs/js/pptxjs.js"></script>
<script type="text/javascript" src="plugins/officetohtml/include/PPTXjs/js/divs2slides.js"></script>
	
<!--officeToHtml-->
<script src="plugins/officetohtml/include/officeToHtml/officeToHtml.js"></script>
<link rel="stylesheet" href="plugins/officetohtml/include/officeToHtml/officeToHtml.css">
<style type="text/css">
.pptShow{
 height:100%;
 width:100%;
}

#content{
    clear:both;
    position:relative;
}
</style>

</head>

<body tabindex="1" class="loadingInProgress">
<div class=pptShow>
	<a href="<%=basePath%>uploadFiles/uploadFile/${pd.FILEPATH}" class="embed">Download file</a>
<a href="<%=basePath%>uploadFiles/uploadFile/${pd.FILEPATH}" id="embedURL">Download file</a>
</div>	
<script type="text/javascript">
$('a.embed').gdocsViewer();
$('#embedURL').gdocsViewer();         
$('a.embed').gdocsViewer({ width: 400, height: 500 });
	</script>

</body>

</html>