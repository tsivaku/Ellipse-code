package servlets;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dbconnection.DBConnectionHandler;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public int getRoleID(int userID) {
		int roleid = 0;
		String sql = "SELECT type_id FROM type_bridge where user_id=?";
		Connection con = DBConnectionHandler.getConnection();
		String result = "";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			if (ps == null) {
				System.out.println("Hello Error!!!");
			}
			ps.setInt(1, userID);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int roleID = rs.getInt("type_id");
				roleid = roleID;

			} else {
				roleid = 0;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleid;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = null;
		System.out.println("----------------------------------Login Servlet--------------------------------------");
		String action = (String) request.getAttribute("action");

		Gson gson = new Gson();
		String json = "";

		Enumeration paramNames = request.getParameterNames();
		String params[] = new String[3];
		int i = 0;
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			System.out.println("Parameter Name from App: " + paramName);
			String[] paramValues = request.getParameterValues(paramName);
			params[i] = paramValues[0];

			System.out.println("Parameter Value from App: " + params[i] + " and index" + i);
			i++;

		}

		String sql = "SELECT * FROM users where username=? and password=?";
		Connection con = DBConnectionHandler.getConnection();
		String result = "";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			if (ps == null) {
				System.out.println("Error at line 98!!!");
			}

			ps.setString(1, params[0]);
			ps.setString(2, params[1]);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int userid = rs.getInt("id");
				int roleid = getRoleID(userid);

				// String[] results = { "success", Integer.toString(1),
				// "userid", Integer.toString(userid) };
				User myUser = new User();
				myUser.setId(rs.getInt("id"));
				myUser.setUsername(rs.getString("username"));
				myUser.setPassword(rs.getString("password"));

				String data = myUser.toString();

				System.out.println(roleid);
				if (roleid != 0) {
					if (roleid == 1) {
						myUser.setRole("End User");
					} else if (roleid == 2) {
						myUser.setRole("Data Collector");
					} 
				}

				result = gson.toJson(myUser);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(result);

				// request.setAttribute("result", result);
				// RequestDispatcher dis = request.getRequestDispatcher("");
				// dis.forward(request, response);

			} else {
				String[] results = { "fail", "0" };

				result = gson.toJson(results);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(result);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(json);

	}

}
