package com.Hospital_Management_System;

import java.io.IOException;
import java.math.BigDecimal;
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

@WebServlet("/Billing")
public class Billing extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String patient_id = req.getParameter("patient_id");
        String bill_amount = req.getParameter("bill_amount");
        String payment_status = req.getParameter("payment_status");
        String bill_date=req.getParameter("date");
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");
			String sql = "INSERT INTO Billing (patient_id, bill_amount, payment_status, bill_date) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(patient_id));
            ps.setBigDecimal(2, new BigDecimal(bill_amount));
            ps.setString(3, payment_status);
            ps.setDate(4, new Date(System.currentTimeMillis()));
            ps.execute();
            RequestDispatcher rd=req.getRequestDispatcher("File4.html");
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
