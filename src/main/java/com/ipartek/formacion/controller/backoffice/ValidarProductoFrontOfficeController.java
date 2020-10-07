package com.ipartek.formacion.controller.backoffice;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.controller.Alerta;
import com.ipartek.formacion.modelo.dao.impl.ProductoDAOImpl;
import com.ipartek.formacion.modelo.pojo.Producto;

/**
 * Servlet implementation class ValidarProductoFrontOfficeController
 */
@WebServlet("/views/backoffice/validar-producto")
public class ValidarProductoFrontOfficeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// private final static Logger LOG =
	// Logger.getLogger(ValidarProductoFrontOfficeController.class);
	private final static ProductoDAOImpl dao = ProductoDAOImpl.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Alerta alerta = null;
		String idParam = request.getParameter("id");

		if (idParam != null) {
			try {
				dao.validar(Integer.parseInt(idParam));
				alerta = new Alerta("success", "El producto " + idParam + " ha sido validado correctamente.");

			} catch (Exception e) {
				alerta = new Alerta("danger", "No se ha podido validar el producto " + idParam + ".");
			}
		}

		// TODO: controlar excepciones
		ArrayList<Producto> productos = dao.getAllSinValidar();

		request.setAttribute("productos", productos);
		request.setAttribute("alerta", alerta);
		request.getRequestDispatcher("validacion-prod.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
