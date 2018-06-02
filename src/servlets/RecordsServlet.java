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

/**
 * Servlet implementation class RecordsServlet
 */
@WebServlet("/RecordsServlet")
public class RecordsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson;
	private String filename;
	private ArrayList<Row> rowDrinking;
	private ArrayList<Row> rowNotDrinking;
	private int counter;
	private int counterND;

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
		System.out.println("----------------------------------Records Servlet--------------------------------------");
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

		ArrayList<Row> rows = gson.fromJson(params[2], new TypeToken<List<Row>>() {
		}.getType());

		if (rows != null && rows.size() != 0) {
			System.out.println(rows.get(0).toString());

			System.out.println(rows.get(rows.size() - 3).toString());
		}
		String result = "";

		if (params[2] != null) {

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

			String sql3 = "insert into sensor_records(labelid,time,x_axis,y_axis,z_axis)" + "values(?,?,?,?,?)";
			Connection con3 = DBConnectionHandler.getConnection();
			String result3 = "";

			try {
				PreparedStatement ps3 = con3.prepareStatement(sql3);
				if (ps3 == null) {
					System.out.println("Hello Error!!!");
				}
				if (params[0] != null) {
					int rs3 = 0;

					String text = "";
					rowDrinking = new ArrayList<Row>();
					rowNotDrinking = new ArrayList<Row>();

					counter = 1;
					counterND = 1;
					boolean trigger = false;
					createFile();
					for (int x = 0; x < rows.size(); x++) {
						ps3.setInt(1, rows.get(x).getLabelid());// label_id
						ps3.setString(2, rows.get(x).getTime());// time
						ps3.setString(3, rows.get(x).getX_axis());// x
						ps3.setString(4, rows.get(x).getY_axis());// y
						ps3.setString(5, rows.get(x).getZ_axis());// z
						if (rows.get(x).getLabelid() == 1) {
							if (!rowNotDrinking.isEmpty()) {
								createNotDrinkingFile();
							}
							rowDrinking.add(rows.get(x));

						} else {
							if (!rowDrinking.isEmpty()) {
								createDrinkingFile();
							}
							rowNotDrinking.add(rows.get(x));
						}

						rs3 = ps3.executeUpdate();
						if (rs3 != 0) {
							x++;
						}
					}

					if (rs3 != 0) {
						Utils.savedAttempts++;
						Utils.lastCounter = +counter;
						String[] results = { "success", Integer.toString(1) };
						System.out.println("success");
						result3 = gson.toJson(results);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(result3);

						// RequestDispatcher dis =
						// request.getRequestDispatcher("homepage.jsp");
						// dis.forward(request,
						// response);

					} else {
						String[] results = { "fail", "0" };
						System.out.println("fail");
						result3 = gson.toJson(results);

						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(result3);

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

		System.out.println(json);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	void createFile() {
		File file = new File("D:\\Directory1");
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		File files = new File("D:\\Directory2\\Sub2\\Sub-Sub2");
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("Multiple directories are created!");
			} else {
				System.out.println("Failed to create multiple directories!");
			}
		}
	}

	void createDrinkingFile() {
		if (rowDrinking != null) {

			filename = "Sub" + "EventD" + Utils.lastCounter + ".txt";
			File f = new File("D:\\Directory1\\" + filename);
			if (f.exists() && !f.isDirectory()) {
				int sum = Utils.lastCounter + counter;
				filename = "Sub" + "EventD" + sum + ".txt";
			}

			try {
				// Assume default encoding.

				FileWriter fileWriter = new FileWriter("D:\\Directory1\\" + filename);
				// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				for (int x = 0; x < rowDrinking.size(); x++) {
					// Note that write() does not automatically
					// append a newline character.
					bufferedWriter.write(rowDrinking.get(x).getLabelid() + " " + rowDrinking.get(x).getX_axis() + " "
							+ rowDrinking.get(x).getY_axis() + " " + rowDrinking.get(x).getZ_axis());
					bufferedWriter.newLine();
					x++;

				}
				// Always close files.
				counter++;
				rowDrinking.clear();
				bufferedWriter.close();
			} catch (IOException ex) {
				System.out.println("Error writing to file '" + filename + "'");
				// Or we could just do this:
				// ex.printStackTrace();
			}

		}
	}

	void createNotDrinkingFile() {
		if (rowNotDrinking != null) {
			filename = "Sub" + "EventND" + Utils.lastCounter + ".txt";
			File f = new File("D:\\Directory1\\" + filename);
			if (f.exists() && !f.isDirectory()) {
				int sum = Utils.lastCounter + counter;
				filename = "Sub" + "EventND" + sum + ".txt";
			}

			try {

				// Assume default encoding.
				// filename = "Sub" + "EventND" + counterND + ".txt";
				FileWriter fileWriter = new FileWriter("D:\\Directory1\\" + filename);
				// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				for (int x = 0; x < rowNotDrinking.size(); x++) {
					// Note that write() does not automatically
					// append a newline character.
					bufferedWriter.write(rowNotDrinking.get(x).getLabelid() + " " + rowNotDrinking.get(x).getX_axis()
							+ " " + rowNotDrinking.get(x).getY_axis() + " " + rowNotDrinking.get(x).getZ_axis());
					bufferedWriter.newLine();
					x++;

				}
				// Always close files.
				// Utils.lastCounter++;
				counterND++;
				rowNotDrinking.clear();
				bufferedWriter.close();
			} catch (IOException ex) {
				System.out.println("Error writing to file '" + filename + "'");
				// Or we could just do this:
				// ex.printStackTrace();
			}

		}
	}

	void createTxtFile(int l, String x, String y, String z) {
		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter("D:\\Directory1\\" + filename);
			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.write(l + " " + x + " " + y + " " + z);
			bufferedWriter.newLine();
			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + filename + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}
}
