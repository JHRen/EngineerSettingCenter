var locat = (window.location+'').split('/'); 
$(function(){
	if('tool'== locat[3])
	{locat =  locat[0]+'//'+locat[2];}
	else
	{locat =  locat[0]+'//'+locat[2]+'/'+locat[3];};
	});

$(top.hangge());

//判断路径是否合法
function checkPath(){
	var SOURCE_PATH=$.trim($("SOURCE_PATH").val());
	$.ajax({
		type: "post",
		url: '<%=basePath%>foldermanage/checkPath.do',
		data: {sourcePath:SOURCE_PATH},
		dataType: 'json',
		cache: false,
		success: function(data){
			 if("success" == data.result){
				$("valid-btn").val("通过");
				 
			 }else{
				 
				 $("error_info").show; 
			 }
			
		}
		
	});
	
	
}

//发送
function sendFolder(){
	
	if($("#SOURCE_PATH").val()==""){
		$("#SOURCE_PATH").tips({
			side:3,
            msg:'请输入源文件路径',
            bg:'#AE81FF',
            time:2
        });
		$("#SOURCE_PATH").focus();
		return false;
	}
	if($("#TARGET_PATH").val()==""){
		$("#TARGET_PATH").tips({
			side:3,
            msg:'请输入目标文件路径',
            bg:'#AE81FF',
            time:2
        });
		$("#TARGET_PATH").focus();
		return false;
	}

	
	$("#zhongxin").hide();
	$("#zhongxin2").show();
	
	var EMAIL = $("#EMAIL").val();
	var TYPE  = $("#TYPE").val();
	var TITLE = $("#TITLE").val();
	var CONTENT = $("#CONTENT").val();
	var isAll = $("#isAll").val();
	
	var fmsg = "${pd.msg}";
	
	$.ajax({
		type: "POST",
		url: locat+'/head/sendEmail.do?tm='+new Date().getTime(),
    	data: {EMAIL:EMAIL,TYPE:TYPE,TITLE:TITLE,CONTENT:CONTENT,isAll:isAll,fmsg:fmsg},
		dataType:'json',
		//beforeSend: validateData,
		cache: false,
		success: function(data){
			 $.each(data.list, function(i, list){
				 if(list.msg == 'ok'){
					 var count = list.count;
					 var ecount = list.ecount;
					 $("#msg").tips({
						side:3,
			            msg:'成功发出'+count+'条,失败'+ecount+'条,检查邮箱格式',
			            bg:'#68B500',
			            time:5
				      });
					 
				 }else{
					 $("#msg").tips({
							side:3,
				            msg:'邮件发送失败,请联系管理员检查邮件服务器配置是否正确!',
				            bg:'#FF0000',
				            time:5
					 });
					 
				 }
				 setTimeout("showdiv()",8000);
				 timer(7);
			 });
		}
	});
	
}

//倒计时
function timer(intDiff){
	window.setInterval(function(){
	$('#second_shows').html('<s></s>'+intDiff+'秒');
	intDiff--;
	}, 1000);
}

function showdiv(){
	$("#zhongxin2").hide();
	$("#zhongxin").show();
}

function setType(value){
	$("#TYPE").val(value);
}

function isAll(){
	if(document.getElementsByName('form-field-checkbox')[0].checked){
		$("#isAll").val('yes');
		$("#EMAIL").attr("disabled",true);
	}else{
		$("#isAll").val('no');
		$("#EMAIL").attr("disabled",false);
	}
}

//编辑邮箱(此方式弃用)
function editEmail(){
   var EMAIL = $("#EMAIL").val();
   var result = showModalDialog(locat+"/head/editEmail.do?EMAIL="+EMAIL,"","dialogWidth=600px;dialogHeight=380px;");
   if(result==null || ""==result){
	    $("#EMAIL").val("");
   }else{
		$("#EMAIL").val(result);
   }
}

//打开编辑邮箱
function dialog_open(){
	$("#EMAILs").val($("#EMAIL").val());
	$("#dialog-add").css("display","block");
}
//关闭编辑邮箱
function cancel_pl(){
	$("#dialog-add").css("display","none");
}
//保存编辑邮箱
function saveEmail(){
	$("#EMAIL").val($("#EMAILs").val());
	$("#dialog-add").css("display","none");
}




