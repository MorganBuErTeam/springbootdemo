$(function(){
//	$(".fixed-table-pagination").show();
//	$(".fixed-table-pagination .pagination").show();
	//  子菜单栏点击效果
	$(".menu_span_wo").removeClass("menu_son_current");
	$(".menu_span_wo").eq(2).addClass("menu_son_current");
	$(".menu_son_ul").addClass("none").removeClass("block");
	$(".menu_son_ul").eq(2).addClass("block").removeClass("none");
	
	$(".menu_span_wo").last().children("i").addClass("icon-jiantou-copy-copy-copy").removeClass("icon-jiantou-copy-copy");
	$(".menu_span_wo").first().children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	$(".menu_span_guan").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	
	$(".menu_son_ul_Three li").eq(1).addClass("menu_current").siblings().removeClass("menu_current");
	initRealInfo();
	setInterval("initRealInfo()",1000);
	
})
var connState;
 function initRealInfo(){
	$.post("../ControlCabinet/queryCabinetInfo",function(result){
		debugger
		if (!result || (!result && result.length == 0)) {
			return;
		}
		var cabinet=result.cabinet;
		$("#ip_plc").val(cabinet.ip);//ip
		$("#port_plc").val(cabinet.port);//端口
		connState = result.connState;
		$("#port_conn_state").val(connState);//连接状态
		$("#port_conn_count").val(result.connCount);//连接次数
		$("#updateDate").val(result.updateTime);//更新时间
		
		//充电信号
		var cabinetReal = result.cabinetReal;
		if (!cabinetReal) {
			return;
		}
		if (cabinetReal) {
 			changeText("cacheStation1", cabinetReal.cacheStationOne);
			changeText("cacheStation2", cabinetReal.cacheStationTwo);
			changeText("cacheStation3", cabinetReal.cacheStationThree);
			changeText("cacheStation4", cabinetReal.cacheStationFour);
			changeText("cacheStation5", cabinetReal.cacheStationFive);
			changeText("packStation", cabinetReal.packStation);
			changeText("putStation", cabinetReal.putStation);
			changeText("emptyStation", cabinetReal.emptyStation);
			changeText("startSign", cabinetReal.callStation);
		}
		 
 	});
	
}

//给指定的ID加class或者去掉class,占位蓝色，闲置绿色
function changeText(id,value){
	if(connState == "未连接"){
		$("#" + id).val("");
//		$("#" + id).addClass("fc_blue");
		return;
	}
	$("#" + id).val(value == "0" ? "闲置" : "占用");
	$("#" + id).addClass(value == "0" ? "fc_green" : "fc_blue");
}

  