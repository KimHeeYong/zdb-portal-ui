var cloudUtil = {
	isEmpty:function(val){
		  if(typeof(val) == 'undefined' || val === null){
		      return true; 
		  } else if(typeof(val) == 'number' || typeof(val) == 'boolean'){ 
		      return false; 
		  } else if(typeof(val.length) != 'undefined'){
	          return val.length == 0;
	      } else{
	    	  return false;
	      }
	},
	nvlString:function(val,def){ //문자형 반환,빈값일 경우 def값
		return cloudUtil.isEmpty(val)?(cloudUtil.isEmpty(def)?'':''+def):''+val;
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
		if(!idx || idx < 1){
			idx = -1;
		};
		val = cloudUtil.nvlString(val);
		if(val.indexOf('.') < 0) return val;
		return val.substring(0, (val.indexOf('.')+idx+1));
	},
	onlyNumerString:function(val){
		return cloudUtil.nvlString(val).replaceAll(/[^\d]/,'');
	},
	parseInt:function(val){
		return parseInt(cloudUtil.nvlString(cloudUtil.onlyNumerString(val),'0'));
	},
	convertSize:function(size,idx) {
		if(size instanceof String){
			size = cloudUtil.parseInt(size);
		};
		
		if( size > 1024 * 1024 * 1024 * 1024) {
			size = cloudUtil.truncDecimal((size / 1024 / 1024 / 1024 / 1024),idx) +"Pi";
		} else if( size > 1024 * 1024 * 1024) {
			size = cloudUtil.truncDecimal((size / 1024 / 1024 /1024),idx) +"Ti";
		} else if( size > 1024 * 1024) {
			size = cloudUtil.truncDecimal((size / 1024 / 1024),idx) +"Gi";
		} else if( size > 1024) {
			size = cloudUtil.truncDecimal((size / 1024),idx) +"Mi";
		} else {
			size = size+"Ki";
		}
		return size;
    },
    convertDiskSize:function(size){
		if(!size){
			size = '0';
		}else if(size instanceof String){
			size.replace(/[^0-9.]+/g,'');
		}		
		
		let sizeBn = new BigNumber(size);
		let re = '';
		
		if(sizeBn.gte(1024 * 1000 * 1000 * 1000)) {
			re = sizeBn.div(1024*1000*1000*1000).integerValue().toFixed() +'Pi';
		}else if(sizeBn.gte(1024 * 1000 * 1000)) {
			re = sizeBn.div(1024*1000*1000).integerValue().toFixed() +'Ti';
		}else if(sizeBn.gte(1024 * 1000)) {
			re = sizeBn.div(1024*1000).integerValue().toFixed() +'Gi';
		}else if(sizeBn.gte(1024)) {
			re = sizeBn.div(1024).integerValue().toFixed() +'Mi';
		}else{
			re = size+'Ki';
		}
		return re;
    },
    //올바른 정규식 형태인지 체크
	checkRegExp:function(val){ 
		var isRegExp = true;
		try{
			new RegExp(val);
		}catch(e){
			isRegExp = false
		}
		return isRegExp;
	}, 
	dtFormat:function(val,optVal){
		let dfOpt = {
			fromFormat:	'YYYYMMDDHHmmss',
			toFormat:'YYYY-MM-DD HH:mm:ss'
		}
		let opt = $.extend({},dfOpt,optVal);
		let isValid = moment(val,opt.fromFormat).isValid();
		return isValid ? moment(val,opt.fromFormat).format(opt.toFormat) : '';
	},
	filedownFromText:function(data,fileName){
		let text = data||'';
		var link = document.createElement('a');
		link.setAttribute('download', fileName);
		link.setAttribute('href', 'data:text/plain;charset=utf-8,'+ encodeURIComponent(text));
		link.click();
	}
}

String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
};
String.prototype.toUnderscore = function(){
	return this.replace(/([A-Z])/g, function($1){return "_"+$1.toLowerCase();});
};