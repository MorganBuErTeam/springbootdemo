$(function(){
    var h = $(window).height();
    $(".log_bg").height(h);

//验证
$("#login_f").bootstrapValidator({
    container: 'tooltip',
    feedbackIcons: {
    	valid: 'iconfont icon-v',
        invalid: 'iconfont icon-x',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
    	acountNumber: {
            validators: {
                notEmpty: {
                    message: '账号不能为空！'
                },
                stringLength:{
                    max:19,
                    message:'用户名长度不能超过20位'
                }
            }
        },
        pwd: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                stringLength:{
                    max:19,
                    message:'密码长度不能超过20位'
                }
            }
        }
    }

}).on('success.form.bv', function(e) {
	debugger
    e.preventDefault();
    // Get the form instance
    var $form = $(e.target);
    // Get the BootstrapValidator instance
    var bv = $form.data('bootstrapValidator');
    // Use Ajax to submit form data
 
    $.post("../userInfo/login", $form.serialize(), function(data) {
    	debugger
        if(data.code == 200){
            window.location = data.data;
//			openwin("../agvRunDiagram/agvRunDiagram.html");
        }else{  // if(data.code == 500)
            $("#login_f").data('bootstrapValidator').resetForm("false");
            // $(".errorLogin").empty();
            // $(".errorLogin").append("<h5 style='color:red'>您输入的账号或密码错误！</h5>")
            publicTipMessage("error","您输入的账号或密码错误！");
        }
    }, 'json');
});

    //鼠标获取焦点时样式
    $("input[name='userName']").focus(function () {
       $(this).parents(".form-group").css("border-color","#1890FF");
    });
    $("input[name='userName']").blur(function () {
            $(this).parents(".form-group").css("border-color","#ccc");
    });

    $("input[name='userPass']").focus(function () {
            $(this).parents(".form-group").css("border-color","#1890FF");
    });
    $("input[name='userPass']").blur(function () {
        $(this).parents(".form-group").css("border-color","#ccc");
    });


});

//function openwin(url) { 
//	window.open (url, "newwindow", "height=800, width=1680, top=200,left=200 , toolbar =no, menubar=no, scrollbars=no, resizeable=no, location=no, status=no") 
//}