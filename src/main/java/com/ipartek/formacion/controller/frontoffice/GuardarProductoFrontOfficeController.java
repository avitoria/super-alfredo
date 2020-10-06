package com.ipartek.formacion.controller.frontoffice;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.ipartek.formacion.controller.Alerta;
import com.ipartek.formacion.modelo.dao.impl.ProductoDAOImpl;
import com.ipartek.formacion.modelo.pojo.Categoria;
import com.ipartek.formacion.modelo.pojo.Producto;
import com.ipartek.formacion.modelo.pojo.Usuario;
import com.ipartek.formacion.seguridad.SeguridadException;

/**
 * Servlet implementation class GuardarProductoFrontOfficeController
 */
@WebServlet("/views/frontoffice/guardar-producto")
@MultipartConfig
public class GuardarProductoFrontOfficeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getLogger(GuardarProductoFrontOfficeController.class);
	private final static ProductoDAOImpl daoProducto = ProductoDAOImpl.getInstance();
	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Se crea un nuevo con id = 0 (ver constructor)
		Producto producto = new Producto();
		Usuario usuario = null;
		String view = "formulario.jsp";

		int idProducto = Integer.parseInt(request.getParameter("id"));

		HttpSession session = request.getSession();
		usuario = (Usuario) session.getAttribute("usuario_login");
		int idUsuario = usuario.getId();

		try {
			if (idProducto != 0) {
				producto = daoProducto.checkSeguridad(idProducto, idUsuario);
			}

		} catch (SeguridadException e) {
			// Le devolvemos al inicio
			view = "/views/frontoffice/inicio";
			LOG.error("El usuario " + idUsuario + " ha intentado manipular la URL.");

		} catch (Exception e) {
			LOG.error(e);

		} finally {
			request.setAttribute("producto", producto);
			request.getRequestDispatcher(view).forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Producto producto = new Producto();
		Usuario usuario = null;
		Alerta alerta = null;
		String view = "";

		// Recuperamos el usuario guardado en la sesión
		HttpSession session = request.getSession();
		usuario = (Usuario) session.getAttribute("usuario_login");
		int idUsuario = usuario.getId();

		// Recogemos los campos del formulario
		// Casca si estamos creando un nuevo producto, porque no hay id
		int idProducto = Integer.parseInt(request.getParameter("id"));
		String nombre = request.getParameter("nombre");
		float precio = Float.parseFloat(request.getParameter("precio"));
		// String imagen = request.getParameter("imagen");
		Part filePart = request.getPart("fichero");
		int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

		try {
			// Si el producto existe (id != 0), comprobamos si le pertenece al usuario
			if (idProducto != 0) {
				// Si el producto no pertenece al usario, se lanza una excepción
				// producto = daoProducto.checkSeguridad(idProducto, idUsuario);
				daoProducto.checkSeguridad(idProducto, idUsuario);
			}

			producto = new Producto();
			producto.setId(idProducto);
			producto.setNombre(nombre);
			String nomFichero = filePart.getSubmittedFileName();
			producto.setImagen("imagenes/" + nomFichero);
			producto.setPrecio(precio);
			producto.setCategoria(new Categoria(idCategoria));
			producto.setUsuario(usuario);

			// Validamos el POJO
			Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

			if (violations.isEmpty()) {
				if (idProducto == 0) {
					// Si el id es 0, insertamos un nuevo producto
					daoProducto.insert(producto);
					alerta = new Alerta("success",
							"Producto creado correctamente. El producto será visible una vez sea validado.");

				} else {
					// Si el id es distinto de 0, actualizamos el producto
					daoProducto.updateByUser(producto);
					alerta = new Alerta("success",
							"Producto actualizado correctamente. Los cambios serán visibles una vez sean validados.");
				}

				// Después de insertar o actualizar, devolvemos al usuario al inicio
				view = "/views/frontoffice/inicio";

			} else {
				String errores = "";

				for (ConstraintViolation<Producto> v : violations) {
					errores += "<p><b>" + v.getPropertyPath() + "</b>: " + v.getMessage() + "</p>";
				}

				alerta = new Alerta("warning", errores);

				// Devolvemos al usuario al formulario para que corrija los errores
				view = "formulario.jsp";
			}

		} catch (SeguridadException e) {
			LOG.error("El usuario " + idUsuario + " ha intentado manipular la URL.");

			// Devolvemos al usuario al inicio, sin darle ningún mensaje (alerta)
			view = "/views/frontoffice/inicio";

		} catch (Exception e) {
			LOG.error(e);
			alerta = new Alerta("warning", "ERROR: ya existe un producto con ese nombre.");

			// Devolvemos al usuario al formulario para que ponga otro nombre
			view = "formulario.jsp";

		} finally {
			request.setAttribute("producto", producto);
			request.setAttribute("alerta", alerta);
			request.getRequestDispatcher(view).forward(request, response);
		}

	}

}
