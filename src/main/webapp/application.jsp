<!DOCTYPE html>
<html>
<head>
<!-- Bootstrap Core CSS -->
<link href="bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Timeline CSS -->
<link href="dist/css/timeline.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="bower_components/morrisjs/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="bower_components/fontawesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
	

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


<script type="text/javascript"
	src="${pageContext.request.contextPath}/org/cometd.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/org/cometd/AckExtension.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/org/cometd/ReloadExtension.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery/jquery-1.8.2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery/jquery.cookie.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery/jquery.cometd.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery/jquery.cometd-reload.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/chat.window.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/comet.chat.js"></script>
<script type="text/javascript">
    
    var chatWindowArray = [];
    
    var config = {
        contextPath: '${pageContext.request.contextPath}'
    };
	
	function getChatWindowByUserPair(loginUserName, peerUserName) {
		
		var chatWindow;
		
		for(var i = 0; i < chatWindowArray.length; i++) {
			var windowInfo = chatWindowArray[i];
			if (windowInfo.loginUserName == loginUserName && windowInfo.peerUserName == peerUserName) {
				chatWindow =  windowInfo.windowObj;
			}
		}
		
		return chatWindow;
	}
	
	function createWindow(loginUserName, peerUserName) {
		
		var chatWindow = getChatWindowByUserPair(loginUserName, peerUserName);
		
		if (chatWindow == null) { //Not chat window created before for this user pair.
			chatWindow = new ChatWindow(); //Create new chat window.
			chatWindow.initWindow({
				loginUserName:loginUserName, 
				peerUserName:peerUserName,
				windowArray:chatWindowArray});
			
			//collect all chat windows opended so far.
			var chatWindowInfo = { peerUserName:peerUserName, 
					               loginUserName:loginUserName,
					               windowObj:chatWindow 
					             };
			
			chatWindowArray.push(chatWindowInfo);
    	}
		
		chatWindow.show();
		return chatWindow;
	}

	function join(userName){
		$.cometChat.join(userName);
	}
</script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/comet.chat.css" />
</head>
<body>
	<script type="text/javascript">
	var userName = '<%=request.getParameter("username")%>';
	$(document).ready(function(){ 
		$.cometChat.onLoad({memberListContainerID:'members'});
		join(userName);
		
// 		 $.cometd.configure({
// 		        url: location.protocol + '//' + location.host + config.contextPath + '/cometd',
// 		        logLevel: 'info'
// 		    });
		 $.cometd.addListener('/meta/handshake', function(message)
				    {
				        if (message.successful)
				        { 
		$.cometd.subscribe('/stock/*', function(message)
	            {
					
	                var data = message.data;
	                var symbol = data.symbol;
	                var value = data.newValue;
	                var aTag = $("#"+symbol.split("_")[0]).find("#_"+symbol.split("_")[1]);
	                aTag.css("color", "blue");
	                aTag.css("text-decoration","blink");
	               
					console.info("update success" + data.newValue)	;
	                // Find the div for the given stock symbol
	                var id = 'stock_' + symbol;
	                var symbolDiv = $(id)[0];
	                if (!symbolDiv)
	                {
	                    symbolDiv = ('<div id="' + id + '"></div>', $('#stocks'));
	                }
	                symbolDiv.innerHTML = '<span>' + symbol + ': ' + value + '</span>';
	            });
				        }
				    });
		// $.cometd.handshake();
	});
		 

</script>

 
    <div id="status"></div>

    <div id="stocks"></div>
    
	<div class="panel panel-green" style="width: 300px;">
		<div class="panel-heading"><i class="fa fa-group fa-fw"></i>Online users</div>
		<div class="panel-body">
			<div class="chat" id="members">
				<ul class="chat" id="online">

				</ul>
			</div>
		</div>
<!-- 		<div class="panel-footer">Panel Footer</div> -->
	</div>

	
	<div class="panel panel-default" style="width:300px;" id="NOTIFICATION">
                        <div class="panel-heading">
                            <i class="fa fa-bell fa-fw"></i> Notifications Panel
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="list-group">
                                <a href="#" class="list-group-item" id="_COMMENT">
                                    <i class="fa fa-comment fa-fw"></i>
                                    New Comment
                                    <span class="pull-right text-muted small"><em>4 minutes ago</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item" id="_SOCIAL">
                                    <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                                    <span class="pull-right text-muted small"><em>12 minutes ago</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-envelope fa-fw" id="_MESSAGE"></i> Message Sent
                                    <span class="pull-right text-muted small"><em>27 minutes ago</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item" id="_TASK">
                                    <i class="fa fa-tasks fa-fw"></i> New Task
                                    <span class="pull-right text-muted small"><em>43 minutes ago</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item" id="_INFO">
                                    <i class="fa fa-upload fa-fw blue"></i> Server Rebooted
                                    <span class="pull-right text-muted small"><em>11:32 AM</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-bolt fa-fw"></i> Server Crashed!
                                    <span class="pull-right text-muted small"><em>11:13 AM</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-warning fa-fw"></i> Server Not Responding
                                    <span class="pull-right text-muted small"><em>10:57 AM</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-shopping-cart fa-fw"></i> New Order Placed
                                    <span class="pull-right text-muted small"><em>9:49 AM</em>
                                    </span>
                                </a>
                                <a href="#" class="list-group-item">
                                    <i class="fa fa-money fa-fw"></i> Payment Received
                                    <span class="pull-right text-muted small"><em>Yesterday</em>
                                    </span>
                                </a>
                            </div>
                            <!-- /.list-group -->
                            <a href="#" class="btn btn-default btn-block">View All Alerts</a>
                        </div>
                        <!-- /.panel-body -->
                    </div>
	
	<div id="chatemplate" style="display: none;">
		<div class="chat-panel panel panel-default" id="chatbox">
			<div class="panel-heading" id="chatheader">
				<i class="fa fa-comments fa-fw"></i> Chat with: <span></span>
				<div class="btn-group pull-right">
					<button id="chatclose" type="button"
						class="btn btn-default btn-xs dropdown-toggle"
						data-toggle="dropdown">
						<i class="fa fa-times"></i>
					</button>
					 
				</div>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body" id="chatcontent">
				<ul class="chat" id="chatmessage">
					 

				</ul>
			</div>
			<!-- /.panel-body -->
			<div class="panel-footer" id="chatfooter">
				<div class="input-group">
					<input id="btn-input" type="text" class="form-control input-sm"
						placeholder="Type your message here..." /> <span
						class="input-group-btn">
						<button class="btn btn-warning btn-sm" id="btn-chat">
							Send</button>
					</span>
				</div>
			</div>
			<!-- /.panel-footer -->
		</div>
	</div>
</body>
</html>
