var gValid = {
	serviceName : {
		rule:/^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*$/g,
		message:'서비스명 양식이 올바르지 않습니다.'
	},
	dbName : {
		rule:/^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*$/g,
		message:'DB명 양식이 올바르지 않습니다.' 
	},
	checkUnitSize :{
		message:'범위를 확인해주세요.'
	},
	storageIops : {
		message:'입력 단위가 100입니다.'
	}
	
}; 
$a.page(function() {
	this.init = function(){
		//console.log(gIsLocale);
		Validator.addMethod('checkRule', function(element, value, param) {
		    var pattern = param;
		    return pattern.test(value);
		});
		Validator.addMethod('checkUnitSize', function(element,value,param) {
			let val = (value||'').toUpperCase();
			let reg = /^([\d]+)([K|M]{0,1})$/gi.exec(val);
			if(reg==null){
				return false;
			}
			let n = bigInt(reg[1]);
			let unit = 1;
			if(reg[2] == 'K'){
				unit = 1024;
			}else if(reg[2] == 'M'){
				unit = 1024 * 1024
			}
			let r = n.multiply(unit);
			let min = bigInt(param.split(':')[0]);
			let max = bigInt(param.split(':')[1]);
		    return (r.greaterOrEquals(min) && r.lesserOrEquals(max));
		});
		Validator.addMethod('checkInputUnit', function(element,value,param) {
			let re = value % param;
			if(re == 0) {
				return true;
			}
			
			return false;
		});
	};
});