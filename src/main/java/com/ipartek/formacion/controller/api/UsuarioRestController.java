package com.ipartek.formacion.controller.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.modelo.dao.impl.UsuarioDAOImpl;

/**
 * Servlet implementation class UsuarioRestController
 */
@WebServlet("/api/usuario/*")
public class UsuarioRestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(UsuarioRestController.class);
	private static UsuarioDAOImpl dao = UsuarioDAOImpl.getInstance();
	private int statusCode;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			String nombre = request.getParameter("nombre");

			if (dao.getByName(nombre)) {
				statusCode = HttpServletResponse.SC_OK;

			} else {
				statusCode = HttpServletResponse.SC_NO_CONTENT;
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

		} finally {
			response.setStatus(statusCode);
		}

	}

}
