var data_code;
$(function(){
	//菜单栏点击效果
	$(".menu_span_wo").removeClass("menu_son_current");
	$(".menu_span_wo").eq(2).addClass("menu_son_current");
	$(".menu_son_ul").addClass("none").removeClass("block");
	$(".menu_son_ul").eq(2).addClass("block").removeClass("none");
	
	$(".menu_span_wo").last().children("i").addClass("icon-jiantou-copy-copy-copy").removeClass("icon-jiantou-copy-copy");
	$(".menu_span_wo").first().children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	$(".menu_span_guan").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	
	$(".menu_son_ul_Three li").eq(0).addClass("menu_current").siblings().removeClass("menu_current");
	
    data_code = GetRequest().data_agvId;
    var agvCode = GetRequest().agvCode;
	debugger
	$(".show_agv_name").text(agvCode);
	$(".show_agv_name").css("cursor","default");
	$(".show_agv_list").addClass("fc_9");
	$(".show_agv_list").css("cursor","pointer");
	initRealInfo();
});
setInterval("initRealInfo()", 1000);
function initRealInfo(){
	$.post("../AGV/queryRealInfo",{"netId":data_code},function(result){
		debugger
		if (result) {
			$("#show_agv_conn_state").text(result.state);
			if(result.state == "已连接"){
				$("#show_agv_conn_state").addClass("fc_green").removeClass("fc_gray");
			}else{
				$("#show_agv_conn_state").addClass("fc_gray").removeClass("fc_green");
			}
			$("#show_agv_charge").text(result.agvRealInfo.battery_soc+"%"); //电量
			$("#show_agv_conn_count").text(result.connCount);// 连接次数
			var agvState = "";
			switch(result.agvRealInfo["agv_control"]) {
			case 1:
				agvState = "手动"
				break;
			case 2:
				agvState = "半自动"
				break;
			case 3:
				agvState = "自动"
				break;
			case 4:
				agvState = "有误"
				break;
			default:
				agvState = "未知";
		 }
			$("#show_agv_state").text(agvState);//小车状态
			if(agvState == "手动"){
				$("#show_agv_state").addClass("fc_orange").removeClass("fc_gray");
			}else{
				$("#show_agv_state").addClass("fc_gray").removeClass("fc_orange");
			}
 			$("#show_agv_addr").text(result.agvRealInfo["address"]);//当前地址
 			$("#show_agv_work_state").text(result.agvRealInfo.finish_task==0?"任务未完成":"任务完成");
			$("#show_agv_speed").text(Math.round(result.agvRealInfo["speedy"] * 100) / 100  + " m/s");//运行速度
 		}
	});
}

