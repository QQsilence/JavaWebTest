package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBWater {
	 String userId;
	

	public String getUserId() {
		return userId;
	}



	public void init() {
		try {
			InitialContext initC = new InitialContext();// 创建InitialContext对象
			if (initC == null) {
				throw new Exception("No Context");
			}
			System.out.println("1");
			Context context = (Context) initC.lookup("java:comp/env");// 不变
			System.out.println("2");

			DataSource ds = (DataSource) context.lookup("jdbc/DBWater");// tomcat服务器配置文件中数据源名称
			System.out.println("3");

			if (ds != null) {
				Connection conn = ds.getConnection();
				if (conn != null) {
					Statement statement = conn.createStatement();
					ResultSet resultSet = statement
							.executeQuery("select * from user");
					while (resultSet.next()) {
						userId = resultSet.getString(1);
						
					}
					conn.close();
					System.out.println(userId);

				}
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public static void main(String[] args) {
	 DBWater rs = new DBWater();
	 System.out.println("11");
	   rs.init();
	
}
}