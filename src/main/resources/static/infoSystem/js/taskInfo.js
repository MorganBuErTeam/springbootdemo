var n = readCookie("CookieType");
//判断登录角色，1管理员，有导出权限，2用户，无导出权限
var id

function getLimt() {
    if (n == "1") {
        $(".excelBtn").addClass("block").removeClass("none");
    } else if (n == "2") {
        $(".excelBtn").addClass("none").removeClass("block");
    }
    //  else{
    //  	$(".excelBtn").addClass("none").removeClass("block");
    //  }
}

function fndateTime() {
    J('#txtclock').calendar({format: 'yyyy-MM-dd HH:mm:ss'});
}

var dataSit = new Array();


function uptr(obj) {
    var $tr = $(obj).parents("tr");
    if ($tr.index() == 0) {
        publicTipMessage("error", "首行数据不可上移");
        return;
    }
    var nowRow = dataSit[$tr.index()];//当前行
    dataSit[$tr.index()] = dataSit[$tr.index() - 1];
    dataSit[$tr.index() - 1] = nowRow;
    $tr.fadeOut().fadeIn();
    $tr.prev().before($tr);
}

function downtr(obj) {
    var $tr = $(obj).parents("tr");
    if ($tr.index() == $(obj).parents("tbody").find("tr").length - 1) {
        publicTipMessage("error", "尾行数据不可下移");
        return;
    }
    var nowRow = dataSit[$tr.index()];//当前行
    dataSit[$tr.index()] = dataSit[$tr.index() + 1];
    dataSit[$tr.index() + 1] = nowRow;
    $tr.fadeOut().fadeIn();
    $tr.next().after($tr);
}

