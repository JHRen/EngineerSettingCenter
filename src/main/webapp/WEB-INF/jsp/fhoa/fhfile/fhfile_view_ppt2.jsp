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
	<div id="resolte-contaniner" class="content"></div>
</div>	
<script type="text/javascript">
	 var file_path = '<%=basePath%>uploadFiles/uploadFile/${pd.FILEPATH}';
    $("#resolte-contaniner").officeToHtml({
        url: file_path,
        pptxSetting: {
	        slidesScale: "100%", //按百分比更改幻灯片比例
	        slideMode: true, //打开演示模式
	        keyBoardShortCut: true,  //如果为true，则可以通过键盘快捷键F5进入或退出演示模式
	        mediaProcess: true, //处理视频和音频文件
	        jsZipV2: false,
	        slideModeConfig: { //演示模式的设置
	            first: 1,  //将要加载的第一张幻灯
	            nav: true, //导航按钮
	            navTxtColor: "white", //导航器区域中显示的幻灯片编号文本的颜色和幻灯片总数,默认黑色
	            keyBoardShortCut: true, //启用通过键盘快捷键控制演示
	            showSlideNum: true, //在导航器区域中显示幻灯片编号
	            showTotalSlideNum: true, //在导航器区域中显示幻灯片总数
	            autoSlide:false, //“ false”或数字（秒）。如果设置为“ false”，它将禁用自动滑动模式。如果设置了编号，则将启用自动幻灯片模式，该编号将是两次幻灯片之间的时间。
	            randomAutoSlide: false, //幻灯片将以随机顺序显示
	            loop: false,  //最后一张幻灯片后将转到第一张幻灯片
	            background: false, //演示文稿背景的颜色。
	            transition: "default", //过渡类型选项: "slid","fade","default","random" , to show transition efects :transitionTime > 0.5 */
	            transitionTime: 1 //幻灯片之间的时间延迟         
	        }
         }
      });
                        
          	
	</script>

</body>

</html>