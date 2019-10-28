package com.pointlion.plugin.quartz.task;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**  
 * @Description: 定时爬取政策文件 , 定时任务执行类
 * @author Lion
 * @mail 439635374@qq.com  
 * @date 2017年12月25日 上午9:39:09
 * @version V1.0  
*/
public class ResetYearHoliday implements Job{
	private static final Log LOG = Log.getLog(ResetYearHoliday.class);
	@Override
	public void execute(JobExecutionContext context) {
		LOG.info("*****************************开始重置所有员工年假******************");
		Db.update("update sys_user set year_holiday = '"+PropKit.get("yearHoliday").trim()+"' where type='1'");
		LOG.info("*****************************完成重置所有员工年假******************");
	}

}