$(function () {
    getLimt(); //初始化权限
    nowTaskTable(); //加载当前任务列表
    sonTaskTable();//加载当前子任务列表
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

    //新增
    $(".add_button").click(function () {
        $("#agvLineId").val("");// 清空隐藏域id
        $("#AGV_add").modal({
            backdrop: false,
        });
        $('#AGV_addForm').bootstrapValidator('resetForm', true);
        $(".message_add_title").text("AGV任务设置");
    });

    //大任务取消
    $(document).off("click", ".task_complete").on("click", ".task_complete", function () {
        $("#box_fun").modal({
            backdrop: false,
        });
        debugger
        id = $(this).attr("dataNo");
        $("#funIdR").val("1");//大任务标识
        $(".zhezhao_delect_jing").text("一旦取消不可恢复");
        $(".zhezhao_delect_text").text("你确定要取消吗？");
    })

    //子任务复位
    $(document).off("click", ".task_fu").on("click", ".task_fu", function () {
        $("#box_fun").modal({
            backdrop: false,
        });
        debugger
        id = $(this).attr("dataNo");
        $("#funIdR").val("2");//大任务标识
        $(".zhezhao_delect_jing").text("一旦复位不可恢复");
        $(".zhezhao_delect_text").text("你确定要复位吗？");
    })

    //子任务强制
    $(document).off("click", ".task_qiang").on("click", ".task_qiang", function () {
        $("#box_fun").modal({
            backdrop: false,
        });
        debugger
        id = $(this).attr("dataNo");
        $("#funIdR").val("3");//大任务标识
        $(".zhezhao_delect_jing").text("一旦强制不可恢复");
        $(".zhezhao_delect_text").text("你确定要强制吗？");
    })

    //确认任务
    $(".zhezhao_delect_affir").click(function () {
        $('#box_fun').modal('hide');
        if ($("#funIdR").val() == "1") {//大任务取消
            $.post('../TaskInfo/updateTaskState', {"id": id, "state": 5}, function (result) {
                if (result.code == "200") {
                    refreshNow(); //刷新table列表
                    publicTipMessage("ok", result.desc);
                } else {
                    publicTipMessage("error", result.desc);
                }
            })
        } else if ($("#funIdR").val() == "2") {//子任务复位
            $.post('../subTaskInfo/updateSubTaskState', {"id": id, "state": 6}, function (result) {
                if (result.code == "200") {
                    refreshSon(); //刷新table列表
                    publicTipMessage("ok", result.desc);
                } else {
                    publicTipMessage("error", result.desc);
                }
            })
        } else if ($("#funIdR").val() == "3") {//子任务强制
            $.post('../subTaskInfo/updateSubTaskState', {"id": id, "state": 5}, function (result) {
                if (result.code == "200") {
                    refreshSon(); //刷新table列表
                    publicTipMessage("ok", result.desc);
                } else {
                    publicTipMessage("error", result.desc);
                }
            })
        }

    });

    //取消完成任务
    $(".zhezhao_delect_cancel").click(function () {
        $('#box_fun').modal('hide');
    })

    //上移
    $("#nowTaskTable").off("click", ".move_up").on("click", ".move_up", function () {//上移
        var index = $(this).attr("dataIndex");
        if (index == 0) {
            publicTipMessage("error", "首行不能再上移！");
            return;
        }
        var id = $(this).attr("dataNo"), sort = $(this).attr("dataSort");
        $.post('../TaskInfo/updateUpOrDown', {"type": 1, "id": id, "sort": sort}, function (result) {
            debugger
            if (result.code == "200") {
                refreshNow();//刷新table列表
                publicTipMessage("ok", result.desc);
            }
        })
    })
    //下移
    $("#nowTaskTable").off("click", ".move_down").on("click", ".move_down", function () {//下移
        var index = $(this).attr("dataIndex");
        if ((parseInt(index) + 1) == $("#nowTaskTable").find(" tbody tr").length) {
            publicTipMessage("error", "尾行不能再下移！");
            return;
        }
        var id = $(this).attr("dataNo"), sort = $(this).attr("dataSort");
        $.post('../TaskInfo/updateUpOrDown', {"type": 2, "id": id, "sort": sort}, function (result) {
            debugger
            if (result.code == "200") {
                refreshNow();//刷新table列表
                publicTipMessage("ok", result.desc);
            }
        })
    })

    //循环
    $('#mycheckbox6').click(function () {
        $("#mycheckbox6").prop("checked", true);
        $("#mycheckbox7").prop("checked", false);
        $("#mycheckbox8").prop("checked", false);
    })

    //单次
    $('#mycheckbox7').click(function () {
        $("#mycheckbox7").prop("checked", true);
        $("#mycheckbox6").prop("checked", false);
        $("#mycheckbox8").prop("checked", false);
    })

    //自定义
    $('#mycheckbox8').click(function () {
        $("#mycheckbox8").prop("checked", true);
        $("#mycheckbox6").prop("checked", false);
        $("#mycheckbox7").prop("checked", false);
    })


    // 新增验证
    $('#AGV_addForm').bootstrapValidator({
        container: 'tooltip',
        // trigger: 'blur',
        feedbackIcons: {
            valid: 'iconfont icon-v',
            invalid: 'iconfont icon-x',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            taskType: {
                validators: {
                    notEmpty: {
                        message: '内容不能为空!'
                    },
                }
            },
            startStop: {
                validators: {
                    notEmpty: {
                        message: '内容不能为空!'
                    },
// regexp: {
// regexp:/^[1-9]\d*$/,
// message: '只能输入数字且第一位不能为0!'
// },
                }
            },
            taskName: {
                validators: {
                    notEmpty: {
                        message: '内容不能为空!'
                    },
                }
            },
            endPoint: {
                validators: {
                    notEmpty: {
                        message: '内容不能为空!'
                    },
                }
            },
            finishNum: {
                validators: {
//					notEmpty : {
//						message : '内容不能为空!'
//					},
                    regexp: {
                        regexp: /^[1-9]\d*$/,
                        message: '只能输入数字且第一位不能为0!'
                    },
                }
            },
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        var url = "";
        if ($(".message_add_title").text() == "1号AGV任务设置") {
            var id = '1';
        } else if ($(".message_add_title").text() == "2号AGV任务设置") {
            var id = '2';
        }
        var str;
        if ($("#mycheckbox6").is(":checked")) {
            str = 1;
        } else if ($("#mycheckbox7").is(":checked")) {
            str = 2;
        } else if ($("#mycheckbox8").is(":checked")) {
            str = 3;
        }
        var agv = {
            "taskType": $("#taskType").val(),
            "taskName": $("#taskName").val(),
            "startSitCode": $("#startStop option:selected").val(),
            "targetSitCode": $("#endPoint option:selected").val(),
            "frequency": $(".checkbix").val(),//任务频率
            "count": $("#finishNum").val(), //任务次数
            "id": id

        }
        var id = $("#agvLineId").val();
        url = "../TaskInfo/save";
        debugger
        $.ajax({
            url: url,
            data: JSON.stringify(agv),
            contentType: "application/json;charset=UTF-8",
            type: "POST",
            success: function (result) {
                if (result.code == "200") {
                    $("#AGV_add").modal("hide");
                    $("#AGV_addForm").bootstrapValidator("resetForm", true);// 清空验证痕迹和内容
                    publicTipMessage("ok", "操作成功");
                    refreshNow();
                    refreshNow1();
                    return;
                }
                publicTipMessage("error", result.desc);
            }
        });
    });

    // 关闭模态框时，清空内容和验证痕迹
    $(".agv_close_form").click(function () {
        $("#AGV_addForm").bootstrapValidator("resetForm", true);// 清空验证痕迹和内容
    });

    //条件搜索
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
            if ($("#c1").val() == "" && $("#c2").val() !== "") {
                publicTipMessage("error", "请选择开始时间！");
                return;
            } else if ($("#c2").val() == "" && $("#c1").val() !== "") {
                publicTipMessage("error", "请选择结束时间！");
                return;
            }
            refreshNow();//刷新table列表
        }, 300);
    })

})

