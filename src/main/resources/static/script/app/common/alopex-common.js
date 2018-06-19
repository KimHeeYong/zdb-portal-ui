$a.page(function() {
	var data = [
    	{
    		"name": "Vera",
    		"phone": "138-955-8109",
    		"email": "eu.odio@maurisblanditmattis.ca",
    		"birthday": "2015-07-18",
    		"zip": "467025",
    		"salary": 787197,
    		"absence": 100,
    		"country": "Bahamas",
    		"city": "Serrungarina",
    		"company": "Suspendisse Dui Fusce PC",
    		"type": 2
    	},
    	{
    		"name": "Bethany",
    		"phone": "949-378-7845",
    		"email": "erat@Morbiaccumsanlaoreet.edu",
    		"birthday": "2014-12-16",
    		"zip": "83245-257",
    		"salary": 998642,
    		"absence": 1,
    		"country": "Poland",
    		"city": "Milwaukee",
    		"company": "Vel Sapien Company",
    		"type": 1
    	}
    ];
    // 초기화 함수
    this.init = function(id, param) {
        gridInit();
    };
    function gridInit(){
    	// 그리드 공통 설정 
    	AlopexGrid.setup({
    		fitTableWidth : true // 테이블의 너비를 그리드 너비에 맞춰 확장시키는 옵션
    	});
    	
    	$('#gr').alopexGrid({
    		pager: true,
    		paging: {
    			perPage: 5,
    			pagerCount: 5,
    			pagerSelect: true
    		},
            defaultColumnMapping : {
                sorting:true
            },
    		autoColumnIndex: true,
    		columnMapping : [
    			{
    				align : 'center',
    				key : 'check',
    				width : '30px',
    				selectorColumn : true
    			}, {
    				key : 'name',
    				title : 'name',
    				width : '100px'
    			}, {
    				key : 'phone',
    				title : 'phone',
    				width : '100px'
    			}, {
    				key : 'email',
    				title : 'email',
    				width : '250px'
    			}, {
    				align : 'center',
    				key : 'birthday',
    				title : 'birthday',
    				width : '100px'
    			}, {
    				align : 'center',
    				key : 'zip',
    				title : 'zip',
    				width : '100px'
    			}, {
    				align : 'right',
    				key : 'salary',
    				title : 'salary',
    				width : '100px'
    			}, {
    				key : 'country',
    				title : 'country',
    				width : '200px'
    			}, {
    				key : 'city',
    				title : 'city',
    				width : '150px'
    			}, {
    				key : 'company',
    				title : 'company',
    				width : '200px'
    			}, {
    				key : 'type',
    				align: 'center',
    				title : 'type',
    				width : '50px'
    			}
    		],
    		data: data
    	});
    	
    	$('#gr2').alopexGrid({
    		height: 300,
    		autoColumnIndex: true,
            pager: true,
            paging: {
                perPage: 5,
                pagerCount: 5,
                pagerSelect: true
            },
    		columnMapping : [
    			{
    				align : 'center',
    				width : 30,
    				numberingColumn : true
    			}, {
    				key : 'name',
    				title : 'name',
    				width : 100
    			}, {
    				key : 'phone',
    				title : 'phone',
    				width : 100
    			}, {
    				key : 'email',
    				title : 'email',
    				width : 250
    			}, {
    				align : 'center',
    				key : 'birthday',
    				title : 'birthday',
    				width : 100
    			}, {
    				align : 'center',
    				key : 'zip',
    				title : 'zip',
    				width : 100
    			}
    		],
    		data: data
    	});
    	$('#gr3').alopexGrid({
    		height: 300,
    		autoColumnIndex: true,
    		columnMapping : [
    			{
    				align : 'center',
    				width : 30,
    				numberingColumn : true
    			}, {
    				key : 'name',
    				title : 'name',
    				width : 100
    			}, {
    				key : 'phone',
    				title : 'phone',
    				width : 100
    			}, {
    				key : 'email',
    				title : 'email',
    				width : 250
    			}, {
    				align : 'center',
    				key : 'birthday',
    				title : 'birthday',
    				width : 100
    			}, {
    				align : 'center',
    				key : 'zip',
    				title : 'zip',
    				width : 100
    			}
    		],
    		data: data
    	});
        $('#gr4').alopexGrid({
            height: 300,
            autoColumnIndex: true,
            columnMapping : [
                {
                    align : 'center',
                    width : 30,
                    numberingColumn : true
                }, {
                    key : 'name',
                    title : 'name',
                    width : 100
                }, {
                    key : 'phone',
                    title : 'phone',
                    width : 100
                }, {
                    key : 'email',
                    title : 'email',
                    width : 250
                }, {
                    align : 'center',
                    key : 'birthday',
                    title : 'birthday',
                    width : 100
                }, {
                    align : 'center',
                    key : 'zip',
                    title : 'zip',
                    width : 100
                }
            ],
            data: data
        });
    	//dialog layout start
    	$('#dialog-layout1').click(function() {
    		$('#div-dialog').open({
    			title : "팝업타이틀",
    			width : 620,
    			resize:true,
    			height : 730
    		});
    		$("#gr3").alopexGrid( "viewUpdate" );
    	});
    	
    };
   
});