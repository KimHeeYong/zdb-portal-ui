var G_SERVICE_TYPE_MARIA = 'mariadb';
var G_SERVICE_TYPE_REDIS = 'redis';

var _gSliderConstantsCommonRange = { 'min': 0, '14%': 1, '28%': 2, '42%': 3, '56%': 4, '70%': 5, '85%':6, 'max': 7 };
var _gSliderConstantsCommonGetValue = function(index,isCluster){
	var val = this.value[index]
	if(isCluster){
		val = val * 2;
	}
	return val+this.unit;
};
var _gSliderConstantsCommonGetIndex = function(val){
	val = cloudUtil.parseInt(val);
	var idx = this.value.indexOf(val);
	if(idx < 0 && val > 0){
		for(var i = this.value.length - 1; i > -1; i--){
			if(val >= this.value[i]){
				idx = [i];
				break;
			};
		};
	};
	return idx < 0 ? 0 : idx;
};

var gSliderConstants = {
	redis :{
		memory : {
			range : _gSliderConstantsCommonRange,
			value :[0,256,512,1000,3000,5000,8000,12000],
			unit : 'Mi',
			getValue : _gSliderConstantsCommonGetValue,
			getIndex : _gSliderConstantsCommonGetIndex
		},
		cpu : {
			range : _gSliderConstantsCommonRange,
			value : [0,200,300,500,700,900,1200,2000],
			unit : 'm',
			getValue : _gSliderConstantsCommonGetValue,
			getIndex : _gSliderConstantsCommonGetIndex
		},
		disk : {
			range : _gSliderConstantsCommonRange,
			value : [0,20,40,80,100,250,500,1000],
			unit : 'Gi',
			getValue : _gSliderConstantsCommonGetValue,
			getIndex : _gSliderConstantsCommonGetIndex
		}		
	},
	mariadb :{
		memory : {
			range : _gSliderConstantsCommonRange,
			value :[0,1000,2000,4000,8000,16000,24000,32000],
			unit : 'Mi',
			getValue : _gSliderConstantsCommonGetValue,
			getIndex : _gSliderConstantsCommonGetIndex
		},
		cpu : {
			range : _gSliderConstantsCommonRange,
			value : [0,500,1000,2000,4000,8000,12000,16000],
			unit : 'm',
			getValue : _gSliderConstantsCommonGetValue,
			getIndex : _gSliderConstantsCommonGetIndex
		},
		disk : {
			range : _gSliderConstantsCommonRange,
			value : [0,20,40,80,100,250,500,1000],
			unit : 'Gi',
			getValue : _gSliderConstantsCommonGetValue,
			getIndex : _gSliderConstantsCommonGetIndex
		}
	}
};
var gStorageClassConstants = {
	 'ibmc-block-bronze':'2 IOPS/GB'
	,'ibmc-block-silver':'4 IOPS/GB'
	,'ibmc-block-gold':'10 IOPS/GB'
};
var gMessage = {
	validPage : '잘못된 접근 입니다.',
	saveSuccess: '정상적으로 저장되었습니다.',
	notValidInput : '유효하지 않은 입력값이 있습니다.',
	procSuccss: '정상적으로 처리되었습니다.',
	gridNodata: '데이터가 없습니다.'
};


