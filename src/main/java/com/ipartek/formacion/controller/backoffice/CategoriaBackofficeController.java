package com.ipartek.formacion.controller.backoffice;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.ipartek.formacion.controller.Alerta;
import com.ipartek.formacion.modelo.dao.impl.CategoriaDAOImpl;
import com.ipartek.formacion.modelo.pojo.Categoria;

/**
 * Servlet implementation class CategoriaBackofficeController
 */
@WebServlet("/views/backoffice/categoria")
public class CategoriaBackofficeController extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(CategoriaBackofficeController.class);
	private static final long serialVersionUID = 1L;
	private static final CategoriaDAOImpl dao = CategoriaDAOImpl.getInstance();
	private static final String VIEW_LISTA = "categoria/index.jsp";
	private static final String VIEW_FORM = "categoria/formulario.jsp";

	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOG.trace("Listado categorias");

		String idParam = request.getParameter("id");
		String accionParam = request.getParameter("accion");
		String forward = VIEW_LISTA;

		try {
			if (idParam == null) {
				// Listar
				// ArrayList<Categoria> listado = dao.getAll();
				// request.setAttribute("categorias", listado);
				request.setAttribute("categorias", dao.getAll());

			} else if (accionParam != null) {
				// Eliminar
				int id = Integer.parseInt(idParam);

				try {
					dao.delete(id);
					request.setAttribute("alerta", new Alerta("success", "Categoría eliminada correctamente."));

				} catch (Exception e) {
					request.setAttribute("alerta", new Alerta("success",
							"No se ha podido eliminar la categoría, ya que tiene productos asociados."));
				}

				request.setAttribute("categorias", dao.getAll()); // repetido

			} else {
				// Modificar
				Categoria categoria = null;
				int id = Integer.parseInt(idParam); // repetido

				if (id != 0) {
					categoria = dao.getById(id);
				}

				request.setAttribute("categoria", categoria);
				forward = VIEW_FORM;
			}

		} catch (Exception e) {
			LOG.error(e);

		} finally {
			// como el controlador escucha en la url "/views/backoffice/categoria"
			// para hacer el forward pierde la utima parte de la url "categoria" y debemos
			// añadir la caroeta donde esta la index de las categorias
			// /views/backoffice/ + categoria/index.jsp
			request.getRequestDispatcher("categoria/index.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOG.trace("envian datos desde un formulario");

		String pId = request.getParameter("id");
		String pNombre = request.getParameter("nombre");
		Categoria cat = null;
		Alerta alerta = null;

		try {
			int id = Integer.parseInt(pId);

			cat = new Categoria();
			cat.setId(id);
			cat.setNombre(pNombre);

			Set<ConstraintViolation<Categoria>> violations = validator.validate(cat);

			if (violations.isEmpty()) {
				try {
					if (id > 0) {
						dao.update(cat);

					} else {
						dao.insert(cat);
					}

					alerta = new Alerta("success", "Categoria creada correctamente.");

				} catch (Exception e) {
					alerta = new Alerta("warning", "El nombre de la categoria ya existe, por favor elija otro.");
				}

			} else {
				alerta = new Alerta("warning", "Los datos introducidos no son correctos.");
			}

		} catch (Exception e) {
			LOG.error(e);
			alerta = new Alerta("danger", "Ha habido un error. Por favor, inténtelo de nuevo más tade.");

		} finally {
			request.setAttribute("categoria", cat);
			request.setAttribute("alerta", alerta);
			request.getRequestDispatcher(VIEW_FORM).forward(request, response);
		}

	}

}
