/**
 * @author Semika Siriwardana(semika.siriwardana@gmail.com)
 */
function ChatWindow(config) {
	
    var _self = this;
    var _peerUserName;
    var _loginUserName;
    var _config;
    this._windowWidth  = 300;
    this._windowHeight = 400;
    this.lastUser      = null;
    this.windowArray   = [];
   
    this.getWindowLeftPosition = function() {
    	return this.windowArray.length*this._windowWidth;
    },
    
    this.getPeerUserName = function() {
    	return this._peerUserName;
    };
    
    this.getLoginUserName = function() {
    	return this._loginUserName;
    };
    
    this.getMessageContainerID = function() {
    	return this.getLoginUserName() + "_" + this.getPeerUserName();
    };
    
    this.getTextInputID = function() {
    	return this.getLoginUserName() + "_" + this.getPeerUserName() + "_chatInput";
    };
    
    this.getWindowID = function() {
    	return this.getLoginUserName() + "_" + this.getPeerUserName() + "_window";
    };
    
    this.hide = function(_self) {
    	$("#" + _self.getWindowID()).css("display", "none");
    	//$("#chatbox").css("display", "none");
    };
    
    this.show = function() {
    	$("#" + this.getWindowID()).css("display", "block");
    	//$("#chatbox").css("display", "block");
    };
    
    /**
     * Returns whether the chat window is currently visible or not
     */
    this.isVisible = function() {
    	return $("#" + this.getWindowID()).css("display") == "none"?false:true;
    };
    
    this.addOnClickListener = function(el, fnHandler, context) {
        $(el).bind("click", context, function(evt) {
            if(context != undefined) {
                fnHandler(context);
            } else {
                fnHandler();
            }
            return false;
        });
    };
    
    this.appendMessage = function(fromUser, text, loginUser) {
    	var userNameCssClass    = "";
    	var textMessageCssClass = "";
    	var userlogedin ="";
		
    	if (fromUser == loginUser) {
    		userlogedin = '<li class="left clearfix"><span class="chat-img pull-left">'; 
    		userlogedin +='<img src="http://placehold.it/30/FA6F57/fff" alt="User Avatar" class="img-circle" /></span>';
    		userlogedin +='<div class="chat-body clearfix"><div class="header"><strong class="primary-font">You</strong>';
    		userlogedin +='<smallclass="pull-right text-muted"><i class="fa fa-clock-o fa-fw"></i> 12 mins ago</small>';
    		userlogedin +='</div><p id="gotmessage">'+text+'</p></div></li>';
    	} else {
    		
    		userlogedin = '<li class="right clearfix">'; 
    		userlogedin +='<span class="chat-img pull-right"> <img src="http://placehold.it/30/55C1E7/fff" alt="User Avatar" class="img-circle" /> </span>';
    		userlogedin +='<div class="chat-body clearfix"> <div class="header"><small class=" text-muted"><i class="fa fa-clock-o fa-fw"></i> 13 mins ago</small><strong class="pull-right primary-font">'+fromUser+'</strong></div>';
      		userlogedin +='</div><p id="gotmessage">'+text+'</p></div></li>';    		
    		 
    	}
    	
    	if (this.lastUser == fromUser) {
    		fromUser = "...";
    	} else {
    		this.lastUser = fromUser;
    		fromUser += ':';
    	}
    	var chatContainer = $("#" + this.getMessageContainerID());
    	chatContainer.find("#chatmessage").append(userlogedin);  
    	chatContainer[0].scrollTop = chatContainer[0].scrollHeight - chatContainer.outerHeight();
    };
    
    this.focusTextInput = function() {
    	$("#" + this.getTextInputID()).focus();
    },
    
    this.getWindowBody = function() {
    	
    	var bodyDIV = document.createElement("div");
    	bodyDIV.setAttribute("id", this.getMessageContainerID()); 
    	bodyDIV.style.width     = this._windowWidth + "px";
    	bodyDIV.style.height    = "140px";
    	bodyDIV.style.position  = 'absolute';
    	bodyDIV.style.left      = 0;
    	bodyDIV.style.bottom    = "30px";
    	bodyDIV.style.overflowY = 'auto';
    	return bodyDIV;
    };
    
    this.getWindowFooter = function() {
    	
    	var footerDIV = document.createElement("div");
    	footerDIV.style.width  = this._windowWidth + "px";
    	footerDIV.style.height = "30px";
    	footerDIV.style.backgroundColor = '#31B404'; 
    	footerDIV.style.position = 'absolute';
    	footerDIV.style.left     = 0;
    	footerDIV.style.bottom   = 0;
    	
    	//create text input
    	var textInput = document.createElement("input");
    	textInput.setAttribute("id", this.getTextInputID());
    	//textInput.setAttribute("type", "text");
    	textInput.setAttribute("name", "chatInput");
    	//textInput.setAttribute("class", "chatInput");
    	
    	$(textInput).attr('autocomplete', 'off');
        $(textInput).keyup(function(e) {
            if (e.keyCode == 13) {
            	$.cometChat.send($(textInput).val(), _self.getPeerUserName());
            	$(textInput).val('');
            	$(textInput).focus();
            }
        });
        
    	footerDIV.appendChild(textInput);
    	
    	return footerDIV;
    };
    
    this.getWindowHeader = function() {
    	
    	var headerDIV = document.createElement("div");
    	headerDIV.style.width  = this._windowWidth + "px";
    	headerDIV.style.height = "30px";
    	headerDIV.style.backgroundColor = '#31B404'; 
    	headerDIV.style.position = 'relative';
    	headerDIV.style.top      = 0;
    	headerDIV.style.left     = 0;
    	
    	var textUserName = document.createElement("span");
    	textUserName.setAttribute("class", "windowTitle");
    	textUserName.innerHTML = this.getPeerUserName();
    	
    	var textClose = document.createElement("span");
    	textClose.setAttribute("class", "windowClose");
    	textClose.innerHTML = "[X]";
    	this.addOnClickListener(textClose, this.hide, this);
    	
    	headerDIV.appendChild(textUserName);
    	headerDIV.appendChild(textClose);
    	
    	return headerDIV;
    };
    
    this.getWindowHTML = function() {
    	var chatemplete = $("#chatemplate");
    	var windowDIV = document.createElement("div"); //$("#chatbox")[0];
    	windowDIV.setAttribute("id", this.getWindowID());
    	windowDIV.style.width  = this._windowWidth + "px"; 
    	windowDIV.style.height = this._windowHeight +"px";
    	//windowDIV.style.backgroundColor = '#FFFFFF'; 
    	windowDIV.setAttribute("class","chat-panel panel panel-default");
    	windowDIV.style.position = 'absolute';
    	windowDIV.style.bottom   = 0;
    	windowDIV.style.right    = this.getWindowLeftPosition() + "px"; 
    	windowDIV.style.zIndex   = 100;
    	//windowDIV.style.border   = '1px solid #31B404'; 
    	
    	var chatheader =  $(chatemplete).find("#chatheader").clone()[0];
    	$(chatheader).find("span")[0].innerHTML = this.getPeerUserName();
    	
    	var chatcontent = $(chatemplete).find("#chatcontent").clone()[0];
    	$(chatcontent).attr("id", this.getMessageContainerID());
    	
    	var chatfooter = $(chatemplete).find("#chatfooter").clone()[0];
    	
    	
        
    	windowDIV.appendChild(chatheader); 
    	windowDIV.appendChild(chatcontent);
    	windowDIV.appendChild(chatfooter); 
    	
    	var textInput = $(windowDIV).find("#btn-input")[0];
    	var sendBtn = $(windowDIV).find("#btn-chat")[0];
    	$(textInput).attr("id", this.getTextInputID());
    	//textInput.setAttribute("type", "text");
    	$(textInput).attr("name", "chatInput");
    	//textInput.setAttribute("class", "chatInput");
    	
    	$(textInput).attr('autocomplete', 'off');
        $(textInput).keyup(function(e) {
            if (e.keyCode == 13) {
            	$.cometChat.send($(textInput).val(), _self.getPeerUserName());
            	$(textInput).val('');
            	$(textInput).focus();
            }
        });
        $(sendBtn).on("click", function() {
            	$.cometChat.send($(textInput).val(), _self.getPeerUserName());
            	$(textInput).val('');
            	$(textInput).focus();
        });
        
    	
    	$(windowDIV).find("#chatclose").on("click", function(){
    		$("#" + _self.getWindowID()).css("display", "none");
    	});
    	return windowDIV;
    };
    
    this.initWindow = function(config) {
    	this._config = config;
    	this._peerUserName    = config.peerUserName;
    	this._loginUserName   = config.loginUserName;
    	this.windowArray      = config.windowArray;
    	
    	var body = document.getElementsByTagName('body')[0];
    	body.appendChild(this.getWindowHTML()); 
    	//focus text input just after opening window
    	this.focusTextInput();
    };
}