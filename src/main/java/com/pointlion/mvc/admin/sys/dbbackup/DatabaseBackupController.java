/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.mvc.admin.sys.dbbackup;

import com.pointlion.mvc.common.base.BaseController;

/***
 * 首页控制器
 */
public class DatabaseBackupController extends BaseController {
	
	public static MySQLDatabaseBackup mysqlDBBackUp = MySQLDatabaseBackup.me;
	
	/***
	 * mysql数据库备份
	 */
	public void mysqlDBBackUp(){
		try {
			if (mysqlDBBackUp.exportDatabaseTool("47.104.255.249", "root", "root", "D:/backupDatabase", "2014-10-14.sql", "pointlion")) {
				System.out.println("数据库成功备份！！！");
			} else {
				System.out.println("数据库备份失败！！！");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
