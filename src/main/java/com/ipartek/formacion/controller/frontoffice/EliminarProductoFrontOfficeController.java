package com.ipartek.formacion.controller.frontoffice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ipartek.formacion.controller.Alerta;
import com.ipartek.formacion.modelo.dao.impl.ProductoDAOImpl;
import com.ipartek.formacion.modelo.pojo.Producto;
import com.ipartek.formacion.modelo.pojo.Usuario;
import com.ipartek.formacion.seguridad.SeguridadException;

/**
 * Servlet implementation class EliminarProductoFrontOfficeController
 */
@WebServlet("/views/frontoffice/eliminar-producto")
public class EliminarProductoFrontOfficeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(GuardarProductoFrontOfficeController.class);
	private final static ProductoDAOImpl daoProducto = ProductoDAOImpl.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Producto p = null;
		Alerta alerta = null;

		int idProducto = Integer.parseInt(request.getParameter("id"));

		HttpSession sesion = request.getSession();
		Usuario usuario = (Usuario) sesion.getAttribute("usuario_login");
		int idUsuario = usuario.getId();

		try {
			// Comprobamos si el producto pertenece al usuario
			daoProducto.checkSeguridad(idProducto, idUsuario);

			// Si hemos llegado hasta aquí, borramos el producto
			p = daoProducto.delete(idProducto);
			alerta = new Alerta("success", "Producto " + p.getNombre() + " eliminado correctamente.");

		} catch (SeguridadException e) {
			LOG.error("Intento de eliminar un producto sin permisos.");

		} catch (Exception e) {
			LOG.error(e);
			alerta = new Alerta("danger",
					"Error inesperado. No se ha podido eliminar el producto " + p.getNombre() + ".");

		} finally {
			request.setAttribute("alerta", alerta);
			request.getRequestDispatcher("/views/frontoffice/inicio").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
