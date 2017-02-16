package com.lion.sys.plugin.activiti;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;


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
