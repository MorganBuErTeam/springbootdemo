package com.test.demo.test.lambaTest;

import com.test.demo.common.vo.TaskGroupVo;
import com.test.demo.common.vo.TaskSortVo;
import com.test.demo.domain.City;
import com.test.demo.domain.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class LambaTest {



 public void test(){


     List<Task> taskList=new ArrayList<>();
     //list移除
     taskList.removeIf(x->"".equals("") && "".equals(""));

     List<String> noList= taskList.stream().map(Task::getTaskName).distinct().collect(toList());

     //多条件分组
     HashMap<TaskGroupVo,List<Task>> singleMap=taskList.stream().collect(groupingBy(d->new TaskGroupVo(d.getTaskName(),d.getTaskType()),HashMap::new,toList()));


     //升序排序
//    list.sort(a,b->a.getCode().compareTo(b.getCode()));

     //布尔类型排序,
     List<TaskSortVo> taskGroupVoList=new ArrayList<>();
     Comparator<TaskSortVo> comparator=(o1, o2) -> {
         if(o1.getFlag() ^ o2.getFlag()){
            return o1.getFlag()?1:-1;  //控制false在前，true在后
         }else {
             return 0;
         }
     };
     taskGroupVoList.sort(comparator);
 }





}
