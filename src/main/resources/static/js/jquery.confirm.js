(function($){	
	$.confirm = function(params){		
		if($('#confirmOverlay').length){
			// A confirm is already shown on the page:
			return false;
		}
		
		var buttonHTML = '';
		$.each(params.buttons,function(name,obj){

			// Generating the markup for the buttons:
			buttonHTML += '<a href="#" class="button '+obj['class']+'">'+name+'</a>';
			if(!obj.action){
				obj.action = function(){};
			}
		});		
		var markup = [
			'<div id="confirmOverlay">',
			'<div id="confirmBox">',
			'<div class="confi_title">',params.title,'</div>',
			'<div class="confi_body"><span class="confi_bodyImg"><img src="../public/tipmessage/images/delete01.png"> </span><span class="confi_bodyText">',params.message,'</span></div>',
			'<div id="confirmButtons">',buttonHTML,'</div></div></div>'
		].join('');
		$(markup).hide().appendTo('body').fadeIn();
		var buttons = $('#confirmBox .button'),
			i = 0;
		$.each(params.buttons,function(name,obj){
			buttons.eq(i++).click(function(){				
				// Calling the action attribute when a
				// click occurs, and hiding the confirm.				
				obj.action();
				$.confirm.hide();
				return false;
			});
		});
	}
	$.confirm.hide = function(){
		$('#confirmOverlay').fadeOut(function(){
			$(this).remove();
		});
	}	
})(jQuery);