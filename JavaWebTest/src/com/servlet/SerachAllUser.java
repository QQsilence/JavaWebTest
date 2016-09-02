package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;




public class SerachAllUser extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SerachAllUser() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF8"); // this line solves the problem
		PrintWriter out = response.getWriter();
//测试下git的pull功能
		try {
			InitialContext initC = new InitialContext();// 创建InitialContext对象
			if (initC == null) {
				throw new Exception("No Context");
			}

			Context context = (Context) initC.lookup("java:comp/env");// 不变
			DataSource ds = (DataSource) context.lookup("jdbc/DBWater");// tomcat服务器配置文件中数据源名称

			if (ds != null) {
				Connection conn = ds.getConnection();
				if (conn != null) {
					Statement statement = conn.createStatement();
					ResultSet rs = statement
							.executeQuery("select * from user");
					 JSONArray array = new JSONArray();  
					    
					   // 获取列数  
					   ResultSetMetaData metaData = rs.getMetaData();  
					   int columnCount = metaData.getColumnCount();
					
					while  (rs.next()) {
						
						   JSONObject jsonObj = new JSONObject();  
					        // 遍历每一列  
					        for (int i = 1; i <= columnCount; i++) {  
					            String columnName =metaData.getColumnLabel(i);  
					            String value = rs.getString(columnName);  
					            jsonObj.put(columnName, value);  
					        }   
					        array.put(jsonObj);  
					}
					conn.close();
					out.print(array.toString());
					out.close();
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

}
