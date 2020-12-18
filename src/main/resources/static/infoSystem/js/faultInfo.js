$(function(){
	//初始化table
	fault_table();
//	$(".fixed-table-pagination").show();
//	$(".fixed-table-pagination .pagination").show();
	
	//  子菜单栏点击效果
	$(".menu_span_wo").removeClass("menu_son_current");
	$(".menu_span_wo").eq(1).addClass("menu_son_current");
	$(".menu_son_ul").addClass("none").removeClass("block");
	$(".menu_son_ul").eq(1).addClass("block").removeClass("none");
	
	$(".menu_span_wo").last().children("i").addClass("icon-jiantou-copy-copy-copy").removeClass("icon-jiantou-copy-copy");
	$(".menu_span_wo").first().children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	$(".menu_span_guan").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	
	$(".menu_son_ul_Three li").eq(3).addClass("menu_current").siblings().removeClass("menu_current");
	
	$(".fault_list_func").addClass("fr_bg");
	
	$(".fault_list_menu").click(function(){//故障信息
		$(".fixed-table-pagination").show();
		$(".fixed-table-pagination .pagination").show();
		$(".fault_list_func").addClass("fr_bg");
		$(".fault_inner").removeClass("fr_bg");
	})
	
	$(".fault_graph_menu").click(function(){//故障统计
		$(".fixed-table-pagination").hide();
		$(".fixed-table-pagination .pagination").hide();
		$(".fault_inner").addClass("fr_bg");
		$(".fault_list_func").removeClass("fr_bg");
	})
	
	//导出excel
	$(".agv_button_add").click(function(){
		debugger
		var startTime = $("#c1").val();
		var endTime = $("#c2").val();
		var id = $(".agv_search_input").val();
		window.location = "../excel/errorLog?startDate=" + (startTime?startTime:null) 
		+ "&endDate=" + (endTime?endTime:null);
	})
	 
});

//按钮动效
//[].map.call(document.querySelectorAll('[anim="ripple"]'), function (el) {
//  el.addEventListener('click', function (e) {
//      e = e.touches ? e.touches[0] : e;
//      var r = el.getBoundingClientRect(), d = Math.sqrt(Math.pow(r.width, 2) + Math.pow(r.height, 2)) * 2;
//      el.style.cssText = "--s: 0; --o: 1;";
//      el.offsetTop;
//      el.style.cssText = "--t: 1; --o: 0; --d: " + d + "; --x:" + (e.clientX - r.left) + "; --y:" + (e.clientY - r.top) + ";";
//  });
//});

//table
function fault_table() {
	$("#fault_table").bootstrapTable({ // 对应table标签的id
		url: "../ErrorLog/query", // 获取表格数据的url
		method: 'POST',
		striped: false, //是否显示行间隔色
		cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: true, //是否显示分页（*）
		sortable: false, //是否启用排序
		sortOrder: "asc", //排序方式
		queryParams: queryParams = function(params) {
			var startTime = $("#c1").val();
			var endTime = $("#c2").val();
        	return {
        		"page" : params.offset / params.limit + 1,
        		"count" : params.limit,
        		"date" : startTime?startTime:null,
        		"endTime" : endTime?endTime:null
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
//		data:[{"serialNum": 1,"agvNo": "AGV1","faultType" : "激光报警","faultTime":"2018-10-13 10:22"},
//		{"serialNum": 2,"agvNo": "AGV2","faultType" : "任务异常","faultTime":"2018-10-13 10:22"}],
		columns: [
		{
			field: 'serialNum',
			title: '故障编号',
			cellStyle: 'formatTableT',
			formatter : function(value, row, index){
                var	html = '<span>' +(row.code ? row.code : "")+'</span>';
                return  html
            }
		},{
			field: 'agvNo',
			title: 'AGV编号',
			cellStyle: 'formatTableT',
			formatter : function(value, row, index){
                var	html = '<span>' + (row.agvCode ? row.agvCode : "") + '</span>';
                return  html
            }
		}, {
			field: 'faultAdress',
			title: '故障位置',
			cellStyle: 'formatTableT',
			formatter : function(value, row, index){
                var	html = '<span>' + (row.sitCode ? row.sitCode : "") + '</span>';
                return  html
            }
		},{
			field: 'faultType',
			title: '故障信息',
			cellStyle: 'formatTableT',
			formatter : function(value, row, index){
                var	html = '<span style="color:#EB5656">' + (row.remark ? row.remark : "") + '</span>';
                return  html
            }
		},{
			field: 'faultTime',
			title: '故障时间',
			cellStyle: 'formatTableT',
			formatter : function(value, row, index){
                var	html = '<span>' + (row.createDate ? row.createDate : "") + '</span>';
                return  html
            }
		}
		],
		onLoadSuccess: function() { //加载成功时执行
			console.info("加载成功");
		},
		onLoadError: function() { //加载失败时执行
			console.info("加载数据失败");
		}

	});
}


//条件搜索	搜索双击后选择日期清空
	var Retimer = null;
	$(".control_refer_img1").dblclick(function () {
	  clearTimeout(Retimer);
		$("#c1").val("");
		$("#c2").val("");
	})
	//条件搜索	
	$(".control_refer_img1").click(function () {
	  clearTimeout(Retimer);
	  //在单击事件中添加一个setTimeout()函数，设置单击事件触发的时间间隔 
	  Retimer = setTimeout(function () {
		  if($("#c1").val() == "" && $("#c2").val() !== ""){
				publicTipMessage("error","请选择开始时间！");
				return;
			}else if($("#c2").val() == "" && $("#c1").val() !== ""){
				publicTipMessage("error","请选择结束时间！");
				return;
			}
			refresh();//刷新table列表
	   }, 300);         
	})

//刷新页面
function refresh() {
	$('#fault_table').bootstrapTable('refresh');
}

function formatTableT(value, row, index) {
	return {
		css: {
			"background": "none",
			"border": "0",
			"border-bottom": "1px solid #EAEAEA",
			"color": "#858C9E",
			"border-radius": "3px"
		}
	}
}
  