$(function(){
	avoidTable();//加载列表
	$(".fixed-table-pagination").show();
	$(".fixed-table-pagination .pagination").show();
	//  子菜单栏点击效果
	$(".menu_son_ul li").eq(1).addClass("menu_current").siblings().removeClass("menu_current");
	$(".menu_span_wo").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	$(".menu_span_wo").first().children("i").addClass("icon-jiantou-copy-copy-copy").removeClass("icon-jiantou-copy-copy");
	$(".menu_span_guan").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
	
	$(".cang_ul_li").addClass("block").removeClass("none");//仓储管理
	$(".systemNavUl li").removeClass("currentLI");
	$(".systemNavUl li p").removeClass("fc_37").addClass("fc_f");
	$(".cang_li img").attr("src","../img/cdh_cc_wdj.png");//仓库
	$(".shi_li img").attr("src","../img/cdh_yxt_wdj.png");//运行图
	
	$(".systemNavUl li").eq(1).addClass("currentLI").siblings().removeClass("currentLI");
	$(".cang_li p").addClass("fc_37");
	$(".cang_li img").attr("src","../img/cdh_cc_dj.png");
	
	$(".avoid_nav h5").click(function() {
		$(this).addClass("title_button_current").siblings().removeClass("title_button_current");
	});
	
//	删除弹框显示
	$(".avoid_button_delete").click(function(){
		var agvIdList = $("input[type='checkbox']:checked");

		if(agvIdList.length == 0){
			publicTipMessage("error","请选择删除的内容！");
			return;
		}
		$("#box_fun").modal({
			backdrop: false,
		});
		$(".zhezhao_delect_text").text("你确定要删除吗？");
		$(".zhezhao_delect_jing").text("一旦删除不可恢复");
		$("#funIdR").val("1");
	});
	
 //	取消删除
	$(".zhezhao_delect_cancel").click(function(){
		$("#box_fun").modal('hide');
	});

    //同步
    $(".button_tong").click(function(){
        $.post("../erpBasicData/syncErpMateriel",function(result){
            if(result.code == "200"){
                debugger
                refresh();//刷新table列表
                publicTipMessage("ok",result.desc);
            }else{
                publicTipMessage("error",result.desc);
            }
        })
    })

	//导入
    $(".button_dao").click(function(){
        $(".zhezhao_dao_box1").modal({
            backdrop : false,
        });
    })

    //取消导入站点配置文件
    $(".zhezhao_dao_cancel1").click(function(){
        $(".zhezhao_dao_box1").modal("hide");
    });

    //关闭导入站点配置文件daoClose
    $(".daoClose1").click(function(){
        $(".zhezhao_dao_box1").modal("hide");
    });

    //确认导入文件
    $("#submitTxt").submit(function(){
        $(this).ajaxSubmit({
            success: function(result) { // data 保存提交后返回的数据，一般为 json 数据
                if (result.code == "200") {
                    $("#avoidTable").bootstrapTable('destroy');
                    avoidTable();
                    publicTipMessage("ok", result.desc);
                    $(".zhezhao_dao_box1").modal("hide");

                }else{
                    publicTipMessage("error", result.desc);
                    $(".zhezhao_dao_box1").modal("hide");
                }
            }
        });

        return false;
    });
	
//  一键上架
	$(".button_put").click(function(){
		var agvIdList = $("input[type='checkbox']:checked");

		if(agvIdList.length == 0){
			publicTipMessage("error","请选择上架物料！");
			return;
		}
		$("#box_fun").modal({
			backdrop: false,
		});
		$(".zhezhao_delect_text").text("你确定要上架吗？");
		$(".zhezhao_delect_jing").text("一旦上架不可恢复");
		$("#funIdR").val("2");
	});
	
	//  一键下架
	$(".button_out").click(function(){
		var agvIdList = $("input[type='checkbox']:checked");

		if(agvIdList.length == 0){
			publicTipMessage("error","请选择下架物料！");
			return;
		}
		$("#box_fun").modal({
			backdrop: false,
		});
		$(".zhezhao_delect_text").text("你确定要下架吗？");
		$(".zhezhao_delect_jing").text("一旦下架不可恢复");
		$("#funIdR").val("3");
	});
	
		
//	新建
	$(".avoid_button_add").click(function () {
		$("#avoidId").val("");//清空隐藏域id
		$("#AGV_add").modal({
			backdrop : false,
		});
		$('#AGV_addForm').bootstrapValidator('resetForm', true);
		$(".message_add_title").text("新增");
    });
	
	
	//取消新增putIn_btn_T
	$("#AGV_add_cancel").click(function(){
		$('#AGV_addForm').bootstrapValidator('resetForm', false);
	});
	
	
//	删除、一键上架、一键下架确认
	$(".zhezhao_delect_affir").click(function(){
		$("#box_fun").modal('hide');
		debugger
		var aovidIdArray = new Array();
		var aovidIdList = $("input[type='checkbox']:checked");
		for (var i = 0; i < aovidIdList.length; i++) {
			aovidIdArray[i] = $(aovidIdList[i]).parent().siblings().find(".config_disable").attr("dataNo");
		}
		var state;
		if($("#funIdR").val() == "1"){//删除
			var url ='../materiel/delByIds';
		}else if($("#funIdR").val() == "2"){//上架
			var url ='../materiel/onoff';
			state="1";
		}else if($("#funIdR").val() == "3"){//下架
			var url ='../materiel/onoff';
            state="2";
		}
		debugger
        $.post(url,{"ids":aovidIdArray.join(","),"state":state},function(result){
            if(result.code == "200"){
                debugger
                refresh();//刷新table列表
                publicTipMessage("ok",result.desc);
            }else{
                publicTipMessage("error",result.desc);
            }
        })
	});
	
	//条件搜索
	$(".search_box").click(function(){
		refresh();//刷新table列表
	}); 
	
	
	//新增区位验证
	$('#AGV_addForm').bootstrapValidator({
		container: 'tooltip',
		//trigger: 'blur',
		feedbackIcons: {
			valid: 'iconfont icon-v',
            invalid: 'iconfont icon-x',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			//条码
			barCode: {
				threshold : 1 ,
				validators : {
					notEmpty : {
						message : '条码不能为空！'
					},
					regexp: {
						regexp:/^[1-9]\d{0,30}$/,
						message: '只能输入数字且不能以0开头!'
					},
				}
			},
			//物料名
			materialName:{
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
			//规格型号
			model: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
			//基本计量单位
			unit: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'///[0-9]{1,20}$/
					},
				}
			},
			//储位
			storageLocation: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
			//重量/KG
			weight: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
			//物料号
			materialCode: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
			//全名
			nameAll: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
			//物料属性
			attribute: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
			//默认仓库
			warehouse: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
			//满箱数量
			fullNum: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
			//频次
			Hz: {
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
			kuType:{
				validators : {
					notEmpty : {
						message : '内容不能为空!'
					},
				}
			},
		}
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		debugger
		var url = "";
		var avoid = {
				"barCode":$("#barCode").val(),//条码
				"materielName":$("#materialName").val(),//物料名
				"model":$("#model").val(),//规格型号
				"measmSheet":$("#unit").val(),//基本计量单位
				"binLocation":$("#storageLocation").val(),//储位
				"weight":$("#weight").val(),//重量
            	"materielNo":$("#materialCode").val(),//物料号
				"materielNames":$("#nameAll").val(),//全名
				"attribute":$("#attribute").val(),//物料属性
				"defaultWarehouse":$("#warehouse").val(),//默认仓库
				"fullboxCount":$("#fullNum").val(),//满箱数量
            	"safetyNumber":$("#safetyNumber").val(),//库存安全数
				"frequency":$("#Hz").val(),//频次
				"areaType":$("#kuType").val()

		}
		var id = $("#avoidId").val();
		if (!id && $(".message_add_title").text()=="新增") {
			url = "../materiel/save";
			avoid["createUser"] = 2;//当前登录用户账号
		} else {	
			//修改
			url = "../materiel/updateById";
			avoid["updateUser"] = 2;//当前登录用户账号
			avoid["id"] = parseInt(id);//避让id
			
		}
		$.ajax({
			url : url,
			data : JSON.stringify(avoid),
			contentType : "application/json;charset=UTF-8",
			type : "POST",
			success : function(result) {
				if(result.code == "200"){
					$("#AGV_add").modal("hide");
					refresh();//刷新table列表
					$("#AGV_addForm").bootstrapValidator("resetForm",true);//清空验证痕迹和内容
					publicTipMessage("ok",result.desc);
					return;
				}
				publicTipMessage("error",result.desc);
			}
		});
	});

	//关闭模态框时，清空内容和验证痕迹
	$(".agv_close_form").click(function(){
		$("#AGV_addForm").bootstrapValidator("resetForm",true);//清空验证痕迹和内容
	})
	
	//取消模态框
	$("#AGV_add_cancel").click(function(){
		$("#AGV_addForm").bootstrapValidator("resetForm",true);//清空验证痕迹和内容
	})
	
	
	//关闭详情
    $(".DetailsX").click(function(){
		$('.taskDetailsBox').addClass("none").removeClass("block");
	})
    
	

});

