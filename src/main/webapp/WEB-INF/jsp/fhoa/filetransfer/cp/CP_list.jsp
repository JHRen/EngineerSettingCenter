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
<!-- 页面css -->
<link rel="stylesheet" href="static/ace/css/filetransfer.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../../jsp/system/index/top.jsp"%>
</head>
<body class="no-skin">
	<div class="main-container">
		<!-- General Info Module -->
		<fieldset id="general-info">
			<legend>General Info</legend>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label >Title</label> <input type="text" class="form-control"
							placeholder="Title" required>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Request ID</label> <input type="text" class="form-control"
							placeholder="Request ID" required>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Requestor Name</label> <input type="text"
							class="form-control" placeholder="Requestor Name" required>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label  class="required">Customer Code</label> <input type="text"
							class="form-control" placeholder="Customer Code" required>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label  class="required">Device</label> <input type="text" class="form-control"
							placeholder="Device" required>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label  class="required">Reason for Change</label>
						<!-- <textarea class="form-control" rows="3"
							placeholder="Reason for Change"></textarea> -->
							<input type="text" class="form-control"
                            placeholder="Reason for Change" required>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label>Attached Test Result</label> <input type="file"
							class="form-control">
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Attach Customer Approval</label> <input type="file"
							class="form-control">
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Customer</label> <select class="form-control" required>
							<option value="">Select</option>
							<option value="SPEC">SPEC</option>
							<option value="CPF">CPF</option>
							<option value="SPEC">SPEC</option>
							<option value="WI#">WI#</option>
							<option value="Test">Test</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label>Foundry (CP)</label> <select class="form-control" required>
							<option value="">Select</option>
							<option value="Foundry1">Foundry1</option>
							<option value="Foundry2">Foundry2</option>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Other info if need</label> <input type="text"
							class="form-control">
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Effect Release Date</label> <input type="date"
							class="form-control">
					</div>
				</div>
			</div>
		</fieldset>

		<hr class="hr-module">

		<!-- CP Module -->
		<fieldset id="cp">
			<legend>CP</legend>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="radio">
							<label> <input type="radio" name="cp"
								value="Program Initial Release" checked> Program Initial
								Release
							</label>
						</div>
						<div class="radio">
							<label> <input type="radio" name="cp"
								value="Program Upgrade" onchange="toggleProgramFields(this)">
								Program Upgrade
							</label>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Tester Type</label> <select class="form-control" required>
							<option value="">Select</option>
							<option value="Type1">Type 1</option>
							<option value="Type2">Type 2</option>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Program Name From</label> <input type="text"
							class="form-control" placeholder="Program Name From"
							id="cp-program-from" required>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label>Program Name To</label> <input type="text"
							class="form-control" placeholder="Program Name To" required>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Program Source path</label> <input type="text"
							class="form-control" placeholder="Program Source path" required>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Program destination path</label> <input type="text"
							class="form-control" placeholder="Program destination path"
							required>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label>Setup file name</label> <input type="text"
							class="form-control" placeholder="Setup file name" required>
					</div>
				</div>
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
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label>MD5 check</label> <select class="form-control" required>
							<option value="">Select</option>
							<option value="yes">Yes</option>
							<option value="no">No</option>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group md5-fields">
						<label>Customer name</label> <input type="text"
							class="form-control" placeholder="Customer name">
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group md5-fields">
						<label>Site</label> <select class="form-control">
							<option value="">Select</option>
							<option value="ATC">ATC</option>
							<option value="QTS">QTS</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label>Old Version Program Path</label> <input type="text"
							class="form-control" placeholder="Old Version Program Path"
							id="cp-old-path" required>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label>Delete enable or not</label> <select class="form-control"
							required>
							<option value="">Select</option>
							<option value="disable">Disable</option>
							<option value="enable">Enable</option>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group delete-options">
						<label>How to delete</label> <select class="form-control" required>
							<option value="">Select</option>
							<option value="immediately">Immediately</option>
							<option value="after_one_day">After one day</option>
							<option value="after_two_days">After two days</option>
							<option value="after_three_days">After three days</option>
							<option value="wait_engineer">Wait engineer dispose</option>
						</select>
					</div>
				</div>
			</div>
		</fieldset>
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
	<%@ include file="../../../jsp/system/index/foot.jsp"%>
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
			if (radio.value === "Program new release"
					|| radio.value === "Program Initial Release") {
				// Disable fields when "Program new release" is selected
				document.getElementById("ft-non-memory-program-from").readOnly = true;
				document.getElementById("ft-non-memory-old-path").readOnly = true;
				document.getElementById("ft-memory-program-from").readOnly = true;
				document.getElementById("ft-memory-old-path").readOnly = true;
				document.getElementById("cp-program-from").readOnly = true;
				document.getElementById("cp-old-path").readOnly = true;
			} else {
				// Enable fields when "Program update" is selected
				document.getElementById("ft-non-memory-program-from").readOnly = false;
				document.getElementById("ft-non-memory-old-path").readOnly = false;
				document.getElementById("ft-memory-program-from").readOnly = false;
				document.getElementById("ft-memory-old-path").readOnly = false;
				document.getElementById("cp-program-from").readOnly = false;
				document.getElementById("cp-old-path").readOnly = false;
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