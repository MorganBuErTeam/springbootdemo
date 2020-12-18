$(function(){
    reloadPager( firstLoadJson.currCourseTotalCount , every_page_count ) ;
})


//提取条目到数组
var courseArrayList = [] ;
//当前课程系数
var curr_course_index = 0 ;
//显示页码的数量
var page_number_count = 5 ;
//当前页面
var curr_page_index = 1 ;
var every_page_count = 5 ;
//当前课程id
var curr_course_id = null ;

var firstLoadJson = {
    teacherId : "FmYE4FAgW6Fi1iqV98yjUfQGyuibJRnU" ,
    currCourseTotalCount : 43 ,

}

//加载分页器
function toAnotherPage(page , size , count){
    console.log(page) ;
    toListPage( page ) ;
    //curr_page_index = page ;
}

function reloadPager( total , every_page_count ){
    var totalPage = Math.ceil( total / every_page_count );
    var totalRecords = total ;

    var pp = new Paging() ;
    pp.init({
        target : "#kkpager" ,
        pagesize : every_page_count ,
        count : total ,
        //toolbar : true ,
        callback : toAnotherPage ,
    })
    pp.render({
        current : curr_page_index
    })

    // var pp1 = new Paging() ;
    // pp1.init({
    //     target : "#kkpager1" ,
    //     pagesize : every_page_count ,
    //     count : total ,
    //     //toolbar : true ,
    //     callback : toAnotherPage ,
    // })
    // pp1.render({
    //     current : curr_page_index
    // })
}