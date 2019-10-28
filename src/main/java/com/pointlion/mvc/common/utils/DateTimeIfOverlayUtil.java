package com.pointlion.mvc.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DateTimeIfOverlayUtil {
    public static boolean checkOverlap(List<String> list){  
        Collections.sort(list);//排序ASC  
          
        boolean flag = false;//是否重叠标识  
        for(int i=0; i<list.size(); i++){  
            if(i>0){  
                //跳过第一个时间段不做判断  
                String[] itime = list.get(i).split("@@@@@");  
                for(int j=0; j<list.size(); j++){  
                    //如果当前遍历的i开始时间小于j中某个时间段的结束时间那么则有重叠，反之没有重叠  
                    //这里比较时需要排除i本身以及i之后的时间段，因为已经排序了所以只比较自己之前(不包括自己)的时间段  
                    if(j==i || j>i){  
                        continue;  
                    }  
                      
                    String[] jtime = list.get(j).split("@@@@@");  
                    //此处DateUtils.compare为日期比较(返回负数date1小、返回0两数相等、返回正整数date1大)  
                    Boolean compare = DateUtil.compareDatetime(  
                            (itime[0]),   
                            (jtime[1]),"yyyy-MM-dd HH:mm:ss");  
                    if(!compare){  
                        flag = true;  
                        break;//只要存在一个重叠则可退出内循环  
                    }  
                }  
            }  
              
            //当标识已经认为重叠了则可退出外循环  
            if(flag){  
                break;  
            }  
        }  
          
        return flag;  
    }  
      
    public static void main(String[] args) {  
        List<String> list = new ArrayList<String>();  
        list.add("2018-02-01 08:00:00@@@@@2018-02-02 09:00:00");  
        list.add("2018-02-01 09:10:00@@@@@2018-02-02 12:00:00");  
        list.add("2018-02-02 13:00:00@@@@@2018-02-02 16:30:00");  
        list.add("2018-02-02 16:40:00@@@@@2018-02-02 17:00:00");  
        list.add("2018-02-02 18:00:00@@@@@2018-02-02 20:00:00");  
          
        boolean flag = checkOverlap(list);  
        for(String time : list){  
            System.out.println(time);  
        }  
          
        System.out.println("\n当前时间段列表重叠验证结果为：" + flag);  
    }  
}
