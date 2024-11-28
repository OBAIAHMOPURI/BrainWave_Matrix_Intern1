package com.Hospital_Management_System;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AppoinmentSchedulingl")
public class AppoinmentScheduling extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String patient_id = req.getParameter("patient_id");
		String patient_name = req.getParameter("patient_name");
        String doctor_name = req.getParameter("doctor_name");
        String appointment_date = req.getParameter("appointment_date");
        String appointment_time = req.getParameter("appointment_time");
        if (appointment_time != null && appointment_time.length() == 5) {
            appointment_time = appointment_time + ":00"; 
        }
        
        
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");
			String sql = "INSERT INTO appointment(patient_id, patient_name, doctor_name, appointment_date, appointment_time) VALUES(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(patient_id));
			ps.setString(2,patient_name);
			ps.setString(3, doctor_name);
			ps.setDate(4, Date.valueOf(appointment_date));
			ps.setTime(5, Time.valueOf(appointment_time));  
			ps.execute();
			RequestDispatcher rd=req.getRequestDispatcher("File2.html");
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
