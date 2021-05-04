package com.telusko.demorest.services;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;

import com.telusko.demorest.model.CategoryModel;


public class CategoryRepository {
	
	List<CategoryModel> categories;
	Connection conn=null;
	
	public CategoryRepository() {
		
		String url="jdbc:mysql://localhost:3306/gadgetbadget";
		String usrName="root";
		String pw="root123";
	
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn=DriverManager.getConnection(url,usrName,pw);
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println(e);
			}
	}
	
	public List<CategoryModel> getCategories() throws SQLException {
		
		List<CategoryModel> categories=new ArrayList<>();
		String query="select * from categories ";
			
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(query);
			
			while (rs.next()) {
				CategoryModel a=new CategoryModel();
				a.setid_categories(rs.getInt(1));
				a.setDescription(rs.getString(2));

				categories.add(a);
			}
			
		

		return categories;
	}
	

}
