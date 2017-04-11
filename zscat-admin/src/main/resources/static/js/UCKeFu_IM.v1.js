
var socket ;
$(document).ready(function(){
    socket = io.connect('http://'+hostname+':'+port+'/im/agent?orgi='+orgi+"&userid="+userid );
    socket.on('connect',function() {
		console.log("连接初始化成功");
		//请求服务端记录 当前用户在线事件
    }).on('disconnect',function() {
		console.log("连接已断开");       
		//请求服务端记录，当前用户离线
    });
	
    socket.on('chatevent', function(data) {
		console.log(data.messageType + " .....  message:"+data.message);          
    }).on('task', function(data) {
		
    }).on('new', function(data) {
    	if($('#multiMediaDialogWin').length > 0 && multiMediaDialogWin.$ &&multiMediaDialogWin.$('#agentusers').length > 0){
    		multiMediaDialogWin.Proxy.newAgentUserService(data);
    	}else{
    		//来电弹屏
    		$('#agentdesktop').attr('data-href' , '/agent/index.html?userid='+data.userid).click();
    	}
    }).on('status', function(data) {
    	$('#agents_status').html("服务中的人数："+data.users+"人，当前排队人数："+data.inquene+"人，在线坐席数："+data.agents+"人");	        
    }).on('message', function(data) {
    	if($('#multiMediaDialogWin').length > 0 && multiMediaDialogWin != null && multiMediaDialogWin.$ && multiMediaDialogWin.$('#agentusers').length > 0){
    		multiMediaDialogWin.Proxy.newAgentUserMessage(data);
    	}else{
    		//来电弹屏
    		$('#agentdesktop').attr('data-href' , '/agent/index.html?userid='+data.userid).click();
    	}
    }).on('workorder', function(data) {
        
    }).on('end', function(data) {
    	if($('#multiMediaDialogWin').length > 0){
    		if(multiMediaDialogWin.document.getElementById('agentusers') != null){
    			multiMediaDialogWin.Proxy.endAgentUserService(data);
    		}
    	}else{
    		//来电弹屏
    		$('#agentdesktop').attr('data-href', '/agent/index.html?userid='+data.userid).click();
    	}
    });	
	/****每分钟执行一次，与服务器交互，保持会话****/
	setInterval(function(){
		WebIM.ping();	
	} , 60000);				
}) ;

var WebIM = {
	sendMessage(message , userid , appid , session , orgi , touser , agentstatus){
		socket.emit('message', {
			appid : appid ,
			userid:userid,
			sign:session,
			touser:touser,
			session: session ,
			orgi:orgi,
			username:agentstatus,
			nickname:agentstatus,
			message : message
        });
	},
	ping : function(){
		loadURL("/message/ping.html") ;	
		console.log("ping:" + new Date().getTime());
	}
}


