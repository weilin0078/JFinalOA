package com.pointlion.plugin.quartz;

import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.mvc.common.model.SchJob;
import com.pointlion.mvc.common.model.SchJobExecute;
import com.pointlion.mvc.common.utils.UuidUtil;
import com.pointlion.plugin.quartz.bean.JobBean;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @author Lion
 * @mail 439635374@qq.com
 * @date 2017年12月22日 下午1:48:24
 * @version V1.0
 */
public class QuartzPlugin implements IPlugin{
	public static final QuartzPlugin me = new QuartzPlugin();
	private List<JobBean> jobs = new ArrayList<JobBean>();
	private SchedulerFactory sf;
	private static Scheduler scheduler;
	private String jobConfig;//任务配置
	private String confConfig;//quartz配置
	private Map<Object, Object> jobProp;

	public QuartzPlugin(String jobConfig, String confConfig) {
		this.jobConfig = jobConfig;
		this.confConfig = confConfig;
	}

	public QuartzPlugin(String jobConfig) {
		this.jobConfig = jobConfig;
		this.confConfig = "/quartz_config.properties";
	}

	public QuartzPlugin() {
		this.jobConfig = "/quartz_job.properties";
		this.confConfig = "/quartz_config.properties";
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
			jobs.add(createJobBeanFromPropertiesKey(jobName));
		}
	}
	/***
	 * 根据配置文件中的key创建job对象
	 * @param key
	 * @return
	 */
	private JobBean createJobBeanFromPropertiesKey(String key) {
		JobBean job = new JobBean();
		job.setJobClass(jobProp.get(key + ".class").toString());
		job.setCronExpression(jobProp.get(key + ".cron").toString());
		job.setJobGroup(jobProp.get(key+ ".group").toString());
		job.setJobDesc(jobProp.get(key + ".desc").toString());
		return job;
	}

	/***
	 * 从数据库中加载任务
	 * @param loadAutoRun 是否自动启动
	 *                    true：加载自动启动的任务（可用的&&自启动的）（应用启动的时候使用）
	 *                    false：加载所有任务（可用的）（主要是保存时候调用）
	 */
	private void loadJobsFromDb(Boolean loadAutoRun) {
		String sql = "SELECT * FROM "+ SchJobExecute.tableName +" E,"+ SchJob.tableName +" J WHERE J.ID=E.JOB_ID AND E.IS_ENABLE='1' AND J.TYPE='1'";
		if(loadAutoRun){
			sql = sql + " AND E.IS_AUTO_RUN = '1' ";
		}
		List<Record> joblist = Db.find(sql);
		for(Record job:joblist){

			String className = job.getStr("JOB_CLASS");
			String name = job.getStr("EXE_NAME");
			String group = "EXE_GROUP_"+ UuidUtil.getUUID();//此处分组名称，不能重名，保证唯一性。具体原因需再分析
			String cronExpression = job.getStr("EXE_PRESSION");
			jobs.add(QuartZKit.createJobBean(name, group, className, cronExpression));
		}
	}

	/***
	 * 启动任务
	 */
	private void startJobs() {
		System.out.println("！！！！！！！！！！！！定时任务启动！！！！！！！！！");
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
			System.out.println("！！！！！！！！添加定时任务："+entry.getJobDesc());
			addJob(entry);
		}
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}






	/*********************************public共有方法********************************************/

	/***
	 * 启用插件
	 * @return
	 */
	@Override
	public boolean start() {
		start(true);
		return true;
	}

	/***
	 * 重启
	 * @param loadAutoRun
	 * @return
	 */
	public boolean start(Boolean loadAutoRun){
		jobs.clear();
		loadJobsFromProperties();//从配置文件中加载作业
		loadJobsFromDb(loadAutoRun);//从数据库中加载作业
		startJobs();
		return true;
	}
	/***
	 * 停用插件
	 * @return
	 */
	@Override
	public boolean stop() {
		System.out.println("！！！！！！！！！！！！定时任务停用！！！！！！！！！");
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}

	/***
	 * 重启
	 * @param loadAutoRun 是否自动启动
	 *                    true：加载自动启动的任务（可用的&&自启动的）（应用启动的时候使用）
	 *                    false：加载所有任务（可用的）（主要是保存时候调用）
	 * @return
	 */
	public boolean reStart(Boolean loadAutoRun){
		System.out.println("！！！！！！！！！！！！定时任务开始重启！！！！！！！！！");
		stop();
		start(loadAutoRun);
		return true;
	}
	/***
	 * 添加作业到调度器
	 * @param job
	 */
	public static void addJob(JobBean job) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobDesc(), job.getJobGroup());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 不存在，创建一个
			if (null == trigger) {
				@SuppressWarnings("unchecked")
				Class<? extends Job> jobClazz = null;
				try {
					jobClazz = Class.forName(job.getJobClass()).asSubclass(Job.class);
				} catch (Exception e) {
					System.out.println("QuartZ任务类："+job.getJobClass()+"任务没有继承job,e=="+e);
					return;
				}
				JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(job.getJobDesc(), job.getJobGroup()).build();
				jobDetail.getJobDataMap().put("scheduleJob", job);
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
				// 按新的cronExpression表达式构建一个新的trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobDesc(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
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

	/*****
	 * 移除作业
	 * @param name
	 * @param group
	 */
	public void removeJob(String name, String group) {
		try {
			TriggerKey tk = TriggerKey.triggerKey(name, group);
			scheduler.pauseTrigger(tk);// 停止触发器
			scheduler.unscheduleJob(tk);// 移除触发器
			JobKey jobKey = JobKey.jobKey(name, group);
			scheduler.deleteJob(jobKey);// 删除作业
			System.out.println("删除作业=> [作业名称：" + name + " 作业组：" + group + "] ");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}




}
