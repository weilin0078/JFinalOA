package com.pointlion.plugin.quartz;

import com.pointlion.plugin.quartz.bean.JobBean;

/**  
 * @Description: TODO
 * @author Lion
 * @mail 439635374@qq.com  
 * @date 2017年12月22日 下午2:03:09
 * @version V1.0  
*/
public class QuartZKit {
	/***
	 *
	 * @param name
	 * @param group
	 * @param clazz
	 * @param cronExpression
	 */
	public static void addTask(String name, String group, String clazz, String cronExpression){
		QuartzPlugin.addJob(createJobBean( name,  group,  clazz,  cronExpression));
	}

	/***
	 * 根据传递参数创建job对象
	 * @param name
	 * @param group
	 * @param clazz
	 * @param cronExpression
	 * @return
	 */
	public static JobBean createJobBean(String name, String group, String clazz, String cronExpression) {
		JobBean job = new JobBean();
		job.setJobClass(clazz);
		job.setCronExpression(cronExpression);
		job.setJobGroup(group);
		job.setJobDesc(name);
		return job;
	}
}
