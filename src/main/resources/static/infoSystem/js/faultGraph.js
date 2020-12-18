 
var countData;
function initRealInfo(){
	//----------------待完善
	var select =  $("#date_search").val(),startDate,endDate,yearAndM;
	if (!select) {
		select = formatDate(new Date());
	}
	yearAndM = select.split("-");
	startDate = yearAndM[0] + "-" + yearAndM[1] + "-01";//选中月的第一天
	endDate = getLastDay(yearAndM[0],yearAndM[1]);//当前月最后一天
	$.post("../ErrorLog/censusCount",{"startDate":startDate,"endDate":endDate},function(result){
		debugger
		countData = result.length > 0 ? result : new Array();
		dealWithDateShow();
 	});
 	
}

/**
 * 获取指定月的最后一天
 * @param y
 * @param m
 * @returns {String}
 */
function getLastDay(y,m){ 
    var date = new Date(y,m,0);  //获取最后一天
    return formatDate(date);
}

/**
 * 格式化日期（yyyy-MM-dd）
 * @param date
 * @returns
 */
function formatDate(date){
	var month = date.getMonth() + 1;
	return date.getFullYear() + '-' + (month < 10 ? "0" + month : month )+ '-' + date.getDate();
}

//条件搜索
$(".control_refer_img1").click(function(){
	initRealInfo();
});

$(function() {
	initRealInfo();
	//  子菜单栏点击效果
	$(".menu_span_wo").removeClass("menu_son_current");
	$(".menu_span_wo").eq(2).addClass("menu_son_current");
	$(".menu_son_ul").addClass("none").removeClass("block");
	$(".menu_son_ul").eq(2).addClass("block").removeClass("none");
	
	$(".menu_span_wo").last().children("i").addClass("icon-jiantou-copy-copy-copy").removeClass("icon-jiantou-copy-copy");
	$(".menu_span_wo").first().children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	$(".menu_span_guan").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	
	$(".menu_son_ul_Three li").eq(4).addClass("menu_current").siblings().removeClass("menu_current");

	$(".fault_inner").addClass("fr_bg");
	$(".fault_list_menu").click(function() { //故障信息
		$(".fixed-table-pagination").show();
		$(".fixed-table-pagination .pagination").show();
		$(".fault_list_func").addClass("fr_bg");
		$(".fault_inner").removeClass("fr_bg");
	})

	$(".fault_graph_menu").click(function() { //故障统计
		$(".fixed-table-pagination").hide();
		$(".fixed-table-pagination .pagination").hide();
		$(".fault_inner").addClass("fr_bg");
		$(".fault_list_func").removeClass("fr_bg");
	})
	
	//年月选择
	laydate.render({ 
	  elem: '#date_search',
	  type: 'month',
	  showBottom: false,
      change: function(value, date, endDate){
    	$('#date_search').val(value);
    	$(".layui-laydate-main").hide();
      }
	});
})

/**
 * 展示统计图
 */
function dealWithDateShow(){
	//统计图表
	$('#fault_graph_box').empty();
	$('#fault_graph_box').highcharts({
		chart: {
			type: 'areaspline'
		},
		title: {
			text: '故障次数统计:',
			style: {
				color: '#5395E5'
			}
		},
		//x轴
		xAxis: {
			categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15',
				'16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30','31'
			],
			title: {
				text: '日期',
				style: {
					right: '0px',
					color: '#5395E5',
					fontSize:'14px'
				}
			},
			labels: {
                y: 20, 
                style: {
                    color: '#B3C9DD',
                    fontSize:'14px'  
                }
            },
            tickWidth:0,
            gridLineWidth: 0,
		},
		tooltip: {
			formatter: function() {
				var s;
				if(this.point.name) {     
					s = '' +
						this.point.name + ': ' + this.y + '次';
				} else {
					s = '' +
						this.x + ': ' + this.y + '次';
				}
				return s;
			}
		},
		labels: {
			items: [{
				html: '故障次数',
				style: {
					left: '10px',
					top: '-30px',
					color: '#5395E5',
					fontSize:'14px'
				}
			}]
		},
		yAxis: {
            labels: {
                style: {
                    color: '#B3C9DD',
                    fontSize:'14px'  
                }
            },
            lineWidth: 1,
            lineColor:"#B3C9DD",
//          gridLineColor:"#B3C9DD",
            gridLineWidth: 0,
        },
		credits: {
			enabled: false
		},
		//[0, 3, 10, 15, 7, 10, 12, 8, 14, 7, 2, 19, 30, 3, 4, 20, 3, 10, 35, 7, 10, 12, 8, 14, 7, 2, 1, 0, 3, 4]
		series: [
//			{
//				type: 'column',
//				name: '柱状图',
//				color: '#FF3883',
//				data: countData
//			},
			{
//				type: 'spline',
				name: '曲线图',
				color: '#FF4E77',
				data: countData,
				fillColor: { 
	 				linearGradient: {
	 					x1: 0,
	 					y1: 0,
	 					x2: 0,
	 					y2: 1
	 				},
	 				stops: [
	 					[0, Highcharts.getOptions().colors[5]],
	 					[1, Highcharts.Color(Highcharts.getOptions().colors[6]).setOpacity(0).get('rgba')]
	 				]
	 			},
			}
		],
		legend:{
			itemStyle:{
				color:"#999"
			},
			enabled: false
		}
	});
}