//当前任务列表====================================
function nowTaskTable() {
    $("#nowTaskTable").bootstrapTable({
        toolbar: '#toolbarAdd', //工具按钮用哪个容器
        url: '../TaskInfo/taskQuery', //请求后台的URL（*）
        method: 'POST',
        striped: false, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false, //是否显示分页（*）
        sortable: false, //是否启用排序
        sortOrder: "asc", //排序方式
        queryParams: queryParams = function (params) {
            var type = $("#search_type").val();
            var startTime = $("#c1").val();
            var endTime = $("#c2").val();
            return {
                "state": "1,2",
                "type": type,
                "date": startTime ? startTime : null,
                "endTime": endTime ? endTime : null
            };
        }, //传递参数（*）
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 2, //每页的记录行数（*）
        pageList: [2], //可供选择的每页的行数（*）
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
                formatter: function (value, row, index) {
                    var html = '<input disabled ="disabled" style="width:60px;" type="text" class="agv_table_input agvCode" value="' + (row.agvCode ? row.agvCode : "") + '"></input>'
                    return html;
                }
            },
            {
                title: '任务名称',
                field: 'taskName',
                cellStyle: formatTableT,
                formatter: function (value, row, index) {
                    var html = '<input disabled ="disabled" type="text" class="agv_table_input" value="' + (row.taskName ? row.taskName : "") + '"></input>'
                    return html;
                }
            },
            {
                title: '任务类型',
                field: 'taskType',
                cellStyle: formatTableT,
                formatter: function (value, row, index) {
                    var html = '<input disabled ="disabled" type="text" class="agv_table_input taskType"  value="' + (row.taskType ? row.taskType : "") + '"></input>'
                    return html;

                }
            },
            {
                title: '任务频率',
                field: 'frequency',
                cellStyle: formatTableT,
                formatter: function (value, row, index) {
                    var html = '<input disabled ="disabled" style="width:60px;" type="text" class="agv_table_input frequency"  value="' + (row.frequency ? row.frequency : "") + '"></input>'
                    return html;

                }
            },
            {
                title: '完成次数',
                field: 'finishCount',
                cellStyle: formatTableT,
                formatter: function (value, row, index) {
                    var html = '<input disabled ="disabled" style="width:60px;" type="text" class="agv_table_input finishCount"  value="' + (row.finishCount ? row.finishCount : "") + '"></input>'
                    return html;

                }
            },
            {
                title: '任务状态',
                field: 'state',
                cellStyle: formatTableT,
                formatter: function (value, row, index) {
                    var str;
                    if (row.state == "1") {
                        str = "未执行"
                    } else if (row.state == "2") {
                        str = "执行中"
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
                    var html = '<input disabled ="disabled" type="text" class="agv_table_input" value="' + (row.createDate ? row.createDate : "") + '"></input>'
                    return html;

                }
            }, {
                field: 'caozuo',
                title: "操作",
                width: 100,
                cellStyle: formatTableT,
                formatter: function (value, row, index) {
                    html = '<p style="border:0;" class="fl config_disable cursor mr10 fc_0 task_complete" dataNo="' + row.id + '"><img src="../../img/close1.png" style="margin-top:-2px;" class="mr5 path_refer_img"/>取消</p>';
                    return html;
                }
            }
        ],

    });

}

