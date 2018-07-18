var gZdbsocket = {
		url : 'https://api.cloudzcp.io/zdbsocket',
		client : null,
		getConnect : function(){
			var socket = new SockJS(this.url);
			this.client = Stomp.over(socket);
			return this.client;
		},
		disconnect : function(){
			if(this.client!=null){
				this.client.disconnect();
				this.client=null
			}
		}
	};
