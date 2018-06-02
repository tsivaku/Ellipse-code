package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import configs.Utils;
import dbconnection.DBConnectionHandler;
import models.Row;
import models.User;

/**
 * Servlet implementation class RecordsServlet
 */
@WebServlet("/FilesServlet")
public class FilesServlet extends HttpServlet {
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
		System.out.println("----------------------------------Files Servlet--------------------------------------");
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

		System.out.println(params[1]);

		String sql = "insert into files(user_id,file_name,file,timestamp) " + "values(?,?,?,?)";
		Connection con = DBConnectionHandler.getConnection();
		String result = "";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			if (ps == null) {
				System.out.println("Hello Error!!!");
			}
			if (params[2] != null) {

				int label_id = 0;
				String timestamp = Utils.getCurrentDate().toGMTString().toString();

				ps.setInt(1, Integer.parseInt(params[0]));// userid
				ps.setString(2, params[1]);// filename;
				ps.setString(3, params[2]); // file
				ps.setString(4, timestamp);

				int rs = ps.executeUpdate();
				if (rs != 0) {

					String[] results = { "success", Integer.toString(1) };
					System.out.println("success");
					result = gson.toJson(results);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(result);

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
