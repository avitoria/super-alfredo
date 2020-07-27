package com.ipartek.formacion.controller.backoffice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.modelo.ConnectionManager;

/**
 * Servlet implementation class MigracionBackOfficeController
 */
@WebServlet("/views/backoffice/migracion")
public class MigracionBackOfficeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(MigracionBackOfficeController.class);

	private final String SQL = "INSERT INTO personas (nombre, empresa, fecha_nacimiento, telefono, email, numero_personal) VALUES (?, ?, ?, ?, ?, ?)";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOG.trace("Inicio");

		String fichero = "C:\\Java\\workspace\\super-alfredo\\personas.txt";
		int numLineas = 0;
		int numInsert = 0;
		int numErrores = 0;
		long tiempoInicio = System.currentTimeMillis();
		long tiempoFin = 0;

		try (Connection conexion = ConnectionManager.getConnection();
				PreparedStatement pst = conexion.prepareStatement(SQL);
				FileReader fr = new FileReader(fichero);
				BufferedReader br = new BufferedReader(fr)) {

			conexion.setAutoCommit(false);

			String linea;
			String[] campos;

			br.readLine(); // Omitimos la primera línea (cabecera)

			while ((linea = br.readLine()) != null) {
				numLineas++;
				campos = linea.split(";");

				if (campos.length == 6) {
					pst.setString(1, campos[0]);
					pst.setString(2, campos[1]);
					pst.setString(3, campos[2]);
					pst.setString(4, campos[3]);
					pst.setString(5, campos[4]);
					pst.setString(6, campos[5]);

					pst.executeUpdate();
					numInsert++;

				} else {
					numErrores++;
				}
			}

			conexion.commit();

		} catch (Exception e) {
			LOG.error(e);

		} finally {
			request.setAttribute("fichero", fichero);
			request.setAttribute("personas_leidas", numLineas);
			request.setAttribute("personas_insertadas", numInsert);
			request.setAttribute("personas_erroneas", numErrores);

			tiempoFin = System.currentTimeMillis();

			request.setAttribute("tiempo", tiempoFin - tiempoInicio);

			request.getRequestDispatcher("resumen-migracion.jsp").forward(request, response);
		}

		LOG.trace("Inicio");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
