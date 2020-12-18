// 获取url参数
function GetRequest() {
  var url = location.search; // 获取url中"?"符后的字串
  var theRequest = new Object();
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}

$(function(){
    TableInit1();
    $("#staInfo_table").removeClass("table-striped");
    var oTable = new TableInit1();
    oTable.Init();
    $(".fixed-table-pagination").show();
    $(".fixed-table-pagination .pagination").show();

    //  子菜单栏点击效果
    $("#li3").addClass("menu_current").siblings().find("menu_current").removeClass("menu_current");
    $("#li3").prevAll().find("ul").removeClass("block").addClass("none");
    $("#li3").prevAll().find("span").removeClass("menu_son_current");
    
//    $(".menu_span_wo").last().children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
//	$(".menu_span_wo").first().children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
    

    	$(".menu_span_wo").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");

    $('.sta_find_btn').click(function(){
    	refresh();
    	$("#sta_date").val("");
    });
});

//table表格
var TableInit1 = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#staInfo_table').bootstrapTable({
        	toolbar: '#toolbarAdd', //工具按钮用哪个容器   
            url: '../subTaskInfo/query',         //请求后台的URL（*）
            method: 'POST',
    		striped: false, //是否显示行间隔色
    		cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
    		pagination: true, //是否显示分页（*）
    		sortable: false, //是否启用排序
    		sortOrder: "asc", //排序方式                    //是否启用排序
		    queryParams:queryParams = function queryParams(params){
		    	debugger
		        return{
                    "page": params.offset/params.limit+1,
                    "count": params.limit,
                    "id":GetRequest().id
		        }
		    }, /*queryParamss,*///传递参数（*）
		    sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
			pageNumber: 1, //初始化加载第一页，默认第一页
			pageSize: 10, //每页的记录行数（*）
			pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
			contentType: "application/x-www-form-urlencoded",
			strictSearch: true,
			showColumns: false, //是否显示所有的列
			showRefresh: false, //是否显示刷新按钮
			minimumCountColumns: 2, //最少允许的列数
			queryParamsType:'limit',//查询参数组织方式
			uniqueId: "no", //每一行的唯一标识，一般为主键列
            columns: [
                {
                    title: 'AGV编号',
                    field: 'agvCode',
                    cellStyle: formatTableT,
                    formatter: function (value, row, index) {
                        var html = '<input disabled ="disabled" style="width:60px;" type="text" class="agv_table_input agvCode" value="' + (row.agvCode ? row.agvCode : "") + '"></input>'
                        return html;
                    }
                },
                {
                    title: '任务编号',
                    field: 'taskId',
                    cellStyle: formatTableT,
                    formatter: function (value, row, index) {
                        var html = '<input disabled ="disabled" type="text" class="agv_table_input" value="' + (row.taskId ? row.taskId : "") + '"></input>'
                        return html;
                    }
                },
                {
                    title: '任务类型',
                    field: 'taskType',
                    cellStyle: formatTableT,
                    formatter: function (value, row, index) {
                        var html = '<input disabled ="disabled" style="width:105px;" type="text" class="agv_table_input taskType"  value="' + (row.type ? row.type : "") + '"></input>'
                        return html;

                    }
                },
                {
                    title: '任务信息',
                    field: 'taskInfo',
                    cellStyle: formatTableT,
                    formatter: function (value, row, index) {
                        var html = '<input disabled ="disabled" style="width:60px;" type="text" class="agv_table_input taskInfo"  value="' + (row.taskInfo ? row.taskInfo : "无") + '"></input>'
                        return html;

                    }
                },
                {
                    title: '起始停靠点',
                    field: 'startSite',
                    cellStyle: formatTableT,
                    formatter: function (value, row, index) {
                        //				var html = '<span class="agv_table_input">'+(row.startSite?row.startSite:"")+'</span>'
                        var html = '<input disabled ="disabled" type="text" class="agv_table_input" value="' + (row.pickSitCode ? row.pickSitCode : "") + '"></input>'
                        return html;
                    }
                }, {
                    title: '终止停靠点',
                    field: 'endSite',
                    cellStyle: formatTableT,
                    formatter: function (value, row, index) {
                        //				var html = '<span class="agv_table_input">'+(row.endSite?row.endSite:"")+'</span>'
                        var html = '<input disabled ="disabled" type="text" class="agv_table_input" value="' + (row.putSitCode ? row.putSitCode : "") + '"></input>'
                        return html;
                    }
                },

                {
                    title: '任务状态',
                    field: 'state',
                    cellStyle: formatTableT,
                    formatter: function (value, row, index) {
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
                    formatter: function (value, row, index) {
                        var html = '<input disabled ="disabled" style="width:140px;" type="text" class="agv_table_input" value="' + (row.createDate ? row.createDate : "") + '"></input>'
                        return html;

                    }
                }
            ],
        });
        
        

    };


    //得到查询的参数
    oTableInit.queryParams = function () {
        var params={
            "workStatue":0
        };
        return params;
    };
    return oTableInit;
};
//请求服务数据时所传参数


//刷新按钮
function refresh() {  
	$('#staInfo_table').bootstrapTable('refresh');  
} 
//tableHeight函数
function tableHeight(){
    //可以根据自己页面情况进行调整
    return $(window).height() -280;
}

//样式方法封装
function formatTableT(value, row, index) {
    return {
        css: {
            "background":"none",
            "border":"0",
            "border-bottom":" 1px solid #ececec",
            "font-size": "14px",
            "color": "#9299A9",
            "padding-left": "20px",
            "height": "44px",
            "padding-top": "14px"
        }
    }
}