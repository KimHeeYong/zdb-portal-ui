var cloudUtil = {
	addComma:function(val){ //3자리 콤마 추가
		val = (val+''||'');
		var valAr=val.split(".");
	    return valAr[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") + (valAr[1] ? "." + valAr[1] : "");
	},
	removeComma:function(val){ //3자리 콤마 제거
		return (val+''||'').replaceAll(',', '');
	},
	truncDecimal:function(val,idx){ // 소수점 idx 자리까지 표시
		if(val.indexOf('.') < 0) return val;
		return val.substring(0, (val.indexOf('.')+idx+1));
	}
}

String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
};
String.prototype.toUnderscore = function(){
	return this.replace(/([A-Z])/g, function($1){return "_"+$1.toLowerCase();});
};