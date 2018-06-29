var gValid = {
	serviceName : {
		rule:/^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*$/g,
		message:'서비스명 양식이 올바르지 않습니다.'
	},
	dbName : {
		rule:/^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*$/g,
		message:'DB명 양식이 올바르지 않습니다.' 
	}
}; 
$a.page(function() {
	this.init = function(){
		Validator.addMethod('checkRule', function(element, value, param) {
		    var pattern = param;
		    return pattern.test(value);
		});		
	};
});