//--------------------------------------table---------------------------
//表格table
function avoidTable(){
	debugger
	$("#avoidTable").bootstrapTable({  
		toolbar: '#toolbarAdd', //工具按钮用哪个容器   
		url:'../materiel/query',//请求后台的URL（*）
		method: 'POST',
		striped: false, //是否显示行间隔色
		cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: true, //是否显示分页（*）
		sortable: false, //是否启用排序
		sortOrder: "asc", //排序方式
		queryParams: queryParams = function(params) {
			var code = $(".avoid_search_code").val();//物料号
			var name = $(".avoid_search_name").val();//物料名
			var type = $(".avoid_search_type").val();//库区类型
        return {
        	  "page":params.offset/params.limit+1,
        	  "count": params.limit,
        	  "id":code?code:null,
			   "type":type?type:null,
			  "name":name?name:null
        	};
		}, //传递参数（*） 
		sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
		pageNumber: 1, //初始化加载第一页，默认第一页
		pageSize: 10, //每页的记录行数（*）
		pageList: [10], //可供选择的每页的行数（*）
		contentType: "application/x-www-form-urlencoded",
		strictSearch: true,
		undefinedText:"",
		showColumns: false, //是否显示所有的列
		showRefresh: false, //是否显示刷新按钮
		minimumCountColumns: 2, //最少允许的列数
		queryParamsType:'limit',//查询参数组织方式
		uniqueId: "no", //每一行的唯一标识，一般为主键列
		// data:[{"materialCode": 1,"materialName": "油箱盖...","model" : "PA6+3...1222","attribute":"自制","unit":"PCS","warehouse":"配件库...","storageLocation":"33020103","state":"下架"}],

		columns : [ {
			checkbox: (n == "2")?false:true, // 显示一个勾选框
			align: 'center' // 居中显示
		
		}, 
		{
			title: '序号',
			field: 'CabinetPosition',
			width:80,
			cellStyle: formatTableT,
			formatter: function(value, row, index) {
				var html = '<input disabled ="disabled" style="width:80px;" type="text" class="agv_table_input CabinetPosition" value="' + (index + 1) + '"></input>'
				html += '<input type="hidden" class="barCode" value="'+row.barCode+'"/>'
				html += '<input type="hidden" class="fullNum" value="'+row.fullboxCount+'"/>'
				html += '<input type="hidden" class="weight" value="'+row.weight+'"/>'
				html += '<input type="hidden" class="Hz" value="'+row.frequency+'"/>'
				return html;
			}
		},
		{
			title : '物料号',  
			field : 'materialCode', 
			cellStyle:formatTableT,
			formatter : function(value, row, index){ 
				var html = '<a disabled ="disabled" type="text" class="agv_table_input materialCode cursor"  dataNo="'+row.id+'" title="'+row.materielNo+'">'+row.materielNo+'</a>'
				return html;	
			}
		}, {  
			title : '物料名',  
			field : 'materialName',
			cellStyle:formatTableT,
			formatter:function(value,row,index){
				var html = '<input disabled ="disabled" type="text" class="agv_table_input materialName" title="'+row.materielNames+'" value="'+row.materielName+'" ></input>'
					html += '<input type="hidden" class="nameAll" value="'+row.materielNames+'"/>'
					return html;
				
			}
		}, 
		{
			title : '规格型号',  
			field : 'model', 
			cellStyle:formatTableT,
			formatter : function(value, row, index){ 
				var html = '<input disabled ="disabled" type="text" class="agv_table_input model" title="'+(row.model?row.model:"")+'" value="'+(row.model?row.model:"")+'" ></input>'
				return html;
			}

		},
		{
			title : '物料属性',  
			field : 'attribute', 
			cellStyle:formatTableT,
			formatter : function(value, row, index){ 
				var html = '<input disabled ="disabled" type="text" style="width:80px;" class="agv_table_input attribute" value="'+(row.attribute?row.attribute:"")+'" ></input>'
				return html;
			}

		},
		{
			title : '基本计量单位',  
			field : 'unit', 
			cellStyle:formatTableT,
			formatter : function(value, row, index){ 
				var html = '<input disabled ="disabled" type="text" style="width:80px;" class="agv_table_input unit" value="'+(row.measmSheet?row.measmSheet:"")+'" ></input>'
				return html;
			}
		},
		// {
		// 	title : '默认仓库',
		// 	field : 'warehouse',
		// 	cellStyle:formatTableT,
		// 	formatter : function(value, row, index){
		// 		var html = '<input disabled ="disabled" type="text" class="agv_table_input warehouse" title="'+(row.defaultWarehouse?row.defaultWarehouse:"")+'" value="'+(row.defaultWarehouse?row.defaultWarehouse:"")+'" ></input>'
		// 		return html;
		// 	}
        //
		// },
		// {
		// 	title : '储位',
		// 	field : 'storageLocation',
		// 	cellStyle:formatTableT,
		// 	formatter : function(value, row, index){
		// 		var html = '<input disabled ="disabled" type="text" style="width:80px;" class="agv_table_input storageLocation" value="'+(row.binLocation?row.binLocation:"")+'" ></input>'
		// 		return html;
		// 	}
        //
		// },
		{
			title : '状态',  
			field : 'state', 
			cellStyle:formatTableT,
			formatter : function(value, row, index){ 
				var html = '<input disabled ="disabled" type="text" style="width:80px;" class="agv_table_input state" value="'+(row.state==1?"正常":"下架")+'" ></input>'
				return html;
			}

		},
            {
                title : '库区类型',
                field : 'areaType',
                cellStyle:formatTableT,
                formatter : function(value, row, index){
                	var areaType;
                	if(row.areaType=="1"){
                        areaType="虚拟库"
					}else if(row.areaType=="2"){
                        areaType="立体库"
                    }else if(row.areaType=="3"){
                        areaType="电子标签库"
                    }
                    var html = '<input disabled ="disabled" type="text" style="width:80px;" class="agv_table_input areaType" value="'+(areaType?areaType:"")+'" ></input>'
                    return html;
                }
            },
		{ 
			field: 'caozuo', 
			title: "操作",
			width:100,
			cellStyle:formatTableT3,
			formatter : function(value, row, index){ 
				var html = '<p class="fl config_disable cursor fc_0" style="width:80px;"  dataNo="'+row.id+'" dataCode="'+row.barCode+'" dataCode="'+row.barCode+'" dataCount="'+row.fullboxCount+'" dataWeight="'+row.weight+'" dataFr="'+row.frequency+'" id="update_info"><img src="../img/xg01.png" class="mr5 agv_refer_img"/>修改</p>'; 
				return  html
			}
		}],

	}); 
	
	//修改
	$("#avoidTable").off("click",".config_disable").on("click",".config_disable",function(){
		debugger
		$("#avoidId").val("");//清空id
		$("#AGV_add").modal({
			backdrop : false,
		});
		$('#AGV_addForm').bootstrapValidator('resetForm', false);
		$(".message_add_title").text("修改");
		var datas = $(this).parent().siblings();
		$("#avoidId").val($(this).attr("dataNo"));//当前的id
		$("#barCode").val($(this).attr("dataCode"));//条码
		$("#materialName").val($(datas).find(".materialName").val());//物料名
		$("#model").val($(datas).find(".model").val());//规格型号
		$("#unit").val($(datas).find(".unit").val());//基本计量单位
		$("#storageLocation").val($(datas).find(".storageLocation").val());//储位
		$("#weight").val($(datas).find(".weight").val());//重量
        debugger
        if($("#weight").val()=="undefined"){
            $("#weight").val("")
        }
		$("#materialCode").val($(datas).find(".materialCode").text());//物料号
		$("#nameAll").val($(datas).find(".nameAll").val());//全名
		$("#attribute").val($(datas).find(".attribute").val());//物料属性
		$("#warehouse").val($(datas).find(".warehouse").val());//默认仓库
		$("#fullNum").val($(datas).find(".fullNum").val());//满箱数量
        if($("#fullNum").val()=="undefined"){
            $("#fullNum").val("")
        }
		$("#Hz").val($(datas).find(".Hz").val());//频次
	});
  //关闭详情
    $("#avoidTable").off("click", ".materialCode").on("click", ".materialCode", function () {
    	debugger
       $('.taskDetailsBox').addClass("block").removeClass("none");
        id = $(this).attr("dataNo");
        detail(id);//详情
    })
	

}

