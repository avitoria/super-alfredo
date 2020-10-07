/**
 * Controlador que gestiona las altas y actualizaciones de los productos
 * 
 * @author alfredo
 * @version 1.0
 * 
 */

package com.ipartek.formacion.controller;

import java.io.IOException;
import java.sql.SQLException;
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

import com.ipartek.formacion.modelo.dao.impl.CategoriaDAOImpl;
import com.ipartek.formacion.modelo.dao.impl.ProductoDAOImpl;
import com.ipartek.formacion.modelo.pojo.Categoria;
import com.ipartek.formacion.modelo.pojo.Producto;

/**
 * Servlet implementation class ProductoCrearController
 */
@WebServlet("/producto")
public class ProductoGuardarController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static ProductoDAOImpl daoProducto = ProductoDAOImpl.getInstance();
	private static CategoriaDAOImpl daoCategoria = CategoriaDAOImpl.getInstance();

	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();

	/**
	 * Obtiene el producto que queremos modificar y carga sus datos en el formulario
	 * 
	 * Atributos: id del producto Parámetros: producto encontrado
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String parametroId = request.getParameter("id");
			Producto producto = new Producto();

			if (parametroId != null) {

				int id = Integer.parseInt(parametroId);
				ProductoDAOImpl dao = ProductoDAOImpl.getInstance();
				producto = dao.getById(id);
			}

			request.setAttribute("producto", producto);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			request.setAttribute("categorias", daoCategoria.getAll());
			request.getRequestDispatcher("views/productos/formulario.jsp").forward(request, response);
		}

	}

	/**
	 * Recoge los datos del formulario de alta/actualización. Si el id es 0, se
	 * procesa el alta. Si es distinto de 0, se procesa la actualización.
	 * 
	 * Parámetros: campos del formulario (id, nombre, precio, imagen, categoria_id)
	 * Atributos: alerta que muestra el resultado de la operación; producto
	 * creado/actualizado
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Alerta alerta = new Alerta();
		Producto producto = new Producto();

		try {
			String idParametro = request.getParameter("id");
			String nombre = request.getParameter("nombre");
			String precio = request.getParameter("precio");
			String imagen = request.getParameter("imagen");
			String categoriaId = request.getParameter("categoria_id");

			int id = Integer.parseInt(idParametro);
			int idCategoria = Integer.parseInt(categoriaId);
			float precioFloat = Float.parseFloat(precio);

			producto.setId(id);
			producto.setNombre(nombre);
			producto.setImagen(imagen);
			producto.setPrecio(precioFloat);

			Categoria c = new Categoria();
			c.setId(idCategoria);
			producto.setCategoria(c);

			Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

			if (violations.isEmpty()) {
				// No hay errores de validación. Realizamos el alta o la actualización.
				if (id == 0) {
					daoProducto.insert(producto);

				} else {
					daoProducto.update(producto);
				}

				alerta = new Alerta("success", "Producto guardado con exito");

			} else {
				// Hay errores de validación
				String errores = "";

				for (ConstraintViolation<Producto> v : violations) {
					errores += "<p><b>" + v.getPropertyPath() + "</b>: " + v.getMessage() + "</p>";
				}

				alerta = new Alerta("danger", errores);
			}

		} catch (SQLException e) {
			alerta = new Alerta("danger",
					"Ya existe un producto con ese nombre. Por favor, elija un nombre diferente.");
			e.printStackTrace();

		} catch (Exception e) {
			alerta = new Alerta("danger", "Se ha producido un error. Por favor, vuelva a intentarlo más tade.");
			e.printStackTrace();

		} finally {
			request.setAttribute("alerta", alerta);
			request.setAttribute("producto", producto);
			request.setAttribute("categorias", daoCategoria.getAll());

			request.getRequestDispatcher("views/productos/formulario.jsp").forward(request, response);
		}

	}

}
