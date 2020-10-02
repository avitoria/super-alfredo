package com.ipartek.formacion.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.modelo.dao.impl.UsuarioDAOImpl;
import com.ipartek.formacion.modelo.pojo.Usuario;

/**
 * Servlet implementation class RegistroUsuarioController
 */
@WebServlet("/registro-usuario")
public class RegistroUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static UsuarioDAOImpl dao = UsuarioDAOImpl.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistroUsuarioController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Alerta alerta = null;
		String url = "views/usuarios/form-usuario.jsp";

		try {
			String nombre = request.getParameter("nombre");
			String password = request.getParameter("pass");
			String rePassword = request.getParameter("repass");

			if (password.equalsIgnoreCase(rePassword)) {

				Usuario usuario = new Usuario();
				usuario.setNombre(nombre);
				usuario.setContrasenia(password);

				dao.insert(usuario);

				alerta = new Alerta("success", "Usuario registrado correctamente.");
				url = "views/login.jsp";

			} else {
				alerta = new Alerta("danger", "Las contraseñas introducidas no coinciden.");
			}

		} catch (Exception e) {
			alerta = new Alerta("danger", "Error: no se ha podido registrar el usuario.");
			e.printStackTrace();

		} finally {
			request.setAttribute("alerta", alerta);
			request.getRequestDispatcher(url).forward(request, response);
		}

	}

}
