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
<!-- datetime日期框 -->
<link rel='stylesheet'
	href="static/ace/css/bootstrap-datetimepicker.min.css">
<!-- 自定义页面css -->
<link rel="stylesheet" href="static/ace/css/filetransfer.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../../system/index/top.jsp"%>
</head>
<body class="no-skin">
	<div class="main-container">
	<div id="zhongxin" style="padding-top: 13px;">
		<!-- ----------------------------- General Info Module ----------------------------------->
      <%@ include file="../../filetransfer/generalInfo_list.jsp"%>
		<!-- -----------------------------FT-Memory Module ----------------------------------->
		<fieldset id="ft-memory">
			<legend>FT-Memory</legend>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="radio">
							<label> <input type="radio" name="ft-memory" value="Program new release" checked onchange="toggleProgramFields(this)">  Program new release
							</label>
						</div>
						<div class="radio">
							<label> <input type="radio" name="ft-memory" value="Program update" onchange="toggleProgramFields(this)">
								Program update
							</label>
						</div>
					</div>
				</div>
			</div>
			
             <div class="row"> 
				<div class="col-md-4">
                    <div class="form-group">
                        <label>Tester Type</label>
                         <input type="text" name="tester-type" class="form-control" placeholder="Tester Type" >
                    </div>
                </div>
                 
				<div class="col-md-4">
					<div class="form-group">
						<label>Program Name From</label> 
						<input type="text" name="programNameFrom" class="form-control" placeholder="Program Name From" id="ft-memory-program-from" readonly>
					</div>
				</div>
				
				<div class="col-md-4">
                    <div class="form-group">
                        <label>Program Name To</label>
                         <input type="text" name="programNameTo"  class="form-control" placeholder="Program Name To" id="ft-memory-program-to" >
                    </div>
                </div>
			</div>
			
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label>Program source path</label> <input type="text"
							class="form-control" placeholder="Program source path" required>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Program destination path</label> <input type="text"
							class="form-control" placeholder="Program destination path"
							required>
					</div>
				</div>
				
				<div class="col-md-4">
                    <div class="form-group">
                        <label>Old Version Program Path</label> <input type="text"
                            class="form-control" placeholder="Old Version Program Path"
                            id="ft-memory-old-path" readonly>
                    </div>
                </div>
			</div>
			<div class="row">	
				<div class="col-md-4">
					<div class="form-group">
						<label>Setup file Source path</label> <input type="text"
							class="form-control" placeholder="Setup file Source path"
							required>
					</div>
				</div>
			
				<div class="col-md-4">
					<div class="form-group">
						<label>Setup file destination path</label> <input type="text"
							class="form-control" placeholder="Setup file destination path"
							required>
					</div>
				</div>
				
                <div class="col-md-4">
                    <div class="form-group">
                        <label>Auto Delete</label>
                         <select class="form-control"  >
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
            <button type="submit">Submit</button>
        </div>
		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
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
	<script type="text/javascript"
		src="<%=basePath%>static/ace/js/date-time/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>static/ace/js/date-time/bootstrap-datetimepicker.zh-CN.js"></script>

	<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="static/ace/js/bootbox.js"></script>

	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		
		
		  function toggleProgramFields(radio) {
		        if (radio.value === "Program new release") {
		            // 禁用输入框
		            document.getElementById("ft-memory-program-from").readOnly = true;
		            document.getElementById("ft-memory-old-path").readOnly = true;
		        } else {
		            // 恢复可输入状态
		            document.getElementById("ft-memory-program-from").readOnly = false;
		            document.getElementById("ft-memory-old-path").readOnly = false;
		        }
		    }

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
		}

		function toggleMD5Fields(selectElement) {
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
		}
	</script>
</body>
</html>