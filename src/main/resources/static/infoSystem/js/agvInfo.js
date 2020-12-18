$(function(){
	//初始化table
	agvInfoTable();
	$(".fixed-table-pagination").addClass("dis_none");
	$(".fixed-table-pagination .pagination").addClass("dis_none");
	
	//菜单栏点击效果
	$(".menu_span_wo").removeClass("menu_son_current");
	$(".menu_span_wo").eq(1).addClass("menu_son_current");
	$(".menu_son_ul").addClass("none").removeClass("block");
	$(".menu_son_ul").eq(1).addClass("block").removeClass("none");
	
	$(".menu_span_wo").last().children("i").addClass("icon-jiantou-copy-copy-copy").removeClass("icon-jiantou-copy-copy");
	$(".menu_span_wo").first().children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	$(".menu_span_guan").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	
	$(".menu_son_ul_Three li").eq(0).addClass("menu_current").siblings().removeClass("menu_current");
	
	
	$(".show_agv_list").css("cursor","default");
	$("#agvInfoTable").off("click",".infor_agvno").on("click",".infor_agvno",function(){
		debugger
		var data_code = $(this).attr("data_code");
		var agv_code = $(this).attr("agv_code");
		window.location="agvInfoDetail.html?data_agvId="+data_code+"&agvCode="+agv_code;
	})

    //条件搜索
    $(".search_box").click(function(){
        refreshTaskTable();//刷新table列表
    });
    //刷新页面
    function refreshTaskTable() {
        $('#agvInfoTable').bootstrapTable("refreshOptions", { pageNumber: 1 });
    }

	
});

//table
function agvInfoTable() {
	$("#agvInfoTable").bootstrapTable({ // 对应table标签的id
		url: "../AGV/queryByIsUse", // 获取表格数据的url
		method: 'POST',
		striped: false, //是否显示行间隔色
		cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: true, //是否显示分页（*）
		sortable: false, //是否启用排序
		sortOrder: "asc", //排序方式
		queryParams: queryParams = function(params) {
			var ip = $(".agv_search_input").val();
        	return {
        	  "page": params.offset/params.limit+1,
        	  "count": params.limit,
        	  "id":ip?ip:null,
        	};
    	}, //传递参数（*）
//		responseHandler:function(res){
//			debugger
//	    	res.rows = res;
//	    	res.total = res.length;
//	    	return res;
//	    },
		sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
		pageNumber: 1, //初始化加载第一页，默认第一页
		pageSize: 10, //每页的记录行数（*）
		pageList: [10], //可供选择的每页的行数（*）
		strictSearch: true,
		showColumns: false, //是否显示所有的列
		showRefresh: false, //是否显示刷新按钮
		minimumCountColumns: 2, //最少允许的列数
		clickToSelect: false, //是否启用点击选中行
		uniqueId: "no", //每一行的唯一标识，一般为主键列
		showToggle: false, //是否显示详细视图和列表视图的切换按钮
		contentType: "application/x-www-form-urlencoded",
		cardView: false, //是否显示详细视图
		detailView: false, //是否显示父子表
//		data:[{"code": "AGV1","ip" : "192.168.1.107","netId":"200","port":"2020"},
//		{"code": "AGV2","ip" : "192.168.1.107","netId":"200","port":"2020"}],
		columns: [
		{
			title: '序号',
			field: 'CabinetPosition',
			width:80,
			cellStyle: formatTableT,
			formatter: function(value, row, index) {
				var html = '<input disabled ="disabled" style="width:80px;" type="text" class="agv_table_input CabinetPosition" value="' + (index + 1) + '"></input>'
				return html;
			}
		},
		{
			title : '编号',  
			field : 'code', 
			cellStyle:formatTableT,
			formatter : function(value, row, index){    
				var html = '<input disabled ="disabled" style="width:80px;" type="text" class="agv_table_input agvNo"  value="'+(row.code?row.code:"")+'"></input>'
				if (index == 0) {
					$("#agvChong").val(row.freeTime?row.freeTime:"");
					agvChong = row.freeTime;
					$("#ChongShang").val(row.upperPower?row.upperPower:"");
					ChongShang = row.upperPower;
					$("#ChongXia").val(row.lowerPower?row.lowerPower:"");
					ChongXia = row.lowerPower;
					$("#agvShortestChong").val(row.chargeLimitTime?row.chargeLimitTime:"");
					agvShortestChong = row.chargeLimitTime;
				}
				return html;	
			}
		},
		{
			title : '通讯地址',  
			field : 'ip',
			cellStyle:formatTableT,
			formatter:function(value,row,index){
				var html = '<input disabled ="disabled" style="width:140px;" type="text" class="agv_table_input ip" value="'+(row.ip?row.ip:"")+'" ></input>'
					return html;
				
			}
		}, 

   		{
   			title : 'Port',
   			field : 'port',
   			cellStyle:formatTableT,
   			formatter : function(value, row, index){
   				var html = '<input disabled ="disabled" style="width:80px;" type="text" class="agv_table_input Port" value="'+(row.port?row.port:"")+'" ></input>'
   				return html;
   			}

   		},
		{
			title : 'AGV状态',  
			field : 'state', 
			cellStyle:formatTableT,
			formatter : function(value, row, index){
                if(row.isUse==1) {
                    var html = '<input disabled ="disabled" style="width:80px;" type="text" class="agv_table_input state agvStateOnline" value="上线" ></input>'//上线
                }else{
                	var html = '<input disabled ="disabled" style="width:80px; " type="text" class="agv_table_input state agvStateOutline" value="离线" ></input>'//离线
            	}
				return html;
			}

		},
		{
			title : '类型',  
			field : 'agvType', 
			cellStyle:formatTableT,
			formatter : function(value, row, index){
                 var html = '<input disabled ="disabled" style="width:80px;" type="text" class="agv_table_input agvType" value="'+(row.agvType?row.agvType:"")+'" ></input>'
              
				return html;
			}

		},
		{
			title : '线号',  
			field : 'lineNo', 
			cellStyle:formatTableT,
			formatter : function(value, row, index){
                var html = '<input disabled ="disabled" style="width:130px;" type="text" class="agv_table_input lineNo" value="'+(row.lineNo?row.lineNo:"")+'" ></input>'
				return html;
			}

		},
		],
		onLoadSuccess: function() { //加载成功时执行
			console.info("加载成功");
		},
		onLoadError: function() { //加载失败时执行
			console.info("加载数据失败");
		}

	});
}






function formatTableT(value, row, index) {
	return {
		css: {
			"border": "0",
			"background":"none",
			"color": "red",
			"border":"0",
		    "border-right": "1px solid #E6E6E6",
			"font-size": "14px",
			"color": "#858C9E",
			"border-radius": "0",
			"text-align":"center"
		}
	}
}
function formatTableT2(value, row, index) {
	return {
		css: {
			"border": "0",
			"background":"none",
			"color": "red",
			"border":"0",
		    "border-right": "1px solid #E6E6E6",
			"font-size": "14px",
			"color": "#858C9E",
			"padding-left": "18px",
			"border-radius": "0",
			"text-align":"center"
		}
	}
}
