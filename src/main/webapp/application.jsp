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
	});
</script>
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
