package com.Hospital_Management_System;

import java.io.IOException;
import java.math.BigDecimal;
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

@WebServlet("/InventoryManagementForMedicalSupplies")
public class InventoryManagementForMedicalSupplies extends HttpServlet {
   @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   String itemName = req.getParameter("item_name");
       String quantity = req.getParameter("quantity");
       String price = req.getParameter("price");
       try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");
        String sql = "INSERT INTO Inventory (item_name, quantity, price) VALUES (?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, itemName);
        stmt.setInt(2, Integer.parseInt(quantity));
        stmt.setBigDecimal(3, new BigDecimal(price));
        RequestDispatcher rd=req.getRequestDispatcher("File5.html");
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
