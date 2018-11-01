package com.Utils;


import com.miz.testframework.config.DBConfig;
import com.miz.testframework.config.PropertyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * 获取DB连接
 *
 * Created by chuwenjun on 2017/7/24.
 */
public class DBConn {

	private Statement statement = null;
	private Connection conn = null;

	private ResultSet myResultSet = null;
	protected static Logger logger = LoggerFactory.getLogger(com.miz.testframework.database.DBConn.class);

	/**
	 * 查询sql执行
	 *
	 * @param sql
	 * @return
	 */
	public ResultSet executeQuery(String tableName, String sql) {
		try {

			initConnection(tableName);

			this.myResultSet = statement.executeQuery(sql);

		} catch (Exception ex) {
			logger.error("数据库操作出错。sql=[" + sql + "]", ex);
		}

		return this.myResultSet;
	}

	/**
	 * 执行更新sql
	 *
	 * @param sql
	 * @return
	 */
	public int executeUpdate(String tableName,String sql) {
		int result = 0;
		try {
			initConnection(tableName);

			result = this.statement.executeUpdate(sql);

		} catch (Exception ex) {
			logger.error("数据库操作出错。sql=[" + sql + "]", ex);

		}
		return result;
	}

	/**
	 * 关闭数据库连接
	 */
	public void close() {
		try {
			if (this.myResultSet != null) {
				this.myResultSet.close();
			}
			if (this.conn != null) {
				this.conn.close();
			}
			if (this.statement != null) {
				this.statement.close();
			}

		} catch (SQLException e) {
			logger.error("关闭数据库连接出错。", e);
		}
	}

	/**
	 * 初始化数据源连，会加载启动配置参数信息来获取数据源信息
	 */
	private void initConnection(String tableName) throws Exception {

		DBConfig dbconf = DBConnect.getDBConfig(tableName);
		String url = dbconf.getConnectionUrl();
		String user = dbconf.getUsername();
		String password = dbconf.getPassword();

		try {

			Class.forName("com.mysql.jdbc.Driver");
				this.conn = DriverManager.getConnection(url, user, password);
				this.statement = this.conn.createStatement();

		} catch (ClassNotFoundException e1) {
			System.out.println("数据库驱动不存在！" + e1.toString());
		} catch (SQLException e2) {
			System.out.println("数据库存在异常" + e2.toString());
		}
	}


}