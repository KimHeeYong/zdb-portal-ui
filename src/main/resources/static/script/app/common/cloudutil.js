var cloudUtil = {
	nvlString:function(val,def){ //문자형 반환,빈값일 경우 def값
		return ''+(!val?(def):val);
	},
	addComma:function(val){ //3자리 콤마 추가
		val = cloudUtil.nvlString(val);
		var valAr=val.split(".");
	    return valAr[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") + (valAr[1] ? "." + valAr[1] : "");
	},
	removeComma:function(val){ //3자리 콤마 제거
		return cloudUtil.nvlString(val).replaceAll(',', '');
	},
	truncDecimal:function(val,idx){ // 소수점 idx 자리까지 표시
		val = cloudUtil.nvlString(val);
		if(val.indexOf('.') < 0) return val;
		return val.substring(0, (val.indexOf('.')+idx+1));
	},
	onlyNumerString:function(val){
		return cloudUtil.nvlString(val).replaceAll(/[^\d]/,'');
	},
	parseInt:function(val){
		return parseInt(cloudUtil.onlyNumerString(val));
	}
}

String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
};
String.prototype.toUnderscore = function(){
	return this.replace(/([A-Z])/g, function($1){return "_"+$1.toLowerCase();});
};