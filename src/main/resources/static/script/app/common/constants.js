var G_SERVICE_TYPE_MARIA = 'mariadb';
var G_SERVICE_TYPE_REDIS = 'redis';

var _gSliderConstantsCommonRange = { 'min': 0, '14%': 1, '28%': 2, '42%': 3, '56%': 4, '70%': 5, '85%':6, 'max': 7 };
var _gSliderConstantsCommonGetValue = function(index){
	return this.value[index]+this.unit;
};
var _gSliderConstantsCommonGetIndex = function(val){
	return this.value.indexOf(parseInt((val||'0').replace(/[a-zA-Z]+/g,'')));
};
var gSliderConstants = {
	redis :{
		memory : {
			range : _gSliderConstantsCommonRange,
			value :[0,256,512,1000,3000,5000,8000,12000],
			unit : 'MB',
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
			unit : 'GB',
			getValue : _gSliderConstantsCommonGetValue,
			getIndex : _gSliderConstantsCommonGetIndex
		}		
	},
	mariadb :{
		memory : {
			range : _gSliderConstantsCommonRange,
			value :[0,1024,2048,4096,8196,16384,24576,32768],
			unit : 'MB',
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
			unit : 'GB',
			getValue : _gSliderConstantsCommonGetValue,
			getIndex : _gSliderConstantsCommonGetIndex
		}
	}
};
var gMessage = {
	validPage : '잘못된 접근 입니다.',
	saveSuccess: '정상적으로 저장되었습니다.',
	notValidInput : '유효하지 않은 입력값이 있습니다.',
	procSuccss: '정상적으로 처리되었습니다.',
	gridNodata: '데이터가 없습니다.'
};


