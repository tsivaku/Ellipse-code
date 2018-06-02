package servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import models.Verify;

/**
 * Servlet implementation class RecordsServlet
 */
@WebServlet("/VerifyServlet")
public class VerifyServlet extends HttpServlet {
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
		System.out.println("----------------------------------Verify Servlet--------------------------------------");
		String action = (String) request.getAttribute("action");

		Gson gson = new Gson();
		String json = "";
		String result3 = "";
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

		ArrayList<Row> rows = gson.fromJson(params[1], new TypeToken<List<Row>>() {
		}.getType());

		ArrayList<String> x_axis = new ArrayList<String>();
		ArrayList<String> y_axis = new ArrayList<String>();
		ArrayList<String> z_axis = new ArrayList<String>();

		if (rows != null && rows.size() != 0) {
			System.out.println(rows.get(0).toString());
			System.out.println(rows.get(rows.size() - 3).toString());

			Double totalX = 0.0;
			Double totalY = 0.0;
			Double totalZ = 0.0;
			Double sdX = 0.0;
			Double sdY = 0.0;
			Double sdZ = 0.0;

			for (int x = 0; x < rows.size(); x++) {
				x_axis.add(rows.get(x).getX_axis());
				totalX += Double.parseDouble(rows.get(x).getX_axis());

				y_axis.add(rows.get(x).getY_axis());
				totalY += Double.parseDouble(rows.get(x).getY_axis());

				z_axis.add(rows.get(x).getZ_axis());
				totalZ += Double.parseDouble(rows.get(x).getZ_axis());

			}

			double meanX = totalX / rows.size();
			double meanY = totalY / rows.size();
			double meanZ = totalZ / rows.size();

			for (int a = 0; a < rows.size(); a++) {
				sdX += ((Double.parseDouble(rows.get(a).getX_axis()) - meanX)
						* (Double.parseDouble(rows.get(a).getX_axis()) - meanX)) / rows.size();
				sdY += ((Double.parseDouble(rows.get(a).getY_axis()) - meanY)
						* (Double.parseDouble(rows.get(a).getY_axis()) - meanY)) / rows.size();
				sdZ += ((Double.parseDouble(rows.get(a).getZ_axis()) - meanZ)
						* (Double.parseDouble(rows.get(a).getZ_axis()) - meanZ)) / rows.size();
			}

			double SDX = Math.sqrt(sdX);
			double SDY = Math.sqrt(sdY);
			double SDZ = Math.sqrt(sdZ);

			System.out.println("x" + SDX);
			System.out.println("y" + SDY);
			System.out.println("z" + SDZ);

			Verify object = new Verify();

			object.setRdx(SDX);
			object.setRdy(SDY);
			object.setRdz(SDZ);

			String[] results = { Double.toString(SDX), Double.toString(SDY), Double.toString(SDZ),
					Integer.toString(1), };

			System.out.println("success");
			result3 = gson.toJson(object);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result3);

		}

		String result = "";

		int label_id = 0;
		String timestamp = Utils.getCurrentDate().toGMTString().toString();

		if (params[1].equalsIgnoreCase("drinking")) {
			label_id = 1;
		} else if (params[1].equalsIgnoreCase("eating")) {
			label_id = 2;
		} else if (params[1].equalsIgnoreCase("scratching")) {
			label_id = 3;
		} else if (params[1].equalsIgnoreCase("unknown")) {
			label_id = 4;
		}

		// String sql3 = "insert into
		// sensor_records(labelid,time,x_axis,y_axis,z_axis)" +
		// "values(?,?,?,?,?)";
		// Connection con3 = DBConnectionHandler.getConnection();
		// String result3 = "";
		//
		// try {
		// PreparedStatement ps3 = con3.prepareStatement(sql3);
		// if (ps3 == null) {
		// System.out.println("Hello Error!!!");
		// }
		// if (params[0] != null) {
		// int rs3 = 0;
		//
		// if (rs3 != 0) {
		//
		// String[] results = { "success", Integer.toString(1) };
		// System.out.println("success");
		// result3 = gson.toJson(results);
		// response.setContentType("application/json");
		// response.setCharacterEncoding("UTF-8");
		// response.getWriter().write(result3);
		//
		// // RequestDispatcher dis =
		// // request.getRequestDispatcher("homepage.jsp");
		// // dis.forward(request,
		// // response);
		//
		// } else {
		// String[] results = { "fail", "0" };
		// System.out.println("fail");
		// result3 = gson.toJson(results);
		//
		// response.setContentType("application/json");
		// response.setCharacterEncoding("UTF-8");
		// response.getWriter().write(result3);
		//
		// }
		//
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		System.out.println(json);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
