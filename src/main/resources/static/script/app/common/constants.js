var G_SERVICE_TYPE_MARIA = 'mariadb';
var G_SERVICE_TYPE_REDIS = 'redis';

var gSliderConstants = {
	redis :{
		memory : {
			range : {
				'min': 0, '4%': 1, '8%': 2, '20%': 3, '40%': 4, '70%': 5, 'max': 6		
			},
			value :[256,512,1000,3000,5000,8000,12000],
			unit : 'Mi',
			getValue : function(index){
				return this.value[index]+this.unit;
			},
			getIndex : function(val){
				return this.value.indexOf(parseInt((val||'0').replace(/[a-zA-Z]+/g,'')));
			}
		},
		cpu : {
			range : {
				'min': 0, '10%': 1, '20%': 2, '30%': 3, '40%': 4, '60%': 5, 'max': 6
			},
			value : [200,300,500,700,900,1200,2000],
			unit : 'm',
			getValue : function(index){
				return this.value[index]+this.unit;
			},
			getIndex : function(val){
				return this.value.indexOf(parseInt((val||'0').replace(/[a-zA-Z]+/g,'')));
			}
		},
		disk : {
			range : {
				'min': 0, '8%': 1, '16%': 2, '24%': 3, '35%': 4, '50%': 5, 'max': 6
			},
			value : [20,40,80,100,250,500,1000],
			unit : 'Gi',
			getValue : function(index){
				return this.value[index]+this.unit;
			},
			getIndex : function(val){
				return this.value.indexOf(parseInt((val||'0').replace(/[a-zA-Z]+/g,'')));
			}
		}		
	},
	mariadb :{
		memory : {
			range : {
				'min': 0, '4%': 1, '8%': 2, '20%': 3, '40%': 4, '70%': 5, 'max': 6				
			},
			value :[256,300,350,400,450,480,512],
			unit : 'Mi',
			getValue : function(index){
				return this.value[index]+this.unit;
			},
			getIndex : function(val){
				return this.value.indexOf(parseInt((val||'0').replace(/[a-zA-Z]+/g,'')));
			}
		},
		cpu : {
			range : {
				'min': 0, '4%': 1, '8%': 2, '20%': 3, '40%': 4, '70%': 5, 'max': 6		
			},
			value : [200,250,300,350,400,450,500],
			unit : 'm',
			getValue : function(index){
				return this.value[index]+this.unit;
			},
			getIndex : function(val){
				return this.value.indexOf(parseInt((val||'0').replace(/[a-zA-Z]+/g,'')));
			}
		},
		disk : {
			range : {
				'min': 0, '8%': 1, '16%': 2, '24%': 3, '35%': 4, '50%': 5, 'max': 6
			},
			value : [20,40,80,100,250,500,1000],
			unit : 'Gi',
			getValue : function(index){
				return this.value[index]+this.unit;
			},
			getIndex : function(val){
				return this.value.indexOf(parseInt((val||'0').replace(/[a-zA-Z]+/g,'')));
			}
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