//详情请求
function detail(id){
	$.post("../materiel/queryDetails",{"id":id},function(result){
		if (!result) {
			return;
		}
		$("#orderNum").text(result.barCode);//条码
		$("#DetailmaterialNo").text(result.materielNo);//物料号
		$("#Detailfullname").text(result.materielNames);//全名
		$("#DetailmaterialPro").text(result.attribute);//物料属性
		$("#DetaildefaultKu").text(result.defaultWarehouse);//默认仓库
		$("#DetailputTime").text(result.fullboxCount);//满箱数量
		$("#DetailoutTime").text(result.frequency);//频次
		$("#DetailCode").text(result.state);//状态
		$("#DetailmaterialName").text(result.materielName);//物料名
		$("#Detailmode").text(result.model);//规格型号
		$("#Detailunit").text(result.measmSheet);//基本计量单位
		$("#Detailbind").text(result.binLocation);//储位
		$("#Detailpi").text(result.weight);//重量
		$("#DetailState").text(result.safetyNumber);//库存安全箱数
	 });
}
	



function refresh() {  
	$('#avoidTable').bootstrapTable("refreshOptions",{pageNumber:1});  
}

//样式方法封装
function formatTableT(value, row, index) {
	return {
		css: {
			"border": "0",
			"background":"none",
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
function formatTableT3(value, row, index) {
	return {
		css: {
			"border": "0",
			"background":"none",
			"border":"0",
		    "border-right": "1px solid #E6E6E6",
			"font-size": "14px",
			"color": "#858C9E",
			"border-radius": "0",
			"text-align":"center"
		}
	}
}