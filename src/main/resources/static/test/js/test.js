$(function(){
    //保存
    $(".search_box").click(function(){
        var entity = {
            "duiduoCode":$(".duiduoCode").val(),//堆垛机编号
            "quhuo":$(".quhuo").val(),//取货列
            "fanghuo":$(".fanghuo").val(),//放货列
            "endcoulumn":$(".endcoulumn").val() //停止列
        }
        debugger
        $.ajax({
            url : "../testyiku/save",
            data : JSON.stringify(entity),
            contentType : "application/json;charset=UTF-8",
            type : "POST",
            success : function(result) {
                if(result.code == "200"){
                    publicTipMessage("ok",result.desc);
                    return;
                }
                publicTipMessage("error",result.desc);
            }
        });
    })
});





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