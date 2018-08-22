var cloudUtil = {
	addComma:function(val){
        return (val+''||'').replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"); 
	},
	removeComma:function(val){
		return (val+''||'').replaceAll(',', '');
	}
}

String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
};
String.prototype.toUnderscore = function(){
	return this.replace(/([A-Z])/g, function($1){return "_"+$1.toLowerCase();});
};