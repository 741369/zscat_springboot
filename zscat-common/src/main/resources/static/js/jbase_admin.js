
//是否存在指定函数 
function isExitsFunction(funcName) {
    try {
        if (typeof(eval(funcName)) == "function") {
            return true;
        }
    } catch(e) {}
    return false;
}
//是否存在指定变量 
function isExitsVariable(variableName) {
    try {
        if (typeof(variableName) == "undefined") {
            return false;
        } else {
            return true;
        }
    } catch(e) {}
    return false;
}

/*textarea 字数限制*/
function textarealength(obj,maxlength){
	var v = $(obj).val();
	var l = v.length;
	if( l > maxlength){
		v = v.substring(0,maxlength);
		$(obj).val(v);
	}
	$(obj).parent().find(".textarea-length").text(v.length);
}

/*使用ajax显示操作界面*/
function AjaxRender(divid, url, data, func) {
	layer.load(1, {shade: [0.3,'#fff']});
    $("#"+divid).load(url, data,
    function(response, status, xhr) {
    	layer.closeAll('loading');
        if ("error" == status) {
        	if(404 == xhr.status){
        		HtmlRender(BASE_PATH+"/admin/error404");
                return;
        	}else{
            	layer.msg('服务器产生异常，您的请求失败！', {icon: 5,offset: '45px'});
                return;
        	}
        } else {
            if (200 != xhr.status) $("#page-wrapper").html(response);
            else {
                //alert(response);
                if ("ajax_request_session_timeout" == response) {
                    alert('因长时间未操作，请重新登录！');
                    location.href = "/admin/login";
                    return;
                }
                if (func) func();
            }
        }
    });
    return false;
}

/*使用ajax显示操作界面*/
function HtmlRender( url, data, func) {
	return AjaxRender("main_content",url, data, func);
}
/*使用ajax显示操作界面*/
function SideRender( url, data, func) {
	return AjaxRender("div_side_menu",url, data, func);
}

/*上传图片*/
function uploadImage(packId,fileImageId,fileInfoId,imageId){
	var uploader = WebUploader.create({
		auto: true,
		swf: STATIC_PATH+'/lib/webuploader/0.1.5/Uploader.swf',
		server: BASE_PATH+'/attachment/uploadImage',
		pick: '#'+packId,
		resize: false,
		accept: {
			title: 'Images',
			extensions: 'gif,jpg,jpeg,png',
			mimeTypes: 'image/*'
		}
	});
	uploader.on( 'beforeFileQueued', function( file ) {
		uploader.reset();
	});
	uploader.on( 'fileQueued', function( file ) {
		uploader.makeThumb( file, function( error, src ) {
			if ( error ) {
				$("#"+fileImageId).replaceWith('<span>不能预览</span>');
				return;
			}
			$("#"+fileImageId).attr( 'src', src );
			$("#"+fileInfoId).html(file.name);
		});
	});
	uploader.on( 'uploadSuccess', function( file, response ) {
		if ( "SUCCESS" != response.state ) {
			$("#"+fileInfoId).html("<font color='red'>文件上传失败！</font><br>"+response.error_info);
			return false;
		}
		$("#"+imageId).val(response.url );
		uploader.reset();
	}); 
}
/*表格里的checkbox，全选功能*/
function table_checkbox_all(){
	/*全选*/
	$("table thead th input:checkbox").on("click" , function(){
		$(this).closest("table").find("tr > td:first-child input:checkbox").prop("checked",$("table thead th input:checkbox").prop("checked"));
    });
}