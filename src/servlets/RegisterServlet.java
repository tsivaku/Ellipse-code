package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import configs.Utils;
import dbconnection.DBConnectionHandler;
import models.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(request, response);

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = null;
		System.out.println("----------------------------------Register Servlet--------------------------------------");
		String action = (String) request.getAttribute("action");

		Gson gson = new Gson();
		String json = "";

		Enumeration paramNames = request.getParameterNames();
		String params[] = new String[10];
		int i = 0;
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			System.out.println("Parameter Name from App: " + paramName);
			String[] paramValues = request.getParameterValues(paramName);
			params[i] = paramValues[0];

			System.out.println("Parameter Value from App: " + params[i] + " and index " + i);
			i++;

		}

		// String sql =
		// "SELECT admin_username, admin_password FROM admin where
		// admin_username=? and admin_password=?";
		String sql = "insert into users(username,password,email,startdate) "
				+ "values(?,?,?,?)";
		Connection con = DBConnectionHandler.getConnection();
		String result = "";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			if (ps == null) {
				System.out.println("Hello Error!!!");
			}
			if (params[0] != null) {

				ps.setString(1, params[1]);// email
				ps.setString(2, params[2]);// username
				ps.setString(3, params[0]);// password
				ps.setString(4, Utils.getCurrentDate().toGMTString().toString());

				int rs = ps.executeUpdate();
				if (rs != 0) {

					String sql2 = "select * from users where username = ? and password = ?";
					Connection con2 = DBConnectionHandler.getConnection();
					String result2 = "";

					try {
						PreparedStatement ps2 = con2.prepareStatement(sql2);
						if (ps2 == null) {
							System.out.println("Hello Error!!!");
						}
						if (params[0] != null) {

							ps2.setString(1, params[1]);// uname
							ps2.setString(2, params[2]);// pass

							ResultSet rs2 = ps2.executeQuery();

							if (rs2.next()) {
								int userid = rs2.getInt("id");

								// String[] results = { "success",
								// Integer.toString(1),
								// "userid", Integer.toString(userid) };
								User myUser = new User();
								myUser.setId(rs2.getInt("id"));
								myUser.setUsername(rs2.getString("username"));
								myUser.setPassword(rs2.getString("password"));
								myUser.setEmail(rs2.getString("email"));
								myUser.setStartdate(rs2.getString("startdate"));

								String data = myUser.toString();
								// String finalParse =
								// results.toString()+"parse"+data;

								String sql3 = "insert into type_bridge(user_id,type_id)" + "values(?,?)";
								Connection con3 = DBConnectionHandler.getConnection();
								String result3 = "";

								try {
									PreparedStatement ps3 = con3.prepareStatement(sql3);
									if (ps3 == null) {
										System.out.println("Hello Error!!!");
									}
									if (params[0] != null) {

										ps3.setInt(1, myUser.getId());// age
										ps3.setInt(2, Integer.parseInt(params[3]));// type

										int rs3 = ps3.executeUpdate();
										if (rs3 != 0) {

											String[] results = { "success", Integer.toString(1) };
											System.out.println("success");
											result = gson.toJson(results);
											response.setContentType("application/json");
											response.setCharacterEncoding("UTF-8");
											response.getWriter().write(result);

											// RequestDispatcher dis =
											// request.getRequestDispatcher("homepage.jsp");
											// dis.forward(request, response);

										} else {
											String[] results = { "fail", "0" };
											System.out.println("fail");
											result = gson.toJson(results);

											response.setContentType("application/json");
											response.setCharacterEncoding("UTF-8");
											response.getWriter().write(result);

										}

									}
								} catch (Exception e) {
									e.printStackTrace();
								}

							} else {
								String[] results = { "fail", "0" };
							System.out.println("fail");
								result = gson.toJson(results);

								response.setContentType("application/json");
								response.setCharacterEncoding("UTF-8");
								response.getWriter().write(result);

							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					String[] results = { "fail", "0" };
					System.out.println("fail");
					result = gson.toJson(results);

					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(result);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(json);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
