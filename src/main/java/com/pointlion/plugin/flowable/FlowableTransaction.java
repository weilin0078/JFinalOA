package com.pointlion.plugin.flowable;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;

import com.jfinal.plugin.activerecord.DbKit;

/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
public class FlowableTransaction implements Transaction {

	protected Connection connection;
	protected DataSource dataSource;
	protected TransactionIsolationLevel level;
	protected boolean autoCommmit;

	public FlowableTransaction(DataSource ds, TransactionIsolationLevel desiredLevel, boolean desiredAutoCommit) {
		dataSource = ds;
		level = desiredLevel;
		autoCommmit = desiredAutoCommit;
	}

	public FlowableTransaction(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (connection == null) {
			openConnection();
		}
		return connection;
	}

	@Override
	public void commit() throws SQLException {
	}

	@Override
	public void rollback() throws SQLException {
	}

	@Override
	public void close() throws SQLException {
		if(connection!=null){
			DbKit.getConfig().close(connection);
		}
	}

	@Override
	public Integer getTimeout() throws SQLException {
		return null;
	}

	protected void openConnection() throws SQLException {
		connection = DbKit.getConfig().getConnection();
		if (level != null) {
			connection.setTransactionIsolation(level.getLevel());
		}
	}
}
