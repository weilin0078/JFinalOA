package com.pointlion.sys.plugin.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.pointlion.sys.plugin.quartz.bean.JobBean;

/**  
 * @Description: TODO
 * @author Lion
 * @mail 439635374@qq.com  
 * @date 2017年12月22日 下午1:48:24
 * @version V1.0  
*/
public class QuartzPlugin implements IPlugin{

	private List<JobBean> jobs = new ArrayList<JobBean>();
	private SchedulerFactory sf;
	private static Scheduler scheduler;
	private String jobConfig;
	private String confConfig;
	private Map<Object, Object> jobProp;
	
	public QuartzPlugin(String jobConfig, String confConfig) {
		this.jobConfig = jobConfig;
		this.confConfig = confConfig;
	}
 
	public QuartzPlugin(String jobConfig) {
		this.jobConfig = jobConfig;
		this.confConfig = "quartz_config.properties";
	}
 
	public QuartzPlugin() {
		this.jobConfig = "quartz_job.properties";
		this.confConfig = "quartz_config.properties";
	}
 
	
 
	@Override
	public boolean start() {
		loadJobsFromProperties();
		startJobs();
		return true;
	}
	
	
	/***
	 * 从配置文件中加载任务
	 */
	private void loadJobsFromProperties() {
		if (StrKit.isBlank(jobConfig)) {
			return;
		}
		jobProp = PropKit.use(jobConfig).getProperties();
		String jobArray = (String) jobProp.get("jobArray");
		if (StrKit.isBlank(jobArray)) {
			return;
		}
		String[] jobArrayList = jobArray.split(",");
		for (String jobName : jobArrayList) {
			jobs.add(createJobBean(jobName));
		}
	}
	
	private void startJobs() {
		try {
			if (StrKit.notBlank(confConfig)) {
				sf = new StdSchedulerFactory(confConfig);
			} else {
				sf = new StdSchedulerFactory();
			}
			scheduler = sf.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		for (JobBean entry : jobs) {
			addJob(entry);
		}
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
 
	public static void addJob(JobBean job) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobDesc(), job.getJobGroup());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 不存在，创建一个
			if (null == trigger) {
				@SuppressWarnings("unchecked")
				Class<Job> j2 = (Class<Job>) Class.forName(job.getJobClass());
				JobDetail jobDetail = JobBuilder.newJob(j2).withIdentity(job.getJobDesc(), job.getJobGroup()).build();
				jobDetail.getJobDataMap().put("scheduleJob", job);
 
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
 
				// 按新的cronExpression表达式构建一个新的trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobDesc(), job.getJobGroup())
						.withSchedule(scheduleBuilder).build();
				try {
					scheduler.scheduleJob(jobDetail, trigger);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// Trigger已存在，那么更新相应的定时设置
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
 
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
 
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
		}
	}
 
	private JobBean createJobBean(String key) {
		JobBean job = new JobBean();
		job.setJobClass(jobProp.get(key + ".class").toString());
		job.setCronExpression(jobProp.get(key + ".cron").toString());
		job.setJobGroup(jobProp.get(key+ ".group").toString());
		job.setJobDesc(jobProp.get(key + ".desc").toString());
		return job;
	}
 
	@Override
	public boolean stop() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}

}
