package com.pointlion.sys.plugin.quartz;

import com.pointlion.sys.plugin.quartz.bean.JobBean;

/**  
 * @Description: TODO
 * @author Lion
 * @mail 439635374@qq.com  
 * @date 2017年12月22日 下午2:03:09
 * @version V1.0  
*/
public class QuartZKit {
	/***
	 * 动态添加任务
	 */
	public static void addTask(String taskid , String closeTime){
	    JobBean job = new JobBean();
	    job.setJobClass("com.momoda.quartz.DakeTaskJob");
	    job.setCronExpression(FormaterCronExpression.formaterCronExpression(closeTime));
	    job.setJobGroup("DakeTaskJob");
	    job.setJobDesc("DakeTaskJob_" + taskid);
	    QuartzPlugin.addJob(job);
	}
}
