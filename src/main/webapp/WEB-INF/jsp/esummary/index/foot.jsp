		<%
			String pathf = request.getContextPath();
			String basePathf = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ pathf + "/";
		%>
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePathf%>static/ace/js/jquery.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='<%=basePathf%>static/ace/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='<%=basePathf%>static/ace/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<script src="static/ace/js/bootstrap.js"></script>
		  <!-- page specific plugin scripts -->

        <!-- ace scripts -->
        <script src="static/ace/js/ace/elements.scroller.js"></script>
        <script src="static/ace/js/ace/elements.colorpicker.js"></script>
        <script src="static/ace/js/ace/elements.fileinput.js"></script>
        <script src="static/ace/js/ace/elements.typeahead.js"></script>
        <script src="static/ace/js/ace/elements.wysiwyg.js"></script>
        <script src="static/ace/js/ace/elements.spinner.js"></script>
        <script src="static/ace/js/ace/elements.treeview.js"></script>
        <script src="static/ace/js/ace/elements.wizard.js"></script>
        <script src="static/ace/js/ace/elements.aside.js"></script>
        <script src="static/ace/js/ace/ace.js"></script>
        <script src="static/ace/js/ace/ace.ajax-content.js"></script>
       <!--  <script src="static/ace/js/ace/ace.touch-drag.js"></script> -->
        <script src="static/ace/js/ace/ace.sidebar.js"></script>
        <script src="static/ace/js/ace/ace.sidebar-scroll-1.js"></script>
        <script src="static/ace/js/ace/ace.submenu-hover.js"></script>
        <script src="static/ace/js/ace/ace.widget-box.js"></script>
        <script src="static/ace/js/ace/ace.settings.js"></script>
        <script src="static/ace/js/ace/ace.settings-rtl.js"></script>
        <script src="static/ace/js/ace/ace.settings-skin.js"></script>
        <script src="static/ace/js/ace/ace.widget-on-reload.js"></script>
        <script src="static/ace/js/ace/ace.searchbox-autocomplete.js"></script>
        <!-- inline scripts related to this page -->
     
   <!--引入弹窗组件2start-->
   <script type="text/javascript" src="static/js/attention/drag/drag.js"></script>
   <script type="text/javascript" src="static/js/attention/drag/dialog.js"></script>
   <link type="text/css" rel="stylesheet" href="plugins/attention/drag/style.css"  />
        <!-- the following scripts are used in demo only for onpage help and you don't need them -->
        <link rel="stylesheet" href="static/ace/css/ace.onpage-help.css" />

        <script type="text/javascript"> ace.vars['base'] = '..'; </script>
        <script src="static/ace/js/ace/elements.onpage-help.js"></script>
        <script src="static/ace/js/ace/ace.onpage-help.js"></script>
    
        <!--引入属于此页面的js -->
        <!--  <script type="text/javascript" src="static/js/myjs/head.js"></script>  -->
        <!--引入属于此页面的js -->
     <!--    <script type="text/javascript" src="static/js/myjs/index.js"></script> -->
        
        
        <!--提示框-->
        <script type="text/javascript" src="static/js/jquery.tips.js"></script>