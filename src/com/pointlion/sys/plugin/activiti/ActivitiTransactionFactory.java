package com.pointlion.sys.plugin.activiti;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;

/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
public class ActivitiTransactionFactory implements TransactionFactory {

	@Override
	public void setProperties(Properties props) {
	}

	@Override
	public Transaction newTransaction(Connection conn) {
		return new ActivitiTransaction(conn);
	}

	@Override
	public Transaction newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommit) {
		return new ActivitiTransaction(ds, level, autoCommit);
	}
}
