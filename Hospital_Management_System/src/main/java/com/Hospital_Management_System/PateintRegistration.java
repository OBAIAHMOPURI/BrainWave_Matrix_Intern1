package com.Hospital_Management_System;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/PatientRegistration")
public class PateintRegistration extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 String id=req.getParameter("Id");
	     String name=req.getParameter("Name");
	     String date_of_birth=req.getParameter("Dob");
	     String address=req.getParameter("Address");
	     String email=req.getParameter("Email");
	     String  mobilenumber=req.getParameter("Mobilenumber");
	     try {

				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");
				 String sql = "INSERT INTO PatientRegistration (id, name, date_of_birth, address, email, mobilenumber) VALUES (?, ?, ?, ?, ?, ?)";
		           PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(id));
				ps.setString(2, name);
				ps.setDate(3, Date.valueOf(date_of_birth)); 
				ps.setString(4, address);
				ps.setString(5,email);
				ps.setLong(6, Long.parseLong(mobilenumber));
				ps.execute();
				 RequestDispatcher rd=req.getRequestDispatcher("File1.html");
				   rd.forward(req, resp);
				   con.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}
		

		
		

	
}
