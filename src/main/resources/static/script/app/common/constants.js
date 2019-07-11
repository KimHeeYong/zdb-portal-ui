const _gSliderConstantsCommonRange = { 'min': 0, '14%': 1, '28%': 2, '42%': 3, '56%': 4, '70%': 5, '85%':6, 'max': 7 };
const _gSliderConstantsCommonGetValue = function(index,isCluster){
	let val = this.value[index]
	if(isCluster){
		val = val * 2;
	}
	return val+this.unit;
};
const _gSliderConstantsCommonGetIndex = function(val){
	val = cloudUtil.parseInt(val);
	let idx = this.value.indexOf(val);
	if(idx < 0 && val > 0){
		for(let i = this.value.length - 1; i > -1; i--){
			if(val >= this.value[i]){
				idx = [i];
				break;
			};
		};
	};
	return idx < 0 ? 0 : idx;
};

const cServiceType = {
	mariadb: 'mariadb',
	redis: 'redis'
};

const gSliderConstants = {
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
		},
		storage : {
			range : _gSliderConstantsCommonRange,
			value : [0,2,4,10],
			unit : 'IOPS/GB',
			getValue : _gSliderConstantsCommonGetValue,
			getIndex : _gSliderConstantsCommonGetIndex
		}
	}
};

const cStorageClassConstants = {
	 'ibmc-block-bronze':'2'
	,'ibmc-block-silver':'4'
	,'ibmc-block-gold':'10'
	,'ibmc-file-bronze':'2'
	,'ibmc-file-silver':'4'
	,'ibmc-file-gold':'10'
};
const cConstantsToblockStorage = {
	 '2':'ibmc-block-bronze'
	,'4':'ibmc-block-silver'
	,'10':'ibmc-block-gold'
};
const gMessage = {
	validPage : '잘못된 접근 입니다.',
	saveSuccess: '정상적으로 저장되었습니다.',
	notValidInput : '유효하지 않은 입력값이 있습니다.',
	procSuccss: '정상적으로 처리되었습니다.',
	gridNodata: '데이터가 없습니다.',
	selectAddColumn : '추가 할 항목을 선택하세요'
};

const cImg = {
	checkboxY : '<img src="/styles/images/ico-checkmark.png" style="float:initial;width:20px"></img>'
}