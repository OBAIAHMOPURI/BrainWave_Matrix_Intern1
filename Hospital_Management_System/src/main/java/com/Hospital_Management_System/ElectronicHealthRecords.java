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

@WebServlet("/ElectronicHealthRecords")
public class ElectronicHealthRecords extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		   String patient_id = req.getParameter("patient_id"); 
	        String diagnosis = req.getParameter("diagnosis");
	        String treatment = req.getParameter("treatment");
	        String prescription = req.getParameter("prescription");
	        String date=req.getParameter("date");
	        
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");
	            String sql = "INSERT INTO EHR (patient_id, diagnosis, treatment, prescription, date) VALUES (?, ?, ?, ?, ?)";
	            PreparedStatement ps = con.prepareStatement(sql);

	            ps.setInt(1, Integer.parseInt(patient_id ));
	            ps.setString(2, diagnosis);
	            ps.setString(3, treatment);
	            ps.setString(4, prescription);
	            ps.setDate(5, new Date(System.currentTimeMillis()));
	            ps.execute();
	            RequestDispatcher rd=req.getRequestDispatcher("File3.html");
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
