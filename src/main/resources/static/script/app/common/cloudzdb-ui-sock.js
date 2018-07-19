var gZdbsocket = {
		url : 'http://localhost:8080/servicesjs',
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
