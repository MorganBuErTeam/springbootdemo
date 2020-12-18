var n = readCookie("CookieType");
//判断登录角色，1管理员，有导出权限，2用户，无导出权限
function getLimt() {
	if(n == "1") {
		$(".excelBtn").addClass("block").removeClass("none");
	} else if(n == "2") {
		$(".excelBtn").addClass("none").removeClass("block");
	}
	//  else{
	//  	$(".excelBtn").addClass("none").removeClass("block");
	//  }
}



$(function() {
	getLimt(); //初始化权限
	taskTable(); //加载历史任务列表
	$(".fixed-table-pagination").addClass("none1").removeClass("block");
	$(".fixed-table-pagination .pagination").addClass("none1").removeClass("block");
	//  子菜单栏点击效果
	$(".menu_span_wo").removeClass("menu_son_current");
	$(".menu_span_wo").eq(1).addClass("menu_son_current");
	$(".menu_son_ul").addClass("none").removeClass("block");
	$(".menu_son_ul").eq(1).addClass("block").removeClass("none");

	$(".menu_span_wo").last().children("i").addClass("icon-jiantou-copy-copy-copy").removeClass("icon-jiantou-copy-copy");
	$(".menu_span_wo").first().children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	$(".menu_span_guan").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");

	$(".menu_son_ul_Three li").eq(1).addClass("menu_current").siblings().removeClass("menu_current");


	//历史任务导出excel
	$(".agv_button_add").click(function() {
		var startTime = $("#c1").val();
		var endTime = $("#c2").val();
        var taskType = $("#search_type").val();
        var taskState = $("#zhi_state").val();
        if(taskState=="完成"){
            taskState="3"
        }else if(taskState=="删除"){
            taskState="4"
        }else if(taskState=="取消"){
            taskState="5"
        }
		window.location = "../excel/taskLog?state=3,4,5" +
            "&taskType="+taskType+
            "&taskState="+taskState+
			"&startDate=" + (startTime ? startTime : null) +
			"&endDate=" + (endTime ? endTime : null);
	})
	
	//条件搜索	搜索双击后选择日期清空
	var Retimer = null;
	$(".search_box").dblclick(function () {
	  clearTimeout(Retimer);
		$("#c1").val("");
		$("#c2").val("");
	})
	//条件搜索	
	$(".search_box").click(function () {
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
			refreshTaskTable();//刷新table列表
	   }, 300);         
	})
	

})


//历史任务列表====================================
function taskTable() {
    $("#taskTable").bootstrapTable({
        toolbar: '#toolbarAdd', //工具按钮用哪个容器
        url: '../TaskInfo/historyTaskQuery', //请求后台的URL（*）
        method: 'POST',
        striped: false, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, //是否显示分页（*）
        sortable: false, //是否启用排序
        sortOrder: "asc", //排序方式
        queryParams: queryParams = function(params) {
            var startTime = $("#c1").val();
            var endTime = $("#c2").val();
            var taskType = $("#search_type").val();
            var taskState = $("#zhi_state").val();
            return {
                "page": params.offset / params.limit + 1,
                "count": params.limit,
                "state":"3,4,5",
                "type": taskType ? taskType : null,
                "taskState": taskState ? taskState : null,
                "date": startTime ? startTime : null,
                "endTime": endTime ? endTime : null
            };


        }, //传递参数（*）
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 10, //每页的记录行数（*）
        pageList: [10], //可供选择的每页的行数（*）
        contentType: "application/x-www-form-urlencoded",
        strictSearch: true,
        undefinedText: "",
        showColumns: false, //是否显示所有的列
        showRefresh: false, //是否显示刷新按钮
        minimumCountColumns: 2, //最少允许的列数
        queryParamsType: 'limit', //查询参数组织方式
        uniqueId: "no", //每一行的唯一标识，一般为主键列
        columns: [
            {
                title: 'AGV编号',
                field: 'agvCode',
                cellStyle: formatTableT,
                formatter: function(value, row, index) {
                    var html = '<input disabled ="disabled" style="width:60px;" type="text" class="agv_table_input agvCode" value="' + (row.agvCode ? row.agvCode : "") + '"></input>'
                    return html;
                }
            },
            {
                title: '任务名称',
                field: 'taskName',
                cellStyle: formatTableT,
                formatter: function(value, row, index) {
                    var html = '<a disabled ="disabled" class="agv_table_input"  href="historySubTaskInfo.html?id='+row.id+'"  >'
                        + (row.taskName ? row.taskName : "") + '</a >'
                    return html;
                }
            },
            {
                title: '任务类型',
                field: 'taskType',
                cellStyle: formatTableT,
                formatter: function(value, row, index) {
                    var html = '<input disabled ="disabled" type="text" class="agv_table_input taskType"  value="' + (row.taskType ? row.taskType : "") + '"></input>'
                    return html;

                }
            },
            {
                title: '任务频率',
                field: 'frequency',
                cellStyle: formatTableT,
                formatter: function(value, row, index) {
                    var html = '<input disabled ="disabled" style="width:60px;" type="text" class="agv_table_input frequency"  value="' + (row.frequency ? row.frequency : "") + '"></input>'
                    return html;

                }
            },
            {
                title: '完成次数',
                field: 'finishCount',
                cellStyle: formatTableT,
                formatter: function(value, row, index) {
                    var html = '<input disabled ="disabled" style="width:60px;" type="text" class="agv_table_input finishCount"  value="' + (row.finishCount ? row.finishCount : "") + '"></input>'
                    return html;

                }
            },
            {
                title: '任务状态',
                field: 'state',
                cellStyle: formatTableT,
                formatter: function(value, row, index) {
                    var str;
                    if(row.state=="3"){
                        str="完成"
                    }else if(row.state=="4"){
                        str="删除"
                    }else if(row.state=="5"){
                        str="取消"
                    }
                    var html = '<input disabled ="disabled" style="width:60px;" type="text" class="agv_table_input state"  value="' + str + '"></input>'
                    return html;

                }
            },
            {
                title: '创建时间',
                field: 'createDate',
                cellStyle: formatTableT,
                formatter: function(value, row, index) {
                    var html = '<input disabled ="disabled" type="text" class="agv_table_input" value="' + (row.createDate ? row.createDate : "") + '"></input>'
                    return html;

                }
            }
        ],

    });

}


//样式方法封装
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

function refreshTaskTable() {
	$('#taskTable').bootstrapTable("refreshOptions", { pageNumber: 1 });
//	$('#taskTable').bootstrapTable('refresh');
}