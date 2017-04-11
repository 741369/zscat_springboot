
var layer , iframe , layerwin , cursession ;
$(document).ready(function(){
	var hide ;
	$('.dropdown-menu').on("click" , function(){
		var distance = getDistance(this);
		if(hide = true){
			$(this).closest(".ukefu-btn-group").addClass("open");
		}else{
			$(this).closest(".ukefu-btn-group").removeClass("open");
		}
		if(distance.right < 200){
			$(this).next().css("right" , "0px").css("left" , "auto");
		}
	}).hover(function(){
		hide = true ;
	} , function(){
		hide = false ;
		var btn = $(this); 
		setTimeout(function(){
			if(hide){
				$(btn).removeClass("open");
			}
		} , 500);
	});
	$('.ukefu-btn-group').hover(function(){
		$(this).addClass("open");
		$(this).find('.ukefu-dropdown-menu').css("right" , "0px").css("left" , "auto");
		hide = false ;
	} , function(){
		hide = true ;
		setTimeout(function(){
			if(hide){
				$(".ukefu-btn-group.open").removeClass("open");
			}
		} , 500);
	});
	layui.use(['layer'], function(){
		layer = layui.layer;	 	 
	});
	$(document).on('click','[data-toggle="ajax"]', function ( e ) {
		var url = $(this).attr("href");
		if(url && url != "javascript:void(0)"){
			var title = $(this).attr("title") ? $(this).attr("title") : $(this).attr("data-title");
			var artwidth = $(this).attr("data-width") ? $(this).attr("data-width") : 800 ;
			var artheight = $(this).attr("data-height") ? $(this).attr("data-height") : 400 ;
			top.iframe = window.frameElement && window.frameElement.id || '';
			$.ajax({
				url:url,
				cache:false,
				success: function(data){
					top.layerwin = top.layer.open({title:title, type: 1, id: 'mainajaxwin', area:[artwidth+"px" , artheight+"px"] ,content: data});
				}
			});
		}
		
		return false;
	});
	
	$(document).on('click','[data-toggle="load"]', function ( e ) {
		var url = $(this).attr("href");
		var target = $(this).data("target");
		$.ajax({
			url:url,
			cache:false,
			success: function(data){
				$(target).empty().html(data);
			}
		});
		
		return false;
	});
	
	$(document).on('click','[data-toggle="tip"]', function ( e ) {
		var title = $(this).attr("title") ? $(this).attr("title") : $(this).attr("data-title");
		var href = 	$(this).attr('href')  ;
		if(href == null){
			href = $(this).data('href') ;
		}
		var callback = $(this).data('callback') ;
		top.layer.confirm(title, {icon: 3, title:'提示'}, function(index){
			top.layer.close(index);
			if(callback!=null){
				eval(callback+"('"+href+"')");
			}else{
				location.href = href ;
			}
		});
		return false;
	});
	$(document).on('submit.form.data-api','form', function ( e ) {
		var formValue = $(e.target) ;
		if(iframe){
			$(e.target).attr('target' , iframe);
		}
		if(layerwin){
			layer.close(layerwin);
		}
	});
	function getDistance(obj) {  
		 var distance = {};  
		 distance.top = ($(obj).offset().top - $(document).scrollTop());  
		 distance.bottom = ($(window).height() - distance.top - $(obj).outerHeight());  
		 distance.left = ($(obj).offset().left - $(document).scrollLeft());  
		 distance.right = ($(window).width() - distance.left - $(obj).outerWidth());  
		 return distance;  
	}
});

function loadURL(url , panel , callback  , append){
	loadURLWithTip(url  , panel , callback , append , false) ;
}

function loadURLWithTip(url , panel , callback , append  , tip){
	$.ajax({
		url:url,
		cache:false,
		success: function(data){
			if(panel){
				if(append){
					$(panel).append(data);
				}else{
					$(panel).empty().html(data);
				}
			}
			if(callback){
				callback(data);			
			}
		},
		error:  function(xhr, type, s){	
			if(xhr.getResponseHeader("emsg")){
				art.alert(xhr.getResponseHeader("emsg"));
			}
		}
	}).done(function(){
		
	});
}
var Proxy = {
	newAgentUserService:function(data){
		if($('#tip_message_'+data.userid).length >0){
			$('#tip_message_'+data.userid).removeClass('bg-gray').addClass("bg-green").text('在线');
		}else{
			if($('.chat-list-item.active').length > 0){
				var id = $('.chat-list-item.active').data('id') ;
				loadURL('/agent/agentusers.html?userid='+id , '#agentusers');
			}else{
				location.href = "/agent/index.html" ;
			}
		}
		if(data.id == cursession){
			$('#agentuser-curstatus').remove();
			$("#chat_msg_list").append(template($('#begin_tpl').html(), {data: data}));
		}
	},
	newAgentUserMessage:function(data){
		if(data.session == cursession){
			if(data.type == 'writing' && $('#writing').length > 0){
				$('#writing').remove();		
			}
			var id = $('.chat-list-item.active').data('id') ;
			if(data.message!=""){
				$("#chat_msg_list").append(template($('#message_tpl').html(), {data: data}));					
				document.getElementById('chat_msg_list').scrollTop = document.getElementById('chat_msg_list').scrollHeight  ;
			}
			loadURL("/agent/readmsg.html?userid="+data.agentuser);	//更新数据状态，将当前对话的新消息数量清空
		}else{
			if(data.type == 'message'){
				$('#last_msg_'+data.userid).text(data.tokenum).show();
			}
		}
	},
	endAgentUserService:function(data){
		if($('#tip_message_'+data.userid).length >0){
			$('#tip_message_'+data.userid).removeClass("bg-green").addClass('bg-gray').text('离开');
		}
		if(data.id == cursession){
			$('#agentuser-curstatus').remove();
			$("#chat_msg_list").append(template($('#end_tpl').html(), {data: data}));
		}
	},
	tipMsgForm : function(href){
		top.layer.prompt({formType: 2,title: '请输入拉黑原因',area: ['300px', '50px']} , function(value, index, elem){
			location.href = href+"&description="+encodeURIComponent(value);
			top.layer.close(index);
		});
	}
}