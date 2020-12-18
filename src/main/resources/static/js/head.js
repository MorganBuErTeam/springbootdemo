var loginUser = undefined;
var desc = 1;//UserType是用户类型 1是管理员 2 是用户

//获取url参数
function GetRequest() {
	debugger
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

//电子狗的时间剩余
var timer = readCookie("time");
function getTimer(){
	if(timer!= ""){
		$(".timeBox").addClass("block").removeClass("none");
	}else if(timer == undefined){
		$(".timeBox").addClass("none").removeClass("block");
	}else if(timer == null){
		$(".timeBox").addClass("none").removeClass("block");
	}else{
		$(".timeBox").addClass("none").removeClass("block");
	}
	$(".head_timer").text(timer);　
}


$(function(){
	userHead('../head/head.html');
    readCookie();//检查登录
    getname();
    getTimer();//时间
  	setInterval("getTimer()", 1000*60);//电子狗的时间剩余实时刷新
 
//	菜单展开方式
    $(".menu_gong").click(function(){
//    	debugger
    	$(this).addClass("menu_current");
    	$(this).siblings().find('.menu_son_ul').hide("fast");
    	$(this).siblings().find('.menu_son_ul').removeClass("menu_son_current");
    	$(this).siblings().find(".menu_span").removeClass("menu_son_current");
      	$(".menu_span_wo").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
    });
    　　 $(".tree>.menu_box_ul>li").addClass('parent_li').find('>span');
    　　$('.tree li.parent_li>span').on('click', function (e) {
//    	debugger  
    	if($(this).children("i").hasClass("icon-jiantou-copy-copy")){
    		$(this).children("i").addClass("icon-jiantou-copy-copy-copy").removeClass("icon-jiantou-copy-copy");
    		$(this).children("i").parent().parent().siblings().find(".menu_span_wo").children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
    	}
    	else{
    		$(this).children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
    	}
    	   
    	$(".menu_gong").removeClass("menu_current");
       　　 var children = $(this).parent('li.parent_li').find('.menu_son_ul');
       
        　　if (children.is(":visible")) {
        	
        	children.hide("fast");
        	children.children().eq(0).removeClass("menu_current");
        	$(this).removeClass("menu_son_current");
        
        }else{
//              if($(this).children("i").hasClass("icon-jiantou-copy-copy-copy")){
//                  $(this).children("i").addClass("icon-jiantou-copy-copy").removeClass("icon-jiantou-copy-copy-copy");
//              }else{
//                  $(this).children("i").addClass("icon-jiantou-copy-copy-copy").removeClass("icon-jiantou-copy-copy");
//              }
        	children.show("fast");
        	//children.children().eq(0).addClass("menu_current");
        	$(this).addClass("menu_son_current");
        	$(this).parent().siblings().children("ul").hide("fast");
        	$(this).parent().siblings().children(".menu_span").removeClass("menu_son_current");
        }
        　　
        });
    
//    $(".menu_span").click(function(){
//    	
//    })
    
//  子菜单栏点击效果
	$(".menu_son_ul li").click(function(){
//		debugger
		$(this).parents(".menu_son_ul").removeClass('none')//.addClass("block");//.removeClass('none').siblings().addClass("none").removeClass('block');
		$(this).addClass("menu_current").siblings().removeClass("menu_current");
	});
	
//	setInterval("getCallclear()", 2000);//呼叫清除
//  　　
});
function userHead(url){
    $.ajax({
    	url : url,
    	contentType : "application/json;charset=UTF-8",
    	type : "GET",
    	async:false,
    	success : function(data) {
            $(".add_top").html(data);
    	}
    	})
}


//检查登录信息
//获取COOLKIE
function readCookie (name)
{
//	debugger
    var cookieValue = "";
    var search = name + "=";
    if (document.cookie.length > 0)
    {
        offset = document.cookie.indexOf (search);
        if (offset != -1)
        {
            offset += search.length;
            end = document.cookie.indexOf (";", offset);
            if (end == -1)
                end = document.cookie.length;
            cookieValue = unescape (document.cookie.substring (offset, end))
        }
    }
    return cookieValue;
}

var n = readCookie("CookieType");
function getname() {
    if(n == "2"){
        $(".head_auth").text("操作员");
        $(".operatorLi").addClass("none").removeClass("block");
        
    }else if(n == "1"){
        $(".head_auth").text("管理员");
        $(".operatorLi").addClass("block").removeClass("none");
    }else{
//    	$(".head_auth").text("操作员");
//        $(".operatorLi").addClass("none").removeClass("block");
        //publicTipMessage("error", "登录过期，请重新登录");
        //window.location = "../login/login.html"
    }
}

/**
 * 获取呼叫清除
 * @returns
 */
function getCallclear(){
	$.post("../taskInfo/getCallclear",function(result){
		/*debugger*/
		if(result!="无"){
			publicTipMessage("ok",result);
			setCallclear();
		}
	});
}

/**
 * 呼叫清除还原更改
 * @returns
 */
function setCallclear(){
	$.post("../taskInfo/setCallclear",function(result){
		
	});
}

