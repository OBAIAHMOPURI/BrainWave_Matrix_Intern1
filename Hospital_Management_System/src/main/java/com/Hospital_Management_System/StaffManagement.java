package com.Hospital_Management_System;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StaffManagement")
public class StaffManagement extends HttpServlet {
   @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String Staff_Id=req.getParameter("Staff_Id");
    String Staff_name=req.getParameter("Staff_name");
    String role =req.getParameter("role");
    String salary=req.getParameter("salary");
    String contact_info =req.getParameter("contact_info");
    String  email=req.getParameter("email");
    
    try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");
		String sql="INSERT INTO staff(Staff_Id,Staff_name,role,salary,contact_info,email) VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(Staff_Id));
        ps.setString(2,Staff_name);
        ps.setString(3, role);
        ps.setDouble(4, Double.parseDouble(salary));
        ps.setLong(5, Long.parseLong(contact_info));
        ps.setString(6, email);
        
        ps.execute();
        RequestDispatcher rd=req.getRequestDispatcher("File6.html");
		   rd.forward(req, resp);


	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
