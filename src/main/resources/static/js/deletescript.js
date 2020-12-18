	function Delete(id) {
        $('#'+id).click(function(){
            // var elem = $(this).closest('#'+id);
            $.confirm({
                'title'		: '警告',
                'message'	: '此项目删除不可恢复!继续吗?',
                'buttons'	: {
                    '取消'	: {
                        'class'	: 'gray',
                        'action': function(){
                            deletePost();
                        }	// Nothing to do in this case. You can as well omit the action property.
                    },
                    '确定'	: {
                        'class'	: 'blue',
                        'action': function(){
                            deleteOk();
                            // elem.slideUp();
                        }
                    },
                }
            });
        });
    }
    function deleteOk() {
        publicTipMessage("busy","你点击了确定!");
    }
    function deletePost() {      
        publicTipMessage("busy","你点击了取消!");
    }
$(function(){
    Delete('popupWin');
})