function refreshNow() {
//	$('#nowTaskTable').bootstrapTable('refresh');
    $('#nowTaskTable').bootstrapTable("refreshOptions", {pageNumber: 1});

}

//样式方法封装
function formatTableT(value, row, index) {
    return {
        css: {
            "border": "0",
            "background": "none",
            "color": "red",
            "border": "0",
            "border-right": "1px solid #E6E6E6",
            "font-size": "14px",
            "color": "#858C9E",
            "border-radius": "0",
            "text-align": "center"
        }
    }
}

function formatTableT2(value, row, index) {
    return {
        css: {
            "border": "0",
            "background": "none",
            "color": "red",
            "border": "0",
            "border-right": "1px solid #E6E6E6",
            "font-size": "14px",
            "color": "#858C9E",
            "padding-left": "18px",
            "border-radius": "0",
            "text-align": "center"
        }
    }
}


//==============子任务列表========================
function sonTaskTable() {
    $("#sonTaskTable").bootstrapTable({
        toolbar: '#toolbarAdd', //工具按钮用哪个容器
        url: '../subTaskInfo/taskQuery', //请求后台的URL（*）
        method: 'POST',
        striped: false, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false, //是否显示分页（*）
        sortable: false, //是否启用排序
        sortOrder: "asc", //排序方式
        queryParams: queryParams = function (params) {
            return {
                "type": "1,2"
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
                    if (row.state == "1") {
                        str = "未执行"
                    } else if (row.state == "2") {
                        str = "执行中"
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
            }, {
                field: 'caozuo',
                title: "操作",
                width: 200,
                cellStyle: formatTableT,
                formatter: function (value, row, index) {
                    var html = '<p class="fl config_disable cursor mr10 fc_33 task_fu" dataNo="' + row.id + '"><img src="../../img/fw.png" class="mr5 path_refer_img"/>复位</p>';
                    html += '<p style="border:0;padding-right:0;" class="fl config_disable cursor mr10 fc_33 task_qiang" dataNo="' + row.id + '"><img src="../../img/qz.png" class="mr5 path_refer_img"/>强制</p>';
                    return html;
                }
            }
        ],

    });

}

function refreshSon() {
//	$('#nowTaskTable').bootstrapTable('refresh');
    $('#sonTaskTable').bootstrapTable("refreshOptions", {pageNumber: 1});

}
