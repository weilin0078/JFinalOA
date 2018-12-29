package com.pointlion.sys.plugin.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;

/**  
 * @Description: 定时爬取政策文件 , 定时任务执行类
 * @author Lion
 * @mail 439635374@qq.com  
 * @date 2017年12月25日 上午9:39:09
 * @version V1.0  
*/
public class ResetYearHoliday implements Job{
	private static final Logger logger = LoggerFactory.getLogger(ResetYearHoliday.class);  
	@Override
	public void execute(JobExecutionContext context) {
		logger.info("*****************************开始重置所有员工年假******************");
		Db.update("update sys_user set year_holiday = '"+PropKit.get("yearHoliday").trim()+"' where type='1'");
		logger.info("*****************************完成重置所有员工年假******************");
	}

}
